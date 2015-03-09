;; 4Clojure Question 116
;;
;; A <a href="http://en.wikipedia.org/wiki/Balanced_prime">balanced prime</a> is a prime number which is also the mean of the primes directly before and after it in the sequence of valid primes.  Create a function which takes an integer n, and returns true iff it is a balanced prime.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= false (__ 4))

(= true (__ 563))

(= 1103 (nth (filter __ (range)) 15))

;me
(fn [x]
  (letfn [(is-prime? [k]
                     (not (some #(zero? (rem k %))
                                (range 2 k))))]
    (and (is-prime? x)
         (>= x 2)
         (loop [l (dec x) r (inc x)]
           (let [lp (is-prime? l)
                 rp (is-prime? r)]
             (cond
               (= l 1) false
               (and lp rp) true
               (or lp rp) false
                       :else
               (recur (dec l) (inc r))))))))