;; 4Clojure Question 161
;;
;; Set A is a subset of set B, or equivalently B is a superset of A, if A is "contained" inside B. A and B may coincide.
;;
;; Use M-x 4clojure-check-answers when you're done!

(clojure.set/superset? #{1 2} #{2})

(clojure.set/subset? #{1} #{1 2})

(clojure.set/superset? #{1 2} #{1 2})

(clojure.set/subset? #{1 2} #{1 2})

;이건 superset?, subset? 의미를 알면 쉽다. 이런게 있다는 것을 알려주려고 만든 문제.