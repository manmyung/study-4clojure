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

;; 4Clojure Question 79
;;
;; Write a function which calculates the sum of the minimal path through a triangle.  The triangle is represented as a collection of vectors.  The path should start at the top of the triangle and move to an adjacent number on the next row until the bottom of the triangle is reached.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 7 (__ '([1]
          [2 4]
         [5 1 4]
        [2 3 4 5]))) ; 1->2->1->3

(= 20 (__ '([3]
           [2 4]
          [1 9 3]
         [9 9 2 4]
        [4 6 6 7 8]
       [5 7 3 5 1 4]))) ; 3->4->3->2->7->1

;me
(fn k
  ([l] (k l 0))
  ([l i]
   (if l
     (let [[v & vs] l]
       (+ (nth v i) (min
                      (k vs i)
                      (k vs (inc i)))))
     0)))

;chouser. 나와 거의 비슷. 차이 1. nth 대신 vector를 함수로 사용한 점이 차이. 2. 인수분해를 함수 인자에서 바로 함.
;그리고 k보다는 f가 이해가 좀더 쉽다.
(fn f
  ([t] (f 0 t))
  ([i [r & t]]
   (+ (r i)
      (if t
        (min (f i t) (f (inc i) t))
        0))))

;; 4Clojure Question 84
;;
;; Write a function which generates the <a href="http://en.wikipedia.org/wiki/Transitive_closure">transitive closure</a> of a <a href="http://en.wikipedia.org/wiki/Binary_relation">binary relation</a>.  The relation will be represented as a set of 2 item vectors.
;;
;; Use M-x 4clojure-check-answers when you're done!

