;; 4Clojure Question 158
;;
;; Write a function that accepts a curried function of unknown arity <i>n</i>.  Return an equivalent function of <i>n</i> arguments.
;;
;; <br/>
;;
;; You may wish to read <a href="http://en.wikipedia.org/wiki/Currying">this</a>.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 10 ((__ (fn [a]
             (fn [b]
               (fn [c]
                 (fn [d]
                   (+ a b c d))))))
       1 2 3 4))

(= 24 ((__ (fn [a]
             (fn [b]
               (fn [c]
                 (fn [d]
                   (* a b c d))))))
       1 2 3 4))

(= 25 ((__ (fn [a]
             (fn [b]
               (* a b))))
       5 5))

;me
(fn [f]
  (fn [& v]
    (loop [a v b f]
      (if (empty? a)
        b
        (recur (rest a) (b (first a)))))))

;chouser.
(fn [f] (fn [& a] (reduce #(% %2) f a)))

;나도 처음에 reduce를 생각했는데 2 arguments가 필요하다는 정의를 보고 안된다고 생각했다. f 가 두항이 필요한 것으로 착각한 것이다. 두항이 들어가도록 내가 자유롭게 만들면 된다는 것을 다시 깨달음.