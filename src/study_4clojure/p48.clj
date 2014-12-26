;; 4Clojure Question 48
;;
;; The some function takes a predicate function and a collection.  It returns the first logical true value of (predicate x) where x is an item in the collection.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= __ (some #{2 7 6} [5 6 7 8]))

(= __ (some #(when (even? %) %) [5 6 7 8]))

(some #{2 7 6} [5 6 7 8])

(#{2 7 6} 5) ;=>nil
(#{2 7 6} 6) ;=>6

;some은 첫번째 nil이 아닌 return 값이다.