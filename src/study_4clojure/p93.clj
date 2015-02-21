;; 4Clojure Question 93
;;
;; Write a function which flattens any nested combination of sequential things (lists, vectors, etc.), but maintains the lowest level sequential items.  The result should be a sequence of sequences with only one level of nesting.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [["Do"] ["Nothing"]])
   [["Do"] ["Nothing"]])

(= (__ [[[[:a :b]]] [[:c :d]] [:e :f]])
   [[:a :b] [:c :d] [:e :f]])

(= (__ '((1 2)((3 4)((((5 6)))))))
   '((1 2)(3 4)(5 6)))

;me
(fn k [x]
  (if (some coll? x)
    (mapcat k x)
    [x]))

;amalloy. every? 인걸 제외하면 나와 동일. 지금은 every?이 some보다 의미가 확실한 경우일듯.
(fn f [x]
  (if (every? coll? x)
    (mapcat f x)
    [x]))
