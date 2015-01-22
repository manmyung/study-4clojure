;; 4Clojure Question 86
;;
;; Happy numbers are positive integers that follow a particular formula: take each individual digit, square it, and then sum the squares to get a new number. Repeat with the new number and eventually, you might get to a number whose squared sum is 1. This is a happy number. An unhappy number (or sad number) is one that loops endlessly. Write a function that determines if a number is happy or not.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 7) true)

(= (__ 986543210) true)

(= (__ 2) false)

(= (__ 3) false)

;me
(fn [x]
  (letfn [(is-happy [prevs n]
                    (cond
                      (= 1 n) true
                      (prevs n) false
                      :else (is-happy
                              (conj prevs n)
                              (apply + (map #(* % %) (map #(- (int %) 48) (str n)))))))]
    (is-happy #{} x)))

;max. 이건 fake. 4로 판별하면 안됨.
(fn [m]
  (= 1
     (some #{1 4}
           (iterate (fn [k] (reduce #(+ % (let [c (- (int %2) 48)] (* c c)))
                                    0
                                    (str k)))
                    m))))

;daowen. 이건 loop를 쓴 버전.
(fn is-happy? [n]
  (let [sqr #(* % %)
        to-int #(- (int %) (int \0))
        sqr-as-int (comp sqr to-int)]
    (loop [n n, seen #{}]
      (if (seen n) false
                   (let [n2 (apply + (map sqr-as-int (str n)))]
                     (if (= 1 n2) true
                                  (recur n2 (conj seen n))))))))