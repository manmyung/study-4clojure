;; 4Clojure Question 115
;;
;; A balanced number is one whose component digits have the same sum on the left and right halves of the number.  Write a function which accepts an integer n, and returns true iff n is balanced.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true (__ 11))

(= true (__ 121))

(= false (__ 123))

(= true (__ 0))

(= false (__ 88099))

(= true (__ 89098))

(= true (__ 89089))

(= (take 20 (filter __ (range)))
   [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101])

;me
(fn [x]
  (let
    [k (map #(- (int %) 48) (str x))
     n (int (/ (count k) 2))
     l (take n k)
     r (take-last n k)]
    (= (apply + l) (apply + r))))

;daowen
(fn balanced? [n]
  (let [ns (map int (str n))
        size (quot (count ns) 2)]
    (= (apply + (drop size ns))
       (apply + (drop-last size ns)))))
;(map int (str n)) 더 짧아서 좋다. quot는 몫. int로 내림하는 것과 별 차이없다.