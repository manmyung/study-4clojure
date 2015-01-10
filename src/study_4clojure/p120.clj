;; 4Clojure Question 120
;;
;; Write a function which takes a collection of integers as an argument.  Return the count of how many elements are smaller than the sum of their squared component digits.  For example: 10 is larger than 1 squared plus 0 squared; whereas 15 is smaller than 1 squared plus 5 squared.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 8 ((fn [col]
        (letfn [(k [x]
                   (let [r (rem x 10)
                         q (quot x 10)]
                     (+ (* r r) (if (= 0 q)
                                  0
                                  (k q)))))]
          (count (filter #(< % (k %)) col)))) (range 10)))

(= 19 (__ (range 30)))

(= 50 (__ (range 100)))

(= 50 (__ (range 1000)))

(
(fn k [x]
  (let [r (rem x 10)
        q (quot x 10)]
    (+ (* r r) (if (= 0 q)
                 0
                 (k q)))))
  32)

(rem 32 10)
(quot 327 10)
(quot 3 10)

(
(fn [col]
  (letfn [(k [x]
             (let [r (rem x 10)
                   q (quot x 10)]
               (+ (* r r) (if (= 0 q)
                            0
                            (k q)))))]
    (count (filter #(< % (k %)) col))))
  (range 10))

;지금
(fn [col]
  (letfn [(k [x]
             (let [r (rem x 10)
                   q (quot x 10)]
               (+ (* r r) (if (= 0 q)
                            0
                            (k q)))))]
    (count (filter #(< % (k %)) col))))

;이전
(fn [list]
  (count
    (filter
      (fn [x] (<  x
                  (reduce #(+ %1 (* %2 %2)) 0 (map #(- (int %) 48) (str x)))))
      list)))

;max. 이 문제는 (- (int %) 48)을 쓰는게 k를 구현하는 것보다는 약간 더 간단. Math/pow도 기억해두자.
(fn [s]
  (count (filter (fn [n] (< n
                            (apply +
                                   (map #(Math/pow (- (int %) 48) 2)
                                        (str n)))))
                 s)))