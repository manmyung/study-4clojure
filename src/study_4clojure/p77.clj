;; 4Clojure Question 77
;;
;; Write a function which finds all the anagrams in a vector of words.  A word x is an anagram of word y if all the letters in x can be rearranged in a different order to form y.  Your function should return a set of sets, where each sub-set is a group of words which are anagrams of each other.  Each sub-set should have at least two words.  Words without any anagrams should not be included in the result.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ ["meat" "mat" "team" "mate" "eat"])
   #{#{"meat" "team" "mate"}})

(= (__ ["veer" "lake" "item" "kale" "mite" "ever"])
   #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})

(defn anag [x y] (= (sort (vec x)) (sort (vec y))))

;me. 다른 답을 보니 sort seq 가 더 낫다.
(fn [c] (set (map set
     (filter #(> (count %) 1)
             (vals
               (group-by #(sort (vec %)) c))))))

;netpyoung. ->> 를 쓰니 더 깔끔. group-by frequencies 도 또다른 방법.
(fn [lst]
  (->> lst
       (group-by frequencies)
       (map second)
       (filter #(<= 2 (count %)))
       (map set)
       (set)))

;daowen. 더 깔끔. filter second는 그냥은 생각못할 방법.
#(->> % (group-by sort) vals (filter second) (map set) set)

;chouser. vals 대신 destructuring 사용. for도 재밌다.
#(set (for [[_ g] (group-by frequencies %)
            :when (next g)]
        (set g)))