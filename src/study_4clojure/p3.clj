;; 4Clojure Question 3
;;
;; Clojure strings are Java strings.  This means that you can use any of the Java string methods on Clojure strings.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= "HELLO WORLD" (__ "hello world"))

;me
(= "HELLO WORLD" (.toUpperCase "hello world"))

;clojure 에서는 이게 더 좋아보임
(= "HELLO WORLD" (clojure.string/upper-case "hello world"))
