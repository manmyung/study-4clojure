;; 4Clojure Question 44
;;
;; Write a function which can rotate a sequence in either direction.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 2 [1 2 3 4 5]) '(3 4 5 1 2))

(= (__ -2 [1 2 3 4 5]) '(4 5 1 2 3))

(= (__ 6 [1 2 3 4 5]) '(2 3 4 5 1))

(= (__ 1 '(:a :b :c)) '(:b :c :a))

(= (__ -4 '(:a :b :c)) '(:c :a :b))

(take 2 [1 2 3 4 5]) ;=> (1 2)
(drop 2 [1 2 3 4 5]) ;=> (3 4 5)
(mod -2 5)

;me. %1, %2가 여러개 나올 때 fn을 약간이나마 간단.
#(let [i (mod %1 (count %2))]
  (concat (drop i %2) (take i %2)))

;ming. split-at 활용예.
#(apply concat (reverse (split-at (mod % (count %2)) %2)))

;hypirion. juxt 활용예.
(fn [n coll]
  (apply concat ((juxt drop take) (mod n (count coll)) coll)))

;chouser. cycle 활용예.
#(let [c (count %2)] (take c (drop (mod % c) (cycle %2))))
