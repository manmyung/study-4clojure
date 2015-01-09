;; 4Clojure Question 88
;;
;; Write a function which returns the symmetric difference of two sets.  The symmetric difference is the set of items belonging to one but not both of the two sets.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1)) #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 7})

(= (__ #{:a :b :c} #{}) #{:a :b :c})

(= (__ #{} #{4 5 6}) #{4 5 6})

(= (__ #{[1 2] [2 3]} #{[2 3] [3 4]}) #{[1 2] [3 4]})

(#{1 3 5 7} 2)

;지금
#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1))

;이전
(fn [a b]
  (letfn [(_diff [x y]
                 (filter #(= nil (x %)) y))]
    (set (concat (_diff a b) (_diff b a)))))

;Max
#(set `(~@(% %2 %3) ~@(% %3 %2))) remove

(remove #{1 2 3} #{2 3 4}) ;=> (4)
;~@는 괄호를 없애는 역활. 괄호를 없애서 합친후 다시 리스트를 만들어 set에 넣었구나.
;한개 더 배울 점은 함수도 인자로 사용하는 것.

;chouser. 이게 Max보다 좋아 보인다. into에서 앞쪽이 set이면 뒤의 것도 자동으로 set으로 합쳐진다.
#(into (set (remove %2 %)) (remove % %2))