;; 4Clojure Question 83
;;
;; Write a function which takes a variable number of booleans.  Your function should return true if some of the parameters are true, but not all of the parameters are true.  Otherwise your function should return false.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= false ((fn [& c] (and (apply not= c) (some true? c))) false false))

(= true (__ true false))

(= false (__ true))

(= true (__ false true false))

(= false (__ true true true))

(= true (__ true true true false))

((fn [& c] (and (apply not= c) (some true? c))) true false)

(apply not= [true false])

;이전답. 이건 테스트만 통과할 뿐 (not= 1 1) => true 이므로 문제의 설명에는 맞지 않다.
not=

;이것도 가능. %& 기억해 두자.
#(and (apply not= %&) (some true? %&))

(#(and (apply not= %&) (some true? %&)) true false)