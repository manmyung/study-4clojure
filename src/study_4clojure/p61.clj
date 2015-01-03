;; 4Clojure Question 61
;;
;; Write a function which takes a vector of keys and a vector of values and constructs a map from them.
;;
;; Restrictions (please don't use these function(s)): zipmap
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(into {} (map vector %1 %2)) [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})

(= (__ [1 2 3 4] ["one" "two" "three"]) {1 "one", 2 "two", 3 "three"})

(= (__ [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo", :bar "bar"})

;zipmap이 이런 의미였군. 다시한번 기억.

(into {} '([:a 1] [:b 2]))

(into {} ['(:a 1) '(:b 2)]) ;=> 이건 에러. 테스트해보니 {}로 변경은 vector만 가능

(into [] ['(:a 1) '(:b 2)]) ;=> [(:a 1) (:b 2)]

#(into {} (map vector %1 %2))