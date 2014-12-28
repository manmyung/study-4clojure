;; 4Clojure Question 34
;;
;; Write a function which creates a list of all integers in a given range.
;;
;; Restrictions (please don't use these function(s)): range
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(take (- %2 %1) (iterate inc %1)) 1 4) '(1 2 3))

(= (#(take (- %2 %1) (iterate inc %1)) -2 2) '(-2 -1 0 1))

(= (__ 5 8) '(5 6 7))

#(take (- %2 %1) (iterate inc %1))
;우와 Max와 답이 같다.