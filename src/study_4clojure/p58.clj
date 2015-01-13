;; 4Clojure Question 58
;;
;; Write a function which allows you to create function compositions.  The parameter list should take a variable number of functions, and create a function applies them from right-to-left.
;;
;; Restrictions (please don't use these function(s)): comp
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [3 2 1] (((fn [& fs] (fn [& vs] (reduce (fn [v f] (f v)) (apply (last fs) vs) (rest (reverse fs))))) rest reverse) [1 2 3 4]))

(= 5 ((__ (partial + 3) second) [1 2 3 4]))

(= true ((__ zero? #(mod % 8) +) 3 5 7 9))

(= "HELLO" ((__ #(.toUpperCase %) #(apply str %) take) 5 "hello world"))


;me. 어려웠다. apply 를 처음만 적용하면 되는데 헷갈렸다.
(fn [& fs] (fn [& vs] (reduce (fn [v f] (f v)) (apply (last fs) vs) (rest (reverse fs)))))

;max. max뿐 아니라 모두 비슷.
(fn [& s]
  #(reduce (fn [c f] (f c))
           (apply (last s) %&)
           (rest (reverse s))))