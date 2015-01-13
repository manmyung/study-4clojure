;; 4Clojure Question 54
;;
;; Write a function which returns a sequence of lists of x items each.  Lists of less than x items should not be returned.
;;
;; Restrictions (please don't use these function(s)): partition, partition-all
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 3 (range 9)) '((0 1 2) (3 4 5) (6 7 8)))

(= (__ 2 (range 8)) '((0 1) (2 3) (4 5) (6 7)))

(= (__ 3 (range 8)) '((0 1 2) (3 4 5)))

;me. 재귀로 해보려다가 잘 안되어서 loop로 했음.
#(loop [c %2 result []]
  (if (> % (count c))
    result
    (recur (drop % c) (conj result (take % c)))))

;max.
(fn p [n x]
  (if (>= (count x) n)
    (cons (take n x) (p n (drop n x))))
(cons '(0 1 2) nil) ;=> ((0 1 2)). 이야 cons에 nil을 붙이면 빈리스트에 붙이는 것과 같은 효과를 이용. 대단. 아. lazy-seq 사용할 때 많이 나오는 패턴이구나.