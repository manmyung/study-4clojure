;; 4Clojure Question 124
;;
;; <p><a href="http://en.wikipedia.org/wiki/Reversi">Reversi</a> is normally played on an 8 by 8 board. In this problem, a 4 by 4 board is represented as a two-dimensional vector with black, white, and empty pieces represented by 'b, 'w, and 'e, respectively. Create a function that accepts a game board and color as arguments, and returns a map of legal moves for that color. Each key should be the coordinates of a legal move, and its value a set of the coordinates of the pieces flipped by that move.</p>
;;
;; 
;;
;; <p>Board coordinates should be as in calls to get-in. For example, <code>[0 1]</code> is the topmost row, second column from the left.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= {[1 3] #{[1 2]}, [0 2] #{[1 2]}, [3 1] #{[2 1]}, [2 0] #{[2 1]}}
   (__ '[[e e e e]
         [e w b e]
         [e b w e]
         [e e e e]] 'w))

(= {[3 2] #{[2 2]}, [3 0] #{[2 1]}, [1 0] #{[1 1]}}
   (__ '[[e e e e]
         [e w b e]
         [w w w e]
         [e e e e]] 'b))

(= {[0 3] #{[1 2]}, [1 3] #{[1 2]}, [3 3] #{[2 2]}, [2 3] #{[2 2]}}
   (__ '[[e e e e]
         [e w b e]
         [w w b e]
         [e e b e]] 'w))

(= {[0 3] #{[2 1] [1 2]}, [1 3] #{[1 2]}, [2 3] #{[2 1] [2 2]}}
   (__ '[[e e w e]
         [b b w e]
         [b w w e]
         [b w w w]] 'b))

;me
(fn [b mc]
  (let [oc (if (= 'b mc) 'w 'b)
        ps (fn [c]
             (for [x (range 4)
                   y (range 4)
                   :when (= c (get-in b [x y]))]
               [x y]))
        e-ps (ps 'e)
        mc-ps (ps mc)
        betw (fn [[x1 y1] [x2 y2]]
               (let [dx (- x2 x1)
                     dy (- y2 y1)
                     l-dx (Math/abs dx)
                     l-dy (Math/abs dy)
                     l-max (max l-dx l-dy)]
                 (when (or (zero? dx)
                      (zero? dy)
                           (= l-dx l-dy))
                   (case l-max
                     2 #{[(+ x1 (/ dx 2)) (+ y1 (/ dy 2))]}
                     3 #{[(+ x1 (/ dx 3)) (+ y1 (/ dy 3))]
                         [(+ x1 (* 2 (/ dx 3))) (+ y1 (* 2 (/ dy 3)))]}
                     nil))))
        betw-ps? (fn [p]
                   (some (fn [x]
                           (let [betw-ps (betw p x)]
                             (when (and betw-ps
                                        (every? #(= oc (get-in b %))
                                                betw-ps))
                               betw-ps)))
                         mc-ps))]
    (reduce (fn [m p]
              (if-let [betw-ps (betw-ps? p)]
                (conj m [p betw-ps])
                m)) {} e-ps)))
;밑의 답은 모두 특정 점에서 8방으로 계속 진행하는 방식인데, 나는 모든 e에 대해 중간 점이 있는지 확인했다. 밑의 답들이 좀 더 일반적이고 간결한 듯.

;maximental
(fn [R b s]
  (reduce (fn [a [k v]] (assoc a k (into v (a k))))
          {}
          (for [d [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]
                p (for [i R j R] [i j])
                :let [f #(map + d %)
                      g #(= % (get-in b %2))
                      v (take-while #(g ('{w b b w} s) %) (iterate f (f p)))
                      k (f (last v))]
                :when (and (g s p) (g 'e k))]
            [k (set v)])))
[0 1 2 3]
;모든 p에 대해서 그 p가 s이고, 8방으로 상대편인 경우 쭉 진행하여 마지막이 :e인 경우를 뽑아낸다. 훌륭하다.
;모든 p와 모든 d를 모두 살펴보려고 할 때 for가 제격.
;쭉 진행은 iterate로 처리.

;chouser
(fn [G b c]
  (reduce (fn [m p]
            (if-let [f (seq (mapcat
                              (fn [d]
                                (let [[f e] (split-with #(not (#{'e c} (G b %)))
                                                        (rest (take-while (fn [[y x]] (and (< -1 y 4) (< -1 x 4)))
                                                                          (iterate #(map + % d) p))))]
                                  (if (and (> (count f) 0)
                                           (= (G b (first e)) c))
                                    f)))
                              (for [y [-1 0 1] x [-1 0 1] :when (not= 0 x y)] [y x])))]
              (assoc m p (set f))
              m))
          {}
          (for [y [0 1 2 3] x [0 1 2 3] :when (= (G b [y x]) 'e)] [y x])))
get-in
;이것도 maximental과 비슷. iterate를 사용.

;daowen
(fn legal-moves [board player]
  (let [to-indexed #(into {} (map-indexed vector %))
        board (to-indexed (map to-indexed board))
        other ('{w b b w} player)
        dim (range 4)
        mt-locs (for [i dim j dim :when (= ((board i) j) 'e)] [i j])
        vecs (for [i [-1 0 1] j [-1 0 1] :when (not= 0 i j)] [i j])
        flip-vec (fn [[r c] [i j]]
                   (loop [r (+ r i), c (+ c j), acc nil]
                     (condp = (get-in board [r c])
                       ; These two should have been the default case,
                       ; but it complained about recur and tail position...
                       nil nil, 'e nil
                       player acc
                       other (recur (+ r i) (+ c j) (cons [r c] acc)))))
        flipped-at (fn [loc] (mapcat #(flip-vec loc %) vecs))
        all-moves (map #(vector % (set (flipped-at %))) mt-locs)]
    (into {} (filter #(seq (second %)) all-moves))))
;여긴 iterate대신 loop를 사용했다.

;hypirion
(fn [b c]
  (let [op '{w b b w}
        f (fn f [p d s]
            (let [p' (map + p d)]
              (condp = (get-in b p')
                (op c) (f p' d (conj s p'))
                c s
                #{})))]
    (into {}
          (for [p (filter #(= 'e (get-in b %))
                          (map (juxt quot mod) (range 16) (repeat 4)))
                fs [(reduce
                      into
                      (for [y [-1 0 1], x [-1 0 1]
                            :when (not (= 0 x y))]
                        (f p [y x] #{})))]
                :when (not (empty? fs))]
            [p fs]))))
;여기서는 iterate대신 f의 재귀를 사용해서 진행.
