;; 4Clojure Question 106
;;
;; Given a pair of numbers, the start and end point, find a path between the two using only three possible operations:<ul>
;;
;; <li>double</li>
;;
;; <li>halve (odd numbers cannot be halved)</li>
;;
;; <li>add 2</li></ul>
;;
;; 
;;
;; Find the shortest path through the "maze". Because there are multiple shortest paths, you must return the length of the shortest path, not the path itself.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 1 (__ 1 1))  ; 1

(= 3 (__ 3 12)) ; 3 6 12

(= 3 (__ 12 3)) ; 12 6 3

(= 3 (__ 5 9))  ; 5 7 9

(= 9 (__ 9 2))  ; 9 18 20 10 12 6 8 4 2

(= 5 (__ 9 12)) ; 9 11 22 24 12

;me
(fn [s e]
  (let [expand (fn [n]
                 (into [(* n 2) (+ n 2)] (when (= (rem n 2) 0) [(/ n 2)])))
        search (fn search [paths]
                 (let [[n l] (first paths)]
                   (if (= n e)
                     l
                     (search (into (vec (rest paths))
                              (map #(vec [% (inc l)]) (expand n)))))))]
    (search [[s 1]])))

;daowen
(fn n-maze [start end]
  (let [double #(* 2 %)
        halve  #(if (odd? %) nil `(~(quot % 2)))
        add2   #(+ 2 %)
        ops    #(list* (double %) (add2 %) (halve %))]
    (loop [xs [start], i 1]
      (if (some #(== % end) xs) i
                                (recur (mapcat ops xs) (inc i))))))
; odd? 기억하자.
;(list* 3 5 '(2 4)) ;=> (3 5 2 4). list*는 마지막이 sequence 여야 함. list*를 사용한 이유는 내가 nil을 추가하고 싶지 않아서. 내가 into를 사용한 이유와 같음.
; mapcat으로 하면 이전 xs의 요소는 안 들어가고 그게 펼쳐진 list만 들어가는 구나. 한번에 한 depth씩 펼치기. 한 depth는 모두 펼쳐야 해서 약간 비효율적이긴 하지만 코드 단순하네.

;max
(fn [n m]
  (loop [p 1 s #{n}]
    (if (s m)
      p
      (recur (+ p 1)
             (reduce #(conj %
                            (if (even? %2) (/ %2 2) n)
                            (* %2 2)
                            (+ %2 2))
                     s
                     s)))))
;그렇지. 한 depth씩 펼친다면 some을 안 써도 되니 set이 더 편하겠다.

;chouser
#((fn r [i w]
(if ((set w) %2)
  i
  (r (+ i 1)
     (for [i w f [* + /]
           :when (or (even? i) (not= f /))]
       (f i 2))))) 1 [%])
;이 코드가 가장 깔끔하다. recursive로 구현하고, for에 연산자 들어가는 것도 좋다.