;; 4Clojure Question 68
;;
;; Clojure only has one non-stack-consuming looping construct: recur.  Either a function or a loop can be used as the recursion point.  Either way, recur rebinds the bindings of the recursion point to the values it is passed.  Recur must be called from the tail-position, and calling it elsewhere will result in an error.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [7 6 5 4 3]
   (loop [x 5
          result []]
     (if (> x 0)
       (recur (dec x) (conj result (+ 2 x)))
       result)))

;지금까지 코드에서 recur가 마지막쯤에 오는게 관례라고 생각했는데 알고보니 그렇지 않으면 에러가 나네.