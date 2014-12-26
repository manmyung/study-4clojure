;; 4Clojure Question 32
;;
;; Write a function which duplicates each element of a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(interleave % %) [1 2 3]) '(1 1 2 2 3 3))

(= (__ [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))

(= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

(= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;내가 쓴 답.
( #(interleave % % ) [1 2 3])

(take 5 (repeat "5"))

(+ 1 2)

;Max 이게 더 나은 것은 아니다.
(mapcat #(list % %) [1 2 3])

;netpyoung: partial 사용의 좋은 예인듯. 여기에 다시 #(...)를 사용할 수는 없으므로.
#(mapcat (partial repeat 2) %)


