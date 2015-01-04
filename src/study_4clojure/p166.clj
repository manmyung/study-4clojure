;; 4Clojure Question 166
;;
;; For any orderable data type it's possible to derive all of the basic comparison operations (&lt;, &le;, =, &ne;, &ge;, and &gt;) from a single operation (any operator but = or &ne; will work). Write a function that takes three arguments, a <var>less than</var> operator for the data and two items to compare. The function should return a keyword describing the relationship between the two items. The keywords for the relationship between <var>x</var> and <var>y</var> are as follows:
;;
;; 
;;
;; <ul>
;;
;; <li><var>x</var> = <var>y</var> &rarr; :eq</li>
;;
;; <li><var>x</var> &gt; <var>y</var> &rarr; :gt</li>
;;
;; <li><var>x</var> &lt; <var>y</var> &rarr; :lt</li>
;;
;; </ul>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= :gt ((fn [op a b] (if (op a b) :lt (if (op b a) :gt :eq))) < 5 1))

(= :eq (__ (fn [x y] (< (count x) (count y))) "pear" "plum"))

(= :lt (__ (fn [x y] (< (mod x 5) (mod y 5))) 21 3))

(= :gt (__ > 0 2))

;지금
(fn [op a b] (if (op a b) :lt (if (op b a) :gt :eq)))

;이전
(fn [f x y]
  (let [v1 (f x y) v2 (f y x)]
    (cond
      (and (= true v1) (= false v2)) :lt
      (and (= false v1) (= true v2)) :gt
      :else :eq)))

;이전보다 깔끔해졌네.

;norman. 이게 굿.
;cond가 잘 모르겠다는 생각을 버리자. 조건문과 실행식의 나열일뿐. 만약 아무것도 만족하는 조건이 없으면 nil을 반환. :else는 특별한 조건문.
(fn [op x y]
  (cond (op x y) :lt (op y x) :gt :else :eq))