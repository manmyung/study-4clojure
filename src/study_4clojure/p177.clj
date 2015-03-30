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

;4clojure가 현재 다운되어서 답 확인은 로컬에서만 했음.