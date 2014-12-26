;; 4Clojure Question 27
;;
;; Write a function which returns true if the given sequence is a palindrome.<br/><br>
;;
;; Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)
;;
;; Use M-x 4clojure-check-answers when you're done!

(false? (#(= (seq %) (reverse %)) '(1 2 3 4 5)))

(true? (__ "racecar"))

(true? (__ [:foo :bar :foo]))

(true? (__ '(1 1 3 3 1 1)))

(false? (__ '(:a :b :c)))

((reverse "racecar")