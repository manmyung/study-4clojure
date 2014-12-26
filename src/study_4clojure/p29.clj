;; 4Clojure Question 29
;;
;; Write a function which takes a string and returns a new string containing only the capital letters.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (
     #(apply str (re-seq #"[A-Z]" %))
       "HeLlO, WoRlD!") "HLOWRD")

(empty? (__ "nothing"))

(= (__ "$#A(*&987Zf") "AZ")

;여러개를 한꺼번에 찾아 리스트로 만드는데 re-seq. 만약 하나만 찾는다면 re-find.
(apply str (re-seq #"[A-Z]" "HeLlO, WoRlD!"))