;; 4Clojure Question 168
;;
;; <p>
;;
;; In what follows, <code>m</code>, <code>n</code>, <code>s</code>, <code>t</code> 
;;
;; denote nonnegative integers, <code>f</code> denotes a function that accepts two 
;;
;; arguments and is defined for all nonnegative integers in both arguments.
;;
;; </p>
;;
;; 
;;
;; <p>
;;
;; In mathematics, the function <code>f</code> can be interpreted as an infinite 
;;
;; <a href="http://en.wikipedia.org/wiki/Matrix_%28mathematics%29">matrix</a>
;;
;; with infinitely many rows and columns that, when written, looks like an ordinary 
;;
;; matrix but its rows and columns cannot be written down completely, so are terminated 
;;
;; with ellipses. In Clojure, such infinite matrix can be represented 
;;
;; as an infinite lazy sequence of infinite lazy sequences, 
;;
;; where the inner sequences represent rows.
;;
;; </p> 
;;
;; 
;;
;; <p>
;;
;; Write a function that accepts 1, 3 and 5 arguments
;;
;; <ul>
;;
;; <li>
;;
;; with the argument <code>f</code>, it returns the infinite matrix <b>A</b>  
;;
;; that has the entry in the <code>i</code>-th row and the <code>j</code>-th column 
;;
;; equal to <code>f(i,j)</code> for <code>i,j = 0,1,2,...</code>;</li>
;;
;; <li>
;;
;; with the arguments <code>f</code>, <code>m</code>, <code>n</code>, it returns 
;;
;; the infinite matrix <b>B</b> that equals the remainder of the matrix <b>A</b> 
;;
;; after the removal of the first <code>m</code> rows and the first <code>n</code> columns;</li>
;;
;; <li>
;;
;; with the arguments <code>f</code>, <code>m</code>, <code>n</code>, <code>s</code>, <code>t</code>,
;;
;; it returns the finite s-by-t matrix that consists of the first t entries of each of the first 
;;
;; <code>s</code> rows of the matrix <b>B</b> or, equivalently, that consists of the first s entries 
;;
;; of each of the first <code>t</code> columns of the matrix <b>B</b>.</li>
;;
;; </ul>
;;
;; </p>
;;
;; Restrictions (please don't use these function(s)): for, range, iterate, repeat, cycle, drop
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 5 (map #(take 6 %) (__ str)))
   [["00" "01" "02" "03" "04" "05"]
    ["10" "11" "12" "13" "14" "15"]
    ["20" "21" "22" "23" "24" "25"]
    ["30" "31" "32" "33" "34" "35"]
    ["40" "41" "42" "43" "44" "45"]])

(= (take 6 (map #(take 5 %) (__ str 3 2)))
   [["32" "33" "34" "35" "36"]
    ["42" "43" "44" "45" "46"]
    ["52" "53" "54" "55" "56"]
    ["62" "63" "64" "65" "66"]
    ["72" "73" "74" "75" "76"]
    ["82" "83" "84" "85" "86"]])

(= (__ * 3 5 5 7)
   [[15 18 21 24 27 30 33]
    [20 24 28 32 36 40 44]
    [25 30 35 40 45 50 55]
    [30 36 42 48 54 60 66]
    [35 42 49 56 63 70 77]])

(= (__ #(/ % (inc %2)) 1 0 6 4)
   [[1/1 1/2 1/3 1/4]
    [2/1 2/2 2/3 1/2]
    [3/1 3/2 3/3 3/4]
    [4/1 4/2 4/3 4/4]
    [5/1 5/2 5/3 5/4]
    [6/1 6/2 6/3 6/4]])

(= (class (__ (juxt bit-or bit-xor)))
   (class (__ (juxt quot mod) 13 21))
   (class (lazy-seq)))

(= (class (nth (__ (constantly 10946)) 34))
   (class (nth (__ (constantly 0) 5 8) 55))
   (class (lazy-seq)))

(= (let [m 377 n 610 w 987
         check (fn [f s] (every? true? (map-indexed f s)))
         row (take w (nth (__ vector) m))
         column (take w (map first (__ vector m n)))
         diagonal (map-indexed #(nth %2 %) (__ vector m n w w))]
     (and (check #(= %2 [m %]) row)
          (check #(= %2 [(+ m %) n]) column)
          (check #(= %2 [(+ m %) (+ n %)]) diagonal)))
   true)

;me. 첫번째 버전. 코드는 복잡하지만 동일한 iter를 사용하므로 지금의 2차원을 넘어 무한 차원의 가능성을 생각할 수 있다.
(fn k
  ([f]
   (k f 0 0))
  ([f i-min j-min i-max j-max]
   (take i-max (map #(take j-max %) (k f i-min j-min))))
  ([f i-min j-min]
   (let [iter (fn iter [f x] (cons x (lazy-seq (iter f (f x)))))
         m (iter (fn [l] (map (fn [a] (assoc a 0 (inc (first a)))) l))
                 (iter (fn [b] (assoc b 1 (inc (second b)))) [i-min j-min]))]
     (map (fn [ijs] (map (fn [ij] (apply f ij)) ijs)) m))))

;me. 두번째 버전. 더 단순. lazy-seq에 인자를 갖고 다닐 수 있다는 점을 다시 깨닫게 된다.
(fn matrix
  ([f]
   (matrix f 0 0))
  ([f i-min j-min i-max j-max]
   (take i-max (map #(take j-max %) (matrix f i-min j-min))))
  ([f i-min j-min]
   (let [row (fn row [f i j]
               (lazy-seq (cons (f i j) (lazy-seq (row f i (inc j))))))]
     (lazy-seq (cons (row f i-min j-min) (lazy-seq (matrix f (inc i-min) j-min)))))))

;psk810. 나와 비슷. lazy-seq를 2번만 사용한 점이 더 좋다. 나도 안쪽 lazy-seq는 사용할 필요 없었다.
(fn d
  ([c f] (c f 0 0))
  ([c f m n] (c f m n))
  ([c f m n s t] (take s (map #(take t %) (c f m n)))))

(fn c [f m n]
  (letfn [(r [f k n]
             (lazy-seq (cons (apply f [k n]) (r f k (inc n)))))]
    (lazy-seq (cons (r f m n) (c f (inc m) n)))))

;silverio. 이건 나와 더 비슷.
(letfn [(row [f i j]
             (lazy-seq (cons (f i j) (row f i (+ j 1)))))]
  (fn mtx
    ([f] (mtx f 0 0))
    ([f m n] (lazy-seq (cons (row f m n) (mtx f (+ m 1) n))))
    ([f m n s t] (take s (map #(take t %) (mtx f m n))))))

;max. 나중을 위해 적어만 둔다.
(fn g
  ([f]
   (g f 0 0))
  ([f m n]
   (g f m n nil nil))
  ([f m n s t]
   ((fn r [i]
      (lazy-seq
        (if (= (- i m) s)
          ()
          (cons
            ((fn c [j]
               (lazy-seq
                 (if (= (- j n) t)
                   ()
                   (cons
                     (f i j)
                     (c (inc j)))))) n)
            (r (inc i)))))) m)))

;chouser. 나중을 위해 적어만 둔다.
(fn [r f & [m n s t]]
  (map #(map (fn [x] (f % x)) (r n t)) (r m s)))
(fn r [a b]
  ({0 ()} b (lazy-cat [(or a 0)]
                      (r (+ (or a 0) 1) (- (or b 0) 1)))))

