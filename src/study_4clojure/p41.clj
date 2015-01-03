;; 4Clojure Question 41
;;
;; Write a function which drops every Nth item from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [c x]
      (keep-indexed
        #(when (not= 0 (rem (inc %1) x))
          %2) c))
      [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])

(= (__ [:a :b :c :d :e :f] 2) [:a :c :e])

(= (__ [1 2 3 4 5 6] 4) [1 2 3 5 6])

(keep-indexed #(if (odd? %1) %2) [:a :b :c :d :e])

;keep은 some처럼 filter보다 더 general하게 사용할 수 있다. 여기에 index정보를 추가한 것인 keep-index이다. 좋다.
(fn [c x]
  (keep-indexed
    #(when (not= 0 (rem (inc %1) x))
      %2) c))

(rem 9 3)

;Max 오 이게 더 Clojure's way이다.
;partition-all 은 partition인데 마지막도 남기는 것.
;partition-all n step coll: coll에서 step만큼 뛰면서 n개씩 가져오기.
;이전 p30에서 partition-by 가 Clojure's way였는데 partition이 Clojure를 잘 사용하는 것의 key 이군.
#(apply concat (partition-all (- %2 1) %2 %))

(#(apply concat (partition-all (dec %2) %2 %)) [1 2 3 4 5 6 7 8] 3)
