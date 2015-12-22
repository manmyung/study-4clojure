;; 4Clojure Question 106
;;
;; Given a pair of numbers, the start and end point, find a path between the two using only three possible operations:<ul>
;;
;; <li>double</li>
;;
;; <li>halve (odd numbers cannot be halved)</li>
;;
;; <li>add 2</li></ul>
;;
;; 
;;
;; Find the shortest path through the "maze". Because there are multiple shortest paths, you must return the length of the shortest path, not the path itself.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 1 (__ 1 1))  ; 1

(= 3 (__ 3 12)) ; 3 6 12

(= 3 (__ 12 3)) ; 12 6 3

(= 3 (__ 5 9))  ; 5 7 9

(= 9 (__ 9 2))  ; 9 18 20 10 12 6 8 4 2

(= 5 (__ 9 12)) ; 9 11 22 24 12

(
(fn bfs [reminder]
  (let [f (peek reminder)
        next [(* 2 f) (+ 2 f)]]
    (println f)
    (if (= f 24)
      nil
      (cons f (bfs
                (into (pop reminder) next))))))
  (conj (clojure.lang.PersistentQueue/EMPTY) 3))

(cons 2 3)

(defn seq-graph-bfs [g s]
  ((fn rec-bfs [explored frontier]
     (println [explored frontier])
     (lazy-seq
       (if (empty? frontier)
         nil
         (let [v (peek frontier)
               neighbors (g v)]
           (cons v (rec-bfs
                     (into explored neighbors)
                     (into (pop frontier) (remove explored neighbors))))))))
    #{s} (conj (clojure.lang.PersistentQueue/EMPTY) s)))
(def G {
        :1 [:2 :3],
        :2 [:4],
        :3 [:4],
        :4 [] })
(seq-graph-bfs G :1)

(peek [1 2 3 4])

(peek (conj (conj (clojure.lang.PersistentQueue/EMPTY) 1) 2))

(lazy-seq nil)
