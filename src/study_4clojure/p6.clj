;; 4Clojure Question 6
;;
;; Vectors can be constructed several ways.  You can compare them with lists.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [__] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

;리스트와 벡터는 = 에서 같다고 판단하는 구나.
(= '(:a :b :c) (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))
