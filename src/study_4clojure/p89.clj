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

