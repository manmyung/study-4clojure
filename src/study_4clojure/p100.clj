;; 4Clojure Question 100
;;
;; Write a function which calculates the <a href="http://en.wikipedia.org/wiki/Least_common_multiple">least common multiple</a>.  Your function should accept a variable number of positive integers or ratios. 
;;
;; Use M-x 4clojure-check-answers when you're done!

(== (__ 2 3) 6)

(== (__ 5 3 7) 105)

(== (__ 1/3 2/5) 2)

(== (__ 3/4 1/6) 3/2)

(== (__ 7 5/7 2 3/5) 210)

(defn gcd [a b]
  (let [x (max a b)
        y (min a b)]
    (if (= 0 y)
      x
      (gcd b (rem a b)))))

(gcd 1/3 2/5)
(/ (* 1/3 2/5) (gcd 1/3 2/5))

(gcd (gcd (gcd 7 5/7) 2) 3/5)

(* 7 5/7 2 3/5)

(
(fn [& c]
  (letfn [(gcd [a b]
               (let [x (max a b)
                     y (min a b)]
                 (if (= 0 y)
                   x
                   (gcd b (rem a b)))))]
    (/
      (apply * c)
      (reduce gcd c))))
  3 3 6)

;지금
(fn [& c]
  (letfn [(gcd [a b]
               (let [x (max a b)
                     y (min a b)]
                 (if (= 0 y)
                   x
                   (gcd b (rem a b)))))]
    (/
      (apply * c)
      (reduce gcd c))))

;이전
(fn [& vs]
  (letfn [(gcd [x y] (if (= y 0)
                       x
                       (gcd y (rem x y))))]
    (reduce #(/ (* %1 %2) (gcd %1 %2)) vs)))
;이건 이전이 더 낫다.

;psk810. 이걸 보니 letfn보다 let이 가독성이 더 좋다. 다음에는 let으로 하자.
(fn [& nums]
  (let [
        gcd (fn [x y] (cond
                        (zero? x) y
                        (zero? y) x
                        :else (recur y (mod x y))))
        lcm (fn [x y] (/ (* x y) (gcd x y)))]
    (reduce #(lcm % %2) nums)))
