;; 4Clojure Question 47
;;
;; The contains? function checks if a KEY is present in a given collection.  This often leads beginner clojurians to use it incorrectly with numerically indexed collections like vectors and lists.
;;
;; Use M-x 4clojure-check-answers when you're done!

(contains? #{4 5 6} 4)

(contains? [1 1 1 1 1] __)

(contains? {4 :a 2 :b} __)

(not (contains? '(1 2 4) __))

(contains? [1 1 1 2 2] 5)

;답이 4구나
vector인 경우는 값이 아니라 index를 판단하는 구나.