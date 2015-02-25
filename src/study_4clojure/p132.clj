;; 4Clojure Question 132
;;
;; Write a function that takes a two-argument predicate, a value, and a collection; and returns a new collection where the <code>value</code> is inserted between every two items that satisfy the predicate.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= '(1 :less 6 :less 7 4 3) (__ < :less [1 6 7 4 3]))

(= '(2) (__ > :more [2]))

(= [0 1 :x 2 :x 3 :x 4]  (__ #(and (pos? %) (< % %2)) :x (range 5)))

(empty? (__ > :more ()))

(= [0 1 :same 1 2 3 :same 5 8 13 :same 21]
   (take 12 (->> [0 1]
                 (iterate (fn [[a b]] [b (+ a b)]))
                 (map first) ; fibonacci numbers
                 (__ (fn [a b] ; both even or both odd
                       (= (mod a 2) (mod b 2)))
                     :same))))

;첫번때 시도. 잘 나가다가 마지막 문제에서 fail. reduce는 마지막까지 가야 결과가 나오므로 lazy는 안된다.
(fn [p v col]
  (reduce #(concat %1
                   (if (or (empty? %1) (not (p (last %1) %2))) [%2] [v %2]))
          [] col))

;me
(fn [p v col]
  (if (empty? col)
    col
    (cons (first col)
          (apply concat (map #(if (p %1 %2)
                               [v %2] [%2]) col (rest col))))))

;amalloy. 나와 가장 비슷한 답이지만 세련되었다. 차이점 1. empty? 대신 패턴인 when-let과 seq를 썼다. 2. mapcat를 쓰면 훨씬 간단.
(fn [pred inter coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (cons (first s)
            (mapcat (fn [[left right]]
                      (if (pred left right)
                        [inter right]
                        [right]))
                    (partition 2 1 s))))))
(partition 2 1 [1 2 3 4 5]) ;=> ((1 2) (2 3) (3 4) (4 5))
;처음에 이거 어떻게 하나 생각하다 포기했는데 이렇게 partition 2(n) 1(step) 로 하면 되는 구나

;hypirion. amalloy에 비해 partition-all을 사용하여 cons와 when-let 등이 필요없어졌다.
#(mapcat
  (fn [[a b]] (cond (nil? b) [a]
                    (%1 a b) [a %2]
                    :else [a]))
  (partition-all 2 1 %3))
(partition-all 2 1 [1 2 3 4 5]) ;=> ((1 2) (2 3) (3 4) (4 5) (5))

;silverio. hypirion에 비해 (and b (% a b))가 더 간다. 그러나 flatten대신 mapcat을 쓰는 것이 더 간편했을 듯.
#(->> %3
      (partition-all 2 1)
      (map (fn [[a b]] (if (and b (% a b)) [a %2] [a])))
      flatten)

;silverio와 hypirion의 장점만 뽑아 합친 답
#(mapcat
  (fn [[a b]] (if (and b (% a b)) [a %2] [a]))
  (partition-all 2 1 %3))