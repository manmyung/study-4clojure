;; 4Clojure Question 107
;;
;; <p>Lexical scope and first-class functions are two of the most basic building blocks of a functional language like Clojure. When you combine the two together, you get something very powerful called <strong>lexical closures</strong>. With these, you can exercise a great deal of control over the lifetime of your local bindings, saving their values for use later, long after the code you're running now has finished.</p>
;;
;; 
;;
;; <p>It can be hard to follow in the abstract, so let's build a simple closure. Given a positive integer <i>n</i>, return a function <code>(f x)</code> which computes <i>x<sup>n</sup></i>. Observe that the effect of this is to preserve the value of <i>n</i> for use outside the scope in which it is defined.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 256 (((fn [n] (fn [x] (apply * (repeat n x)))) 2) 16),
       (((fn [n] (fn [x] (apply * (repeat n x)))) 8) 2))

(= [1 8 27 64] (map (__ 3) [1 2 3 4]))

(= [1 2 4 8 16] (map #((__ %) 2) [0 1 2 3 4]))

;지금. x n 이 잘 보이도록 일부러 fn을 두번 썼다.
(fn [n] (fn [x] (apply * (repeat n x))))

;이전
#(fn [x] (reduce * (repeat % x)))

;norman. Math/pow도 있구나. Cheat Sheet에 없는 걸로 보아 라이브러리.
#(fn [n] (Math/pow n %))

(Math/pow 2 8)

;http://en.wikipedia.org/wiki/Closure_(computer_programming)
;closure는 함수이거나 함수의 레퍼런드이다(이 예에서는 (fn [x] (apply * (repeat n x))). 그런데 자신의 범위를 벗어난 변수(이 예에서는 n)도 환경으로 보존하고 있는 함수 또는 함수의 레퍼런스이다.