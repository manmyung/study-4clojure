;; 4Clojure Question 76
;;
;; The trampoline function takes a function f and a variable number of parameters.  Trampoline calls f with any parameters that were supplied.  If f returns a function, trampoline calls that function with no arguments.  This is repeated, until the return value is not a function, and then trampoline returns that non-function value.  This is useful for implementing mutually recursive algorithms in a way that won't consume the stack.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [1 3 5 7 9 11]
   (letfn
     [(foo [x y] #(bar (conj x y) y))
      (bar [x y] (if (> (last x) 10)
                   x
                   #(foo x (+ 2 y))))]
     (trampoline foo [] 1)))

;trampoline은 함수가 리턴되면 그 함수를 다시 콜한다. 인자가 리턴되면 끝.