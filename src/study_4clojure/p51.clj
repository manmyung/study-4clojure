;; 4Clojure Question 51
;;
;; Here is an example of some more sophisticated destructuring.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] __] [a b c d]))

;답
[1 2 3 4 5]

; :as를 다시한번 기억하자.