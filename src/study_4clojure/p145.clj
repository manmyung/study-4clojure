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
