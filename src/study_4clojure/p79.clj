;; 4Clojure Question 79
;;
;; Write a function which calculates the sum of the minimal path through a triangle.  The triangle is represented as a collection of vectors.  The path should start at the top of the triangle and move to an adjacent number on the next row until the bottom of the triangle is reached.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 7 (__ '([1]
          [2 4]
         [5 1 4]
        [2 3 4 5]))) ; 1->2->1->3

(= 20 (__ '([3]
           [2 4]
          [1 9 3]
         [9 9 2 4]
        [4 6 6 7 8]
       [5 7 3 5 1 4]))) ; 3->4->3->2->7->1

;me
(fn k
  ([l] (k l 0))
  ([l i]
   (if l
     (let [[v & vs] l]
       (+ (nth v i) (min
                      (k vs i)
                      (k vs (inc i)))))
     0)))

;chouser. 나와 거의 비슷. 차이 1. nth 대신 vector를 함수로 사용한 점이 차이. 2. 인수분해를 함수 인자에서 바로 함.
;그리고 k보다는 f가 이해가 좀더 쉽다.
(fn f
  ([t] (f 0 t))
  ([i [r & t]]
   (+ (r i)
      (if t
        (min (f i t) (f (inc i) t))
        0))))