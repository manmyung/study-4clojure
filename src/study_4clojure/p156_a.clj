;; 4Clojure Question 156
;;
;; When retrieving values from a map, you can specify default values in case the key is not found:<br/><br/>(= 2 (:foo {:bar 0, :baz 1} 2))<br/><br/>However, what if you want the map itself to contain the default values?  Write a function which takes a default value and a sequence of keys and constructs a map.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(zipmap %2 (repeat %1)) 0 [:a :b :c]) {:a 0 :b 0 :c 0})

(= (#(zipmap %2 (repeat %1)) "x" [1 2 3]) {1 "x" 2 "x" 3 "x"})

(= (#(zipmap %2 (repeat %1)) [:a :b] [:foo :bar]) {:foo [:a :b] :bar [:a :b]})

(zipmap [:a :b :c] (repeat 0))

#(zipmap %2 (repeat %1))

;maxim 이렇게 vector를 만들어서 map으로 바꾸는 방법. zipmap을 몰라도 되기 때문에 좀더 general할듯.
#(into {} (map vector %2 (repeat %)))

;norman: for 사용법이 재밌다.[(각 요소 of %2) %1]의 요소를 갖는 리스트를 만들었음.
#(into {} (for [key %2] [key %1]))
(for [key [:a :b :c]] [key 0]) ;=> ([:a 0] [:b 0] [:c 0])