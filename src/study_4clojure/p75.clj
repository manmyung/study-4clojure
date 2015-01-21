;; 4Clojure Question 75
;;
;; Two numbers are coprime if their greatest common divisor equals 1.  Euler's totient function f(x) is defined as the number of positive integers less than x which are coprime to x.  The special case f(1) equals 1.  Write a function which calculates Euler's totient function.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1) 1)

(= (__ 10) (count '(1 3 7 9)) 4)

(= (__ 40) 16)

(= (__ 99) 60)

;p66에서 가져온 것
(defn gcd [a b] (if (= b 0) a (gcd b (rem a b))))

;me. psk810과 동일.
(fn [n]
  (letfn [(gcd [a b]
               (if (= b 0) a (gcd b (rem a b))))]
    (count (filter #(= 1 (gcd n %)) (range 1 (inc n))))))
