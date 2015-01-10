;; 4Clojure Question 95
;;
;; Write a predicate which checks whether or not a given sequence represents a <a href="http://en.wikipedia.org/wiki/Binary_tree">binary tree</a>.  Each node in the tree must have a value, a left child, and a right child.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn k [x]
      (if (coll? x)
        (let [[a b c] x]
          (if (and (= 3 (count x))
                   (k a)
                   (k b)
                   (k c))
            true
            false))
        (not (false? x)))) '(:a (:b nil nil) nil))
   true)

(= ((fn k [x]
      (if (coll? x)
        (let [[a b c] x]
          (if (and (= 3 (count x))
                   (k a)
                   (k b)
                   (k c))
            true
            false))
        (not (false? x)))) '(:a (:b nil nil)))
   false)

(= (__ [1 nil [2 [3 nil nil] [4 nil nil]]])
   true)

(= (__ [1 [2 nil nil] [3 nil nil] [4 nil nil]])
   false)

(= (__ [1 [2 [3 [4 nil nil] nil] nil] nil])
   true)

(= (__ [1 [2 [3 [4 false nil] nil] nil] nil])
   false)

(= (__ '(:a nil ()))
   false)

;이전
(fn _tree
  ([] false)
  ([x]
    (if (coll? x)
      (apply _tree x)
      (not= x false)))
  ([x y] false)
  ([x y z] (and (_tree x) (_tree y) (_tree z)))
  ([x y z & more] false)
  )

;지금. 이전보다는 좀더 깔끔.
(fn k [x]
  (if (coll? x)
    (let [[a b c] x]
      (if (and (= 3 (count x))
               (k a)
               (k b)
               (k c))
        true
        false))
    (not (false? x))))

;max
(fn ? [t]
  (or (and (coll? t)
           (= (count t) 3)
           (every? ? (rest t)))
      (nil? t)))
;나와 차이점
;- leaf node에 대해서 nil?로만 체크
;- every? 사용
;- every? 에 rest만 체크
;- if 대신 or 사용. 이건 생각하기 쉽진 않다.
;결론: 이 문제에 대해서는 max가 깔끔하게 구현했다. every? 기억하자.