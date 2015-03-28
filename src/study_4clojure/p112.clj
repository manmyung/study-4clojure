;; 4Clojure Question 112
;;
;; Create a function which takes an integer and a nested collection of integers as arguments.  Analyze the elements of the input collection and return a sequence which maintains the nested structure, and which includes all elements starting from the head whose sum is less than or equal to the input integer.
;;
;; Use M-x 4clojure-check-answers when you're done!

(=  (__ 10 [1 2 [3 [4 5] 6] 7])
   '(1 2 (3 (4))))

(=  (__ 30 [1 2 [3 [4 [5 [6 [7 8]] 9]] 10] 11])
   '(1 2 (3 (4 (5 (6 (7)))))))

(=  (__ 9 (range))
   '(0 1 2 3))

(=  (__ 1 [[[[[1]]]]])
   '(((((1))))))

(=  (__ 0 [1 2 [3 [4 5] 6] 7])
   '())

(=  (__ 0 [0 0 [0 [0]]])
   '(0 0 (0 (0))))

(=  (__ 1 [-10 [1 [2 3 [4 5 [6 7 [8]]]]]])
   '(-10 (1 (2 3 (4)))))

;me. max 나 ming 과 비슷.
(fn k [n [h & t]]
  (when h
    (if (coll? h)
      (list (k n h))
      (let [next-n (- n h)]
        (if (>= next-n 0)
          (cons h (k next-n t))
          `())))))

;max
(fn [n s]
  ((fn g [n [h & t]]
     (if (coll? h)
       `(~(g n h))
       (if (and h (>= n h))
         (cons h (g (- n h) t))
         ())))
    n s))

;ming
(fn f [sm [ft & rt]]
  (if (coll? ft)
    (list (f sm (if (empty? rt) ft (conj ft rt))))
    (if (or (nil? ft) (< sm ft))
      '()
      (conj (f (- sm ft) rt) ft))))