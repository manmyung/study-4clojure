;; 4Clojure Question 84
;;
;; Write a function which generates the <a href="http://en.wikipedia.org/wiki/Transitive_closure">transitive closure</a> of a <a href="http://en.wikipedia.org/wiki/Binary_relation">binary relation</a>.  The relation will be represented as a set of 2 item vectors.
;;
;; Use M-x 4clojure-check-answers when you're done!

(let [divides #{[8 4] [9 3] [4 2] [27 9]}]
  (= (__ divides) #{[4 2] [8 4] [8 2] [9 3] [27 9] [27 3]}))

(let [more-legs
      #{["cat" "man"] ["man" "snake"] ["spider" "cat"]}]
  (= (__ more-legs)
     #{["cat" "man"] ["cat" "snake"] ["man" "snake"]
       ["spider" "cat"] ["spider" "man"] ["spider" "snake"]}))

(let [progeny
      #{["father" "son"] ["uncle" "cousin"] ["son" "grandson"]}]
  (= (__ progeny)
     #{["father" "son"] ["father" "grandson"]
       ["uncle" "cousin"] ["son" "grandson"]}))

;me
(fn [s]
  (let [m (into {} s)
        f (fn f [[a b :as c]]
            (when b
              (cons c (f [a (m b)]))))]
    (reduce #(into %1 (f %2)) #{} s)))

;max
(fn f [s]
  (#(if (= s %) % (f %))
    (reduce (fn [a [x y]]
              (into a (keep (fn [[u v]] (if (= u y) [x v])) s)))
            s
            s)))

;chouser
#(set (mapcat
        (fn f [[a b :as p] s]
          (cons p
                (mapcat (fn [[c d]]
                          (if (= c b)
                            (cons [a d] (f [a d] (disj s p)))))
                        s)))
        %
        (repeat %)))
