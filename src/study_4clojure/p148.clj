;; 4Clojure Question 148
;;
;; <p>Write a function which calculates the sum of all natural numbers under <i>n</i> (first argument) which are evenly divisible by at least one of <i>a</i> and <i>b</i> (second and third argument). Numbers <i>a</i> and <i>b</i> are guaranteed to be <a href="http://en.wikipedia.org/wiki/Coprime">coprimes</a>.</p>
;;
;; 
;;
;; <p>Note: Some test cases have a very large <i>n</i>, so the most obvious solution will exceed the time limit.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 0 (__ 3 17 11))

(= 23 (__ 10 3 5))

(= 233168 (__ 1000 3 5))

(= "2333333316666668" (str (__ 100000000 3 5)))

(= "110389610389889610389610"
  (str (__ (* 10000 10000 10000) 7 11)))

(= "1277732511922987429116"
  (str (__ (* 10000 10000 10000) 757 809)))

(= "4530161696788274281"
  (str (__ (* 10000 10000 1000) 1597 3571)))

;me. bigint 대신 int를 쓰면 범위를 넘는다는 말이 나온다.
;이 문제는 1부터 n까지의 합을 공식으로 아느냐를 테스트하므로 그리 좋은 문제는 아닌 것 같다.
(fn [n a b]
  (letfn [(sum [n]
               (/ (* n (inc n)) 2))
          (sum-to-n-by-x [n x]
                    (* x (sum (bigint (/ (dec n) x)))))]
    (- (+ (sum-to-n-by-x n a)
          (sum-to-n-by-x n b))
       (sum-to-n-by-x n (* a b)))
      ))