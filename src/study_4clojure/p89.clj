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

;me
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