;; 4Clojure Question 26
;;
;; Write a function which returns the first X fibonacci numbers.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [n]
      (map last (take n (iterate #(vector (last %) (+ (first %) (last %))) [0 1])))) 3) '(1 1 2))

(= (__ 6) '(1 1 2 3 5 8))

(= (__ 8) '(1 1 2 3 5 8 13 21))

( #(vector (second %) (+ (first %) (second %)))[0 1] )

(fn [n]
  (map last (take n (iterate #(vector (last %) (+ (first %) (last %))) [0 1]))))

;Max
(fn [n]
  (take n
        (map first
             (iterate (fn [[f s]] [s (+ f s)])
                      [1 1]))))

;Max 를 보니 인수분해가 더 좋아보임. 그렇다면 나도 이렇게.
(= (
     (fn [n]
       (map last (take n (iterate (fn [[f s]] [s (+ f s)]) [0 1])))) 3)
   '(1 1 2))

;다시 Max를 보니 map, take 순서보다는 take map 순서이 약간 더 직관적. 이게 최종!
(= (
     (fn [n]
       (take n (map last (iterate (fn [[f s]] [s (+ f s)]) [0 1])))) 3)
   '(1 1 2))
