;; 4Clojure Question 13
;;
;; The rest function will return all the items of a sequence except the first.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= '(20 30 40) (rest [10 20 30 40]))

(rest [10 20 30 40]) ;=>(20 30 40) 벡터의 경우 list 로 나오네.
(rest {10 20 30 40 50 60});=>([30 40] [10 20]) 맵의 경우 벡터의 list로 나오네.