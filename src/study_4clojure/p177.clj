;; 4Clojure Question 177
;;
;; When parsing a snippet of code it's often a good idea to do a sanity check to see if all the brackets match up. Write a function that takes in a string and returns truthy if all square [ ] round ( ) and curly { } brackets are properly paired and legally nested, or returns falsey otherwise.
;;
;; Use M-x 4clojure-check-answers when you're done!

(__ "This string has no brackets.")

(__ "class Test {
      public static void main(String[] args) {
        System.out.println(\"Hello world.\");
      }
    }")

(not (__ "(start, end]"))

(not (__ "())"))

(not (__ "[ { ] } "))

(__ "([]([(()){()}(()(()))(([[]]({}()))())]((((()()))))))")

(not (__ "([]([(()){()}(()(()))(([[]]({}([)))())]((((()()))))))"))

(not (__ "["))

(map identity "[]")

;me. 처음에 recursive로 풀려다가 한참 고생. 하나씩 들어올때 매칭되어 사라지는 방식으로 하니까 금방됨.
(fn [str]
  (let [brackets (re-seq #"[\(\)\[\]\{\}]" str)
        match? (fn [p n] (= ({"[" "]" "(" ")" "{" "}"} p) n))]
    (-> (reduce #(if (match? (last %1) %2) (vec (butlast %1)) (conj %1 %2)) [] brackets)
        seq
        not
        )))

;max. 나처럼 re-seq를 안한 점이 좋다. pop, peek도 이번 기회에 배워두면 좋을 듯.
#(empty?
  (reduce (fn [a c] (if (#{\( \[ \{} c)
                      (conj a c)
                      (if (= ({\) \( \] \[ \} \{} c 0) (peek a))
                        (pop a)
                        (if (#{\) \] \}} c)
                          (conj a c)
                          a))))
          [] %))
;pop은 butlast에 비해 기존 자료구조를 유지하는 구나.
(pop [1 2 3]) ;=> [1 2]
(peek [1 2 3]) ;=> 3

;chouser. 이건 seq에서 앞쪽으로 넣으면서 비교했구나. 그러면 [a & m] 인수분해로 앞쪽 찾기 쉽다. 하지만 pop, peek를 사용한 max쪽이 조금 더 쉽다.
#(not
  (reduce
    (fn [[a & m :as p] c]
      (if-let [r ({\[ \] \{ \} \( \)} c)]
        (cons r p)
        (cond
          (= c a) m
          (#{\] \} \)} c) %
          1 p)))
    nil
    %))

;daowen. sorted-set, zipmap의 사용을 잘했다.
;pairs => {\{ \}, \[ \], \( \)}
(fn check-brackets [in]
  (let [openers (apply sorted-set "{[(")
        closers (apply sorted-set "}])")
        pairs   (zipmap openers closers)]
    (loop [[c & cs] in, stack []]
      (cond
        (nil? c) (empty? stack)
        (openers c) (recur cs (conj stack (pairs c)))
        (closers c) (if (= c (peek stack))
                      (recur cs (pop stack)))
        :else (recur cs stack)))))