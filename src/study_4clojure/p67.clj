;; 4Clojure Question 67
;;
;; Write a function which returns the first x
;;
;; number of prime numbers.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 2) [2 3])

(= (__ 5) [2 3 5 7 11])

(= (last (__ 100)) 541)

;me
(fn [x]
  (letfn [(is-prime? [n]
                      (not (some zero? (map #(rem n %) (range 2 n)))))]
          (take x (filter is-prime? (drop 2 (range))))))

;max. 나와 비슷하지만 좀 더 간단.
(fn [n]
  (take n
        (remove (fn [k] (some #(zero? (rem k %))
                              (range 2 k)))
                (iterate inc 2))))


;hypirion. 이게 좀더 소수로만 나누기를 확인하니까 더 효율적인 코드. every?, pos? 도 기억할 만 하다.
(fn [n]
  (loop [i 2
         acc []]
    (if (= (count acc) n)
      acc
      (recur (inc i)
             (if (every? #(pos? (rem i %)) acc)
               (conj acc i)
               acc)))))