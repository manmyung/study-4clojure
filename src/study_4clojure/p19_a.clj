;; 4Clojure Question 19
;;
;; Write a function which returns the last element in a sequence.
;;
;; Restrictions (please don't use these function(s)): last
;;
;; Use M-x 4clojure-check-answers when you're done!


(= (#(first (reverse %)) [1 2 3 4 5]) 5)

(= (__ '(5 4 3)) 3)

(= (__ ["b" "c" "d"]) "d")

;max 이게 계산은 많겠지만 로직이 단순하네.
#(-> % reverse first)

;이것도 기억해둘만하다.
#(peek(vec %))
