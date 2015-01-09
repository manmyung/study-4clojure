;; 4Clojure Question 122
;;
;; Convert a binary number, provided in the form of a string, to its numerical value.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 0     (__ "0"))

(= 7     (__ "111"))

(= 8     (__ "1000"))

(= 9     ((fn [x]
            (apply +
                   (map *
                        (reverse (map #(- (int %) 48) x))
                        (iterate #(* % 2) 1)))) "1001"))

(= 255   (__ "11111111"))

(= 1365  (__ "10101010101"))

(= 65535 (__ "1111111111111111"))

;지금. 이전보다는 지금이 낫다.
(fn [x]
  (apply +
         (map *
              (reverse (map #(- (int %) 48) x))
              (iterate #(* % 2) 1))))

;이전
(fn [x]
  (apply + (map-indexed #(* (apply * (repeat %1 2)) %2)
                        (reverse
                          (map #(- (int %) 48) (seq x))))))

;Max. 지금 답과 비슷하다. string을 int로 바꿀때 수식으로 하지 않고 일대일로 대응했다. 2진수에서는 가능한 일. 기억해 둘것은 ('맵' '키' '없는경우')
(fn [s]
  (apply + (map #({\1 %2} % 0)
                (reverse s)
                (iterate #(* 2 %) 1))))

;daowen. 자바 function을 이용하는 거라고 나오네. Programming Clojure에서 자바를 부를때 . 없이도 가능했던 것 같다. 나중에 확인해 보자.
#(Integer/parseInt % 2)

