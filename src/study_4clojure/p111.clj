;; 4Clojure Question 111
;;
;; Write a function that takes a string and a partially-filled crossword puzzle board, and determines if the input string can be legally placed onto the board.
;;
;; </br></br>
;;
;; 
;;
;; The crossword puzzle board consists of a collection of partially-filled rows.  Empty spaces are denoted with an underscore (_), unusable spaces are denoted with a hash symbol (#), and pre-filled spaces have a character in place; the whitespace characters are for legibility and should be ignored.
;;
;; </br></br>
;;
;; For a word to be legally placed on the board:
;;
;; </br>
;;
;; - It may use empty spaces (underscores)
;;
;; </br>
;;
;; - It may use but must not conflict with any pre-filled characters.
;;
;; </br>
;;
;; - It must not use any unusable spaces (hashes).
;;
;; </br>
;;
;; - There must be no empty spaces (underscores) or extra characters before or after the word (the word may be bound by unusable spaces though).
;;
;; </br>
;;
;; - Characters are not case-sensitive. 
;;
;; </br>
;;
;; - Words may be placed vertically (proceeding top-down only), or horizontally (proceeding left-right only).
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true  (__ "the" ["_ # _ _ e"]))

(= false (__ "the" ["c _ _ _"
                    "d _ # e"
                    "r y _ _"]))

(= true  (__ "joy" ["c _ _ _"
                    "d _ # e"
                    "r y _ _"]))

(= false (__ "joy" ["c o n j"
                    "_ _ y _"
                    "r _ _ #"]))

(= true  (__ "clojure" ["_ _ _ # j o y"
                        "_ _ o _ _ _ _"
                        "_ _ f _ # _ _"]))

;me
(fn [s m]
  (let [rows (map #(take-nth 2 %) m)
        cols (apply map list rows)
        lines (concat rows cols)
        line-map (fn [line]
                   (into {} (map-indexed (fn [i x] [i x]) line)))
        boundary? (fn [c]
                    (or (= c \#) (= c nil)))
        legal?
        (fn [l-m]
          (loop [m (range (count l-m))]
            (when-let [i (first m)]
              (if (and (boundary? (l-m (dec i)))
                       (boundary? (l-m (+ i (count s))))
                       (every? true?
                               (map-indexed
                                 (fn [j x] (let [c (l-m (+ j i))]
                                               (or (= c x)
                                                   (= c \_)))) s)))
                true
                (recur (rest m))))))]
    (true? (some #(legal? (line-map %)) lines))))

;daowen
(fn fits? [word board]
  (let [board (map #(.replace % " " "") board)
        from (partial mapcat #(.split % "#"))
        spots (concat (from board) (from (apply map str board)))
        ok? #(every? (fn [[x y]] (or (= \_ x) (= x y)))
                     (map vector % word))]
    (boolean
      (some ok? (filter #(= (count word) (count %)) spots)))))
; board에서 빈칸 제거
; spots로 종, 횡으로 모음
; word와 길이가 같은 문자열로 비교
; every?로 모든 문자에 대해 \_ 이거나 같은지 확인
; .replace와 .split이 remove와 partition-by로 대체하면 더 좋겠음. 나머지는 최고.

;chouser
(fn [t b]
  (or
    (some #(= (seq t) %)
          (for [a [(map #(remove #{\ } %) b)]
                b [a (apply map list a)]
                r b
                w (partition-by #{\#} r)]
            (map #({\_ %2} % %) w (str t 0))))
    false))
;daowen에서 아쉬었던 것이 여기서 보충: remove, partition-by
;그러나 for의 binding을 여러개로 해서 반복하는 것은 복잡해서 별로.

;hyperion
(fn [g b]
  (let [b (map #(.replaceAll % " " "") b)]
    (->>
      (apply map list b)
      (map #(apply str %))
      (concat b)
      (mapcat #(.split % "#"))
      (filter #(= (count g) (count %)))
      (some #(every? (fn [[a b]] (or (= a \_) (= a b)))
                     (map vector % g)))
      true?)))
;daowen과 거의 비슷. threading form인 것만 다름.



