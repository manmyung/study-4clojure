;; 4Clojure Question 131
;;
;; Given a variable number of sets of integers, create a function which returns true iff all of the sets have a non-empty subset with an equivalent summation.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true  (__ #{-1 1 99} 
             #{-2 2 888}
             #{-3 3 7777})) ; ex. all sets have a subset which sums to zero

(= false (__ #{1}
             #{2}
             #{3}
             #{4}))

(= true  (__ #{1}))

(= false (__ #{1 -3 51 9} 
             #{0} 
             #{9 2 81 33}))

(= true  (__ #{1 3 5}
             #{9 11 4}
             #{-3 12 3}
             #{-3 4 -2 10}))

(= false (__ #{-1 -2 -3 -4 -5 -6}
             #{1 2 3 4 5 6 7 8 9}))

(= true  (__ #{1 3 5 7}
             #{2 4 6 8}))

(= true  (__ #{-1 3 -5 7 -9 11 -13 15}
             #{1 -3 5 -7 9 -11 13 -15}
             #{1 -1 2 -2 4 -4 8 -8}))

(= true  (__ #{-10 9 -8 7 -6 5 -4 3 -2 1}
             #{10 -9 8 -7 6 -5 4 -3 2 -1}))

;me. p85의 power set 사용
(fn [& vs]
  (letfn [(power-set [s]
                     (reduce
                       (fn [a b]
                         (into a (map #(conj % b) a)))
                       #{#{}} s))
          (non-empty-power-set [s]
                               (clojure.set/difference (power-set s) #{#{}}))
          (sum-set [s]
                   (set (map #(apply + %) (non-empty-power-set s))))]
    (not (empty?
           (apply clojure.set/intersection (map sum-set vs))))))

;jafingerhut. 나와 거의 비슷. 배울점은 다음과 같다.
;1. let으로 power-set에 fn 으로 세팅. 좀더 보기 쉽다.
;2. difference 대신 disj 사용
(fn [& int-sets]
  (let [power-set (fn [s]
                    (reduce (fn [power-set x]
                              (into power-set (map #(conj % x) power-set)))
                            #{#{}} s))
        all-nonempty-subsets (map #(disj (power-set %) #{}) int-sets)
        subset-sums (map (fn [subsets-of-one]
                           (set (map #(apply + %) subsets-of-one)))
                         all-nonempty-subsets)]
    (not= #{} (apply clojure.set/intersection subset-sums))))

;silverio. 배울점. some을 사용한 것. intersection 으로 모두 검사하는 것이 아니라 공통적으로 같은게 하나라도 나오면 멈추고 true를 리턴.
(fn [& sl]
  (let [pset (partial reduce
                      #(into %(map (fn [v] (conj v %2)) %)), #{#{}})
        sums (->> sl (map pset) (map next)
                  (map #(map (partial reduce +) %)) (map set))]
    (if (some
          #(every? (fn [s] (s %)) (next sums)) (first sums))
      true false)))

;chouser. 이해 못했지만 일단 기록.
(fn [& n]
  (< 0 (count
         (reduce #(keep %2 %)
                 (map (fn f [[x & s]]
                        (into #{x}
                              (if s
                                (into (f s)
                                      (map #(+ x %) (f s))))))
                      (map seq n))))))