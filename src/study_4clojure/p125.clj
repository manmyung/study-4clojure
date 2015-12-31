;; 4Clojure Question 125
;;
;; Create a function of no arguments which returns a string that is an <i>exact</i> copy of the function itself.
;;
;; <br /><br />
;;
;; Hint: read <a href="http://en.wikipedia.org/wiki/Quine_(computing)">this</a> if you get stuck (this question is harder than it first appears); but it's worth the effort to solve it independently if you can!
;;
;; <br /><br />
;;
;; Fun fact: Gus is the name of the <a href="http://i.imgur.com/FBd8z.png">4Clojure dragon</a>.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (str '__) (__))

;me
(fn [] (let [a "(fn [] (let [a \\\"] (str (subs a 0 15) (subs a 16 17) (subs a 0 15) (subs a 15 16) (subs a 15 16) (subs a 15) (subs a 16) (subs a 134)))"] (str (subs a 0 15) (subs a 16 17) (subs a 0 15) (subs a 15 16) (subs a 15 16) (subs a 15) (subs a 16) (subs a 134))))

;silverio
(fn []
  (let [[q s h f] [(char 34) (char 32)
                   "(fn [] (let [[q s h f] [(char 34) (char 32) "
                   "]] (str h q h q s q f q f)))"]]
    (str h q h q s q f q f)))
;나도 처음에 이렇게 분리해서 하려고 했는데 " 때문에 실패했다. char를 쓰면 됐었군. char의 doc은 "Coerce to char". 억지로 char로 만들다.

;psk810
(fn [] (let [q (char 34) a "(fn [] (let [q (char 34) a %s] (format a (str q a q))))"] (format a (str q a q))))
;format을 사용하면 중간에 낑겨 넣는 것과 같은 다양한 처리가 가능해서 편리하다.

;mfike
(fn [x] (str x x)) "(fn [x] (str x x))"
;이것 참 기발하다. (str '__)를 생각해 보면, str의 인자로 하나는 quote해서 들어가고, 다른 하나는 그대로 들어간다.
;quine은 인자가 없어야 한다는 고정관념을 피했다.

;chouser
(fn [x] (str x x))
'(fn [x] (str x x))
;이것도 mfike와 같은 방식. 답이 아름답다.

