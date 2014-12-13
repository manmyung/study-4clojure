;; 4Clojure Question 14
;;
;; Clojure has many different ways to create functions.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 8 ((fn add-five [x] (+ x 5)) 3))

(= 8 ((fn [x] (+ x 5)) 3))

(= 8 (#(+ % 5) 3))

;partial은 왜 필요한지 모르겠다. 위의 #형식에 비해 인자 위치의 일반성도 떨어지고 그렇다고 길이가 짧지도 않고.
(= 8 ((partial + 5) 3))
