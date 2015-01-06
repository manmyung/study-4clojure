;; 4Clojure Question 99
;;
;; Write a function which multiplies two numbers and returns the result as a sequence of its digits.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(reverse(
               (fn k [x]
                 (if (= x 0)
                   '()
                   (cons (rem x 10) (lazy-seq (k (int (/ x 10)))))))
               (* %1 %2))) 1 1) [1])

(= (#((fn k [x]
        (if (= x 0)
          []
          (conj (k (quot x 10)) (rem x 10))))
      (* %1 %2)) 99 9) [8 9 1])

(= (__ 999 99) [9 8 9 0 1])

(rem 423 10)
(int (/ 423 10))
(int (/ 3 10))

;지금
#(reverse(
           (fn k [x]
             (if (= x 0)
               '()
               (conj (lazy-seq (k (int (/ x 10)))) (rem x 10))))
           (* %1 %2)))

;이전
#(letfn
  [(_pd [x]
        (if (= x 0)
          []
          (conj (_pd (quot x 10)) (rem x 10))))]
  (_pd (* %1 %2)))


;이전과 비교해보니 이 경우는 무한으로 나가는게 아니므로 lazy sequence가 필요없다.
;recursive를 이용한다면 이게 최선.
;그리고 몫은 quot라는 것을 기억해두자.
#((fn k [x]
    (if (= x 0)
      []
      (conj (k (quot x 10)) (rem x 10))))
  (* %1 %2))

;psk810. 그러나 str로 변환하는 것을 떠올릴 수 있다면 이렇게 간단.
(fn [x y] (map #(- (int %) (int \0)) (str (* x y))))