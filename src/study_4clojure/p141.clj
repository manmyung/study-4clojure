;; 4Clojure Question 141
;;
;; <p>
;;
;; In <a href="http://en.wikipedia.org/wiki/Trick-taking_game">trick-taking
;;
;; card games</a> such as bridge, spades, or hearts, cards are played
;;
;; in groups known as "tricks" - each player plays a single card, in
;;
;; order; the first player is said to "lead" to the trick. After all
;;
;; players have played, one card is said to have "won" the trick. How
;;
;; the winner is determined will vary by game, but generally the winner
;;
;; is the highest card played <i>in the suit that was
;;
;; led</i>. Sometimes (again varying by game), a particular suit will
;;
;; be designated "trump", meaning that its cards are more powerful than
;;
;; any others: if there is a trump suit, and any trumps are played,
;;
;; then the highest trump wins regardless of what was led.
;;
;; </p>
;;
;; <p>
;;
;; Your goal is to devise a function that can determine which of a
;;
;; number of cards has won a trick. You should accept a trump suit, and
;;
;; return a function <code>winner</code>. Winner will be called on a
;;
;; sequence of cards, and should return the one which wins the
;;
;; trick. Cards will be represented in the format returned
;;
;; by <a href="/problem/128/">Problem 128, Recognize Playing Cards</a>:
;;
;; a hash-map of <code>:suit</code> and a
;;
;; numeric <code>:rank</code>. Cards with a larger rank are stronger.
;;
;; </p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(let [notrump (__ nil)]
  (and (= {:suit :club :rank 9}  (notrump [{:suit :club :rank 4}
                                           {:suit :club :rank 9}]))
       (= {:suit :spade :rank 2} (notrump [{:suit :spade :rank 2}
                                           {:suit :club :rank 10}]))))

(= {:suit :club :rank 10} ((__ :club) [{:suit :spade :rank 2}
                                       {:suit :club :rank 10}]))

(= {:suit :heart :rank 8}
   ((__ :heart) [{:suit :heart :rank 6} {:suit :heart :rank 8}
                 {:suit :diamond :rank 10} {:suit :heart :rank 4}]))

;me. 이 문제를 통해 max-key를 배울 수 있었다.
(fn [suit]
  (fn [vs]
    (let [suits (map :suit vs)
          won-suit (if (some #(= suit %) suits) suit (first suits))]
      (->> vs
           (filter #(= won-suit (% :suit)))
           (apply max-key :rank)))))

;max.
(fn [t]
  #(let [S :suit
         R :rank
         t (or t (S (first %)))]
    (reduce
      (fn [a {s S r R}]
        (if (and (= s t) (< (R a 0) r))
          (assoc a R r)
          a))
      {S t}
      %)))
;여기서 재미있는 점은 map을 인수분해하는 것.
(let [{s :suit r :rank} {:suit :heart :rank 6}]
  [s r]) ;=> [:heart 6]
;그리고 맵을 함수로 사용할 때 nil일 경우 예외값을 지정할 수 있는 것.
(:rank {:suit :heart} 3) ;=> 3

;chouser
#(fn [s] (let [t :suit g (group-by t s)] (apply max-key :rank (g % (-> 0 s t g)))))
;clojuredcs에서 group-by 예제
(group-by count ["a" "as" "asd" "aa" "asdf" "qwer"]) ;;=> {1 ["a"], 2 ["as" "aa"], 3 ["asd"], 4 ["asdf" "qwer"]}
;이렇게 won-shit를 뽑는 거구나. 이해가 쉽지는 않지만 짧은 코드임.
((
#(fn [s] (let [t :suit g (group-by t s)] (g % (-> 0 s t g))))
   :heart)
  [{:suit :heart :rank 6} {:suit :heart :rank 8}
   {:suit :diamond :rank 10} {:suit :heart :rank 4}])
;=> [{:suit :heart, :rank 6} {:suit :heart, :rank 8} {:suit :heart, :rank 4}]