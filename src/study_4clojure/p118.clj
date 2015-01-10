;; 4Clojure Question 118
;;
;; <p>Map is one of the core elements of a functional programming language. Given a function <code>f</code> and an input sequence <code>s</code>, return a lazy sequence of <code>(f x)</code> for each element <code>x</code> in <code>s</code>.
;;
;; Restrictions (please don't use these function(s)): map, map-indexed, mapcat, for
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [3 4 5 6 7]
   (__ inc [2 3 4 5 6]))

(= (repeat 10 nil)
   (__ (fn [_] nil) (range 10)))

(= [1000000 1000001]
   (->> (__ inc (range))
        (drop (dec 1000000))
        (take 2)))