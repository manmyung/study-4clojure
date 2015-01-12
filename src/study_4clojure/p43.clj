;; 4Clojure Question 43
;;
;; Write a function which reverses the interleave process into x number of subsequences.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(apply map list (partition %2 %)) [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))

(= (__ (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))

(= (__ (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))

;me. apply map list 가 행렬을 transpose 하는 거구나. max와 답이 같다.
#(apply map list (partition %2 %))