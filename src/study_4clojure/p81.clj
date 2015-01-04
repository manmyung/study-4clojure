;; 4Clojure Question 81
;;
;; Write a function which returns the intersection of two sets.  The intersection is the sub-set of items that each set has in common.
;;
;; Restrictions (please don't use these function(s)): intersection
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(set (filter (partial get %1) %2)) #{0 1 2 3} #{2 3 4 5}) #{2 3})

(= (#(set (filter (partial get %1) %2)) #{0 1 2} #{3 4 5}) #{})

(= (__ #{:a :b :c :d} #{:c :e :a :f :d}) #{:a :c :d})

(#(set (filter (partial get %1) %2)) #{0 1 2 3} #{2 3 4 5})

;이전
(fn [s1 s2] (set (filter #(get s2 %) s1)))

;max. 그렇구나. 집합 자체도 함수라는 것을 잊고 있었다.
#(set (filter % %2))