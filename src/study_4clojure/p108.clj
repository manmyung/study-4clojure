;; 4Clojure Question 108
;;
;; <p>Given any number of sequences, each sorted from smallest to largest, find the smallest single number which appears in all of the sequences. The sequences may be infinite, so be careful to search lazily.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 3 (__ [3 4 5]))

(= 4 (__ [1 2 3 4 5 6 7] [0.5 3/2 4 19]))

(= 7 (__ (range) (range 0 100 7/6) [2 3 5 7 11 13]))

(= 64 (__ (map #(* % % %) (range)) ;; perfect cubes
          (filter #(zero? (bit-and % (dec %))) (range)) ;; powers of 2
          (iterate inc 20))) ;; at least as large as 20

;me. ns를 n으로 하면 마지막 문제의 두번째 항목때문에 시간제한이 걸림. 그러므로 ns로 바꿔서 min-last를 가진 시퀀스만 take 증가.
(fn s [& vs]
  (letfn [(k [ns ls]
             (let [takes (map #(take %1 %2) ns ls)
                   result (first (reduce clojure.set/intersection (map set takes)))]
               (if result
                 result
                 (let [lasts (map last takes)
                       min-last (apply min lasts)
                       incs (map #(if (= min-last %) 1 0) lasts)]
                   (k (map + incs ns) ls)))))]
    (k (repeat (count vs) 1) vs)))

;max. 제일 큰 녀석을 고르고 이것보다 작은 것들 버려나가기 전략.
(fn f [& s]
  (let [h (map first s)
        m (apply max h)]
    (if (apply = h)
      m
      (apply f (map (fn [z] (drop-while #(< % m) z)) s)))))
;([1 2 3 4 5 6 7] [0.5 3/2 4 19])
;((1 2 3 4 5 6 7) (3/2 4 19))
;((2 3 4 5 6 7) (3/2 4 19))
;((2 3 4 5 6 7) (4 19))
;((4 5 6 7) (4 19))

;chouser. 제일 작은 것만 버려나가는 전략.
#(let [a (map first %&)
       b (apply min a)]
  (if (apply = a)
    b
    (recur (map (fn [[x & y :as z]] (if (= x b) y z)) %&))))

;max와 chouser를 보면 내 방식은 비효율적이다.