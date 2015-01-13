;; 4Clojure Question 70
;;
;; Write a function that splits a sentence up into a sorted list of words.  Capitalization should not affect sort order and punctuation should be ignored.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__  "Have a nice day.")
   ["a" "day" "Have" "nice"])

(= (__  "Clojure is a fun language!")
   ["a" "Clojure" "fun" "is" "language"])

(= (__  "Fools fall for foolish follies.")
   ["fall" "follies" "foolish" "Fools" "for"])

;me.
#(sort-by clojure.string/lower-case (clojure.string/split % #"[ .!]"))

;psk81. 기억할 점. #"\W" 영숫자_ 가 아닌 문자. http://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D
(fn [s]
  (sort #(.compareToIgnoreCase %1 %2) (clojure.string/split s #"\W")))

;max. 기억할 점. #"w+" 영숫자_ 1회 이상. re-seq 매칭패턴 찾아서 sequence로.
#(sort-by (fn [c] (.toLowerCase c)) (re-seq #"\w+" %))