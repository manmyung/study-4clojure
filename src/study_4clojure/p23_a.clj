;; 4Clojure Question 23
;;
;; Write a function which reverses a sequence.
;;
;; Restrictions (please don't use these function(s)): reverse, rseq
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (reduce #(cons %2 %) [] [1 2 3 4 5]) [5 4 3 2 1])

(= (__ (sorted-set 5 7 2 7)) '(7 5 2))

(= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])

;이것이 더 좋겠다.
(reduce conj () [1 2 3 4 5])

;Max:  Returns a new coll consisting of to-coll with all of the items of from-coll conjoined. 정의에 conj 가 포함되어 있으므로 이게 가장 좋다.
(into () [1 2 3 4 5])