(let [divides #{[8 4] [9 3] [4 2] [27 9]}]
  (= (__ divides) #{[4 2] [8 4] [8 2] [9 3] [27 9] [27 3]}))

(let [more-legs
      #{["cat" "man"] ["man" "snake"] ["spider" "cat"]}]
  (= (__ more-legs)
     #{["cat" "man"] ["cat" "snake"] ["man" "snake"]
       ["spider" "cat"] ["spider" "man"] ["spider" "snake"]}))

(let [progeny
      #{["father" "son"] ["uncle" "cousin"] ["son" "grandson"]}]
  (= (__ progeny)
     #{["father" "son"] ["father" "grandson"]
       ["uncle" "cousin"] ["son" "grandson"]}))

;me
(fn [s]
  (let [m (into {} s)
        f (fn f [[a b :as c]]
            (when b
              (cons c (f [a (m b)]))))]
    (reduce #(into %1 (f %2)) #{} s)))

;max
(fn f [s]
  (#(if (= s %) % (f %))
    (reduce (fn [a [x y]]
              (into a (keep (fn [[u v]] (if (= u y) [x v])) s)))
            s
            s)))

;chouser
#(set (mapcat
        (fn f [[a b :as p] s]
          (cons p
                (mapcat (fn [[c d]]
                          (if (= c b)
                            (cons [a d] (f [a d] (disj s p)))))
                        s)))
        %
        (repeat %)))


;; 4Clojure Question 82
;;
;; A word chain consists of a set of words ordered so that each word differs by only one letter from the words directly before and after it.  The one letter difference can be either an insertion, a deletion, or a substitution.  Here is an example word chain:<br/><br/>cat -> cot -> coat -> oat -> hat -> hot -> hog -> dog<br/><br/>Write a function which takes a sequence of words, and returns true if they can be arranged into one continous word chain, and false if they cannot.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true (__ #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"}))

(= false (__ #{"cot" "hot" "bat" "fat"}))

(= false (__ #{"to" "top" "stop" "tops" "toss"}))

(= true (__ #{"spout" "do" "pot" "pout" "spot" "dot"}))

(= true (__ #{"share" "hares" "shares" "hare" "are"}))

(= false (__ #{"share" "hares" "hare" "are"}))

;me
(fn [s]
  (let [diff (fn diff [s1 s2]
               (cond
                 (empty? s1) (count s2)
                 (empty? s2) (count s1)
                 :else
                 (min
                   (+ (if (= (first s1) (first s2)) 0 1)
                      (diff (rest s1) (rest s2)))
                   (+ 1 (diff (rest s1) s2))
                   (+ 1 (diff s1 (rest s2))))))
        relative (into
                   (reduce #(into %1 {%2 (keep (fn [x] (if (= 1 (diff %2 x)) x nil)) s)}) {} s)
                   {nil (seq s)})
        search (fn search [path visited]
                 (let [last-chains (relative (last path))
                       last-chains-except (remove #(visited %) last-chains)]
                   (if (empty? last-chains-except)
                     [path]
                     (mapcat #(search (conj path %) (conj visited %)) last-chains-except))))
        n (count s)]
    (true? (some #(= n (count %)) (search [] #{})))
    ))

;silverio.
#(let [
       nv    (count %)
       words (map seq %)
       edge? (fn edge? [w1 w2]
               (let [[h1 & t1] w1 [h2 & t2] w2]
                 (if (= h1 h2) (edge? t1 t2)
                               (or (= t1 t2)     ; substitution
                                   (= t1 w2)     ; deletion
                                   (= w1 t2))))) ; insertion
       graph (apply (partial merge-with concat) (for [a words b words
                                                      :when (and (not= a b) (edge? a b))] {a [b]}))
       chain (fn chain [visited w] ; depth-first search for the chain
               (or (= nv (inc (count visited)))
                   (some (partial chain (conj visited w))
                         (filter (comp not visited) (graph w)))))]
  (not (not-any? (partial chain #{}) (keys graph))))
; edge? 를 지금 상황에 딱 적합하게 작성했음.
; graph 만드는 코드가 이해가 쉽게 잘 되어 있다. for로 돌리고 merge-with concat 모으기.
; chain도 모든 path를 다 만드는 것이 아니라 만족하면 true 리턴. 이 chain은 정말 내가 만들고 싶었던 코드이다.

;psk810. 정말 간략하다. 굿!. silverio와 비교하면 visited를 인자로 넣은 것이 아니라 non-visited를 인자로 넣었다.
(fn [s]
  (letfn [(ch? [s1 s2]
               (loop [[a & b :as c] (seq s1) [d & e :as g] (seq s2)]
                 (if (= a d) (recur b e)
                             (or (= b e) (= b g) (= c e)))))
          (t [e s] (if (empty? s) true (some #(t % (disj s %)) (filter #(ch? e %) s))))]
    (if (some #(t % (disj s %)) s) true false)))

;; 4Clojure Question 89
;;
;; Starting with a graph you must write a function that returns true if it is possible to make a tour of the graph in which every edge is visited exactly once.<br/><br/>The graph is represented by a vector of tuples, where each tuple represents a single edge.<br/><br/>The rules are:<br/><br/>- You can start at any node.<br/>- You must visit each edge exactly once.</br>- All edges are undirected.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true (__ [[:a :b]]))

(= false (__ [[:a :a] [:b :b]]))

(= false (__ [[:a :b] [:a :b] [:a :c] [:c :a]
               [:a :d] [:b :d] [:c :d]]))

(= true (__ [[1 2] [2 3] [3 4] [4 1]]))

(= true (__ [[:a :b] [:a :c] [:c :b] [:a :e]
              [:b :e] [:a :d] [:b :d] [:c :e]
              [:d :e] [:c :f] [:d :f]]))

(= false (__ [[1 2] [2 3] [2 4] [2 5]]))

;me. 좀 복잡하게 코드가 나왔다. 그 이유는 visited에 edge를 넣어서이다. node를 넣는 방식으로 했으면 그냥 넣고 뒤집어 넣는 두가지 경우가 필요없다. 밑의 daowen이 그것을 보여준다. 그리고 my-desj는 좀더 간단한 방법이 있을 것 같은데 daowen이 자바 유틸을 사용한 것 이외에 다른 방법은 못찾았다.
(fn [a]
  (let [rev (fn [[x y]] [y x])
        my-disj (fn [col x]
                  (loop [col col result []]
                    (if (nil? col)
                      result
                      (let [[v & vs] col]
                        (if (= v x)
                          (concat result vs)
                          (recur vs (conj result v)))))))
        k (fn k [visited rest-set]
            (if (empty? rest-set)
              true
              (some #(let [last-n (last (last visited))]
                      (cond
                        (= last-n (first %)) (k (conj visited %) (my-disj rest-set %))
                        (= last-n (last %)) (k (conj visited (rev %)) (my-disj rest-set %))))
                    rest-set)))]
    (true? (some #(or (k (conj [] %) (my-disj a %))
                      (k (conj [] (rev %)) (my-disj a %)))
                 a))))

;daowen
(fn tourable? [es]
  (let [edges (for [[a b] es] (assoc {} a b b a))
        del #(doto (java.util.LinkedList. %) (.remove %2))
        t? (fn t? [v es]
             (or (empty? es)
                 (some #(t? (% v) (del es %)) (filter #(% v) es))))]
    (boolean (some #(t? % edges) (-> es flatten distinct)))))

(#(doto (java.util.LinkedList. %) (.remove %2)) [1 2 3] 2)
;psk810
(fn [s]
  (letfn [(find-relations [n ts] (filter (fn [x] (some #(= n %) x)) ts))
          (omit [n s] (concat (take n s) (drop (inc n) s)))
          (omit-item [e s] (some (fn [[n v]] (if (= v e) (omit n s)))  (map-indexed #(vector % %2) s)))
          (other [x [a b]] (if (= b x) a b))
          (make-graph [n s] (if (empty? s) true
                                           (some #(make-graph (other n %) (omit-item % s)) (find-relations n s))))]
    (if (nil? (some #(make-graph (second %) (omit-item % s)) s)) false true)))

;jafingerhut. Eulerian Path 이론을 이용한 답.
(fn [edges]
  (and (= 1 (count (reduce (fn [c [u v]]
                             (let [s (or (first (filter #(% u) c)) #{u})
                                   t (or (first (filter #(% v) c)) #{v})]
                               (conj (disj c s t) (clojure.set/union s t))))
                           #{} edges)))
       (let [num-odd-degree (count (filter #(odd? (count (val %)))
                                           (group-by identity (apply concat edges))))]
         (number? (#{0 2} num-odd-degree)))))



;; 4Clojure Question 91
;;
;; Given a graph, determine whether the graph is connected. A connected graph is such that a path exists between any two given nodes.<br/><br/>-Your function must return true if the graph is connected and false otherwise.<br/><br/>-You will be given a set of tuples representing the edges of a graph. Each member of a tuple being a vertex/node in the graph.<br/><br/>-Each edge is undirected (can be traversed either direction).
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true (__ #{[:a :a]}))

(= true (__ #{[:a :b]}))

(= false (__ #{[1 2] [2 3] [3 1]
               [4 5] [5 6] [6 4]}))

(= true (__ #{[1 2] [2 3] [3 1]
              [4 5] [5 6] [6 4] [3 4]}))

(= false (__ #{[:a :b] [:b :c] [:c :d]
               [:x :y] [:d :a] [:b :e]}))

(= true (__ #{[:a :b] [:b :c] [:c :d]
              [:x :y] [:d :a] [:b :e] [:x :a]}))


;me
(fn [s]
  (true?
    ((fn connected? [node-set reminder]
       (or (empty? reminder)
           (some #(when (some (fn [k] (node-set k)) %)
                   (connected? (into node-set %) (disj (set reminder) %))) reminder)))
      (into #{} (first s)) (rest s))))

;max
(fn [g]
  ((fn f [e]
     (#(if (= e %) (= % g) (f %))
       (reduce (fn [a b] (into a (filter #(some (set b) %) g)))
               #{}
               e)))
    #{(first g)}))
;(into #{} (first s)) 대신 #{(first g)}가 좀더 쉽네.
;내용이해는 생략.

;psk810
(fn [s]
  (letfn [(r [t v] (some (fn [[a b]] (= a b)) (for [x t y v] [x y])))
          (getA [t s] (some #(when (r t %) %) s))]
    ((fn f [t s]
       (let [a (getA t s)]
         (if a
           (f (distinct (concat t a)) (disj s a))
           (empty? s))))
      (first s) (disj s (first s)))))

;me의 (rest s) 대신 여기서는 (disj s (first s))를 사용했기 때문에 내부에서 다시 set을 사용할 필요가 없었다.