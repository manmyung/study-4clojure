;; 4Clojure Question 105
;;
;; Given an input sequence of keywords and numbers, create a map such that each key in the map is a keyword, and the value is a sequence of all the numbers (if any) between it and the next keyword in the sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= {} (__ []))

(= {:a [1]} (__ [:a 1]))

(= {:a [1], :b [2]} (__ [:a 1, :b 2]))

(= {:a [1 2 3], :b [], :c [4]} (__ [:a 1 2 3 :b :c 4]))


;me
#(loop [x %
        result {}
        prev-k nil]
  (if (empty? x)
    result
    (let [f (first x)]
      (recur (rest x)
             (merge-with concat result (if (keyword? f)
                                         {f []}
                                         {prev-k [f]}))
             (if (keyword? f)
               f
               prev-k)))))

;joelgrus. loop를 쓴 버전으로는 assoc을 사용한점이 더 깔끔.
(fn kav [s]
(loop [m {}
       remain s
       current-key nil]
  (if (empty? remain) m
                      (let [elt (first remain)
                            new-remain (rest remain)
                            elt-key (keyword? elt)
                            new-key (if elt-key elt current-key)
                            old-vec (if elt-key [] (m current-key))
                            new-vec (if elt-key [] (conj old-vec elt))
                            new-map (assoc m new-key new-vec)]
                        (recur new-map new-remain new-key)))))

;chouser. 아주 간단하게 했다. 이런 생각은 도저히 안날듯.
(
(fn f [[k & v]]
  (if v
    (let [[a b] (split-with number? v)]
      (assoc (f b) k a))
    {}))
  [:b :c 4])

(
(fn [[k & v]]
  v)
  [:a 1 2 3 :b :c 4]) ;=> (1 2 3 :b :c 4)

;max. 이것도 생각하기 어렵다.
(fn f [a k [h & t]]
  (if h
    (if (keyword? h)
      (f (assoc a h []) h t)
      (f (assoc a k (conj (a k) h)) k t))
    a))
{} 0