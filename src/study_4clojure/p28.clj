;; 4Clojure Question 28
;;
;; Write a function which flattens a sequence.
;;
;; Restrictions (please don't use these function(s)): flatten
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn flat
      [[h & t :as x]]
      (when x
        (if (coll? h)
          (concat (flat h) (flat t))
          (cons h (flat t))
          )))
     '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(= ((fn flat
      [[h & t :as x]]
      (when x
        (if (coll? h)
          (concat (flat h) (flat t))
          (cons h (flat t))
          ))) ["a" ["b"] "c"]) '("a" "b" "c"))

(= ((fn flat
      [[h & t :as x]]
      (when x
        (if (coll? h)
          (concat (flat h) (flat t))
          (cons h (flat t))
          ))) '((((:a))))) '(:a))

(defn flat
  [[h & t :as x]]
  (print ["flat->" h t x])
  (when x
    (if (coll? h)
      (concat (flat h) (flat t))
      (cons h (flat t))
      )))

(flat nil)

(= [1] (flat [1]))
(= [1 2] (flat [1 2]))
(= [1 2 3] (flat [[1] 2 3]))
(= [1 2 3] (flat [[1] [2 3]]))
(= [1 2 3] (flat [[[1]] [2 3]]))

(fn flat
  [[h & t :as x]]
  (when x
    (if (coll? h)
      (concat (flat h) (flat t))
      (cons h (flat t))
      )))

;이전 답
#(letfn
  [
   (flat [x y]
         (cond
           (sequential? y)
           (reduce flat x y)
           :else
           (conj x y)
           )
         )
   ]
  (flat [] %)
  )

;이 문제에서 배운점
; 1. 잘 안풀리면 test case를 만들어서 풀자.
; 2. :as의 용법

;Max와 비슷. 단지 Max는 h를 사용. x가 nil이면 h도 nil인점 때문에 이렇게 해도 된다. 그러나 비슷
(fn f [[h & t]]
  (if h
    (if (coll? h)
      (concat (f h) (f t))
      (cons h (f t)))))

;그러나 좀더 좋은 답은 daowen. flat을 부분에 대해서 다시 적용한다는 점이 명료하다!
(fn flat [x]
  (if (coll? x)
    (mapcat flat x)
    [x]))
