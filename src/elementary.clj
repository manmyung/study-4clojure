;; 4Clojure Question 1
;;
;; This is a clojure form.  Enter a value which will make the form evaluate to true.  Don't over think it!  If you are confused, see the <a href='/directions'>getting started</a> page.  Hint: true is equal to true.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true true)

;; 4Clojure Question 3
;;
;; Clojure strings are Java strings.  This means that you can use any of the Java string methods on Clojure strings.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= "HELLO WORLD" (.toUpperCase "hello world"))

;clojure 에서는 이게 더 좋아보임
(= "HELLO WORLD" (clojure.string/upper-case "hello world"))


;; 4Clojure Question 6
;;
;; Vectors can be constructed several ways.  You can compare them with lists.
;;
;; Use M-x 4clojure-check-answers when you're done!

;리스트와 벡터는 = 에서 같다고 판단하는 구나.
(= '(:a :b :c) (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))


;; 4Clojure Question 11
;;
;; When operating on a map, the conj function returns a new map with one or more key-value pairs "added".
;;
;; Use M-x 4clojure-check-answers when you're done!

(= {:a 1, :b 2, :c 3} (conj {:a 1} [:b 2] [:c 3]))

; conj map에 대해서
; list: 불가, vector: 하나의 pair만, map: 여러개의 pair도 가능
(= {:c 3, :d 2, :b 2, :a 1} (conj {:a 1} {:b 2 :d 2} [:c 3]))

;; 4Clojure Question 13
;;
;; The rest function will return all the items of a sequence except the first.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= '(20 30 40) (rest [10 20 30 40]))

(rest [10 20 30 40]) ;=>(20 30 40) 벡터의 경우 list 로 나오네.
(rest {10 20 30 40 50 60});=>([30 40] [10 20]) 맵의 경우 벡터의 list로 나오네.

;; 4Clojure Question 14
;;
;; Clojure has many different ways to create functions.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 8 ((fn add-five [x] (+ x 5)) 3))

(= 8 ((fn [x] (+ x 5)) 3))

(= 8 (#(+ % 5) 3))

;partial은 왜 필요한지 모르겠다. 위의 #형식에 비해 인자 위치도 자유롭지 않고 그렇다고 길이가 짧지도 않고.
(= 8 ((partial + 5) 3))


;; 4Clojure Question 17
;;
;; The map function takes two arguments: a function (f) and a sequence (s).  Map returns a new sequence consisting of the result of applying f to each item of s.  Do not confuse the map function with the map data structure.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [6 7 8] (map #(+ % 5) '(1 2 3)))

;maxim 도 '(6 7 8) 대신 [6 7 8] 사용함. 한 글자 적으니까 vector가 더 나을듯.


;; 4Clojure Question 37
;;
;; Regex patterns are supported with a special reader macro.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

(re-seq #"[A-Z]+" "bA1B3Ce ") ;=> ("A" "B" "C")

;; 4Clojure Question 64
;;
;; <a href='http://clojuredocs.org/clojure_core/clojure.core/reduce'>Reduce</a> takes a 2 argument function and an optional starting value. It then applies the function to the first 2 items in the sequence (or the starting value and the first element of the sequence). In the next iteration the function will be called on the previous return value and the next item from the sequence, thus reducing the entire collection to one value. Don't worry, it's not as complicated as it sounds.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 15 (reduce + [1 2 3 4 5]))

(=  0 (reduce + [])) ;(+) => 0 이므로 이 코드가 문제가 없었다.

(=  6 (reduce + 1 [2 3]))



;; 4Clojure Question 71
;;
;; The -> macro threads an expression x through a variable number of forms. First, x is inserted as the second item in the first form, making a list of it if it is not a list already.  Then the first form is inserted as the second item in the second form, making a list of that form if necessary.  This process continues for all the forms.  Using -> can sometimes make your code more readable.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (last (sort (rest (reverse [2 5 4 1 3 6]))))
   (-> [2 5 4 1 3 6] (reverse) (rest) (sort) (last))
   5)

(sort (rest (reverse [2 5 4 1 3 6])))

;; 4Clojure Question 68
;;
;; Clojure only has one non-stack-consuming looping construct: recur.  Either a function or a loop can be used as the recursion point.  Either way, recur rebinds the bindings of the recursion point to the values it is passed.  Recur must be called from the tail-position, and calling it elsewhere will result in an error.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [7 6 5 4 3]
   (loop [x 5
          result []]
     (if (> x 0)
       (recur (dec x) (conj result (+ 2 x)))
       result)))

;지금까지 코드에서 recur가 마지막쯤에 오는게 관례라고 생각했는데 알고보니 그렇지 않으면 에러가 나네.

;; 4Clojure Question 134
;;
;; Write a function which, given a key and map, returns true <a href="http://en.wikipedia.org/wiki/If_and_only_if">iff</a> the map contains an entry with that key and its value is nil.
;;
;; Use M-x 4clojure-check-answers when you're done!

(true?  (#(and (contains? %2 %1) (nil? (%1 %2))) :a {:a nil :b 2}))

(false? (#(and (contains? %2 %1) (nil? (%1 %2))) :b {:a nil :b 2}))

(false? (#(and (contains? %2 %1) (nil? (%1 %2))) :c {:a nil :b 2}))

(contains? {:a nil :b 2} :a)
(contains? {nil :a 2 :b} :a)
#(and (contains? %2 %1) (nil? (%1 %2)))

;; 4Clojure Question 145
;;
;; Clojure's <a href="http://clojuredocs.org/clojure_core/clojure.core/for">for</a> macro is a tremendously versatile mechanism for producing a sequence based on some other sequence(s). It can take some time to understand how to use it properly, but that investment will be paid back with clear, concise sequence-wrangling later. With that in mind, read over these <code>for</code> expressions and try to see how each of them produces the same result.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= '(1 5 9 13 17 21 25 29 33 37) (for [x (range 40)
            :when (= 1 (rem x 4))]
        x))

(= __ (for [x (iterate #(+ 4 %) 0)
            :let [z (inc x)]
            :while (< z 40)]
        z))

(= __ (for [[x y] (partition 2 (range 20))]
        (+ x y)))

(range 40) ;=> (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39)
(for [x (range 40)
      :when (= 1 (rem x 4))]
  x)

;for에 :when :let :while 등이 사용될 수 있음.
;for 소스에 있는 내용 - Supported modifiers are: :let [binding-form expr ...],:while test, :when test.


;; 4Clojure Question 162
;;
;; In Clojure, only nil and false represent the values of logical falsity in conditional tests - anything else is logical truth.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= __ (if-not false 1 0))

(= __ (if-not nil 1 0))

(= __ (if true 1 0))

(= __ (if [] 1 0))

(= __ (if [0] 1 0))

(= __ (if 0 1 0))

(= __ (if 1 1 0))

;이것을 기억하기 위해 save함: only nil and false represent the values of logical falsity in conditional tests

;; 4Clojure Question 161
;;
;; Set A is a subset of set B, or equivalently B is a superset of A, if A is "contained" inside B. A and B may coincide.
;;
;; Use M-x 4clojure-check-answers when you're done!

(clojure.set/superset? #{1 2} #{2})

(clojure.set/subset? #{1} #{1 2})

(clojure.set/superset? #{1 2} #{1 2})

(clojure.set/subset? #{1 2} #{1 2})

;이건 superset?, subset? 의미를 알면 쉽다. 이런게 있다는 것을 알려주려고 만든 문제.

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