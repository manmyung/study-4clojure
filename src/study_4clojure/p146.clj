;; 4Clojure Question 146
;;
;; <p>Because Clojure's <code>for</code> macro allows you to "walk" over multiple sequences in a nested fashion, it is excellent for transforming all sorts of sequences. If you don't want a sequence as your final output (say you want a map), you are often still best-off using <code>for</code>, because you can produce a sequence and feed it into a map, for example.</p>
;;
;; 
;;
;; <p>For this problem, your goal is to "flatten" a map of hashmaps. Each key in your output map should be the "path"<sup>1</sup> that you would have to take in the original map to get to a value, so for example <code>{1 {2 3}}</code> should result in <code>{[1 2] 3}</code>. You only need to flatten one level of maps: if one of the values is a map, just leave it alone.</p>
;;
;; 
;;
;; <p><sup>1</sup> That is, <code>(get-in original [k1 k2])</code> should be the same as <code>(get result [k1 k2])</code></p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ '{a {p 1, q 2}
         b {m 3, n 4}})
   '{[a p] 1, [a q] 2
     [b m] 3, [b n] 4})

(= (__ '{[1] {a b c d}
         [2] {q r s t u v w x}})
   '{[[1] a] b, [[1] c] d,
     [[2] q] r, [[2] s] t,
     [[2] u] v, [[2] w] x})

(= (__ '{m {1 [a b c] 3 nil}})
   '{[m 1] [a b c], [m 3] nil})

(first
(second
(first '{a {p 1, q 2}
         b {m 3, n 4}})))

(let [[k v] (first '{a {p 1, q 2}
                 b {m 3, n 4}})]
  (into {}
        (map (fn [[a b]]
                  {[k a] b}) v)))

;지금
#(into {}
      (mapcat (fn [[k v]]
                (map (fn [[a b]]
                       {[k a] b}) v)) %))

;이전
(fn [m]
  (into {}
        (mapcat (fn [x y] (map #(vector (vector x (first %)) (last %)) y))
                (keys m) (vals m))))

;max.
#(into {} (for [[i j] % [k l] j] [[i k] l]))
;이렇게 간단하기 때문에 for를 힌트로 줬구나. for는 하나를 풀어헤치고, 풀어헤친 그것을 다시 풀어해칠때 매우 편리하다!