;; 4Clojure Question 63
;;
;; Given a function f and a sequence s, write a function which returns a map.  The keys should be the values of f applied to each item in s.  The value at each key should be a vector of corresponding items in the order they appear in s.
;;
;; Restrictions (please don't use these function(s)): group-by
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (
     (fn [f col]
       (reduce
         (fn [prev-col x]
           (let [fx (f x)]
             (assoc-in prev-col [fx]
                       (if (contains? prev-col fx)
                         (concat (get prev-col fx) [x])
                         [x]))))
         {} col))
     #(> % 5) [1 3 6 8]) {false [1 3], true [6 8]})

(= (__ #(apply / %) [[1 2] [2 4] [4 6] [3 6]])
   {1/2 [[1 2] [2 4] [3 6]], 2/3 [[4 6]]})

(= (__ count [[1] [1 2] [3] [1 2 3] [2 3]])
   {1 [[1] [3]], 2 [[1 2] [2 3]], 3 [[1 2 3]]})

(reduce + {} [3 4 5])

(get {3 5} 3)
(get {} 3)

(let [v (get {} 3)]
  (assoc-in {} [3] (if v
                     (concat v [1])
                     [1])))

;지금
(fn [f col]
  (reduce
    (fn [prev-col x]
      (let [fx (f x)]
        (assoc-in prev-col [fx]
                  (if (contains? prev-col fx)
                    (concat (get prev-col fx) [x])
                    [x]))))
            {} col))

(assoc-in {false 2 true 3} [false] 3)
(get {1 2} 1)
(contains? {false 2} false)

;이전
(fn [f x](apply merge-with concat (map #(hash-map %1 [%2]) (map f x) x)))

;Max. 정말 훌륭.
(fn [f c]
  (reduce #(assoc %
                  (f %2)
                  (conj (get % (f %2) []) %2))
          {}
          c))

;지금부터 Max쪽으로 가보자.
;지금에서 assoc으로 대치한다면
(fn [f col]
  (reduce
    (fn [prev-col x]
      (let [fx (f x)]
        (assoc prev-col fx
                  (if (contains? prev-col fx)
                    (concat (get prev-col fx) [x])
                    [x]))))
    {} col))

;여기서 col대신 c를 쓴 후, #로 대체
(fn [f c]
  (reduce
    #(let [fx (f %2)]
        (assoc %1 fx
               (if (contains? %1 fx)
                 (concat (get %1 fx) [%2])
                 [%2])))
    {} c))

;get에 not-found 파라미터가 있는 것은 꼭 기억해둘만하다.
(fn [f c]
  (reduce
    #(let [fx (f %2)]
      (assoc %1 fx
             (concat (get %1 fx []) [%2])
             ))
    {} c))

;이것도 테스트는 통과는 하지만 conj로 하면 vector를 보존. 이제서야 Max에 다다랐다.
(fn [f c]
  (reduce
    #(let [fx (f %2)]
      (assoc %1 fx
             (conj (get %1 fx []) %2)
             ))
    {} c))

;daowen. 또다른길
(
(fn [f vs]
  (reduce #(merge-with concat % {(f %2) [%2]}) {} vs))
  #(> % 5) [1 3 6 8])

(merge-with concat
            {1 [2]}
            {1 [3]}) ;=> {1 (2 3)}

(merge-with into
            {1 [2]}
            {1 [3]}) ;=> {1 [2 3]}

(concat [2] [3]) ;=> (2 3)
(into [2] [3]) ;=> [2 3]

;기억해두자. merge-with가 있다는 사실과 into를 쓰면 기존 자료구조를 보존하면서 합칠 수 있다는 것을.