;; 4Clojure Question 17
;;
;; The map function takes two arguments: a function (f) and a sequence (s).  Map returns a new sequence consisting of the result of applying f to each item of s.  Do not confuse the map function with the map data structure.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [6 7 8] (map #(+ % 5) '(1 2 3)))

;maxim 도 '(6 7 8) 대신 [6 7 8] 사용함. 한 글자 적으니까 vector가 더 나을듯.
