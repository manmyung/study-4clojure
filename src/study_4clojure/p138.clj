;; 4Clojure Question 138
;;
;; Create a function of two integer arguments: the start and end, respectively.  You must create a vector of strings which renders a 45&deg; rotated square of integers which are successive squares from the start point up to and including the end point.  If a number comprises multiple digits, wrap them around the shape individually.  If there are not enough digits to complete the shape, fill in the rest with asterisk characters.  The direction of the drawing should be clockwise, starting from the center of the shape and working outwards, with the initial direction being down and to the right.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 2 2) ["2"])

(= (__ 2 4) [" 2 "
             "* 4"
             " * "])

(= (__ 3 81) [" 3 "
              "1 9"
              " 8 "])



(= (__ 2 256) ["  6  "
               " 5 * "
               "2 2 *"
               " 6 4 "
               "  1  "])

(= (__ 10 10000) ["   0   "
                  "  1 0  "
                  " 0 1 0 "
                  "* 0 0 0"
                  " * 1 * "
                  "  * *  "
                  "   *   "])

(
(fn [x y]
  (let [a (take-while #(<= % y) (iterate #(* % %) x))
        b (mapcat (fn [x] (map #(str (- (int %) 48)) (str x))) a)
        l (some #(when (<= (count b) %) %) (reductions + 1 (iterate #(+ % 2) 3)))
        c (concat b (repeat (- l (count b)) "*"))
        d (mapcat (fn [x] [x x]) (drop 1 (range)))
        e (cycle [[1 1] [1 -1] [-1 -1] [-1 1]])
        f (mapcat (fn [n x] (repeat n x)) d e)
        sum (fn sum [[a b] [c d]] [(+ a c) (+ b d)])
        g (reduce (fn [x y] (conj x (sum (last x) y))) [[0 0]] (take (dec l) f))
        gi (sort (map first g))
        gj (sort (map second g))
        h (map (fn [[x y]] [(+ (- (first gi)) x) (+ (- (first gj)) y)]) g)
        lx (inc (- (last gi) (first gi)))
        k (zipmap h c)
        m (partition lx (for [x (range lx) y (range lx)]
                          (if-let [z (k [x y])] z \space)))
        n (map #(apply str %) m)]
    n))
      2 256)

         

         
