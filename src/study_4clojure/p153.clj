;; 4Clojure Question 153
;;
;; 
;;
;; <p>
;;
;; Given a set of sets, create a function which returns <code>true</code> 
;;
;; if no two of those sets have any elements in common<sup>1</sup> and <code>false</code> otherwise. 
;;
;; Some of the test cases are a bit tricky, so pay a little more attention to them. 
;;
;; </p>
;;
;; 
;;
;; <p>
;;
;; <sup>1</sup>Such sets are usually called <i>pairwise disjoint</i> or <i>mutually disjoint</i>.
;;
;; </p>
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #{#{\U} #{\s} #{\e \R \E} #{\P \L} #{\.}})
   true)

(= (__ #{#{:a :b :c :d :e}
         #{:a :b :c :d}
         #{:a :b :c}
         #{:a :b}
         #{:a}})
   false)

(= (__ #{#{[1 2 3] [4 5]}
         #{[1 2] [3 4 5]}
         #{[1] [2] 3 4 5}
         #{1 2 [3 4] [5]}})
   true)

(= (__ #{#{'a 'b}
         #{'c 'd 'e}
         #{'f 'g 'h 'i}
         #{''a ''c ''f}})
   true)

(= (__ #{#{'(:x :y :z) '(:x :y) '(:z) '()}
         #{#{:x :y :z} #{:x :y} #{:z} #{}}
         #{'[:x :y :z] [:x :y] [:z] [] {}}})
   false)

(= (__ #{#{(= "true") false}
         #{:yes :no}
         #{(class 1) 0}
         #{(symbol "true") 'false}
         #{(keyword "yes") ::no}
         #{(class '1) (int \0)}})
   false)

(= (__ #{#{distinct?}
         #{#(-> %) #(-> %)}
         #{#(-> %) #(-> %) #(-> %)}
         #{#(-> %) #(-> %) #(-> %)}})
   true)

(= (__ #{#{(#(-> *)) + (quote mapcat) #_ nil}
         #{'+ '* mapcat (comment mapcat)}
         #{(do) set contains? nil?}
         #{, , , #_, , empty?}})
   false)

(
#(let [l (for [a % b a] b)]
  (= (count l) (count (set l))))
  #{#{:a :b :c :d :e}
    #{:a :b :c :d}
    #{:a :b :c}
    #{:a :b}
    #{:a}})

;지금. p146에서 배운 for을 사용.
#(let [l (for [a % b a] b)]
  (= (count l) (count (set l))))
;chouser와 답이 같네ㅎ

;이전
(fn [a]
  (every? empty?
          (for [x a
                y a]
            (if (= x y)
              #{}
              (clojure.set/intersection x y)))))

;max
#(apply distinct? (mapcat seq %))

(mapcat seq #{#{:a :b :c :d :e}
              #{:a :b :c :d}
              #{:a :b :c}
              #{:a :b}
              #{:a}})
;=> (:c :b :a :e :c :b :d :a :c :b :d :a :b :a :a)
;distinct? := "Returns true if no two of the arguments are ="
;max는 distinct?를 많이 사용해 봤기때문에 이런 답을 떠올렸을 것이다.