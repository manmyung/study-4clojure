;; 4Clojure Question 78
;;
;; Reimplement the function described in <a href="76"> "Intro to Trampoline"</a>.
;;
;; Restrictions (please don't use these function(s)): trampoline
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (letfn [(triple [x] #(sub-two (* 3 x)))
          (sub-two [x] #(stop?(- x 2)))
          (stop? [x] (if (> x 50) x #(triple x)))]
    ((fn [f a]
       (let [r (f a)]
         (if (fn? r)
           (r)
           r))) triple 2))
  82)

(= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
          (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
    (map (partial __ my-even?) (range 6)))
  [true false true false true false])

;me
(fn k
  ([f a]
    (let [r (f a)]
      (if (fn? r)
        (k r)
        r)))
  ([f]
    (let [r (f)]
      (if (fn? r)
        (k r)
        r))))

;chouser. apply는 x가 nil이면 f만 평가해주는구나. 인자가 없는 것도 apply를 이용하면 한꺼번에 처리할 수 있다.
(fn t [f & x]
  (if (fn? f)
    (t (apply f x))
    f))

(apply (fn []
         1) nil) ;=> 1

;max. trampoline의 정의를 그대로 적용한 느낌.
#(loop [f (% %2)] (if (fn? f) (recur (f)) f))