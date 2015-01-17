;; 4Clojure Question 26
;;
;; Write a function which returns the first X fibonacci numbers.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [n]
      (map last (take n (iterate #(vector (last %) (+ (first %) (last %))) [0 1])))) 3) '(1 1 2))

(= (__ 6) '(1 1 2 3 5 8))

(= (__ 8) '(1 1 2 3 5 8 13 21))

( #(vector (second %) (+ (first %) (second %)))[0 1] )

(fn [n]
  (map last (take n (iterate #(vector (last %) (+ (first %) (last %))) [0 1]))))

;Max
(fn [n]
  (take n
        (map first
             (iterate (fn [[f s]] [s (+ f s)])
                      [1 1]))))

;Max 를 보니 인수분해가 더 좋아보임. 그렇다면 나도 이렇게.
(= (
     (fn [n]
       (map last (take n (iterate (fn [[f s]] [s (+ f s)]) [0 1])))) 3)
   '(1 1 2))

;다시 Max를 보니 map, take 순서보다는 take map 순서이 약간 더 직관적. 이게 최종!
(= (
     (fn [n]
       (take n (map last (iterate (fn [[f s]] [s (+ f s)]) [0 1])))) 3)
   '(1 1 2))

;4clojure에는 이런 풀이도 있다.Programming Clojure 책에서 지연 시퀀스를 이용하는 방법과 동일. 기억해둘만하다. 책 발췌한다. "map 과 iterate가 지연 시퀀스를 반환하기 때문에 map과 iterate로 짜여진 fibo역시 지연 시퀀스를 반환하게 된다. 또한 fibo는 지금까지 살펴본 피보나치 수열의 구현가눙데 가장 짧고도 간단하다". 내생각: 이 문제에 대해서는 iterate 과 lazy-seq 버전의 크기는 거의 같다. 그런데 나에겐 iterate가 약간 더 쉽다. 더 구체적이라 그런 것 같다.
#(take % ((fn fib [x y] (lazy-seq (cons x (fib y (+ x y))))) 1 1))

;medium의 p60 푼 다음 다시 생각해 보니 lazy-seq 버전이 원래 fib의 간결한 정의를 그대로 구현한 것이다. 생각해 낼 수만 있으면 lazy-seq 버전이 더 simple.
