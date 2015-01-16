;; 4Clojure Question 74
;;
;; Given a string of comma separated integers, write a function which returns a new comma separated string that only contains the numbers which are perfect squares.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(apply str
            (interpose ","
                       (filter (fn [n] (some (fn [x] (= n (* x x))) (range n)))
                               (map read-string (clojure.string/split % #",")))))
     "4,5,6,7,8,9") "4,9")

(= (__ "15,16,25,36,37") "16,25,36")

;me.
#(apply str
        (interpose ","
                   (filter (fn [n] (some (fn [x] (= n (* x x))) (range n)))
                           (map read-string (clojure.string/split % #",")))))


;ming. 기억할것들 re-seq, Integer/parseInt, Math/sqrt, clojure.string/join
(fn [v]
  (clojure.string/join
    ","
    (filter
      #(zero? (rem (Math/sqrt %) 1))
      (map #(Integer/parseInt %) (re-seq #"\w+" v)))))
;re-seq는 패턴 맞는 것을 sequence로 만드는 것. Math/sqrt는 Cheat Sheet에 없네. clojure.string/join 은 interpose하여 str하는 것과 동일.