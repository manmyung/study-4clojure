;; 4Clojure Question 31
;;
;; Write a function which packs consecutive duplicates into sub-lists.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (partition-by identity [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))

(= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))

(= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

;이건 뭐 이 답이 최선