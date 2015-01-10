;; 4Clojure Question 96
;;
;; Let us define a binary tree as "symmetric" if the left half of the tree is the mirror image of the right half of the tree.  Write a predicate to determine whether or not a given binary tree is symmetric. (see <a href='/problem/95'>To Tree, or not to Tree</a> for a reminder on the tree representation we're using).
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [[x y z]]
      (let [symmetric?
            (fn [a b]
              (if (and (coll? a) (coll? b))
                (let [[a1 a2 a3] a
                      [b1 b2 b3] b]
                  (and (= a1 b1)
                       (symmetric? a2 b3)
                       (symmetric? a3 b2)))
                (= a b)))]
        (symmetric? y z))) '(:a (:b nil nil) (:b nil nil))) true)

(= (__ '(:a (:b nil nil) nil)) false)

(= (__ '(:a (:b nil nil) (:c nil nil))) false)

(= (__ [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
          [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]])
   true)

(= (__ [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
          [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]])
   false)

(= (__ [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
          [2 [3 nil [4 [6 nil nil] nil]] nil]])
   false)

;연습
(defn symmetric? [a b]
  (if (and (coll? a) (coll? b))
    (let [[a1 a2 a3] a
          [b1 b2 b3] b]
      (and (= a1 b1)
           (symmetric? a2 b3)
           (symmetric? a3 b2)))
    (= a b)))

(symmetric? nil nil)

;지금. 이전과 비슷하다. letfn 대신 let을 사용하면 여기서는 작동하는데 4clojure에서는 에러를 낸다. 아 joelgrus의 답을 보니 let [x (fn x [a b] ...)] 의 형식으로 x를 다시쓰면 되는 것 같다.
(
  #(letfn [(symmetric? [a b]
                       (if (and (coll? a) (coll? b))
                         (let [[a1 a2 a3] a
                               [b1 b2 b3] b]
                           (and (= a1 b1)
                                (symmetric? a2 b3)
                                (symmetric? a3 b2)))
                         (= a b)))]
    (symmetric? (second %) (last %)))
  '(:a (:b nil nil) (:b nil nil)))

;이것처럼 내부 함수를 정의하고 외부에서는 바로 대입하는 경우 fn만 사용할 수도 있다.
(
#((fn symmetric? [a b]
    (print a b)
    (if (and (coll? a) (coll? b))
      (let [[a1 a2 a3] a
            [b1 b2 b3] b]
        (print a1 a2 a3 b1 b2 b3)
        (and (= a1 b1)
             (symmetric? a2 b3)
             (symmetric? a3 b2)))
      (= a b)))
  (second %) (last %))
'(:a nil nil))

;이전
#(letfn [(_symm [x y]
                (or (and (coll? x)
                         (coll? y)
                         (= (nth x 0) (nth y 0))
                         (_symm (nth x 1) (nth y 2))
                         (_symm (nth x 2) (nth y 1)))
                    (and (not (coll? x))
                         (not (coll? y))
                         (= x y))))]
  (_symm (nth % 1) (nth % 2)))

;max. 이건 다른 방식이다. 좌우를 recursive하게 바꾼 후 기존과 같은지 비교. m의 입력으로 nil이 들어가면 v가 nil이 되므로 멈춘다.
#(= % ((fn m [[v l r]] (if v [v (m r) (m l)])) %))

;chouser
(fn [[_ a b]]
  ((fn m [[c d e] [f g h]]
     (if c
       (and (= c f) (m d h) (m e g))
       true))
    a b))

(defn k [[c d e] [f g h]]
  c)

(k :a nil)

(
(fn [[_ a b]]
  ((fn m [[c d e] [f g h]]
     (print c d e f g h)
     (if c
       (and (= c f) (m d h) (m e g))
       true))
    a b))
  '(1 nil [1 nil nil])) ;=> true. 빈틈이 있다.

(
  (fn [[_ a b]]
    ((fn m [[c d e] [f g h]]
       (if (or c f)
         (and (= c f) (m d h) (m e g))
         true))
      a b))
  '(1 nil [1 nil nil])) ;=> false. 빈틈이 없어짐.
;1. true/false 전략에서는 chouser에서 (or c f)로 바꾼 게 끝판왕.
;2. reverse해서 현재와 비교하는 전략으로는 max가 끝판왕.
