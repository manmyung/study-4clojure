;; 4Clojure Question 157
;;
;; Transform a sequence into a sequence of pairs containing the original elements along with their index.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (map-indexed (fn [i x] [x i]) [:a :b :c]) [[:a 0] [:b 1] [:c 2]])

(= (__ [0 1 3]) '((0 0) (1 1) (3 2)))

(= (__ [[:foo] {:bar :baz}]) [[[:foo] 0] [{:bar :baz} 1]])


;지금
map-indexed (fn [i x] [x i])

;이전
(fn [s]
  (partition 2 (interleave s (range))))

;max. range를 사용할 수도 있구나.
#(map list % (range))

(take 5 (range))