;; 4Clojure Question 60
;;
;; Write a function which behaves like reduce, but returns each intermediate value of the reduction.  Your function must accept either two or three arguments, and the return sequence must be lazy.
;;
;; Restrictions (please don't use these function(s)): reductions
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 5 (__ + (range))) [0 1 3 6 10])

(= (__ conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])

(= (last (__ * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)

;처음 답. 오래 걸렸다. 겨우겨우 만들었다. recursive하게 생각하기가 힘들다.
(fn
  ([f c]
    (for [i (map inc (range))
          :let [sc (take i c)]
          :while (not-empty (drop (dec i) c))]
      (reduce f sc)))
  ([f v c]
    (for [i (range)
          :let [sc (take i c)]
          :while (not-empty (drop (dec i) c))]
      (reduce f v sc))))

;다음 답. lazy-seq를 사용할 수 있다는 것을 알고 난 후. 결국 (f v x) 를 v 자리에 넣는 것이 핵심.
(fn r
  ([f [x & xs]]
    (cons x (lazy-seq (when (first xs)
                        (r f (f x (first xs)) (rest xs))))))
  ([f v [x & xs]]
    (cons v (lazy-seq (when x
                        (r f (f v x) xs))))))

;max. 함수 선언이 뒤에 있어도 사용가능.
(fn g
  ([f [x & s]] (g f x s))
  ([f a [x & s]]
    (lazy-seq
      (cons a (if x (g f (f a x) s))))))