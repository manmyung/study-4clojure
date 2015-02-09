;; 4Clojure Question 98
;;
;; A function f defined on a domain D induces an <a href="http://en.wikipedia.org/wiki/Equivalence_relation">equivalence relation</a> on D, as follows: a is equivalent to b with respect to f if and only if (f a) is equal to (f b).  Write a function with arguments f and D that computes the <a href="http://en.wikipedia.org/wiki/Equivalence_class">equivalence classes</a> of D with respect to f.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #(* % %) #{-2 -1 0 1 2})
   #{#{0} #{1 -1} #{2 -2}})

(= (__ #(rem % 3) #{0 1 2 3 4 5 })
   #{#{0 3} #{1 4} #{2 5}})

(= (__ identity #{0 1 2 3 4})
   #{#{0} #{1} #{2} #{3} #{4}})

(= (__ (constantly true) #{0 1 2 3 4})
   #{#{0 1 2 3 4}})

;me
(fn [f s]
  (set (vals (reduce #(merge-with clojure.set/union %1 {(f %2) #{%2}}) {} s))))

;max.
#(set (map set (vals (group-by % %2))))

(vals (group-by #(* % %) #{-2 -1 0 1 2}))
;이번 문제는 group-by를 기억하지 못해서 코드도 길어지고 고생했다.