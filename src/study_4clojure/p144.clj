;; 4Clojure Question 144
;;
;; Write an oscillating iterate: a function that takes an initial value and a variable number of functions. It should return a lazy sequence of the functions applied to the value in order, restarting from the first function after it hits the end.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 3 (__ 3.14 int double)) [3.14 3 3.0])

(= (take 5 (__ 3 #(- % 3) #(+ 5 %))) [3 0 5 2 7])

(= (take 12 (__ 0 inc dec inc dec inc)) [0 1 0 1 0 1 2 1 2 1 2 3])

;me
(fn k [x & fs]
  (let [col (reductions #(%2 %1) x fs)]
    (lazy-cat (butlast col) (apply k (cons (last col) fs)))))

;max version1
(fn [x & f] (reductions #(%2 %) x (cycle f)))
;cycle을 알고 있었으면 쉽게 할 수 있었을 듯.

;max version2
(fn [x & f]
  ((fn g [x [h & t]]
     (lazy-seq (cons x (g (h x) (or t f))))) x f))
;우왕 (or t f)로 하면 t가 하나씩 줄어들다가 없어지면 다시 f로 복원.

;psk810
(fn [n & fs]
  (letfn [(osc [n fs]
               (cons n
                     (lazy-seq (osc ((first fs) n) (rest fs)))))]
    (osc n (cycle fs))))
;cycle를 사용하면 lazy-seq도 더 쉽게 활용가능.