;; 4Clojure Question 90
;;
;; Write a function which calculates the <a href="http://en.wikipedia.org/wiki/Cartesian_product"> Cartesian product</a> of two sets.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(set (for [x %1
                y %2]
            [x y])) #{"ace" "king" "queen"} #{"&#9824;" "&#9829;" "&#9830;" "&#9827;"})
   #{["ace"   "&#9824;"] ["ace"   "&#9829;"] ["ace"   "&#9830;"] ["ace"   "&#9827;"]
     ["king"  "&#9824;"] ["king"  "&#9829;"] ["king"  "&#9830;"] ["king"  "&#9827;"]
     ["queen" "&#9824;"] ["queen" "&#9829;"] ["queen" "&#9830;"] ["queen" "&#9827;"]})

(= (__ #{1 2 3} #{4 5})
   #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]})

(= 300 (count (__ (into #{} (range 10))
                  (into #{} (range 30)))))

#(set (for [x %1
            y %2]
        [x y]))

;이건 뭐 모두 비슷.