;; 4Clojure Question 30
;;
;; Write a function which removes consecutive duplicates from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (apply str (__ "Leeeeeerrroyyy")) "Leroy")

(= (reduce #(if (= (last %1) %2) %1 (conj %1 %2)) [] [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))

(= (__ [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

(reduce #(if (= (last %1) %2) %1 (conj %1 %2)) [] [1 1 2 3 3 2 2 3])

;이전답
reduce (fn [x y] (if (= (last x) y) x(conj x y)))[]

;지금답. 이전답과 별 차이는 없다.
reduce #(if (= (last %1) %2) %1 (conj %1 %2)) []

;chouser 이게 좋다. 이 문제는 이게 갑. 이런게 clojure's way 인듯.
#(map first (partition-by identity %))
(partition-by identity [1 1 2 3 3 2 2 3])
