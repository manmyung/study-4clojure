;; 4Clojure Question 42
;;
;; Write a function which calculates factorials.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1) 1)

(= (#(reduce * (take % (iterate inc 1))) 3) 6)

(= (#(reduce * (range 1 (inc %))) 5) 120)

(= (__ 8) 40320)

#(reduce * (take % (iterate inc 1)))

;이전답
#(reduce *(map inc (range %)))

;이전답을 보니 이게 더 좋을듯. 이걸 4clojure에 넣어보자.
#(reduce * (range 1 (inc %)))

;norman. 이 답도 좋다.
#(apply * (range 1 (inc %)))