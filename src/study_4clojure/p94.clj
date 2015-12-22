;; 4Clojure Question 94
;;
;; The <a href="http://en.wikipedia.org/wiki/Conway's_Game_of_Life">game of life</a> is a cellular automaton devised by mathematician John Conway. <br/><br/>The 'board' consists of both live (#) and dead ( ) cells. Each cell interacts with its eight neighbours (horizontal, vertical, diagonal), and its next state is dependent on the following rules:<br/><br/>1) Any live cell with fewer than two live neighbours dies, as if caused by under-population.<br/>2) Any live cell with two or three live neighbours lives on to the next generation.<br/>3) Any live cell with more than three live neighbours dies, as if by overcrowding.<br/>4) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.<br/><br/>Write a function that accepts a board, and returns a board representing the next generation of cells.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ ["      "  
        " ##   "
        " ##   "
        "   ## "
        "   ## "
        "      "])
   ["      "  
    " ##   "
    " #    "
    "    # "
    "   ## "
    "      "])

(= (__ ["     "
        "     "
        " ### "
        "     "
        "     "])
   ["     "
    "  #  "
    "  #  "
    "  #  "
    "     "])

(= (__ ["      "
        "      "
        "  ### "
        " ###  "
        "      "
        "      "])
   ["      "
    "   #  "
    " #  # "
    " #  # "
    "  #   "
    "      "])

;me
(fn [b]
  (let
    [is-live? (fn [cs i j]
                (-> cs
                    (get i)
                    (get j)
                    (= \#)))
     dij [[-1 -1] [0 -1] [1 -1] [-1 0] [1 0] [-1 1] [0 1] [1 1]]
     next-live? (fn [i j]
                  (let [c (count (filter
                                   (fn [[di dj]] (is-live? b (+ di i) (+ dj j)))
                                   dij))
                        l (is-live? b i j)]
                    (or (and l (<= 2 c 3))
                        (and (not l) (= c 3)))))
     next-cell (fn [i j]
                 (if (next-live? i j) \# \space))]
    (for [i (range (count b))]
      (apply str
             (for [j (range (count (get b i)))]
               (next-cell i j))))))

;chouser
#(let [r (range (count %))
       v [-1 0 1]
       a \#]
  (for [y r]
    (apply str (for [x r c [(count
                              (for [j v
                                    k v
                                    :when (= a (get-in % [(+ y j) (+ x k)]))]
                                1))]]
                 (if (or (= c 3) (and (= c 4) (= a (get-in % [y x]))))
                   a " " )))))
;for[j v k v ...]를 보면 다차원 순회해서 \#의 갯수를 구하는 부분이 알아둘만 하다.
;get-in도 나처럼 두번 get을 쓰는 것보다 좋다.

;daowen
(fn next-gen [grid]
  (let [cell? (->> grid
                   (map-indexed (fn [i r]
                                  (map-indexed (fn [j c]
                                                 (if (= \# c) [i j] nil)) r)))
                   (apply concat)
                   (set))
        ns '([-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1])
        n-count (fn [r c]
                  (count (filter cell? (map (fn [[x y]]
                                              [(+ r x) (+ c y)]) ns))))
        live-map (for [r (range (count grid))]
                   (for [c (range (count (first grid)))]
                     (let [n (n-count r c)]
                       (or (= n 3) (and (cell? [r c]) (= n 2))))))]
    (map (fn [row] (apply str (map #(if % \# " " ) row))) live-map)))
;map-indexed도 있었군.
;\#가 있는 인덱스를 set으로 전처리해두어서 효율적. 이거 좋다.
;live-map으로 true, false 가 요소인 grid를 만든 후, 문자열로 바꿨다. 이것도 뷰와 처리를 분리하니 좋다.
