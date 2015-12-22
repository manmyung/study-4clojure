;; 4Clojure Question 25
;;
;; Write a function which returns only the odd numbers from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #{1 2 3 4 5}) '(1 3 5))

(= (__ [4 2 1 6]) '(1))

(= (__ [2 2 4 6]) '())

(= (__ [1 1 1 3]) '(1 1 1 3))

(filter odd? #{1 2 3 4 5})
;이것도 풀이가 거의 동일

(async/go-loop []
               (println "s"))

(ef/at "#button1" (ev/listen :click
                             #()
