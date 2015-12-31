;; 4Clojure Question 178
;;
;; <p>Following on from <a href="http://www.4clojure.com/problem/128">Recognize Playing Cards</a>, determine the best poker hand that can be made with five cards. The hand rankings are listed below for your convenience.</p>
;;
;; 
;;
;; <ol>
;;
;; <li>Straight flush: All cards in the same suit, and in sequence</li>
;;
;; <li>Four of a kind: Four of the cards have the same rank</li>
;;
;; <li>Full House: Three cards of one rank, the other two of another rank</li>
;;
;; <li>Flush: All cards in the same suit</li>
;;
;; <li>Straight: All cards in sequence (aces can be high or low, but not both at once)</li>
;;
;; <li>Three of a kind: Three of the cards have the same rank</li>
;;
;; <li>Two pair: Two pairs of cards have the same rank</li>
;;
;; <li>Pair: Two cards have the same rank</li>
;;
;; <li>High card: None of the above conditions are met</li>
;;
;; </ol>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= :high-card (__ ["HA" "D2" "H3" "C9" "DJ"]))

(= :pair (__ ["HA" "HQ" "SJ" "DA" "HT"]))

(= :two-pair (__ ["HA" "DA" "HQ" "SQ" "HT"]))

(= :three-of-a-kind (__ ["HA" "DA" "CA" "HJ" "HT"]))

(= :straight (__ ["HA" "DK" "HQ" "HJ" "HT"]))

(= :straight (__ ["HA" "H2" "S3" "D4" "C5"]))

(= :flush (__ ["HA" "HK" "H2" "H4" "HT"]))

(= :full-house (__ ["HA" "DA" "CA" "HJ" "DJ"]))

(= :four-of-a-kind (__ ["HA" "DA" "CA" "SA" "DJ"]))

(= :straight-flush (__ ["HA" "HK" "HQ" "HJ" "HT"]))

;me
(fn [cards]
  (let [[suits ranks] (apply map vector cards)
        ranks-freq (frequencies (vals (frequencies ranks)))
        flush? (apply = suits)
        ranks-map (fn [map-A]
                    (into (zipmap
                            "23456789TJQK"
                            (range 2 14)) map-A ))
        straight? (some
                    #(every? (fn [[p n]] (= 1 (- n p)))
                             (->> ranks
                                  (map (ranks-map %))
                                  sort
                                  (partition 2 1)))
                    [{\A 14} {\A 1}])]

    (cond
      (and flush? straight?) :straight-flush
      (ranks-freq 4) :four-of-a-kind
      (and (ranks-freq 3) (ranks-freq 2)) :full-house
      flush? :flush
      straight? :straight
      (ranks-freq 3) :three-of-a-kind
      (= 2 (ranks-freq 2)) :two-pair
      (= 1 (ranks-freq 2)) :pair
      :else :high-card)))
;밑의 답을 보니 every? 보다는 시퀀스 변환을 계속해서 (1 1 1 1)이 되는지로 판단하도록 바꾸면 코드가 더 깔끔해질 듯.

;chouser
#(let [[S R] (apply map list %)
       k (-> R frequencies vals sort)
       f (apply = S)
       s (.contains
           "AKQJT98765432A.A23456789TJQKA"
           (apply str R))]
  (cond
    (if s f) :straight-flush
    (= k [1 4]) :four-of-a-kind
    (= k [2 3]) :full-house
    f :flush
    s :straight
    (= k [1 1 3]) :three-of-a-kind
    (= k [1 2 2]) :two-pair
    (= k [1 1 1 2]) :pair
    0 :high-card))
;[S R] 구하는 것은 나와 동일. [1 2 2]을 사용하는 것은 소스 크기가 줄어드는 장점. straight는 일반적이지 않아서 좋은 방법이 아님.

;maximental
(fn [h]
  (let [A apply
        r (sort (map (fn [[_ r]] ({\T 8 \J 9 \Q 10 \K 11 \A 12} r (- (int r) 50))) h))
        s (map first h)
        v (vals (frequencies r))
        k (A = -1 (map #(A - %) (partition 2 1 r)))
        u (set v)
        f (A = s)]
    (cond
      (= [2 3] (sort v)) :full-house
      (and f k) :straight-flush
      (or (= r [0 1 2 3 12]) k) :straight
      f :flush
      (= (count (keep #(if (= % 2) 2) v)) 2) :two-pair
      (u 2) :pair
      (u 3) :three-of-a-kind
      (u 4) :four-of-a-kind
      0 :high-card)))
;이렇게 할 수도 있겠지만 약간 tricky: (or (= r [0 1 2 3 12]) k)

; daowen
(fn [hand]
  (let [rvals     (vec "23456789TJQKA")
        flush?    (apply = (map first hand))
        ranks     (->> hand (map #(.indexOf rvals (second %))) sort vec)
        by-rank   (frequencies ranks)
        groups    (clojure.set/map-invert by-rank)
        [p1 p2]   (filter (fn [[_ f]] (= f 2)) by-rank)
        straight? (or (and (= 5 (count by-rank))
                           (= 4 (- (ranks 4) (ranks 0))))
                      (= ranks [0 1 2 3 12]))]
    (cond
      (and flush? straight?) :straight-flush
      (groups 4)             :four-of-a-kind
      (and (groups 3) p1)    :full-house
      flush?                 :flush
      straight?              :straight
      (groups 3)             :three-of-a-kind
      p2                     :two-pair
      p1                     :pair
      :else                  :high-card)))
;[0 1 2 3 12]을 사용한 게 maximental과 비슷.