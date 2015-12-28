;; 4Clojure Question 113
;;
;; Write a function that takes a variable number of integer arguments.  If the output is coerced into a string, it should return a comma (and space) separated list of the inputs sorted smallest to largest.  If the output is coerced into a sequence, it should return a seq of unique input elements in the same order as they were entered.
;;
;; Restrictions (please don't use these function(s)): proxy
;;
;; Use M-x 4clojure-check-answers when you're done!

(= "1, 2, 3" (str (__ 2 1 3)))

(= '(2 1 3) (seq (__ 2 1 3)))

(= '(2 1 3) (seq (__ 2 1 3 3 1 2)))

(= '(1) (seq (apply __ (repeat 5 1))))

(= "1, 1, 1, 1, 1" (str (apply __ (repeat 5 1))))

(and (= nil (seq (__)))
     (=  "" (str (__))))

;me
(fn [& v]
     (reify
          clojure.lang.Seqable
          (seq [this] (seq (reduce
                                #(if (some (fn [x] (= x %2)) %1) %1 (conj %1 %2))
                                [] v)))
          Object
          (toString [this]
               (apply str (interpose ", " (sort v))))))

;daowen
(fn dancer [& xs]
     (reify clojure.lang.Seqable
          (seq [_] (seq (reduce #(if ((set %) %2) % (conj % %2)) [] xs)))
          (toString [_] (clojure.string/join ", " (sort xs)))))
;some대신 매번 set을 만들어서 들어있는지 확인할 수 있구나.
;clojure.string/join은 배워둘만 하다.
;clojure.lang.Seqable 인터페이스의 toString만 정의해도 된다.

;chouser
(fn [& s] (reify clojure.lang.Seqable
               (seq [_] (seq (distinct s)))
               (toString [_] (clojure.string/join ", " (sort s)))))
;가장 간결한 답이라 할 만하다.