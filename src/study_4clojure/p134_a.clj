;; 4Clojure Question 134
;;
;; Write a function which, given a key and map, returns true <a href="http://en.wikipedia.org/wiki/If_and_only_if">iff</a> the map contains an entry with that key and its value is nil.
;;
;; Use M-x 4clojure-check-answers when you're done!

(true?  (#(and (contains? %2 %1) (nil? (%1 %2))) :a {:a nil :b 2}))

(false? (#(and (contains? %2 %1) (nil? (%1 %2))) :b {:a nil :b 2}))

(false? (#(and (contains? %2 %1) (nil? (%1 %2))) :c {:a nil :b 2}))

(contains? {:a nil :b 2} :a)
(contains? {nil :a 2 :b} :a)
#(and (contains? %2 %1) (nil? (%1 %2)))