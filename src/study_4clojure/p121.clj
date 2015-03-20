;; 4Clojure Question 121
;;
;; 	 Given a mathematical formula in prefix notation, return a function that calculates
;;
;; 	 the value of the formula.
;;
;; 	 The formula can contain nested calculations using the four basic
;;
;; 	 mathematical operators, numeric constants, and symbols representing variables.
;;
;; 	 The returned function has to accept a single parameter containing the map
;;
;; 	 of variable names to their values.
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 2 ((__ '(/ a b))
      '{b 8 a 16}))

(= 8 ((__ '(+ a b 2))
      '{a 2 b 4}))

(= [6 0 -4]
     (map (__ '(* (+ 2 a)
  	              (- 10 b)))
	        '[{a 1 b 8}
	          {b 5 a -2}
	          {a 2 b 11}]))

(= 1 ((__ '(/ (+ x 2)
              (* 3 (+ y 1))))
      '{x 4 y 1}))

;처음 답. 4clojure에서 'You tripped the alarm! eval is bad!' 이런 에러가 나옴
(fn [l]
  (fn [m]
    (eval
      (map
        (fn [x]
          (reduce #(if (= %1 (first %2))
                    (second %2) %1) x (map identity m)))
        l))))


;결국 풀었다! 만세. 4clojure에는 eval을 사용하지 못해서 너무 힘들었다.
;https://groups.google.com/forum/#!topic/4clojure/sg4KCPnUCpg 여기에서 "you have to map from the symbol + to the function +, etc." 언급을 보고 해결.
(fn [l]
  (fn [m]
    (letfn [(change [x]
                    (reduce #(if (= %1 (first %2))
                              (second %2) %1) x (concat (map identity m) [['/ /] ['+ +] ['- -] ['* *]])))
            (calc [y]
                  (apply (change (first y))
                         (map #(if (list? %)
                                (calc %)
                                (change %)) (rest y))))]
      (calc l)
      )))

;amalloy
(fn [formula]
  (fn [values]
    ((fn compute [x]
       (if (seq? x)
         (let [[op & args] x]
           (apply ({'+ + '/ / '- - '* *} op)
                  (map compute args)))
         (get values x x)))
      formula)))
;배울점
;1. change 대신 get을 쓰면 간단하겠구나.
(get '{b 8 a 16} 7 7) ;=> 7
(get '{b 8 a 16} 'a 'a) ;=> 16
;2. op만 따로 처리한 점때문에 간단해졌다.
;3. (fn compute [x] ...) 이렇게 처리해도 재귀 호출이 가능하다.
;4. list? 대신 seq? 를 사용하는 것이 더 general

;psk810
(fn f [exp]
  (let [[op & args] exp
        op ({'+ + '- - '* * '/ /} op)]
    (fn [env]
      (apply op (map
                  #(if (seq? %) ((f %) env)
                                (if (number? %) %
                                                (env %))) args)))))

;((f %) env) 이렇게 재귀적으로 처리하면 생각하기 쉽다.
;(env %) 밑에서 테스트
('{b 8 a 16} 'b) ;=> 8
;get대신 그냥 이렇게 처리해도 된다. 나처럼 (map identity m) 이런거 할 필요없다.
