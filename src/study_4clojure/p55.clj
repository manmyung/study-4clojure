;; 4Clojure Question 55
;;
;; Write a function which returns a map containing the number of occurences of each distinct item in a sequence.
;;
;; Restrictions (please don't use these function(s)): frequencies
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})

(= (__ [:b :a :b :a :b]) {:a 2, :b 3})

(= (__ '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})

;me.
#(into {} (map (fn [[k v]] [k (count v)]) (group-by identity %)))

;hypirion. merge-with를 기억하고 있다면 나올 수 있는 답.
#(apply merge-with + (map (fn [a] {a 1}) %))

;chouser. 이것도 멋지다. assoc과 (map key don't)를 응용.
reduce #(assoc % %2 (+ 1 (% %2 0))) {}