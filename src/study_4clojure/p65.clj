;; 4Clojure Question 65
;;
;; Clojure has many sequence types, which act in subtly different ways. The core functions typically convert them into a uniform "sequence" type and work with them that way, but it can be important to understand the behavioral and performance differences so that you know which kind is appropriate for your application.<br /><br />Write a function which takes a collection and returns one of :map, :set, :list, or :vector - describing the type of collection it was given.<br />You won't be allowed to inspect their class or use the built-in predicates like list? - the point is to poke at them and understand their behavior.
;;
;; Restrictions (please don't use these function(s)): class, type, Class, vector?, sequential?, list?, seq?, map?, set?, instance?, getClass
;;
;; Use M-x 4clojure-check-answers when you're done!

(= :map (__ {:a 1, :b 2}))

(= :list (__ (range (rand-int 20))))

(= :vector (__ [1 2 3 4 5 6]))

(= :set (__ #{10 (rand-int 5)}))

(= [:map :set :vector :list] (map __ [{} #{} [] ()]))

;me
#(let [x (conj % [-1 -2])]
  (if (zero? (count (flatten x)))
    (if (= -2 (x -1))
      :map
      :set)
    (if (= -2 (last (conj (conj % -1) -2)))
      :vector
      :list)))

;max
#(cond
  (= (conj % nil) %) :map ;map에 nil을 conj하면 그대로구나.
  (= (conj % 0) (conj % 0 0)) :set
  (= (conj % 0 1) (cons 1 (cons 0 %))) :list ;col 자체를 비교하는 것이 나와의 차이점이다.
  :else :vector)

;chouser. zipmap은 keys와 values로 map 만드는 것. str를 사용한 것이 기발하다.
#((zipmap (map str [{} #{} () []]) [:map :set :list :vector]) (str (empty %)))