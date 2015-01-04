;; 4Clojure Question 62
;;
;; Given a side-effect free function f and an initial value x write a function which returns an infinite lazy sequence of x, (f x), (f (f x)), (f (f (f x))), etc.
;;
;; Restrictions (please don't use these function(s)): iterate
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 5 ((fn k [f x]
              (cons x (lazy-seq (k f (f x))))) #(* 2 %) 1)) [1 2 4 8 16])

(= (take 100 (__ inc 0)) (take 100 (range)))

(= (take 9 (__ #(inc (mod % 3)) 1)) (take 9 (cycle [1 2 3])))


(take 3 (
          (fn k [f x]
            (cons x (lazy-seq (k f (f x)))))
            inc 0))

;lazy-seq는 잘 적응이 되지 않는다. 어떻게 하면 될까? 이 경우는 x가 들어가는 자리에 (f x) 가 들어가도록 함수 호출하는 방식이다.

;그렇다면 피보나치 수열은?
(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))
(take 7 (fib 1 1)) ;=> (1 1 2 3 5 8 13)
(defn fib1 [a b] (cons b (lazy-seq (fib1 b (+ b a)))))
(take 7 (fib1 1 1)) ;=> (1 2 3 5 8 13 21)
(defn fib2 [a b] (cons (+ a b) (lazy-seq (fib2 b (+ b a)))))
(take 7 (fib2 1 1)) ;=> (2 3 5 8 13 21 34)
;공통점은 재귀적 식이 두번째 인자로 들어간다. 그리고 초기값은 a, b, (+ a b) 무엇이든 자유롭게 만들 수 있다.

(defn fib3 [a b] (cons a (lazy-seq (fib3 a (+ b a)))))
(take 7 (fib3 1 1)) ;=> (1 1 1 1 1 1 1)
;두번째 호출에서 1 2 가 들어가지만 더 이상 재귀가 되지 않는다. 무얼 의미하는 것일까?