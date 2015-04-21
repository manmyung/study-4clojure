;; 4Clojure Question 91
;;
;; Given a graph, determine whether the graph is connected. A connected graph is such that a path exists between any two given nodes.<br/><br/>-Your function must return true if the graph is connected and false otherwise.<br/><br/>-You will be given a set of tuples representing the edges of a graph. Each member of a tuple being a vertex/node in the graph.<br/><br/>-Each edge is undirected (can be traversed either direction).
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true (__ #{[:a :a]}))

(= true (__ #{[:a :b]}))

(= false (__ #{[1 2] [2 3] [3 1]
               [4 5] [5 6] [6 4]}))

(= true (__ #{[1 2] [2 3] [3 1]
              [4 5] [5 6] [6 4] [3 4]}))

(= false (__ #{[:a :b] [:b :c] [:c :d]
               [:x :y] [:d :a] [:b :e]}))

(= true (__ #{[:a :b] [:b :c] [:c :d]
              [:x :y] [:d :a] [:b :e] [:x :a]}))


;me
(fn [s]
  (true?
    ((fn connected? [node-set reminder]
       (or (empty? reminder)
           (some #(when (some (fn [k] (node-set k)) %)
                   (connected? (into node-set %) (disj (set reminder) %))) reminder)))
      (into #{} (first s)) (rest s))))

;max
(fn [g]
  ((fn f [e]
     (#(if (= e %) (= % g) (f %))
       (reduce (fn [a b] (into a (filter #(some (set b) %) g)))
               #{}
               e)))
    #{(first g)}))
;(into #{} (first s)) 대신 #{(first g)}가 좀더 쉽네.
;내용이해는 생략.

;psk810
(fn [s]
  (letfn [(r [t v] (some (fn [[a b]] (= a b)) (for [x t y v] [x y])))
          (getA [t s] (some #(when (r t %) %) s))]
    ((fn f [t s]
       (let [a (getA t s)]
         (if a
           (f (distinct (concat t a)) (disj s a))
           (empty? s))))
      (first s) (disj s (first s)))))

;me의 (rest s) 대신 여기서는 (disj s (first s))를 사용했기 때문에 내부에서 다시 set을 사용할 필요가 없었다.