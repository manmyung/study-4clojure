;; 4Clojure Question 33
;;
;; Write a function which replicates each element of a sequence a variable number of times.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [coll n] (mapcat #(repeat n %) coll)) [1 2 3] 2) '(1 1 2 2 3 3))

(= (__ [:a :b] 4) '(:a :a :a :a :b :b :b :b))

(= (__ [4 5 6] 1) '(4 5 6))

(= (__ [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))

(= (__ [44 33] 2) [44 44 33 33])

((fn [coll n] (mapcat #(repeat n %) coll)) [1 2 3] 2)

;max 이건 나랑 동일
(fn [x n] (mapcat #(repeat n %) x))

;norman 이게 좀더 짧아서 좋다. 함수가 겹친 경우 #...과 fn대신, #...과 partial을 사용하는 것도 알아두자.
#(mapcat (partial repeat %2) %1)

(#(mapcat (partial repeat %2) %1) [1 2 3] 2)
