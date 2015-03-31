;; 4Clojure Question 46
;;
;; Write a higher-order function which flips the order of the arguments of an input function.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 3 ((__ nth) 2 [1 2 3 4 5]))

(= true ((__ >) 7 8))

(= 4 ((__ quot) 2 8))

(= [1 2 3] ((__ take) [1 2 3 4 5] 3))

#(fn [x y]
  (% y x))
;이건 대부분 답이 비슷.

;; 4Clojure Question 44
;;
;; Write a function which can rotate a sequence in either direction.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 2 [1 2 3 4 5]) '(3 4 5 1 2))

(= (__ -2 [1 2 3 4 5]) '(4 5 1 2 3))

(= (__ 6 [1 2 3 4 5]) '(2 3 4 5 1))

(= (__ 1 '(:a :b :c)) '(:b :c :a))

(= (__ -4 '(:a :b :c)) '(:c :a :b))

(take 2 [1 2 3 4 5]) ;=> (1 2)
(drop 2 [1 2 3 4 5]) ;=> (3 4 5)
(mod -2 5)

;me. %1, %2가 여러개 나올 때 fn을 약간이나마 간단.
#(let [i (mod %1 (count %2))]
  (concat (drop i %2) (take i %2)))

;ming. split-at 활용예.
#(apply concat (reverse (split-at (mod % (count %2)) %2)))

;hypirion. juxt 활용예.
(fn [n coll]
  (apply concat ((juxt drop take) (mod n (count coll)) coll)))

;chouser. cycle 활용예.
#(let [c (count %2)] (take c (drop (mod % c) (cycle %2))))


;; 4Clojure Question 43
;;
;; Write a function which reverses the interleave process into x number of subsequences.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))

(= (__ (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))

(= (__ (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))

;me. apply map list 가 행렬을 transpose 하는 거구나. max와 답이 같다.
#(apply map list (partition %2 %))

;; 4Clojure Question 50
;;
;; Write a function which takes a sequence consisting of items with different types and splits them up into a set of homogeneous sub-sequences. The internal order of each sub-sequence should be maintained, but the sub-sequences themselves can be returned in any order (this is why 'set' is used in the test cases).
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (set (__ [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]})

(= (set (__ [:a "foo"  "bar" :b])) #{[:a :b] ["foo" "bar"]})

(= (set (__ [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]})

(type [1 2])

;group-by는 순서를 보존하는구나. partition-by는 지역적이고 group-by는 전역적이다.
#(vals (group-by type %))

;; 4Clojure Question 55
;;
;; Write a function which returns a map containing the number of occurences of each distinct item in a sequence.
;;
;; Restrictions (please don't use these function(s)): frequencies
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})

(= (__ [:b :a :b :a :b]) {:a 2, :b 3})

(= (__ '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})

;me.
#(into {} (map (fn [[k v]] [k (count v)]) (group-by identity %)))

;hypirion. merge-with를 기억하고 있다면 나올 수 있는 답.
#(apply merge-with + (map (fn [a] {a 1}) %))

;chouser. 이것도 멋지다. assoc과 (map key don't)를 응용.
reduce #(assoc % %2 (+ 1 (% %2 0))) {}

;; 4Clojure Question 56
;;
;; Write a function which removes the duplicates from a sequence. Order of the items must be maintained.
;;
;; Restrictions (please don't use these function(s)): distinct
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 2 1 3 1 2 4]) [1 2 3 4])

(= (__ [:a :a :b :b :c :c]) [:a :b :c])

(= (__ '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3]))

(= (__ (range 50)) (range 50))

;처음시도. 틀린답. group-by는 values의 순서는 보장하지만 keys의 순서를 보존하지 않는다.
#(keys (group-by identity %))

;두번째 시도. 틀린답. sorted-set는 들어오는 순서를 보존하는 것이 아니라 크기 순서를 보장한다.
#(apply vector (apply sorted-set %))

;me.
reduce #(if ((set %1) %2) %1 (conj %1 %2)) []

;silverio. set 대신 some을 이용할 수도 있다.
(partial reduce
         #(if (some (partial = %2) %) % (conj % %2))
         [])

;; 4Clojure Question 58
;;
;; Write a function which allows you to create function compositions.  The parameter list should take a variable number of functions, and create a function applies them from right-to-left.
;;
;; Restrictions (please don't use these function(s)): comp
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [3 2 1] ((__ rest reverse) [1 2 3 4]))

(= 5 ((__ (partial + 3) second) [1 2 3 4]))

(= true ((__ zero? #(mod % 8) +) 3 5 7 9))

(= "HELLO" ((__ #(.toUpperCase %) #(apply str %) take) 5 "hello world"))


;me. 어려웠다. apply 를 처음만 적용하면 되는데 헷갈렸다.
(fn [& fs] (fn [& vs] (reduce (fn [v f] (f v)) (apply (last fs) vs) (rest (reverse fs)))))

;max. max뿐 아니라 모두 비슷.
(fn [& s]
  #(reduce (fn [c f] (f c))
           (apply (last s) %&)
           (rest (reverse s))))

;; 4Clojure Question 59
;;
;; Take a set of functions and return a new function that takes a variable number of arguments and returns a sequence containing the result of applying each function left-to-right to the argument list.
;;
;; Restrictions (please don't use these function(s)): juxt
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [21 6 1] ((__ + max min) 2 3 5 1 6 4))

(= ["HELLO" 5] ((__ #(.toUpperCase %) count) "hello"))

(= [2 6 4] ((__ :a :c :b) {:a 2, :b 4, :c 6, :d 8 :e 10}))

;me.
(fn [& fs] (fn [& vs] (map #(apply % vs) fs)))

;; 4Clojure Question 54
;;
;; Write a function which returns a sequence of lists of x items each.  Lists of less than x items should not be returned.
;;
;; Restrictions (please don't use these function(s)): partition, partition-all
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 3 (range 9)) '((0 1 2) (3 4 5) (6 7 8)))

(= (__ 2 (range 8)) '((0 1) (2 3) (4 5) (6 7)))

(= (__ 3 (range 8)) '((0 1 2) (3 4 5)))

;me. 재귀로 해보려다가 잘 안되어서 loop로 했음.
#(loop [c %2 result []]
  (if (> % (count c))
    result
    (recur (drop % c) (conj result (take % c)))))

;max.
(fn p [n x]
  (if (>= (count x) n)
    (cons (take n x) (p n (drop n x))))
(cons '(0 1 2) nil) ;=> ((0 1 2)). 이야 cons에 nil을 붙이면 빈리스트에 붙이는 것과 같은 효과를 이용. 대단. 아. lazy-seq 사용할 때 많이 나오는 패턴이구나.

;; 4Clojure Question 70
;;
;; Write a function that splits a sentence up into a sorted list of words.  Capitalization should not affect sort order and punctuation should be ignored.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__  "Have a nice day.")
   ["a" "day" "Have" "nice"])

(= (__  "Clojure is a fun language!")
   ["a" "Clojure" "fun" "is" "language"])

(= (__  "Fools fall for foolish follies.")
   ["fall" "follies" "foolish" "Fools" "for"])

;me.
#(sort-by clojure.string/lower-case (clojure.string/split % #"[ .!]"))

;psk81. 기억할 점. #"\W" 영숫자_ 가 아닌 문자. http://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D
(fn [s]
  (sort #(.compareToIgnoreCase %1 %2) (clojure.string/split s #"\W")))

;max. 기억할 점. #"w+" 영숫자_ 1회 이상. re-seq 매칭패턴 찾아서 sequence로.
#(sort-by (fn [c] (.toLowerCase c)) (re-seq #"\w+" %))

;; 4Clojure Question 67
;;
;; Write a function which returns the first x
;;
;; number of prime numbers.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 2) [2 3])

(= (__ 5) [2 3 5 7 11])

(= (last (__ 100)) 541)

;me
(fn [x]
  (letfn [(is-prime? [n]
                      (not (some zero? (map #(rem n %) (range 2 n)))))]
          (take x (filter is-prime? (drop 2 (range))))))

;max. 나와 비슷하지만 좀 더 간단.
(fn [n]
  (take n
        (remove (fn [k] (some #(zero? (rem k %))
                              (range 2 k)))
                (iterate inc 2))))


;hypirion. 이게 좀더 소수로만 나누기를 확인하니까 더 효율적인 코드. every?, pos? 도 기억할 만 하다.
(fn [n]
  (loop [i 2
         acc []]
    (if (= (count acc) n)
      acc
      (recur (inc i)
             (if (every? #(pos? (rem i %)) acc)
               (conj acc i)
               acc)))))

;; 4Clojure Question 74
;;
;; Given a string of comma separated integers, write a function which returns a new comma separated string that only contains the numbers which are perfect squares.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ "4,5,6,7,8,9") "4,9")

(= (__ "15,16,25,36,37") "16,25,36")

;me.
#(apply str
        (interpose ","
                   (filter (fn [n] (some (fn [x] (= n (* x x))) (range n)))
                           (map read-string (clojure.string/split % #",")))))


;ming. 기억할것들 re-seq, Integer/parseInt, Math/sqrt, clojure.string/join
(fn [v]
  (clojure.string/join
    ","
    (filter
      #(zero? (rem (Math/sqrt %) 1))
      (map #(Integer/parseInt %) (re-seq #"\w+" v)))))
;re-seq는 패턴 맞는 것을 sequence로 만드는 것. Math/sqrt는 Cheat Sheet에 없네. clojure.string/join 은 interpose하여 str하는 것과 동일.

;; 4Clojure Question 65
;;
;; Clojure has many sequence types, which act in subtly different ways. The core functions typically convert them into a uniform "sequence" type and work with them that way, but it can be important to understand the behavioral and performance differences so that you know which kind is appropriate for your application.<br /><br />Write a function which takes a collection and returns one of :map, :set, :list, or :vector - describing the type of collection it was given.<br />You won't be allowed to inspect their class or use the built-in predicates like list? - the point is to poke at them and understand their behavior.
;;
;; Restrictions (please don't use these function(s)): class, type, Class, vector?, sequential?, list?, seq?, map?, set?, instance?, getClass
;;
;; Use M-x 4clojure-check-answers when you're done!

(= :map (__ {:a 1, :b 2}))

(= :list (__ (range (rand-int 20))))

(= :vector (__ [1 2 3 4 5 6]))

(= :set (__ #{10 (rand-int 5)}))

(= [:map :set :vector :list] (map __ [{} #{} [] ()]))

;me
#(let [x (conj % [-1 -2])]
  (if (zero? (count (flatten x)))
    (if (= -2 (x -1))
      :map
      :set)
    (if (= -2 (last (conj (conj % -1) -2)))
      :vector
      :list)))

;max
#(cond
  (= (conj % nil) %) :map ;map에 nil을 conj하면 그대로구나.
  (= (conj % 0) (conj % 0 0)) :set
  (= (conj % 0 1) (cons 1 (cons 0 %))) :list ;col 자체를 비교하는 것이 나와의 차이점이다.
  :else :vector)

;chouser. zipmap은 keys와 values로 map 만드는 것. str를 사용한 것이 기발하다.
#((zipmap (map str [{} #{} () []]) [:map :set :list :vector]) (str (empty %)))

;; 4Clojure Question 76
;;
;; The trampoline function takes a function f and a variable number of parameters.  Trampoline calls f with any parameters that were supplied.  If f returns a function, trampoline calls that function with no arguments.  This is repeated, until the return value is not a function, and then trampoline returns that non-function value.  This is useful for implementing mutually recursive algorithms in a way that won't consume the stack.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= __
   (letfn
     [(foo [x y] #(bar (conj x y) y))
      (bar [x y] (if (> (last x) 10)
                   x
                   #(foo x (+ 2 y))))]
     (trampoline foo [] 1)))

;me
[1 3 5 7 9 11]

;trampoline은 함수가 리턴되면 그 함수를 다시 콜한다. 인자가 리턴되면 끝.

;; 4Clojure Question 80
;;
;; A number is "perfect" if the sum of its divisors equal the number itself.  6 is a perfect number because 1+2+3=6.  Write a function which returns true for perfect numbers and false otherwise.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 6) true)

(= (__ 7) false)

(= (__ 496) true)

(= (__ 500) false)

(= (__ 8128) true)

;me. 다른 답들도 비슷.
(fn [n] (= n (apply + (filter #(zero? (rem n %)) (range 1 n)))))

;; 4Clojure Question 77
;;
;; Write a function which finds all the anagrams in a vector of words.  A word x is an anagram of word y if all the letters in x can be rearranged in a different order to form y.  Your function should return a set of sets, where each sub-set is a group of words which are anagrams of each other.  Each sub-set should have at least two words.  Words without any anagrams should not be included in the result.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ ["meat" "mat" "team" "mate" "eat"])
   #{#{"meat" "team" "mate"}})

(= (__ ["veer" "lake" "item" "kale" "mite" "ever"])
   #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})

(defn anag [x y] (= (sort (vec x)) (sort (vec y))))

;me. 다른 답을 보니 sort seq 가 더 낫다.
(fn [c] (set (map set
     (filter #(> (count %) 1)
             (vals
               (group-by #(sort (vec %)) c))))))

;netpyoung. ->> 를 쓰니 더 깔끔. group-by frequencies 도 또다른 방법.
(fn [lst]
  (->> lst
       (group-by frequencies)
       (map second)
       (filter #(<= 2 (count %)))
       (map set)
       (set)))

;daowen. 더 깔끔. filter second는 그냥은 생각못할 방법.
#(->> % (group-by sort) vals (filter second) (map set) set)

;chouser. vals 대신 destructuring 사용. for도 재밌다.
#(set (for [[_ g] (group-by frequencies %)
            :when (next g)]
        (set g)))

;; 4Clojure Question 60
;;
;; Write a function which behaves like reduce, but returns each intermediate value of the reduction.  Your function must accept either two or three arguments, and the return sequence must be lazy.
;;
;; Restrictions (please don't use these function(s)): reductions
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 5 (__ + (range))) [0 1 3 6 10])

(= (__ conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])

(= (last (__ * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)

;처음 답. 오래 걸렸다. 겨우겨우 만들었다. recursive하게 생각하기가 힘들다.
(fn
  ([f c]
    (for [i (map inc (range))
          :let [sc (take i c)]
          :while (not-empty (drop (dec i) c))]
      (reduce f sc)))
  ([f v c]
    (for [i (range)
          :let [sc (take i c)]
          :while (not-empty (drop (dec i) c))]
      (reduce f v sc))))

;다음 답. lazy-seq를 사용할 수 있다는 것을 알고 난 후. 결국 (f v x) 를 v 자리에 넣는 것이 핵심.
(fn r
  ([f [x & xs]]
    (cons x (lazy-seq (when (first xs)
                        (r f (f x (first xs)) (rest xs))))))
  ([f v [x & xs]]
    (cons v (lazy-seq (when x
                        (r f (f v x) xs))))))

;max. 함수 선언이 뒤에 있어도 사용가능.
(fn g
  ([f [x & s]] (g f x s))
  ([f a [x & s]]
    (lazy-seq
      (cons a (if x (g f (f a x) s))))))

;참고 p62, p

;; 4Clojure Question 69
;;
;; Write a function which takes a function f and a variable number of maps.  Your function should return a map that consists of the rest of the maps conj-ed onto the first.  If a key occurs in more than one map, the mapping(s) from the latter (left-to-right) should be combined with the mapping in the result by calling (f val-in-result val-in-latter)
;;
;; Restrictions (please don't use these function(s)): merge-with
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ * {:a 2, :b 3, :c 4} {:a 2} {:b 2} {:c 5})
   {:a 4, :b 6, :c 20})

(= (__ - {1 10, 2 20} {1 3, 2 10, 3 15})
   {1 7, 2 10, 3 15})

(= (__ concat {:a [3], :b [6]} {:a [4 5], :c [8 9]} {:b [7]})
   {:a [3 4 5], :b [6 7], :c [8 9]})

;me
(fn [f & vs]
  (reduce
    (fn [m1 m2]
      (reduce
        (fn [m [new-k new-v]]
          (let [v (m new-k)]
            (assoc m new-k (if v (f v new-v) new-v))))
        m1 (vec m2)))
    {} vs))

;max. 나보다 나은 점은 (vec m2) 대신 m2 사용한 것. 그리고 {} 없는 것.
(fn [f & s]
  (reduce
    #(reduce
      (fn [a [k v]]
        (assoc a k (if (a k) (f (a k) v) v)))
      %
      %2)
    s))

;; 4Clojure Question 102
;;
;; When working with java, you often need to create an object with <code>fieldsLikeThis</code>, but you'd rather work with a hashmap that has <code>:keys-like-this</code> until it's time to convert. Write a function which takes lower-case hyphen-separated strings and converts them to camel-case strings.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ "something") "something")

(= (__ "multi-word-key") "multiWordKey")

(= (__ "leaveMeAlone") "leaveMeAlone")

#(let [[x & xs] (clojure.string/split % #"-")]
  (->> (cons x (map clojure.string/capitalize xs))
       (apply str)))

;max. reduce를 사용하면 첫번째에는 적용안되니까 좋다. 그리고 `(, ~, @~ 용법도 사용해서 쉽게 합성.
#(reduce (fn [a [c & s]] (apply str `(~a ~(Character/toUpperCase c) ~@s)))
         (.split % "-"))

;chouser. replace를 알고 있다면 가능.
#(clojure.string/replace % #"-." (fn [[_ x]] (format "%S" x)))

;amalloy. chouser와 비슷하지만 destructuring 안 사용한 게 아쉽다.
(fn [s]
  (clojure.string/replace s
                          #"-(\w)"
                          (comp clojure.string/upper-case
                                second)))

;; 4Clojure Question 75
;;
;; Two numbers are coprime if their greatest common divisor equals 1.  Euler's totient function f(x) is defined as the number of positive integers less than x which are coprime to x.  The special case f(1) equals 1.  Write a function which calculates Euler's totient function.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1) 1)

(= (__ 10) (count '(1 3 7 9)) 4)

(= (__ 40) 16)

(= (__ 99) 60)

;p66에서 가져온 것
(defn gcd [a b] (if (= b 0) a (gcd b (rem a b))))

;me. psk810과 동일.
(fn [n]
  (letfn [(gcd [a b]
               (if (= b 0) a (gcd b (rem a b))))]
    (count (filter #(= 1 (gcd n %)) (range 1 (inc n))))))


;; 4Clojure Question 86
;;
;; Happy numbers are positive integers that follow a particular formula: take each individual digit, square it, and then sum the squares to get a new number. Repeat with the new number and eventually, you might get to a number whose squared sum is 1. This is a happy number. An unhappy number (or sad number) is one that loops endlessly. Write a function that determines if a number is happy or not.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 7) true)

(= (__ 986543210) true)

(= (__ 2) false)

(= (__ 3) false)

;me
(fn [x]
  (letfn [(is-happy [prevs n]
                    (cond
                      (= 1 n) true
                      (prevs n) false
                      :else (is-happy
                              (conj prevs n)
                              (apply + (map #(* % %) (map #(- (int %) 48) (str n)))))))]
    (is-happy #{} x)))

;max. 이건 fake. 4로 판별하면 안됨.
(fn [m]
  (= 1
     (some #{1 4}
           (iterate (fn [k] (reduce #(+ % (let [c (- (int %2) 48)] (* c c)))
                                    0
                                    (str k)))
                    m))))

;daowen. 이건 loop를 쓴 버전.
(fn is-happy? [n]
  (let [sqr #(* % %)
        to-int #(- (int %) (int \0))
        sqr-as-int (comp sqr to-int)]
    (loop [n n, seen #{}]
      (if (seen n) false
                   (let [n2 (apply + (map sqr-as-int (str n)))]
                     (if (= 1 n2) true
                                  (recur n2 (conj seen n))))))))

;; 4Clojure Question 85
;;
;; Write a function which generates the <a href="http://en.wikipedia.org/wiki/Power_set">power set</a> of a given set.  The power set of a set x is the set of all subsets of x, including the empty set and x itself.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #{1 :a}) #{#{1 :a} #{:a} #{} #{1}})

(= (__ #{}) #{#{}})

(= (__ #{1 2 3})
   #{#{} #{1} #{2} #{3} #{1 2} #{1 3} #{2 3} #{1 2 3}})

(= (count (__ (into #{} (range 10)))) 1024)

;me
(fn [s]
  (reduce
    (fn [prev v]
      (set (mapcat #(set [(conj % v) %]) prev)))
    #{#{}} s))

;max. 나와 비슷하지만 into때문에 훨씬 간결.
(fn [s]
  (reduce
    (fn [a b]
      (into a (map #(conj % b) a)))
    #{#{}}
    s))

;silverio. max와 같지만 partial 사용한 게 특징.
(partial reduce
         #(into % (map (fn[s](conj s %2)) %))
         #{#{}})

;; 4Clojure Question 78
;;
;; Reimplement the function described in <a href="76"> "Intro to Trampoline"</a>.
;;
;; Restrictions (please don't use these function(s)): trampoline
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (letfn [(triple [x] #(sub-two (* 3 x)))
          (sub-two [x] #(stop?(- x 2)))
          (stop? [x] (if (> x 50) x #(triple x)))]
    (__ triple 2))
  82)

(= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
          (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
    (map (partial __ my-even?) (range 6)))
  [true false true false true false])

;me
(fn k
  ([f a]
    (let [r (f a)]
      (if (fn? r)
        (k r)
        r)))
  ([f]
    (let [r (f)]
      (if (fn? r)
        (k r)
        r))))

;chouser. apply는 x가 nil이면 f만 평가해주는구나. 인자가 없는 것도 apply를 이용하면 한꺼번에 처리할 수 있다.
(fn t [f & x]
  (if (fn? f)
    (t (apply f x))
    f))

(apply (fn []
         1) nil) ;=> 1

;max. trampoline의 정의를 그대로 적용한 느낌.
#(loop [f (% %2)] (if (fn? f) (recur (f)) f))

;; 4Clojure Question 115
;;
;; A balanced number is one whose component digits have the same sum on the left and right halves of the number.  Write a function which accepts an integer n, and returns true iff n is balanced.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true (__ 11))

(= true (__ 121))

(= false (__ 123))

(= true (__ 0))

(= false (__ 88099))

(= true (__ 89098))

(= true (__ 89089))

(= (take 20 (filter __ (range)))
   [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101])

;me
(fn [x]
  (let
    [k (map #(- (int %) 48) (str x))
     n (int (/ (count k) 2))
     l (take n k)
     r (take-last n k)]
    (= (apply + l) (apply + r))))

;daowen
(fn balanced? [n]
  (let [ns (map int (str n))
        size (quot (count ns) 2)]
    (= (apply + (drop size ns))
       (apply + (drop-last size ns)))))
;(map int (str n)) 더 짧아서 좋다. quot는 몫. int로 내림하는 것과 별 차이없다.

;; 4Clojure Question 98
;;
;; A function f defined on a domain D induces an <a href="http://en.wikipedia.org/wiki/Equivalence_relation">equivalence relation</a> on D, as follows: a is equivalent to b with respect to f if and only if (f a) is equal to (f b).  Write a function with arguments f and D that computes the <a href="http://en.wikipedia.org/wiki/Equivalence_class">equivalence classes</a> of D with respect to f.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #(* % %) #{-2 -1 0 1 2})
   #{#{0} #{1 -1} #{2 -2}})

(= (__ #(rem % 3) #{0 1 2 3 4 5 })
   #{#{0 3} #{1 4} #{2 5}})

(= (__ identity #{0 1 2 3 4})
   #{#{0} #{1} #{2} #{3} #{4}})

(= (__ (constantly true) #{0 1 2 3 4})
   #{#{0 1 2 3 4}})

;me
(fn [f s]
  (set (vals (reduce #(merge-with clojure.set/union %1 {(f %2) #{%2}}) {} s))))

;max.
#(set (map set (vals (group-by % %2))))

(vals (group-by #(* % %) #{-2 -1 0 1 2}))
;이번 문제는 group-by를 기억하지 못해서 코드도 길어지고 고생했다.

;; 4Clojure Question 105
;;
;; Given an input sequence of keywords and numbers, create a map such that each key in the map is a keyword, and the value is a sequence of all the numbers (if any) between it and the next keyword in the sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= {} (__ []))

(= {:a [1]} (__ [:a 1]))

(= {:a [1], :b [2]} (__ [:a 1, :b 2]))

(= {:a [1 2 3], :b [], :c [4]} (__ [:a 1 2 3 :b :c 4]))


;me
#(loop [x %
        result {}
        prev-k nil]
  (if (empty? x)
    result
    (let [f (first x)]
      (recur (rest x)
             (merge-with concat result (if (keyword? f)
                                         {f []}
                                         {prev-k [f]}))
             (if (keyword? f)
               f
               prev-k)))))

;joelgrus. loop를 쓴 버전으로는 assoc을 사용한점이 더 깔끔.
(fn kav [s]
(loop [m {}
       remain s
       current-key nil]
  (if (empty? remain) m
                      (let [elt (first remain)
                            new-remain (rest remain)
                            elt-key (keyword? elt)
                            new-key (if elt-key elt current-key)
                            old-vec (if elt-key [] (m current-key))
                            new-vec (if elt-key [] (conj old-vec elt))
                            new-map (assoc m new-key new-vec)]
                        (recur new-map new-remain new-key)))))

;chouser. 아주 간단하게 했다. 이런 생각은 도저히 안날듯.
(
(fn f [[k & v]]
  (if v
    (let [[a b] (split-with number? v)]
      (assoc (f b) k a))
    {}))
  [:b :c 4])

(
(fn [[k & v]]
  v)
  [:a 1 2 3 :b :c 4]) ;=> (1 2 3 :b :c 4)

;max. 이것도 생각하기 어렵다.
(fn f [a k [h & t]]
  (if h
    (if (keyword? h)
      (f (assoc a h []) h t)
      (f (assoc a k (conj (a k) h)) k t))
    a))
{} 0

;; 4Clojure Question 137
;;
;; Write a function which returns a sequence of digits of a non-negative number (first argument) in numerical system with an arbitrary base (second argument). Digits should be represented with their integer values, e.g. 15 would be [1 5] in base 10, [1 1 1 1] in base 2 and [15] in base 16. 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [1 2 3 4 5 0 1] (__ 1234501 10))

(= [0] (__ 0 11))

(= [1 0 0 1] (__ 9 2))

(= [1 0] (let [n (rand-int 100000)](__ n n)))

(= [16 18 5 24 15 1] (__ Integer/MAX_VALUE 42))

;me. (= 0 q) 대신 (zero? q) 쓰는 것 이외에 더할 나위 없다.
(fn f [x n]
  (let [q (quot x n)]
    (conj (if (= 0 q) [] (f q n)) (rem x n))))

;; 4Clojure Question 110
;;
;; <p>Write a function that returns a lazy sequence of "pronunciations" of a sequence of numbers. A pronunciation of each element in the sequence consists of the number of repeating identical numbers and the number itself. For example, <code>[1 1]</code> is pronounced as <code>[2 1]</code> ("two ones"), which in turn is pronounced as <code>[1 2 1 1]</code> ("one two, one one").</p><p>Your function should accept an initial sequence of numbers, and return an infinite lazy sequence of pronunciations, each element being a pronunciation of the previous element.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [[1 1] [2 1] [1 2 1 1]] (take 3 (__ [1])))

(= [3 1 2 4] (first (__ [1 1 1 4 4])))

(= [1 1 1 3 2 1 3 2 1 1] (nth (__ [1]) 6))

(= 338 (count (nth (__ [3 2]) 15)))

;me
(fn f [s]
  (let [next (mapcat #(vector (count %) (first %)) (partition-by identity s))]
    (lazy-cat (vector next) (f next))))

;chouser
(fn [x] (rest (iterate #(mapcat (juxt count first) (partition-by identity %)) x)))
;iterate를 까먹고 있었다.
;그리고 기억하면 좋은 것. juxt
((juxt count first) [1 1 1]) ;=> [3 1]

;; 4Clojure Question 144
;;
;; Write an oscillating iterate: a function that takes an initial value and a variable number of functions. It should return a lazy sequence of the functions applied to the value in order, restarting from the first function after it hits the end.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 3 (__ 3.14 int double)) [3.14 3 3.0])

(= (take 5 (__ 3 #(- % 3) #(+ 5 %))) [3 0 5 2 7])

(= (take 12 (__ 0 inc dec inc dec inc)) [0 1 0 1 0 1 2 1 2 1 2 3])

;me
(fn k [x & fs]
  (let [col (reductions #(%2 %1) x fs)]
    (lazy-cat (butlast col) (apply k (cons (last col) fs)))))

;max version1
(fn [x & f] (reductions #(%2 %) x (cycle f)))
;cycle을 알고 있었으면 쉽게 할 수 있었을 듯.

;max version2
(fn [x & f]
  ((fn g [x [h & t]]
     (lazy-seq (cons x (g (h x) (or t f))))) x f))
;우왕 (or t f)로 하면 t가 하나씩 줄어들다가 없어지면 다시 f로 복원.

;psk810
(fn [n & fs]
  (letfn [(osc [n fs]
               (cons n
                     (lazy-seq (osc ((first fs) n) (rest fs)))))]
    (osc n (cycle fs))))
;cycle를 사용하면 lazy-seq도 더 쉽게 활용가능.

;; 4Clojure Question 108
;;
;; <p>Given any number of sequences, each sorted from smallest to largest, find the smallest single number which appears in all of the sequences. The sequences may be infinite, so be careful to search lazily.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 3 (__ [3 4 5]))

(= 4 (__ [1 2 3 4 5 6 7] [0.5 3/2 4 19]))

(= 7 (__ (range) (range 0 100 7/6) [2 3 5 7 11 13]))

(= 64 (__ (map #(* % % %) (range)) ;; perfect cubes
          (filter #(zero? (bit-and % (dec %))) (range)) ;; powers of 2
          (iterate inc 20))) ;; at least as large as 20

;me. ns를 n으로 하면 마지막 문제의 두번째 항목때문에 시간제한이 걸림. 그러므로 ns로 바꿔서 min-last를 가진 시퀀스만 take 증가.
(fn s [& vs]
  (letfn [(k [ns ls]
             (let [takes (map #(take %1 %2) ns ls)
                   result (first (reduce clojure.set/intersection (map set takes)))]
               (if result
                 result
                 (let [lasts (map last takes)
                       min-last (apply min lasts)
                       incs (map #(if (= min-last %) 1 0) lasts)]
                   (k (map + incs ns) ls)))))]
    (k (repeat (count vs) 1) vs)))

;max. 제일 큰 녀석을 고르고 이것보다 작은 것들 버려나가기 전략.
(fn f [& s]
  (let [h (map first s)
        m (apply max h)]
    (if (apply = h)
      m
      (apply f (map (fn [z] (drop-while #(< % m) z)) s)))))
;([1 2 3 4 5 6 7] [0.5 3/2 4 19])
;((1 2 3 4 5 6 7) (3/2 4 19))
;((2 3 4 5 6 7) (3/2 4 19))
;((2 3 4 5 6 7) (4 19))
;((4 5 6 7) (4 19))

;chouser. 제일 작은 것만 버려나가는 전략.
#(let [a (map first %&)
       b (apply min a)]
  (if (apply = a)
    b
    (recur (map (fn [[x & y :as z]] (if (= x b) y z)) %&))))

;max와 chouser를 보면 내 방식은 비효율적이다.

;; 4Clojure Question 93
;;
;; Write a function which flattens any nested combination of sequential things (lists, vectors, etc.), but maintains the lowest level sequential items.  The result should be a sequence of sequences with only one level of nesting.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [["Do"] ["Nothing"]])
   [["Do"] ["Nothing"]])

(= (__ [[[[:a :b]]] [[:c :d]] [:e :f]])
   [[:a :b] [:c :d] [:e :f]])

(= (__ '((1 2)((3 4)((((5 6)))))))
   '((1 2)(3 4)(5 6)))

;me
(fn k [x]
  (if (some coll? x)
    (mapcat k x)
    [x]))

;amalloy. every? 인걸 제외하면 나와 동일. 지금은 every?이 some보다 의미가 확실한 경우일듯.
(fn f [x]
  (if (every? coll? x)
    (mapcat f x)
    [x]))


;; 4Clojure Question 158
;;
;; Write a function that accepts a curried function of unknown arity <i>n</i>.  Return an equivalent function of <i>n</i> arguments.
;;
;; <br/>
;;
;; You may wish to read <a href="http://en.wikipedia.org/wiki/Currying">this</a>.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 10 ((__ (fn [a]
             (fn [b]
               (fn [c]
                 (fn [d]
                   (+ a b c d))))))
       1 2 3 4))

(= 24 ((__ (fn [a]
             (fn [b]
               (fn [c]
                 (fn [d]
                   (* a b c d))))))
       1 2 3 4))

(= 25 ((__ (fn [a]
             (fn [b]
               (* a b))))
       5 5))

;me
(fn [f]
  (fn [& v]
    (loop [a v b f]
      (if (empty? a)
        b
        (recur (rest a) (b (first a)))))))

;chouser.
(fn [f] (fn [& a] (reduce #(% %2) f a)))

;나도 처음에 reduce를 생각했는데 2 arguments가 필요하다는 정의를 보고 안된다고 생각했다. f 가 두항이 필요한 것으로 착각한 것이다. 두항이 들어가도록 내가 자유롭게 만들면 된다는 것을 다시 깨달음.

;이 문제는 환경을 기억하고 다니는 closure를 잘 이용한 것 같다.

;; 4Clojure Question 114
;;
;; <p><a
;;
;; href="http://clojuredocs.org/clojure_core/clojure.core/take-while">take-while</a>
;;
;; is great for filtering sequences, but it limited: you can only examine
;;
;; a single item of the sequence at a time. What if you need to keep
;;
;; track of some state as you go over the sequence?</p>
;;
;; 
;;
;; <p>Write a function which accepts an integer <code>n</code>, a predicate <code>p</code>, and a sequence. It should return a lazy sequence of items in the list up to, but not including, the <code>n</code>th item that satisfies the predicate.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [2 3 5 7 11 13]
   (__ 4 #(= 2 (mod % 3))
         [2 3 5 7 11 13 17 19 23]))

(= ["this" "is" "a" "sentence"]
   (__ 3 #(some #{\i} %)
         ["this" "is" "a" "sentence" "i" "wrote"]))

(= ["this" "is"]
   (__ 1 #{"a"}
         ["this" "is" "a" "sentence" "i" "wrote"]))

(take-while neg? [-2 -1 0 1 -32 3]) ;=> (-2 -1)

;me. take-while의 정의를 보고 따라하니 쉬웠다.
(fn k [n pred coll]
  (lazy-seq
    (let [next-n (if (pred (first coll)) (dec n) n)]
      (when (> next-n 0)
        (cons (first coll) (k next-n pred (rest coll)))))))

;amalloy. 밑의 clojuredocs의 내용과 take-while의 정의를 보면 when-let과 seq 를 사용하는 것은 1.성능과 2.nil 인 경우 밑으로 안 들어가기 위한 일종의 패턴
(fn f [n pred coll]
  (lazy-seq
    (when-let [[x & xs] (seq coll)]
      (let [n (if (pred x), (dec n), n)]
        (when-not (zero? n)
          (cons x (f n pred xs)))))))
;그리고 [x & xs] 사용하는 방식이 first를 두번 쓰지 않아도 되므로 좋다.

;clojuredocs의 내용
;; Very useful when working with sequences. Capturing the return value
;; of `seq` brings a performance gain in subsequent `first`/`rest`/`next`
;; calls. Also the block is guarded by `nil` punning.

(defn drop-one
  [coll]
  (when-let [s (seq coll)]
    (rest s)))

(drop-one [1 2 3]) ;=> (2 3)
(drop-one []) ;=> nil
;~clojuredocs의 내용

(seq []) ;=> nil

;; 4Clojure Question 132
;;
;; Write a function that takes a two-argument predicate, a value, and a collection; and returns a new collection where the <code>value</code> is inserted between every two items that satisfy the predicate.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= '(1 :less 6 :less 7 4 3) (__ < :less [1 6 7 4 3]))

(= '(2) (__ > :more [2]))

(= [0 1 :x 2 :x 3 :x 4]  (__ #(and (pos? %) (< % %2)) :x (range 5)))

(empty? (__ > :more ()))

(= [0 1 :same 1 2 3 :same 5 8 13 :same 21]
   (take 12 (->> [0 1]
                 (iterate (fn [[a b]] [b (+ a b)]))
                 (map first) ; fibonacci numbers
                 (__ (fn [a b] ; both even or both odd
                       (= (mod a 2) (mod b 2)))
                     :same))))

;첫번때 시도. 잘 나가다가 마지막 문제에서 fail. reduce는 마지막까지 가야 결과가 나오므로 lazy는 안된다.
(fn [p v col]
  (reduce #(concat %1
                   (if (or (empty? %1) (not (p (last %1) %2))) [%2] [v %2]))
          [] col))

;me
(fn [p v col]
  (if (empty? col)
    col
    (cons (first col)
          (apply concat (map #(if (p %1 %2)
                               [v %2] [%2]) col (rest col))))))

;amalloy. 나와 가장 비슷한 답이지만 세련되었다. 차이점 1. empty? 대신 패턴인 when-let과 seq를 썼다. 2. mapcat를 쓰면 훨씬 간단.
(fn [pred inter coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (cons (first s)
            (mapcat (fn [[left right]]
                      (if (pred left right)
                        [inter right]
                        [right]))
                    (partition 2 1 s))))))
(partition 2 1 [1 2 3 4 5]) ;=> ((1 2) (2 3) (3 4) (4 5))
;처음에 이거 어떻게 하나 생각하다 포기했는데 이렇게 partition 2(n) 1(step) 로 하면 되는 구나

;hypirion. amalloy에 비해 partition-all을 사용하여 cons와 when-let 등이 필요없어졌다.
#(mapcat
  (fn [[a b]] (cond (nil? b) [a]
                    (%1 a b) [a %2]
                    :else [a]))
  (partition-all 2 1 %3))
(partition-all 2 1 [1 2 3 4 5]) ;=> ((1 2) (2 3) (3 4) (4 5) (5))

;silverio. hypirion에 비해 (and b (% a b))가 더 간다. 그러나 flatten대신 mapcat을 쓰는 것이 더 간편했을 듯.
#(->> %3
      (partition-all 2 1)
      (map (fn [[a b]] (if (and b (% a b)) [a %2] [a])))
      flatten)

;silverio와 hypirion의 장점만 뽑아 합친 답
#(mapcat
  (fn [[a b]] (if (and b (% a b)) [a %2] [a]))
  (partition-all 2 1 %3))

;; 4Clojure Question 104
;;
;; This is the inverse of <a href='92'>Problem 92</a>, but much easier. Given an integer smaller than 4000, return the corresponding roman numeral in uppercase, adhering to the <a href='http://www.numericana.com/answer/roman.htm#valid'>subtractive principle</a>.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= "I" (__ 1))

(= "XXX" (__ 30))

(= "IV" (__ 4))

(= "CXL" (__ 140))

(= "DCCCXXVII" (__ 827))

(= "MMMCMXCIX" (__ 3999))

(= "XLVIII" (__ 48))

;me. 이 문제는 다른 사람 풀이를 별로 연구하고 싶지 않네.
(fn [n]
  (letfn [(k [x a]
             (let [q (quot x 10)
                   r (rem x 10)
                   ar (* a r)]
               (if (= 0 q)
                 [ar]
                 (cons ar (k q (* a 10))))))]
    (apply str
           (map
             {1 "I"   10 "X" 100 "C" 1000 "M"
              2 "II"  20 "XX" 200 "CC" 2000 "MM"
              3 "III" 30 "XXX" 300 "CCC" 3000 "MMM"
              4 "IV"  40 "XL" 400 "CD"
              5 "V"   50 "L" 500 "D"
              6 "VI"  60 "LX" 600 "DC"
              7 "VII" 70 "LXX" 700 "DCC"
              8 "VIII" 80 "LXXX" 800 "DCCC"
              9 "IX" 90 "XC" 900 "CM"}
             (reverse (k n 1))))))


;; 4Clojure Question 103
;;
;; Given a sequence S consisting of n elements generate all <a href="https://secure.wikimedia.org/wikipedia/en/wiki/Combination">k-combinations</a> of S, i. e. generate all possible sets consisting of k distinct elements taken from S.
;;
;; 
;;
;; The number of k-combinations for a sequence is equal to the <a href="https://secure.wikimedia.org/wikipedia/en/wiki/Binomial_coefficient">binomial coefficient</a>.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1 #{4 5 6}) #{#{4} #{5} #{6}})

(= (__ 10 #{4 5 6}) #{})

(= (__ 2 #{0 1 2}) #{#{0 1} #{0 2} #{1 2}})

(= (__ 3 #{0 1 2 3 4}) #{#{0 1 2} #{0 1 3} #{0 1 4} #{0 2 3} #{0 2 4}
                         #{0 3 4} #{1 2 3} #{1 2 4} #{1 3 4} #{2 3 4}})

(= (__ 4 #{[1 2 3] :a "abc" "efg"}) #{#{[1 2 3] :a "abc" "efg"}})

(= (__ 2 #{[1 2 3] :a "abc" "efg"}) #{#{[1 2 3] :a} #{[1 2 3] "abc"} #{[1 2 3] "efg"}
                                    #{:a "abc"} #{:a "efg"} #{"abc" "efg"}})

;me. p85 이용했다.
(fn [n s]
  (set (filter #(= n (count %))
               (reduce
                 (fn [a b]
                   (into a (map #(conj % b) a)))
                 #{#{}} s))))

;max
(fn [n s]
  (loop [n n a #{#{}}]
    (if (> n 0)
      (recur (dec n) (set (for [x a y s :when (not (x y))] (conj x y))))
      a)))

;; 4Clojure Question 116
;;
;; A <a href="http://en.wikipedia.org/wiki/Balanced_prime">balanced prime</a> is a prime number which is also the mean of the primes directly before and after it in the sequence of valid primes.  Create a function which takes an integer n, and returns true iff it is a balanced prime.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= false (__ 4))

(= true (__ 563))

(= 1103 (nth (filter __ (range)) 15))

;me
(fn [x]
  (letfn [(is-prime? [k]
                     (not (some #(zero? (rem k %))
                                (range 2 k))))]
    (and (is-prime? x)
         (>= x 2)
         (loop [l (dec x) r (inc x)]
           (let [lp (is-prime? l)
                 rp (is-prime? r)]
             (cond
               (= l 1) false
               (and lp rp) true
               (or lp rp) false
                       :else
               (recur (dec l) (inc r))))))))

;다른 solution 좋은 것 많아 보이는데 연구는 나중으로 미룸.

;; 4Clojure Question 121
;;
;; 	 Given a mathematical formula in prefix notation, return a function that calculates
;;
;; 	 the value of the formula.
;;
;; 	 The formula can contain nested calculations using the four basic
;;
;; 	 mathematical operators, numeric constants, and symbols representing variables.
;;
;; 	 The returned function has to accept a single parameter containing the map
;;
;; 	 of variable names to their values.
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 2 ((__ '(/ a b))
      '{b 8 a 16}))

(= 8 ((__ '(+ a b 2))
      '{a 2 b 4}))

(= [6 0 -4]
     (map (__ '(* (+ 2 a)
  	              (- 10 b)))
	        '[{a 1 b 8}
	          {b 5 a -2}
	          {a 2 b 11}]))

(= 1 ((__ '(/ (+ x 2)
              (* 3 (+ y 1))))
      '{x 4 y 1}))

;처음 답. 4clojure에서 'You tripped the alarm! eval is bad!' 이런 에러가 나옴
(fn [l]
  (fn [m]
    (eval
      (map
        (fn [x]
          (reduce #(if (= %1 (first %2))
                    (second %2) %1) x (map identity m)))
        l))))


;결국 풀었다! 만세. 4clojure에는 eval을 사용하지 못해서 너무 힘들었다.
;https://groups.google.com/forum/#!topic/4clojure/sg4KCPnUCpg 여기에서 "you have to map from the symbol + to the function +, etc." 언급을 보고 해결.
(fn [l]
  (fn [m]
    (letfn [(change [x]
                    (reduce #(if (= %1 (first %2))
                              (second %2) %1) x (concat (map identity m) [['/ /] ['+ +] ['- -] ['* *]])))
            (calc [y]
                  (apply (change (first y))
                         (map #(if (list? %)
                                (calc %)
                                (change %)) (rest y))))]
      (calc l)
      )))

;amalloy
(fn [formula]
  (fn [values]
    ((fn compute [x]
       (if (seq? x)
         (let [[op & args] x]
           (apply ({'+ + '/ / '- - '* *} op)
                  (map compute args)))
         (get values x x)))
      formula)))
;배울점
;1. change 대신 get을 쓰면 간단하겠구나.
(get '{b 8 a 16} 7 7) ;=> 7
(get '{b 8 a 16} 'a 'a) ;=> 16
;2. op만 따로 처리한 점때문에 간단해졌다.
;3. (fn compute [x] ...) 이렇게 처리해도 재귀 호출이 가능하다.
;4. list? 대신 seq? 를 사용하는 것이 더 general

;psk810
(fn f [exp]
  (let [[op & args] exp
        op ({'+ + '- - '* * '/ /} op)]
    (fn [env]
      (apply op (map
                  #(if (seq? %) ((f %) env)
                                (if (number? %) %
                                                (env %))) args)))))

;((f %) env) 이렇게 재귀적으로 처리하면 생각하기 쉽다.
;(env %) 밑에서 테스트
('{b 8 a 16} 'b) ;=> 8
;get대신 그냥 이렇게 처리해도 된다. 나처럼 (map identity m) 이런거 할 필요없다.


;; 4Clojure Question 148
;;
;; <p>Write a function which calculates the sum of all natural numbers under <i>n</i> (first argument) which are evenly divisible by at least one of <i>a</i> and <i>b</i> (second and third argument). Numbers <i>a</i> and <i>b</i> are guaranteed to be <a href="http://en.wikipedia.org/wiki/Coprime">coprimes</a>.</p>
;;
;; 
;;
;; <p>Note: Some test cases have a very large <i>n</i>, so the most obvious solution will exceed the time limit.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 0 (__ 3 17 11))

(= 23 (__ 10 3 5))

(= 233168 (__ 1000 3 5))

(= "2333333316666668" (str (__ 100000000 3 5)))

(= "110389610389889610389610"
  (str (__ (* 10000 10000 10000) 7 11)))

(= "1277732511922987429116"
  (str (__ (* 10000 10000 10000) 757 809)))

(= "4530161696788274281"
  (str (__ (* 10000 10000 1000) 1597 3571)))

;me. bigint 대신 int를 쓰면 범위를 넘는다는 말이 나온다.
;이 문제는 1부터 n까지의 합을 공식으로 아느냐를 테스트하므로 그리 좋은 문제는 아닌 것 같다.
(fn [n a b]
  (letfn [(sum [n]
               (/ (* n (inc n)) 2))
          (sum-to-n-by-x [n x]
                    (* x (sum (bigint (/ (dec n) x)))))]
    (- (+ (sum-to-n-by-x n a)
          (sum-to-n-by-x n b))
       (sum-to-n-by-x n (* a b)))
      ))

;; 4Clojure Question 171
;;
;; Write a function that takes a sequence of integers and returns a sequence of "intervals".  Each interval is a a vector of two integers, start and end, such that all integers between start and end (inclusive) are contained in the input sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ [1 2 3]) [[1 3]])

(= (__ [10 9 8 1 2 3]) [[1 3] [8 10]])

(= (__ [1 1 1 1 1 1 1]) [[1 1]])

(= (__ []) [])

(= (__ [19 4 17 1 3 10 2 13 13 2 16 4 2 15 13 9 6 14 2 11])
       [[1 4] [6 6] [9 11] [13 17] [19 19]])

;me. 쉬운 문제.
(fn [col]
  (reduce #(if (= (last (last %1)) (dec %2))
            (assoc-in %1 [(dec (count %1)) 1] %2)
            (into %1 [[%2 %2]])) [] (sort (set col))))

;max. 이해가 바로는 안가지만 나중을 위해 적어둔다.
#(map vector (% %2 dec) (% %2 inc))
#(reduce (fn [a b] (if ((set %) (%2 b)) a (conj a b))) [] (sort (set %)))

;chouser. 나와 논리가 같다. clojure 사용방법이 다름.
#(->> % distinct sort
      (reduce
        (fn [[[l h] & im] x] ; =>이렇게 분해하는 것도 봐두자.
          (if (= x (+ 1 h))
            (cons [l x] im) ;=> 앞쪽에다 붙였군
            (list* [x x] [l h] im))) ;=> list*는 "원소, 원소, ..., sequence" 구나
        [[0 -1]])
      reverse
      rest)
;set 대신 distinct를 쓸 수도 있겠군.
(distinct [1 2 1]) ;=> (1 2)

;; 4Clojure Question 131
;;
;; Given a variable number of sets of integers, create a function which returns true iff all of the sets have a non-empty subset with an equivalent summation.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= true  (__ #{-1 1 99} 
             #{-2 2 888}
             #{-3 3 7777})) ; ex. all sets have a subset which sums to zero

(= false (__ #{1}
             #{2}
             #{3}
             #{4}))

(= true  (__ #{1}))

(= false (__ #{1 -3 51 9} 
             #{0} 
             #{9 2 81 33}))

(= true  (__ #{1 3 5}
             #{9 11 4}
             #{-3 12 3}
             #{-3 4 -2 10}))

(= false (__ #{-1 -2 -3 -4 -5 -6}
             #{1 2 3 4 5 6 7 8 9}))

(= true  (__ #{1 3 5 7}
             #{2 4 6 8}))

(= true  (__ #{-1 3 -5 7 -9 11 -13 15}
             #{1 -3 5 -7 9 -11 13 -15}
             #{1 -1 2 -2 4 -4 8 -8}))

(= true  (__ #{-10 9 -8 7 -6 5 -4 3 -2 1}
             #{10 -9 8 -7 6 -5 4 -3 2 -1}))

;me. p85의 power set 사용
(fn [& vs]
  (letfn [(power-set [s]
                     (reduce
                       (fn [a b]
                         (into a (map #(conj % b) a)))
                       #{#{}} s))
          (non-empty-power-set [s]
                               (clojure.set/difference (power-set s) #{#{}}))
          (sum-set [s]
                   (set (map #(apply + %) (non-empty-power-set s))))]
    (not (empty?
           (apply clojure.set/intersection (map sum-set vs))))))

;jafingerhut. 나와 거의 비슷. 배울점은 다음과 같다.
;1. let으로 power-set에 fn 으로 세팅. 좀더 보기 쉽다.
;2. difference 대신 disj 사용
(fn [& int-sets]
  (let [power-set (fn [s]
                    (reduce (fn [power-set x]
                              (into power-set (map #(conj % x) power-set)))
                            #{#{}} s))
        all-nonempty-subsets (map #(disj (power-set %) #{}) int-sets)
        subset-sums (map (fn [subsets-of-one]
                           (set (map #(apply + %) subsets-of-one)))
                         all-nonempty-subsets)]
    (not= #{} (apply clojure.set/intersection subset-sums))))

;silverio. 배울점. some을 사용한 것. intersection 으로 모두 검사하는 것이 아니라 공통적으로 같은게 하나라도 나오면 멈추고 true를 리턴.
(fn [& sl]
  (let [pset (partial reduce
                      #(into %(map (fn [v] (conj v %2)) %)), #{#{}})
        sums (->> sl (map pset) (map next)
                  (map #(map (partial reduce +) %)) (map set))]
    (if (some
          #(every? (fn [s] (s %)) (next sums)) (first sums))
      true false)))

;chouser. 이해 못했지만 일단 기록.
(fn [& n]
  (< 0 (count
         (reduce #(keep %2 %)
                 (map (fn f [[x & s]]
                        (into #{x}
                              (if s
                                (into (f s)
                                      (map #(+ x %) (f s))))))
                      (map seq n))))))

;; 4Clojure Question 112
;;
;; Create a function which takes an integer and a nested collection of integers as arguments.  Analyze the elements of the input collection and return a sequence which maintains the nested structure, and which includes all elements starting from the head whose sum is less than or equal to the input integer.
;;
;; Use M-x 4clojure-check-answers when you're done!

(=  (__ 10 [1 2 [3 [4 5] 6] 7])
   '(1 2 (3 (4))))

(=  (__ 30 [1 2 [3 [4 [5 [6 [7 8]] 9]] 10] 11])
   '(1 2 (3 (4 (5 (6 (7)))))))

(=  (__ 9 (range))
   '(0 1 2 3))

(=  (__ 1 [[[[[1]]]]])
   '(((((1))))))

(=  (__ 0 [1 2 [3 [4 5] 6] 7])
   '())

(=  (__ 0 [0 0 [0 [0]]])
   '(0 0 (0 (0))))

(=  (__ 1 [-10 [1 [2 3 [4 5 [6 7 [8]]]]]])
   '(-10 (1 (2 3 (4)))))

;me. max 나 ming 과 비슷.
(fn k [n [h & t]]
  (when h
    (if (coll? h)
      (list (k n h))
      (let [next-n (- n h)]
        (if (>= next-n 0)
          (cons h (k next-n t))
          `())))))

;max
(fn [n s]
  ((fn g [n [h & t]]
     (if (coll? h)
       `(~(g n h))
       (if (and h (>= n h))
         (cons h (g (- n h) t))
         ())))
    n s))

;ming
(fn f [sm [ft & rt]]
  (if (coll? ft)
    (list (f sm (if (empty? rt) ft (conj ft rt))))
    (if (or (nil? ft) (< sm ft))
      '()
      (conj (f (- sm ft) rt) ft))))

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

;; 4Clojure Question 150
;;
;; <p>A palindromic number is a number that is the same when written forwards or backwards (e.g., 3, 99, 14341).</p>
;;
;; 
;;
;; <p>Write a function which takes an integer <code>n</code>, as its only argument, and returns an increasing lazy sequence of all palindromic numbers that are not less than <code>n</code>.</p>
;;
;; 
;;
;; <p>The most simple solution will exceed the time limit!</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 26 (__ 0))
   [0 1 2 3 4 5 6 7 8 9 
    11 22 33 44 55 66 77 88 99 
    101 111 121 131 141 151 161])

(= (take 16 (__ 162))
   [171 181 191 202 
    212 222 232 242 
    252 262 272 282 
    292 303 313 323])

(= (take 6 (__ 1234550000))
   [1234554321 1234664321 1234774321 
    1234884321 1234994321 1235005321])

(= (first (__ (* 111111111 111111111)))
   (* 111111111 111111111))

(= (set (take 199 (__ 0)))
   (set (map #(first (__ %)) (range 0 10000))))

(= true 
   (apply < (take 6666 (__ 9999999))))

(= (nth (__ 0) 10101)
   9102019)

;첫번째 단순한 버전. timeout에 걸렸다.
(let [palin? (fn [n]
               (let [n-str (str n)]
                 (= (seq n-str) (reverse n-str))))]
  (fn palin-seq [n]
    (if (palin? n)
      (cons n (lazy-seq (palin-seq (inc n))))
      (lazy-seq (palin-seq (inc n))))))

;두번째. 복잡하고 지저분하다. 그런데 답을 보니 다른 사람도 만만치 않네. 별로 좋은 문제가 아닌 것 같다.
(let [palin? (fn [n]
               (let [n-str (str n)]
                 (= (seq n-str) (reverse n-str))))
      next-n (fn [n]
               (let [exp (fn [i] (reduce * (repeat i 10)))
                     x (count (str n))
                     add-n (if (odd? x)
                             (let [k (/ (- x 1) 2)]
                               (if (= \9 (nth (str n) k))
                                 1
                                 (exp k)))
                             (let [k (/ (- x 2) 2)]
                               (if (= [\9 \9] (take 2 (drop k (str n))))
                                 1
                                 (* 11 (exp k)))))
                     next-n-cand (+ add-n n)
                     y (count (str next-n-cand))]
                 (if (= x y)
                   next-n-cand
                   (inc (exp (dec y))))))]
  (fn palin-seq [n]
    (if (palin? n)
      (cons n (lazy-seq (palin-seq (next-n n))))
      (lazy-seq (palin-seq (inc n))))))

;max
(fn [n]
  (let [d #(loop [m % i 0]
            (if (> m 0)
              (recur (quot m 10) (inc i))
              i))
        r #(loop [m % a 0]
            (if (> m 0)
              (recur (quot m 10) (* 10 (+ a (rem m 10))))
              (/ a 10)))
        p #(loop [i % a 1]
            (if (> i 0)
              (recur (dec i) (* 10 a))
              a))
        t #(p (quot (d %) 2))
        w #(let [k (t %)
                 q (- % (rem % k) (- k))]
            (+ q (rem (r q) (if (= 9 %) 11 k))))
        k (t n)
        q (- n (rem n k))
        v (+ q (rem (r q) k))
        m (if (>= v n) v (w n))]
    (iterate w m)))

;chouser
(fn [s]
  (let [p #(Long. (apply str % ((if %2 next seq) (reverse (str %)))))
        d #(count (str %))
        o #(odd? (d %))
        b #(Long. (subs (str %) 0 (quot (+ 1 (d %)) 2)))
        i #(let [x (b %)
                 o (o %)
                 y (+ 1 x)]
            (cond
              (= (d x) (d y)) (p y o)
              o (p (/ y 10) nil)
              1 (p y 1)))]
    (filter #(>= % s) (iterate i (p (b s) (o s))))))

;jafingerhut

;; Note that all palindromic numbers are either of the form:

;; (concat x (reverse x)) for some sequence of one or more digits x,
;; so the palindromic number has an even number of digits.

;; or:

;; (concat x [ d ] (reverse x)) for some sequence of digits x (perhaps
;; empty) and some digit 'd', so the palindromic number has an odd
;; number of digits.

;; To generate the palindromic numbers in increasing order, we just
;; need to make the sequence x (for even-length) or (concat x [d])
;; (for odd-length) represent incrementing decimal numbers.

;; function next-pal returns the smallest palindromic number that is
;; greater than or equal to its argument.

(fn [n]
  (let [to-digits (fn [n] (map #(- (int %) (int \0)) (str n)))
        to-int (fn [s] (read-string (apply str s)))
        make-pal (fn [digits odd rev-digits]
                   (to-int (concat digits
                                   (if odd (rest rev-digits) rev-digits))))
        next-pal (fn [n]
                   (let [digits (to-digits n)
                         len (count digits)
                         half-len (quot (inc len) 2)
                         half-digits (take half-len digits)
                         pal (make-pal half-digits
                                       (odd? len) (reverse half-digits))]
                     (if (>= pal n)
                       pal
                       (let [half-digits+1 (to-digits
                                             (inc (to-int half-digits)))]
                         (make-pal (if (> (count half-digits+1) half-len)
                                     (butlast half-digits+1)
                                     half-digits+1)
                                   (odd? len)
                                   (reverse half-digits+1))))))]
    (iterate #(next-pal (inc %))
             (next-pal n))))
