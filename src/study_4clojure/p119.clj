;; 4Clojure Question 119
;;
;; <p>As in <a href="/problem/73">Problem 73</a>, a tic-tac-toe board is represented by a two dimensional vector. X is represented by :x, O is represented by :o, and empty is represented by :e. Create a function that accepts a game piece and board as arguments, and returns a set (possibly empty) of all valid board placements of the game piece which would result in an immediate win.</p>
;;
;; 
;;
;; <p>Board coordinates should be as in calls to <code>get-in</code>. For example, <code>[0 1]</code> is the topmost row, center position.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ :x [[:o :e :e] 
           [:o :x :o] 
           [:x :x :e]])
   #{[2 2] [0 1] [0 2]})

(= (__ :x [[:x :o :o] 
           [:x :x :e] 
           [:e :o :e]])
   #{[2 2] [1 2] [2 0]})

(= (__ :x [[:x :e :x] 
           [:o :x :o] 
           [:e :o :e]])
   #{[2 2] [0 1] [2 0]})

(= (__ :x [[:x :x :o] 
           [:e :e :e] 
           [:e :e :e]])
   #{})

(= (__ :o [[:x :x :o] 
           [:o :e :o] 
           [:x :e :e]])
   #{[2 2] [1 1]})

;me
(fn [a b]
  (let [win? (fn [t b]
               (let [diag (fn [[[c _ d][_ e _][f _ g]]] [[c e g] [d e f]])
                     lines (concat b (apply map vector b) (diag b))]
                 (some (fn [l] (every? #(= t %) l)) lines)))]
    (set
      (filter #(and
                (= :e (get-in b %))
                (win? a (assoc-in b % a)))
              (for [i (range 3) j (range 3)] [i j])))))

;psk810
(fn [x s]
  (set (filter
         #((fn [v] (some (fn [[a b c]] (or (= :x a b c) (= :o a b c)))
                         (concat v (apply map vector v)
                                 (let [[[a _ b] [_ o _] [c _ d]] v] [[a o d] [b o c]]))))
           (assoc-in s % x))
         (for [i (range 3) j (range 3) :when (= :e (get-in s [i j]))] [i j]))))
;73번 문제에서 psk810의 diag방법을 배웠기 때문에 답이 거의 비슷하다.
;some에서 (fn [[a b c]] ...) 이 방식이 every? 보다 더 단순해서 좋다.

;hypirion
(fn [p board]
  (let [win? #(let [b (concat % (apply map list %)
                              [(map nth % (range)) (map nth (map reverse %) (range))])]
               (some #{[p p p]} b))]
    (set
      (for [y (range 3), x (range 3),
            :when (and (= :e (get-in board [y x]))
                       (win? (assoc-in board [y x] p)))]
        [y x]))))
;diag 만드는 방법으로 [(map nth % (range)) (map nth (map reverse %) (range))] 마음에 든다. nth와 (range) 결헙.
;(some #{[p p p]} b) 이것도 참 좋다. some과 집합으로 어떤 요소가 있는지 판별했다.