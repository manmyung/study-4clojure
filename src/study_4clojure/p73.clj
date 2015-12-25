;; 4Clojure Question 73
;;
;; A <a href="http://en.wikipedia.org/wiki/Tic-tac-toe">tic-tac-toe</a> board is represented by a two dimensional vector. X is represented by :x, O is represented by :o, and empty is represented by :e.  A player wins by placing three Xs or three Os in a horizontal, vertical, or diagonal row.  Write a function which analyzes a tic-tac-toe board and returns :x if X has won, :o if O has won, and nil if neither player has won.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= nil (__ [[:e :e :e]
            [:e :e :e]
            [:e :e :e]]))

(= :x (__ [[:x :e :o]
           [:x :e :e]
           [:x :e :o]]))

(= :o (__ [[:e :x :e]
           [:o :o :o]
           [:x :e :x]]))

(= nil (__ [[:x :e :o]
            [:x :x :e]
            [:o :x :o]]))

(= :x (__ [[:x :e :e]
           [:o :x :e]
           [:o :e :x]]))

(= :o (__ [[:x :e :o]
           [:x :o :e]
           [:o :e :x]]))

(= nil (__ [[:x :o :x]
            [:x :o :x]
            [:o :x :o]]))

;me
(fn [m]
  (let [positions [[[0 0] [0 1] [0 2]] [[1 0] [1 1] [1 2]] [[2 0] [2 1] [2 2]] [[0 0] [1 0] [2 0]] [[0 1] [1 1] [2 1]] [[0 2] [1 2] [2 2]] [[0 0] [1 1] [2 2]] [[0 2] [1 1] [0 2]]]]
    (some (fn [l]
            (let [result (map #(get-in m %) l)]
              (cond
                (every? #(= :o %) result) :o
                (every? #(= :x %) result) :x)
              )) positions)))

;psk810. some을 2번쓰고, apply map vector를 사용해서 코드가 짧다. 굿.
(fn [s]
  (letfn [(f [s] (some (fn [x] (when (every? #(= x %) s) x)) [:x :o]))
          (dia [[[a _ b] [_ x _] [c _ d]]] [[a x d] [b x c]])]
    (some f (concat s (apply map vector s) (dia s)))))

;chouser. 이것도 psk810과 비슷. (when (every? ... 대신 {[:x :x :x] :x ... 를 사용. 덜 general하지만 쉽다. 반면 diagonal 만드는 것은 psk810이 훨씬 간단.
#(some {[:x :x :x] :x [:o :o :o] :o}
       (concat % (apply map list %)
               (for [d [[[0 0] [1 1] [2 2]] [[2 0] [1 1] [0 2]]]]
                 (for [[x y] d] ((% x) y)))))

;daowen. 위에 비해 diag를 general하게 한듯.
(fn [rows]
  (let [cols (apply map vector rows)
        diag (fn [x] (map-indexed #(nth %2 %) x))
        diags [(diag rows) (diag (map reverse rows))]
        lines (concat rows cols diags)
        line-sets (map set lines)]
    (first (some #{#{:x} #{:o}} line-sets))))
(apply map vector
       [[:x :e :o]
        [:x :x :e]
        [:o :x :o]])

