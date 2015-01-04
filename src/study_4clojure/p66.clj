;; 4Clojure Question 66
;;
;; Given two integers, write a function which
;;
;; returns the greatest common divisor.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (
     (fn gcd [a b]
       (let [x (max a b)
             y (min a b)]
         (if (= 0 y)
           x
           (gcd b (rem a b))))) 2 4) 2)

(= (__ 10 5) 5)

(= (__ 5 7) 1)

(= (__ 1023 858) 33)

(fn gcd [a b]
  (let [x (max a b)
        y (min a b)]
    (if (= 0 y)
      x
      (gcd b (rem a b)))))

;max 이게 제일 좋다. max, min이 rem의 특성에 의해 없어도 된다.
(fn g [a b] (if (= b 0) a (g b (rem a b))))
(rem 5 7) ;=> 5

(mod 5 7) ;=> 5 이건 정의를 보니까 rem에 minus infinity에 대한 처리를 추가해 준것이므로 가능하면 rem을 사용하는 것이 낫겠다.