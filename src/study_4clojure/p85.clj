;; 4Clojure Question 85
;;
;; Write a function which generates the <a href="http://en.wikipedia.org/wiki/Power_set">power set</a> of a given set.  The power set of a set x is the set of all subsets of x, including the empty set and x itself.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #{1 :a}) #{#{1 :a} #{:a} #{} #{1}})

(= (__ #{}) #{#{}})

(= (__ #{1 2 3})
   #{#{} #{1} #{2} #{3} #{1 2} #{1 3} #{2 3} #{1 2 3}})

(= (count (__ (into #{} (range 10)))) 1024)

;me
(fn [s]
  (reduce
    (fn [prev v]
      (set (mapcat #(set [(conj % v) %]) prev)))
    #{#{}} s))

;max. 나와 비슷하지만 into때문에 훨씬 간결.
(fn [s]
  (reduce
    (fn [a b]
      (into a (map #(conj % b) a)))
    #{#{}}
    s))

;silverio. max와 같지만 partial 사용한 게 특징.
(partial reduce
         #(into % (map (fn[s](conj s %2)) %))
         #{#{}})