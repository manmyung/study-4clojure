;; 4Clojure Question 37
;;
;; Regex patterns are supported with a special reader macro.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

(re-seq #"[A-Z]+" "bA1B3Ce ") ;("A" "B" "C")