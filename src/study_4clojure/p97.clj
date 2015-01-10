;; 4Clojure Question 97
;;
;; <a href="http://en.wikipedia.org/wiki/Pascal%27s_triangle">Pascal's triangle</a> is a triangle of numbers computed using the following rules:<br/></br>- The first row is 1.</br>- Each successive row is computed by adding together adjacent numbers in the row above, and adding a 1 to the beginning and end of the row.<br/><br/>Write a function which returns the nth row of Pascal's Triangle.
;;
;; 
;;
;; 
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1) [1])

(= (map  (fn [i] (nth (iterate #(vec (map + (cons 0 %) (conj % 0))) [1]) (dec i))) (range 1 6))
   [     [1]
        [1 1]
       [1 2 1]
      [1 3 3 1]
     [1 4 6 4 1]])

(= (__ 11)
   [1 10 45 120 210 252 210 120 45 10 1])

(#(vec (map + (cons 0 %) (conj % 0))) [1 2 1])

;지금. 이전과 비교하면 iterate 구현하지 않고 사용한 것이 간결.
(fn [i] (nth (iterate #(vec (map + (cons 0 %) (conj % 0))) [1]) (dec i)))


;이전
(fn [n] (nth ((fn _iterate [f x] (cons x (lazy-seq (_iterate f (f x)))))
               #(map + (concat [0] %) (concat % [0])) [1]) (dec n)))

;조금더 이쁘려면 cons, conj 를 concat으로 바꿈.
(fn [i] (nth (iterate #(vec (map + (concat [0] %) (concat % [0]))) [1]) (dec i)))

;chouser
(fn p [x]
  (if (= x 1)
    [1]
    `[1 ~@(map + (p (- x 1)) (next (p (- x 1)))) 1]))

;rest 와 next의 차이
;rest is generally preferred over next. See the Clojure.org documentation on writing lazy functions.
;Also, the topic is covered on StackOverflow.com: rest vs. next.

((fn [x] `[1 ~@x 1]) [3 4 5]) ;=> [1 3 4 5 1] chouser에서 배울 것은 이거다. 이것 이외에 해 자체는 모르겠다. 넘기자.
(#(`[1 ~@% 1]) [3 4 5]) ;=> 이건 왠지 모르겠지만 에러가 난다.