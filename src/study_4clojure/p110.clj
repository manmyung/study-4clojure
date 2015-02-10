;; 4Clojure Question 110
;;
;; <p>Write a function that returns a lazy sequence of "pronunciations" of a sequence of numbers. A pronunciation of each element in the sequence consists of the number of repeating identical numbers and the number itself. For example, <code>[1 1]</code> is pronounced as <code>[2 1]</code> ("two ones"), which in turn is pronounced as <code>[1 2 1 1]</code> ("one two, one one").</p><p>Your function should accept an initial sequence of numbers, and return an infinite lazy sequence of pronunciations, each element being a pronunciation of the previous element.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [[1 1] [2 1] [1 2 1 1]] (take 3 (__ [1])))

(= [3 1 2 4] (first (__ [1 1 1 4 4])))

(= [1 1 1 3 2 1 3 2 1 1] (nth (__ [1]) 6))

(= 338 (count (nth (__ [3 2]) 15)))

;me
(fn f [s]
  (let [next (mapcat #(vector (count %) (first %)) (partition-by identity s))]
    (lazy-cat (vector next) (f next))))

;chouser
(fn [x] (rest (iterate #(mapcat (juxt count first) (partition-by identity %)) x)))
;iterate를 까먹고 있었다.
;그리고 기억하면 좋은 것. juxt
((juxt count first) [1 1 1]) ;=> [3 1]