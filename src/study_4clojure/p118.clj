;; 4Clojure Question 118
;;
;; <p>Map is one of the core elements of a functional programming language. Given a function <code>f</code> and an input sequence <code>s</code>, return a lazy sequence of <code>(f x)</code> for each element <code>x</code> in <code>s</code>.
;;
;; Restrictions (please don't use these function(s)): map, map-indexed, mapcat, for
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [3 4 5 6 7]
   ((fn k [f col]
      (if (empty? col)
        col
        (cons (f (first col)) (lazy-seq (k f (rest col)))))) inc [2 3 4 5 6]))

(= (repeat 10 nil)
   (__ (fn [_] nil) (range 10)))

(= [1000000 1000001]
   (->> (__ inc (range))
        (drop (dec 1000000))
        (take 2)))

(
(fn k [f col]
  (if (empty? col)
    col
    (cons (f (first col)) (lazy-seq (k f (rest col))))))
  inc [2 3 4 5 6])

;지금. 이전이랑 거의 똑같네.
(fn k [f col]
  (if (empty? col)
    col
    (cons (f (first col)) (lazy-seq (k f (rest col))))))

;이전
(fn _map [f col]
  (if (empty? col)
    []
    (cons (f (first col)) (lazy-seq (_map f (rest col))))))

;max. 여기서 배울 점 destructuring.
(fn m [f [h & t :as v]]
  (if (empty? v)
    ()
    (lazy-seq (cons (f h) (m f t)))))

;chouser. 여기서 배울 점 if를 lazy-seq 안에서 쓸 수 있다는 점.
(fn l [f [a & m]]
  (lazy-seq
    (cons (f a) (if m (l f m)))))