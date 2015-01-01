;; 4Clojure Question 40
;;
;; Write a function which separates the items of a sequence by an arbitrary value.
;;
;; Restrictions (please don't use these function(s)): interpose
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(rest (interleave (repeat %1) %2)) 0 [1 2 3]) [1 0 2 0 3])

(= (apply str (__ ", " ["one" "two" "three"])) "one, two, three")

(= (__ :z [:a :b :c :d]) [:a :z :b :z :c :z :d])

;지금
(#(rest (interleave (repeat %1) %2)) 0 [1 2 3])

;이전
#(drop-last (interleave %2 (repeat %1)))

;max. butlast는 마지막 하나뺀 것이고 drop-last는 마지막 n개 빼는 것이다. 이 경우는 butlast가 더 낫다.
#(butlast (interleave %2 (repeat %1)))
