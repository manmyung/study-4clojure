;; 4Clojure Question 117
;;
;; A mad scientist with tenure has created an experiment tracking mice in a maze.  Several mazes have been randomly generated, and you've been tasked with writing a program to determine the mazes in which it's possible for the mouse to reach the cheesy endpoint.  Write a function which accepts a maze in the form of a collection of rows, each row is a string where:
;;
;; <ul>
;;
;; <li>spaces represent areas where the mouse can walk freely</li>
;;
;; <li>hashes (#) represent walls where the mouse can not walk</li>
;;
;; <li>M represents the mouse's starting point</li>
;;
;; <li>C represents the cheese which the mouse must reach</li>
;;
;; </ul>
;;
;; The mouse is not allowed to travel diagonally in the maze (only up/down/left/right), nor can he escape the edge of the maze.  Your function must return true iff the maze is solvable by the mouse.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true  (__ ["M   C"]))

(= false (__ ["M # C"]))

(= true  (__ ["#######"
              "#     #"
              "#  #  #"
              "#M # C#"
              "#######"]))

(= false (__ ["########"
              "#M  #  #"
              "#   #  #"
              "# # #  #"
              "#   #  #"
              "#  #   #"
              "#  # # #"
              "#  #   #"
              "#  #  C#"
              "########"]))

(= false (__ ["M     "
              "      "
              "      "
              "      "
              "    ##"
              "    #C"]))

(= true  (__ ["C######"
              " #     "
              " #   # "
              " #   #M"
              "     # "]))

(= true  (__ ["C# # # #"
              "        "
              "# # # # "
              "        "
              " # # # #"
              "        "
              "# # # #M"]))

;me
(fn [maze]
  (let [m
        (reduce #(conj %1 [%2 (get-in maze %2)]) {}
                (for [i (range (count maze))
                      j (range (count (first maze)))]
                  [i j]))
        next-p
        (fn [[i j] prev-ps]
          (filter #(and (not (prev-ps %))
                        (or (= \space (m %)) (= \C (m %))))
                  [[(inc i) j] [(dec i) j] [i (inc j)] [i (dec j)]]))
        search
        (fn search [ps prev-ps]
          (let [next-ps (set (mapcat #(next-p % prev-ps) ps))]
            (cond
              (empty? next-ps) false
              (some #(= \C (m %)) next-ps) true
              :else
              (search next-ps (into prev-ps ps)))))
        start-p
        (some #(when (= (val %) \M) (key %)) m)]
    (search #{start-p} #{start-p})
    ))

;hypirion
(fn [maze]
  (let [at (fn [x y] (.charAt (maze y) x))
        fnd (fn [v] (some (fn [[x y]] (if (not= -1 x) [x y]))
                          (map-indexed (fn [y s] [(.indexOf s v) y]) maze)))
        C (fnd "C")
        M (fnd "M")
        h (count maze)
        w (count (first maze))
        look (apply merge
                    (for [x (range w), y (range h) :when (not= \# (at x y))]
                      {[x y] (map #(map + [x y] %) [[1 0] [0 1] [-1 0] [0 -1]])}))]
    (loop [hs #{M}]
      (let [n-hs (into hs (mapcat look hs))]
        (if (= n-hs hs)
          (= (hs C) C)
          (recur n-hs))))))
;기존 path는 상관하지 않고 모든 hs에 대해서 계속 확장. 비효율적일 듯.
;더 이상 확장이 안되면 hs에 C가 있는지 확인하여 결론을 냄

;mfike
(fn mouse-cheese [board]
  (letfn [(next-board [board]
            (let [board-matrix (mapv #(vec %) board)]
              (map-indexed
                (fn [r row]
                  (apply str
                         (map-indexed
                           (fn [c cell]
                             (let [adjacent (or
                                              (= \M (get-in board-matrix [(dec r) c]))
                                              (= \M (get-in board-matrix [r (dec c)]))
                                              (= \M (get-in board-matrix [(inc r) c]))
                                              (= \M (get-in board-matrix [r (inc c)])))]
                               (if (and adjacent
                                        (or (= \space cell)
                                            (= \C cell)))
                                 \M
                                 cell)))
                           row)))
                board-matrix)))]
    (not (contains? (set (mapcat seq
                                 (loop [board board]
                                   (let [board' (next-board board)]
                                     (if (= board' board)
                                       board
                                       (recur board')))))) \C))))
;주변에 \M이 있고 \space와 \C 라면 모두 \M으로 바꿈.
;history를 따로 기록하지 않고 board에 표시하는 게 재밌다.
;하지만 seq와 board-matrix를 매번 바꾸는 게 복잡하다.

;silverio
(fn [maze] (let [
                 [w h]     [(count (first maze)) (count maze)]
                 at        (fn [[x y]] (nth (nth maze y) x))
                 in?       (fn [v b] (and (>= v 0) (< v b)))
                 can-walk? (fn [[x y]] (and (in? x w) (in? y h) (not= \# (at [x y]))))
                 dirs      [[-1 0] [1 0] [0 -1] [0 1]]
                 cands     (fn [[i j]] (->> dirs (map (fn [[x y]] [(+ x i) (+ y j)])) (filter can-walk?)))
                 start     (some identity (for [i (range w) j (range h)] (if (= \M (at [i j])) [i j])))]
             (loop [visited #{} to-visit [start]]
               (if-let [v (first to-visit)]
                 (if (= (at v) \C) true
                                   (recur (conj visited v)
                                          (into (rest to-visit) (filter #(not (visited %)) (cands v)))))
                 false))))
;to-visit에서 하나씩 빼서 다음 to-visit를 업데이트 하는 방식. 정석적이라 좋다.

;daowen
(fn cheesable? [maze]
  (let [grid (->>
               maze
               (map-indexed (fn [i row] (map-indexed (fn [j col] [[i j] col]) row)))
               (apply concat)
               (into {}))
        rev-grid (clojure.set/map-invert grid)
        m (rev-grid \M)
        c (rev-grid \C)
        usable? #(#{\space \C} (grid %))
        offsets '([1 0] [0 1] [-1 0] [0 -1])
        neighbors (fn [[x y]] (map (fn [[i j]] [(+ x i) (+ y j)]) offsets))
        graph (into {} (map (fn [[loc val]] [loc (filter usable? (neighbors loc))]) grid))]
    (loop [mq `(~m), mv #{m}]
      (if (empty? mq) false
                      (let [m (first mq), mq (rest mq)]
                        (if (= m c) true
                                    (let [mnext (remove mv (graph m))
                                          mq (concat mnext mq)
                                          mv (into mv mnext)]
                                      (recur mq mv))))))))
;grid를 만드는 방법이 나와 비교해서 미리 w, h를 구하지 않아도 되므로 더 일반적이다.
;clojure.set/map-invert는 key와 value를 바꾸는 함수. 내가 가끔 필요해던 것. 알아두자.
;`(~m)은 여기서는 리스트를 만든다. ~m에서 m의 값이 평가되어 들어간다. 즉 m이 그대로 들어가는 '(m)과는 다르다.
;여기도 mq에서 하나씩 빼내서 검사

;chouser
(fn f [a b c]
  (and (not= a c)
       (if (re-seq #"CM|MC" (pr-str c))
         true
         (f b c (apply map str (map #(.replaceAll % " M|M " "MM") c))))))
0
0
;빈 공간을 하나씩 없애나가는 것 같다. 놀랍게 짧지만 일반적이지 않은 것 같아 답만 적어둔다.


