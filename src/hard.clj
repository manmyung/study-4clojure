;; 4Clojure Question 53
;;
;; Given a vector of integers, find the longest consecutive sub-sequence of increasing numbers. If two sub-sequences have the same length, use the one that occurs first. An increasing sub-sequence must have a length of 2 or greater to qualify.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 0 1 2 3 0 4 5]) [0 1 2 3])

(= (__ [5 6 1 3 2 7]) [5 6])

(= (__ [2 3 3 4 5]) [3 4 5])

(= (__ [7 6 5 4]) [])

;me
(fn [l]
  (->>
    (reduce
    #(let [last-l (last %1)
           last-e (last last-l)]
      (if (or (nil? last-e) (>= last-e %2))
        (conj %1 [%2])
        (conj (vec (butlast %1)) (conj last-l %2))))
    [] l)
    (reduce #(if (< (count %1) (count %2)) %2 %1))
    ((fn [x] (if (< 1 (count x)) x [])))))

;daowen
#(loop [xs %, best-c 0, best-s '()]
  (if (< (count xs) 2) best-s
                       (let [s (map second (take-while (fn [p] (< (first p) (second p))) (partition 2 1 xs)))
                             ss (if (empty? s) s (cons (first xs) s))
                             c (count ss)
                             [next-c next-s] (if (> c best-c) [c ss] [best-c best-s])]
                         (recur (rest xs) next-c next-s))))

;hypirion. 코드는 간편한데 2부터 n+1의 길이를 모두 살펴보니 효율이 떨어질 듯.
(fn [coll]
  (->>
    (range 2 (inc (count coll)))
    (mapcat #(partition % 1 coll))
    (filter #(apply < %))
    (cons [])
    (sort-by count >)
    first))

;chouser
#(or
  (first
    (sort-by
      (comp - count)
      (for [f [(fn [[a b]] (= b (+ 1 a)))]
            p (partition-by f (map list % (next %)))
            r [`[~@(map first p) ~(last (last p))]]
            :when (f r)]
        r)))
  [])



;; 4Clojure Question 73
;;
;; A <a href="http://en.wikipedia.org/wiki/Tic-tac-toe">tic-tac-toe</a> board is represented by a two dimensional vector. X is represented by :x, O is represented by :o, and empty is represented by :e.  A player wins by placing three Xs or three Os in a horizontal, vertical, or diagonal row.  Write a function which analyzes a tic-tac-toe board and returns :x if X has won, :o if O has won, and nil if neither player has won.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= nil (__ [[:e :e :e]
            [:e :e :e]
            [:e :e :e]]))

(= :x (__ [[:x :e :o]
           [:x :e :e]
           [:x :e :o]]))

(= :o (__ [[:e :x :e]
           [:o :o :o]
           [:x :e :x]]))

(= nil (__ [[:x :e :o]
            [:x :x :e]
            [:o :x :o]]))

(= :x (__ [[:x :e :e]
           [:o :x :e]
           [:o :e :x]]))

(= :o (__ [[:x :e :o]
           [:x :o :e]
           [:o :e :x]]))

(= nil (__ [[:x :o :x]
            [:x :o :x]
            [:o :x :o]]))

;me
(fn [m]
  (let [positions [[[0 0] [0 1] [0 2]] [[1 0] [1 1] [1 2]] [[2 0] [2 1] [2 2]] [[0 0] [1 0] [2 0]] [[0 1] [1 1] [2 1]] [[0 2] [1 2] [2 2]] [[0 0] [1 1] [2 2]] [[0 2] [1 1] [0 2]]]]
    (some (fn [l]
            (let [result (map #(get-in m %) l)]
              (cond
                (every? #(= :o %) result) :o
                (every? #(= :x %) result) :x)
              )) positions)))

;psk810. some을 2번쓰고, apply map vector를 사용해서 코드가 짧다. 굿.
(fn [s]
  (letfn [(f [s] (some (fn [x] (when (every? #(= x %) s) x)) [:x :o]))
          (dia [[[a _ b] [_ x _] [c _ d]]] [[a x d] [b x c]])]
    (some f (concat s (apply map vector s) (dia s)))))

;chouser. 이것도 psk810과 비슷. (when (every? ... 대신 {[:x :x :x] :x ... 를 사용. 덜 general하지만 쉽다. 반면 diagonal 만드는 것은 psk810이 훨씬 간단.
#(some {[:x :x :x] :x [:o :o :o] :o}
       (concat % (apply map list %)
               (for [d [[[0 0] [1 1] [2 2]] [[2 0] [1 1] [0 2]]]]
                 (for [[x y] d] ((% x) y)))))

;daowen. 위에 비해 diag를 general하게 한듯.
(fn [rows]
  (let [cols (apply map vector rows)
        diag (fn [x] (map-indexed #(nth %2 %) x))
        diags [(diag rows) (diag (map reverse rows))]
        lines (concat rows cols diags)
        line-sets (map set lines)]
    (first (some #{#{:x} #{:o}} line-sets))))



;; 4Clojure Question 92
;;
;; Roman numerals are easy to recognize, but not everyone knows all the rules necessary to work with them. Write a function to parse a Roman-numeral string and return the number it represents.
;;
;; <br /><br />
;;
;; You can assume that the input will be well-formed, in upper-case, and follow the <a href="http://en.wikipedia.org/wiki/Roman_numerals#Subtractive_principle">subtractive principle</a>. You don't need to handle any numbers greater than MMMCMXCIX (3999), the largest number representable with ordinary letters.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 14 (__ "XIV"))

(= 827 (__ "DCCCXXVII"))

(= 3999 (__ "MMMCMXCIX"))

(= 48 (__ "XLVIII"))

;me
(let [m {"M" 1000 "CM" 900 "D" 500 "CD" 400 "C" 100 "XC" 90 "L" 50 "XL" 40 "X" 10 "IX" 9 "V" 5 "IV" 4 "I" 1}]
  (fn k [s]
    (let [v2 (m (apply str (take 2 s)))
          v1 (m (apply str (take 1 s)))]
      (cond
        v2 (+ v2 (k (drop 2 s)))
        v1 (+ v1 (k (drop 1 s)))
        :else 0
        ))))

;max. 로마자 규칙을 깊이 음미하다보면 이렇게 만들 수 있을 듯.
#(reduce
  (fn [m [x y]] ((if y (if (< x y) - +) +) m x))
  0
  (partition-all 2 1 (map {\I 1 \V 5 \X 10 \L 50 \C 100 \D 500 \M 1000} %)))

;amalloy. 이것도 max와 비슷
(fn [s]
  (reduce (fn [sum [a b]]
            ((if (< a b) - +) sum a))
          0
          (partition 2 1 (concat (map {\I 1
                                       \V 5
                                       \X 10
                                       \L 50
                                       \C 100
                                       \D 500
                                       \M 1000} s)
                                 [0]))))