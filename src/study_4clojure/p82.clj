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