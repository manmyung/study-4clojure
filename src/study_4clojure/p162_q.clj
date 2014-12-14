;; 4Clojure Question 162
;;
;; In Clojure, only nil and false represent the values of logical falsity in conditional tests - anything else is logical truth.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= __ (if-not false 1 0))

(= __ (if-not nil 1 0))

(= __ (if true 1 0))

(= __ (if [] 1 0))

(= __ (if [0] 1 0))

(= __ (if 0 1 0))

(= __ (if 1 1 0))