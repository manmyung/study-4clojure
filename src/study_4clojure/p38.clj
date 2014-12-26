;; 4Clojure Question 38
;;
;; Write a function which takes a variable number of parameters and returns the maximum value.
;;
;; Restrictions (please don't use these function(s)): max, max-key
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1 8 3 4) 8)

(= (__ 30 20) 30)

(= (__ 45 67 11) 67)

;새로운 답
(= (
     (fn [& x] (reduce #(if (> %1 %2) %1 %2) x))
     1 8 3 4) 8)

;이전 답. 간단한 대신 계산이 더 많이 수행된다.
(fn [& x] (last (sort x)))

;Max. 여기서 배울 점은 list로 바꾸기 위해서 %& 를 하면 된다는 것.
#(last (sort %&))

;그렇다면 이게 효율도 좋고 코드도 깔금. 최종
;chouser's solution:
#(reduce (fn [a b] (if (> a b) a b)) %&)