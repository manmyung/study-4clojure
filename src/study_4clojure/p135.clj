;; 4Clojure Question 135
;;
;; Your friend Joe is always whining about Lisps using the prefix notation for math. Show him how you could easily write a function that does math using the infix notation. Is your favorite language that flexible, Joe?
;;
;; 
;;
;; Write a function that accepts a variable length mathematical expression consisting of numbers and the operations +, -, *, and /. Assume a simple calculator that does not do precedence and instead just calculates left to right.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 7  ((fn [x & xs]
         (reduce #((first %2) % (second %2)) x (partition 2 xs))) 2 + 5))

(= 42 ((fn [x & xs]
         (reduce #((first %2) % (second %2)) x (partition 2 xs))) 38 + 48 - 2 / 2))

(= 8  (__ 10 / 2 - 1 * 2))

(= 72 (__ 20 / 2 + 2 + 4 + 8 - 6 - 10 * 9))

;지금. reduce의 초기값을 이용하는 지금이 더 간결
(fn [x & xs]
  (reduce #((first %2) % (second %2)) x (partition 2 xs)))

;이전
(fn [x & y] (reduce #((first %2) %1 (last %2)) (conj (partition 2 y) x)))

;hypirion. deconstructuring 기억해둘만 하다.
(fn [a & r]
  (reduce
    (fn [a [op b]] (op a b))
    a (partition 2 r)))
