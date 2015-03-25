;; 4Clojure Question 171
;;
;; Write a function that takes a sequence of integers and returns a sequence of "intervals".  Each interval is a a vector of two integers, start and end, such that all integers between start and end (inclusive) are contained in the input sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 2 3]) [[1 3]])

(= (__ [10 9 8 1 2 3]) [[1 3] [8 10]])

(= (__ [1 1 1 1 1 1 1]) [[1 1]])

(= (__ []) [])

(= (__ [19 4 17 1 3 10 2 13 13 2 16 4 2 15 13 9 6 14 2 11])
       [[1 4] [6 6] [9 11] [13 17] [19 19]])

;me. 쉬운 문제.
(fn [col]
  (reduce #(if (= (last (last %1)) (dec %2))
            (assoc-in %1 [(dec (count %1)) 1] %2)
            (into %1 [[%2 %2]])) [] (sort (set col))))

;max. 이해가 바로는 안가지만 나중을 위해 적어둔다.
#(map vector (% %2 dec) (% %2 inc))
#(reduce (fn [a b] (if ((set %) (%2 b)) a (conj a b))) [] (sort (set %)))

;chouser. 나와 논리가 같다. clojure 사용방법이 다름.
#(->> % distinct sort
      (reduce
        (fn [[[l h] & im] x] ; =>이렇게 분해하는 것도 봐두자.
          (if (= x (+ 1 h))
            (cons [l x] im) ;=> 앞쪽에다 붙였군
            (list* [x x] [l h] im))) ;=> list*는 "원소, 원소, ..., sequence" 구나
        [[0 -1]])
      reverse
      rest)
;set 대신 distinct를 쓸 수도 있겠군.
(distinct [1 2 1]) ;=> (1 2)