;; 4Clojure Question 45
;;
;; The iterate function can be used to produce an infinite lazy sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [1 4 7 10 13] (take 5 (iterate #(+ 3 %) 1)))

;등차수열은 iterate로 만들 수 있구나.