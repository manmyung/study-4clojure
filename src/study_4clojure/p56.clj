;; 4Clojure Question 56
;;
;; Write a function which removes the duplicates from a sequence. Order of the items must be maintained.
;;
;; Restrictions (please don't use these function(s)): distinct
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 2 1 3 1 2 4]) [1 2 3 4])

(= (__ [:a :a :b :b :c :c]) [:a :b :c])

(= (__ '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3]))

(= (__ (range 50)) (range 50))

;처음시도. 틀린답. group-by는 values의 순서는 보장하지만 keys의 순서를 보존하지 않는다.
#(keys (group-by identity %))

;두번째 시도. 틀린답. sorted-set는 들어오는 순서를 보존하는 것이 아니라 크기 순서를 보장한다.
#(apply vector (apply sorted-set %))

;me.
reduce #(if ((set %1) %2) %1 (conj %1 %2)) []

;silverio. set 대신 some을 이용할 수도 있다.
(partial reduce
         #(if (some (partial = %2) %) % (conj % %2))
         [])