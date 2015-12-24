;; 4Clojure Question 101
;;
;; Given two sequences x and y, calculate the <a href="https://secure.wikimedia.org/wikipedia/en/wiki/Levenshtein_distance">Levenshtein distance</a> of x and y, i. e. the minimum number of edits needed to transform x into y.  The allowed edits are:<br/><br/>- insert a single item<br/>- delete a single item<br/>- replace a single item with another item<br/><br/>WARNING: Some of the test cases may timeout if you write an inefficient solution!
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ "kitten" "sitting") 3)

(= (__ "closure" "clojure") (__ "clojure" "closure") 1)

(= (__ "xyx" "xyyyx") 2)

(= (__ "" "123456") 6)

(= (__ "Clojure" "Clojure") (__ "" "") (__ [] []) 0)

(= (__ [1 2 3 4] [0 2 3 4 5]) 2)

(= (__ '(:a :b :c :d) '(:a :d)) 2)

(= (__ "ttttattttctg" "tcaaccctaccat") 10)

(= (__ "gaattctaatctc" "caaacaaaaaattt") 9)

;me
#(let
  [len-x (count %1)
   len-y (count %2)
   ij (atom (vec (repeat len-x (vec (repeat len-y nil)))))
   get-ij (fn [i j] (get-in @ij [i j]))
   set-ij (fn [i j v] (do (swap! ij assoc-in [i j] v) v))
   lev (fn lev [x x-i y y-j]
         (if-let [v (get-ij x-i y-j)]
           v
           (cond
             (zero? x-i) (set-ij x-i y-j y-j)
             (zero? y-j) (set-ij x-i y-j x-i)
             :else
             (set-ij x-i y-j
                     (min
                       (inc (lev x (dec x-i) y y-j))
                       (inc (lev x x-i y (dec y-j)))
                       (+ (lev x (dec x-i) y (dec y-j))
                          (if (= (nth x (dec x-i)) (nth y (dec y-j))) 0 1)))))))]
  (lev %1 len-x %2 len-y))
;위 링크 위키피디아 페이지에서 첫번째 알고리즘 사용.
;마지막 2개의 테스트케이스에서 Timeout이 나옴. with-local-vars와 memoize를 사용하려고 했지만 4clojure에서는 var를 정의하지 못해서 실패. 대신 atom 사용.

;mfike
(fn levenshtein-distance [s t]
  (let [f (fn [f s t]
            (cond
              (empty? s) (count t)
              (empty? t) (count s)
              :else (let [cost (if (= (first s) (first t)) 0 1)]
                      (min (inc (f f (rest s) t))
                           (inc (f f s (rest t)))
                           (+ cost (f f (rest s) (rest t)))))))
        g (memoize f)]
    (g g s t)))
;memoize는 이렇게 사용하면 되는구나! 먼저 f 정의하고 g를 memoize로 정의. 함수 인자에 g를 넣어 그걸 호출하도록 함.
;x-i, y-i는 사용하지 않았다. 나는 count의 비용때문에 인덱스 있는 알고리즘을 사용했지만, 클로저에서는 count와 rest를 사용한 이 방식이 더 깔끔하다. 기존 인덱스 알고리즘을 클로저의 방식으로 바꾸는 법을 배워둘만하다.

;daowen
(fn levenshtein [s1 s2]
  (let [f (fn [rec a b]
            (let [[ca cb] (map count [a b])]
              (if (some zero? [ca cb]) (max ca cb)
                                       (min (inc (rec rec (pop a) b))
                                            (inc (rec rec a (pop b)))
                                            (+ (rec rec (pop a) (pop b))
                                               (if (= (peek a) (peek b)) 0 1))))))]
    (f (memoize f) (vec s1) (vec s2))))
;g를 따로 정의하지 않아 좋다. 그리고 함수 인자를 rec으로 해서 f와 다르다는 것을 명시적으로 보여서 좋다.
;알고리즘은 mfike가 더 깔끔.

;hypirion
(let [mem (atom {})]
  (fn leven [[fa & ra :as a] [fb & rb :as b]]
    (or
      (@mem [a b])
      (let [res
            (cond (nil? a) (count b)
                  (nil? b) (count a)
                  (= fa fb) (leven ra rb)
                  :else (+ 1
                           (min (leven ra rb)
                                (leven a rb)
                                (leven ra b))))]
        (swap! mem assoc [a b] res)
        res))))
;memoize대신 atom을 사용한다면 이게 내꺼보다 훨씬 코드가 짧다.
;인덱스의 matrix라는 기존방식을 버리고 이렇게 통으로 넣었다. {["asd" "sss"] 3, ["asdf" "sss"] 4}. 생각의 전환. 메모리는 커지겠지만 코드가 간결하니 좋다.

;maximental
(fn [C s t]
  ((fn f [i j]
     (cond
       (= i 0) j
       (= j 0) i
       (= (nth s (- i 1)) (nth t (- j 1))) (f (- i 1) (- j 1))
       :else   (+ 1
                  (min (f (- i 1) j)
                       (f i       (- j 1))
                       (f (- i 1) (- j 1))))))
    (C s) (C t))) count
; 테스트해보니 memoize나 atom 방식보다는 느렸다.
; (= (nth s (- i 1)) (nth t (- j 1))) (f (- i 1) (- j 1)) 이 줄 하나때문에 확장이 줄어서 Timeout이 걸리지 않은 것 같다.