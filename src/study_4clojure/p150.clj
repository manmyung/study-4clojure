;; 4Clojure Question 150
;;
;; <p>A palindromic number is a number that is the same when written forwards or backwards (e.g., 3, 99, 14341).</p>
;;
;; 
;;
;; <p>Write a function which takes an integer <code>n</code>, as its only argument, and returns an increasing lazy sequence of all palindromic numbers that are not less than <code>n</code>.</p>
;;
;; 
;;
;; <p>The most simple solution will exceed the time limit!</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 26 (__ 0))
   [0 1 2 3 4 5 6 7 8 9 
    11 22 33 44 55 66 77 88 99 
    101 111 121 131 141 151 161])

(= (take 16 (__ 162))
   [171 181 191 202 
    212 222 232 242 
    252 262 272 282 
    292 303 313 323])

(= (take 6 (__ 1234550000))
   [1234554321 1234664321 1234774321 
    1234884321 1234994321 1235005321])

(= (first (__ (* 111111111 111111111)))
   (* 111111111 111111111))

(= (set (take 199 (__ 0)))
   (set (map #(first (__ %)) (range 0 10000))))

(= true 
   (apply < (take 6666 (__ 9999999))))

(= (nth (__ 0) 10101)
   9102019)

;첫번째 단순한 버전. timeout에 걸렸다.
(let [palin? (fn [n]
               (let [n-str (str n)]
                 (= (seq n-str) (reverse n-str))))]
  (fn palin-seq [n]
    (if (palin? n)
      (cons n (lazy-seq (palin-seq (inc n))))
      (lazy-seq (palin-seq (inc n))))))

;두번째. 복잡하고 지저분하다. 그런데 답을 보니 다른 사람도 만만치 않네. 별로 좋은 문제가 아닌 것 같다.
(let [palin? (fn [n]
               (let [n-str (str n)]
                 (= (seq n-str) (reverse n-str))))
      next-n (fn [n]
               (let [exp (fn [i] (reduce * (repeat i 10)))
                     x (count (str n))
                     add-n (if (odd? x)
                             (let [k (/ (- x 1) 2)]
                               (if (= \9 (nth (str n) k))
                                 1
                                 (exp k)))
                             (let [k (/ (- x 2) 2)]
                               (if (= [\9 \9] (take 2 (drop k (str n))))
                                 1
                                 (* 11 (exp k)))))
                     next-n-cand (+ add-n n)
                     y (count (str next-n-cand))]
                 (if (= x y)
                   next-n-cand
                   (inc (exp (dec y))))))]
  (fn palin-seq [n]
    (if (palin? n)
      (cons n (lazy-seq (palin-seq (next-n n))))
      (lazy-seq (palin-seq (inc n))))))

;max
(fn [n]
  (let [d #(loop [m % i 0]
            (if (> m 0)
              (recur (quot m 10) (inc i))
              i))
        r #(loop [m % a 0]
            (if (> m 0)
              (recur (quot m 10) (* 10 (+ a (rem m 10))))
              (/ a 10)))
        p #(loop [i % a 1]
            (if (> i 0)
              (recur (dec i) (* 10 a))
              a))
        t #(p (quot (d %) 2))
        w #(let [k (t %)
                 q (- % (rem % k) (- k))]
            (+ q (rem (r q) (if (= 9 %) 11 k))))
        k (t n)
        q (- n (rem n k))
        v (+ q (rem (r q) k))
        m (if (>= v n) v (w n))]
    (iterate w m)))

;chouser
(fn [s]
  (let [p #(Long. (apply str % ((if %2 next seq) (reverse (str %)))))
        d #(count (str %))
        o #(odd? (d %))
        b #(Long. (subs (str %) 0 (quot (+ 1 (d %)) 2)))
        i #(let [x (b %)
                 o (o %)
                 y (+ 1 x)]
            (cond
              (= (d x) (d y)) (p y o)
              o (p (/ y 10) nil)
              1 (p y 1)))]
    (filter #(>= % s) (iterate i (p (b s) (o s))))))

;jafingerhut

;; Note that all palindromic numbers are either of the form:

;; (concat x (reverse x)) for some sequence of one or more digits x,
;; so the palindromic number has an even number of digits.

;; or:

;; (concat x [ d ] (reverse x)) for some sequence of digits x (perhaps
;; empty) and some digit 'd', so the palindromic number has an odd
;; number of digits.

;; To generate the palindromic numbers in increasing order, we just
;; need to make the sequence x (for even-length) or (concat x [d])
;; (for odd-length) represent incrementing decimal numbers.

;; function next-pal returns the smallest palindromic number that is
;; greater than or equal to its argument.

(fn [n]
  (let [to-digits (fn [n] (map #(- (int %) (int \0)) (str n)))
        to-int (fn [s] (read-string (apply str s)))
        make-pal (fn [digits odd rev-digits]
                   (to-int (concat digits
                                   (if odd (rest rev-digits) rev-digits))))
        next-pal (fn [n]
                   (let [digits (to-digits n)
                         len (count digits)
                         half-len (quot (inc len) 2)
                         half-digits (take half-len digits)
                         pal (make-pal half-digits
                                       (odd? len) (reverse half-digits))]
                     (if (>= pal n)
                       pal
                       (let [half-digits+1 (to-digits
                                             (inc (to-int half-digits)))]
                         (make-pal (if (> (count half-digits+1) half-len)
                                     (butlast half-digits+1)
                                     half-digits+1)
                                   (odd? len)
                                   (reverse half-digits+1))))))]
    (iterate #(next-pal (inc %))
             (next-pal n))))
