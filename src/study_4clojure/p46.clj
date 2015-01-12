;; 4Clojure Question 46
;;
;; Write a higher-order function which flips the order of the arguments of an input function.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 3 ((#(fn [x y]
         (% y x)) nth) 2 [1 2 3 4 5]))

(= true ((__ >) 7 8))

(= 4 ((__ quot) 2 8))

(= [1 2 3] ((__ take) [1 2 3 4 5] 3))

#(fn [x y]
  (% y x))
;이건 대부분 답이 비슷.