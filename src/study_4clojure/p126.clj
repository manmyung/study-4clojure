;; 4Clojure Question 126
;;
;; Enter a value which satisfies the following:
;;
;; Use M-x 4clojure-check-answers when you're done!

(let [x java.lang.Class]
  (and (= (class x) x) x))

(class java.lang.Class)
(type 2)

;type과 class 모두 clojure.core에 있는 함수.
;type의 정의: Returns the :type metadata of x, or its Class if none
;내 느낌은 type이 좀더 많이 사용되는 것 같다. 정의상 더 빠를 것 같아.

