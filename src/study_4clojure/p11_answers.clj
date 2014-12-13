;; 4Clojure Question 11
;;
;; When operating on a map, the conj function returns a new map with one or more key-value pairs "added".
;;
;; Use M-x 4clojure-check-answers when you're done!

(= {:a 1, :b 2, :c 3} (conj {:a 1} [:b 2] [:c 3]))

; conj map에 대해서
; list: 불가, vector: 하나의 pair만, map: 여러개의 pair도 가능
(= {:c 3, :d 2, :b 2, :a 1} (conj {:a 1} {:b 2 :d 2} [:c 3]))