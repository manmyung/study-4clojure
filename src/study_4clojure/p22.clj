;; 4Clojure Question 22
;;
;; Write a function which returns the total number of elements in a sequence.
;;
;; Restrictions (please don't use these function(s)): count
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(reduce (fn [x _] (inc x)) 0 %) '(1 2 3 3 1)) 5)

(= (__ "Hello World") 11)

(= (__ [[1 2] [3 4] [5 6]]) 3)

(= (__ '(13)) 1)

(= (__ '(:a :b :c)) 3)

(#(reduce (fn [x _] (inc x)) 0 %) [7 4 5 3 4])

;max 이것도 좋지만 약간 꼼수같다.
(reduce #(or (+ % 1) %2) 0 [7 4 5 3 4])

;최종으로 가장 좋다고 생각하는 것
(reduce (fn [x _] (inc x)) 0 [7 4 5 3 4])