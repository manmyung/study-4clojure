;; 4Clojure Question 114
;;
;; <p><a
;;
;; href="http://clojuredocs.org/clojure_core/clojure.core/take-while">take-while</a>
;;
;; is great for filtering sequences, but it limited: you can only examine
;;
;; a single item of the sequence at a time. What if you need to keep
;;
;; track of some state as you go over the sequence?</p>
;;
;; 
;;
;; <p>Write a function which accepts an integer <code>n</code>, a predicate <code>p</code>, and a sequence. It should return a lazy sequence of items in the list up to, but not including, the <code>n</code>th item that satisfies the predicate.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [2 3 5 7 11 13]
   (__ 4 #(= 2 (mod % 3))
         [2 3 5 7 11 13 17 19 23]))

(= ["this" "is" "a" "sentence"]
   (__ 3 #(some #{\i} %)
         ["this" "is" "a" "sentence" "i" "wrote"]))

(= ["this" "is"]
   (__ 1 #{"a"}
         ["this" "is" "a" "sentence" "i" "wrote"]))

(take-while neg? [-2 -1 0 1 -32 3]) ;=> (-2 -1)

;me. take-while의 정의를 보고 따라하니 쉬웠다.
(fn k [n pred coll]
  (lazy-seq
    (let [next-n (if (pred (first coll)) (dec n) n)]
      (when (> next-n 0)
        (cons (first coll) (k next-n pred (rest coll)))))))

;amalloy. 밑의 clojuredocs의 내용과 take-while의 정의를 보면 when-let과 seq 를 사용하는 것은 1.성능과 2.nil 인 경우 밑으로 안 들어가기 위한 일종의 패턴
(fn f [n pred coll]
  (lazy-seq
    (when-let [[x & xs] (seq coll)]
      (let [n (if (pred x), (dec n), n)]
        (when-not (zero? n)
          (cons x (f n pred xs)))))))
;그리고 [x & xs] 사용하는 방식이 first를 두번 쓰지 않아도 되므로 좋다.

;clojuredocs의 내용
;; Very useful when working with sequences. Capturing the return value
;; of `seq` brings a performance gain in subsequent `first`/`rest`/`next`
;; calls. Also the block is guarded by `nil` punning.

(defn drop-one
  [coll]
  (when-let [s (seq coll)]
    (rest s)))

(drop-one [1 2 3]) ;=> (2 3)
(drop-one []) ;=> nil
;~clojuredocs의 내용

(seq []) ;=> nil