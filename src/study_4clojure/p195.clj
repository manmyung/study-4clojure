;; 4Clojure Question 195
;;
;; <p>In a family of languages like Lisp, having balanced parentheses is a defining feature of the language. Luckily, Lisp has almost no syntax, except for these "delimiters" -- and that hardly qualifies as "syntax", at least in any useful computer programming sense.</p>
;;
;; 
;;
;; <p>It is not a difficult exercise to find all the combinations of well-formed parentheses if we only have N pairs to work with. For instance, if we only have 2 pairs, we only have two possible combinations: "()()" and "(())". Any other combination of length 4 is ill-formed. Can you see why? </p>
;;
;; 
;;
;; <p>Generate all possible combinations of well-formed parentheses of length <code>2n</code> (n pairs of parentheses).
;;
;; For this problem, we only consider '(' and ')', but the answer is similar if you work with only {} or only [].</p>
;;
;; 
;;
;; <p>There is an interesting pattern in the numbers!</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [#{""} #{"()"} #{"()()" "(())"}] (map (fn [n] (__ n)) [0 1 2]))

(= #{"((()))" "()()()" "()(())" "(())()" "(()())"} (__ 3))

(= 16796 (count (__ 10)))

(= (nth (sort (filter #(.contains ^String % "(()()()())") (__ 9))) 6) "(((()()()())(())))")

(= (nth (sort (__ 12)) 5000) "(((((()()()()()))))(()))")

;me
(fn [n]
  (let [count-v (fn [col v]
                  (count (filter #(= v %) col)))
        next (fn [c]
               (let [open-n (count-v c \()
                     close-n (count-v c \))
                     open-possible (< open-n n)
                     close-possible (< close-n open-n)]
                 (-> []
                     (#(if open-possible (conj % (conj c \()) %))
                     (#(if close-possible (conj % (conj c \))) %)))))]
    (->>
      (reduce (fn [col _] (mapcat next col)) [[]] (range (* 2 n)))
      (map (fn [l] (apply str l)))
      (into #{}))))

;daowen.
(fn paren-combos
  ([n] (set (paren-combos n 0 [])))
  ([num-avail num-open parens]
   (if (= 0 num-avail num-open)
     (list (apply str parens))
     (concat
       (when (pos? num-avail)
         (paren-combos (dec num-avail)
                       (inc num-open)
                       (conj parens \()))
       (when (pos? num-open)
         (paren-combos num-avail
                       (dec num-open)
                       (conj parens \))))))))

;배울점 하나.
;밑처럼 or 조건을 concat를 이용해 할 수 있다면,
(concat [2 3] nil) ;=> (2 3)
;이것을
((fn [c]
  (-> []
      (#(if true (conj % (conj c \()) %))
      (#(if true (conj % (conj c \))) %))))
  [\(]) ;=> [[\( \(] [\( \)]]
;이렇게 단순화시킬 수 있음
((fn [c]
   (concat
       (when true [(conj c \()])
       (when true [(conj c \))])))
  [\(])

;amalloy. 이게 daowen 보다 좀더 이해가 쉬울 듯 한데 나중에 보기로...
(fn parens [n]
  (set
    (map (partial apply str)
         ((fn lazy-recur [open close]
            (lazy-seq
              (concat (when (pos? open)
                        (map #(cons \( %)
                             (lazy-recur (dec open) (inc close))))
                      (when (pos? close)
                        (map #(cons \) %)
                             (lazy-recur open (dec close))))
                      (when (= [0 0] [open close])
                        [""]))))
           n 0))))