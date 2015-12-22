;; 4Clojure Question 19
;;
;; Write a function which returns the last element in a sequence.
;;
;; Restrictions (please don't use these function(s)): last
;;
;; Use M-x 4clojure-check-answers when you're done!


(= (#(first (reverse %)) [1 2 3 4 5]) 5)

(= (__ '(5 4 3)) 3)

(= (__ ["b" "c" "d"]) "d")

;max 이게 계산은 많겠지만 로직이 단순하네.
#(-> % reverse first)

;이것도 기억해둘만하다.
#(peek(vec %))


;; 4Clojure Question 20
;;
;; Write a function which returns the second to last element from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(-> % reverse second) (list 1 2 3 4 5)) 4)

(= (__ ["a" "b" "c"]) "b")

(= (__ [[1 2] [3 4]]) [1 2])

;Max: butlast 용법 배우기 좋다.
#(last (butlast %))

;; 4Clojure Question 22
;;
;; Write a function which returns the total number of elements in a sequence.
;;
;; Restrictions (please don't use these function(s)): count
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(reduce (fn [x _] (inc x)) 0 %) '(1 2 3 3 1)) 5)

(= (__ "Hello World") 11)

(= (__ [[1 2] [3 4] [5 6]]) 3)

(= (__ '(13)) 1)

(= (__ '(:a :b :c)) 3)

(#(reduce (fn [x _] (inc x)) 0 %) [7 4 5 3 4])

;max 이것도 좋지만 약간 꼼수같다.
(reduce #(or (+ % 1) %2) 0 [7 4 5 3 4])

;최종으로 가장 좋다고 생각하는 것
(reduce (fn [x _] (inc x)) 0 [7 4 5 3 4])

;; 4Clojure Question 21
;;
;; Write a function which returns the Nth element from a sequence.
;;
;; Restrictions (please don't use these function(s)): nth
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(first (drop %2 %1)) '(4 5 6 7) 2) 6)

(= (__ [:a :b :c] 0) :a)

(= (__ [1 2 3 4] 1) 2)

(= (__ '([1 2] [3 4] [5 6]) 2) [5 6])


; #(last (take (inc %2) %1)) 도 가능한데 inc 하나 더 들어간다.

;; 4Clojure Question 24
;;
;; Write a function which returns the sum of a sequence of numbers.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (reduce + [1 2 3]) 6)

(= (__ (list 0 -2 5 5)) 8)

(= (__ #{4 2 1}) 7)

(= (__ '(0 0 -1)) -1)

(= (__ '(1 10 3)) 14)

;이건 대부분 풀이가 비슷

;; 4Clojure Question 25
;;
;; Write a function which returns only the odd numbers from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #{1 2 3 4 5}) '(1 3 5))

(= (__ [4 2 1 6]) '(1))

(= (__ [2 2 4 6]) '())

(= (__ [1 1 1 3]) '(1 1 1 3))

(filter odd? #{1 2 3 4 5})
;이것도 풀이가 거의 동일

(async/go-loop []
               (println "s"))

(ef/at "#button1" (ev/listen :click
                             #()


;; 4Clojure Question 23
;;
;; Write a function which reverses a sequence.
;;
;; Restrictions (please don't use these function(s)): reverse, rseq
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (reduce #(cons %2 %) [] [1 2 3 4 5]) [5 4 3 2 1])

(= (__ (sorted-set 5 7 2 7)) '(7 5 2))

(= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])

;이것이 더 좋겠다.
(reduce conj () [1 2 3 4 5])

;Max:  Returns a new coll consisting of to-coll with all of the items of from-coll conjoined. 정의에 conj 가 포함되어 있으므로 이게 가장 좋다.
(into () [1 2 3 4 5])

;; 4Clojure Question 27
;;
;; Write a function which returns true if the given sequence is a palindrome.<br/><br>
;;
;; Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)
;;
;; Use M-x 4clojure-check-answers when you're done!

(false? (#(= (seq %) (reverse %)) '(1 2 3 4 5)))

(true? (__ "racecar"))

(true? (__ [:foo :bar :foo]))

(true? (__ '(1 1 3 3 1 1)))

(false? (__ '(:a :b :c)))

(reverse "racecar")

;; 4Clojure Question 26
;;
;; Write a function which returns the first X fibonacci numbers.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [n]
      (map last (take n (iterate #(vector (last %) (+ (first %) (last %))) [0 1])))) 3) '(1 1 2))

(= (__ 6) '(1 1 2 3 5 8))

(= (__ 8) '(1 1 2 3 5 8 13 21))

( #(vector (second %) (+ (first %) (second %)))[0 1] )

(fn [n]
  (map last (take n (iterate #(vector (last %) (+ (first %) (last %))) [0 1]))))

;Max
(fn [n]
  (take n
        (map first
             (iterate (fn [[f s]] [s (+ f s)])
                      [1 1]))))

;Max 를 보니 인수분해가 더 좋아보임. 그렇다면 나도 이렇게.
(= (
     (fn [n]
       (map last (take n (iterate (fn [[f s]] [s (+ f s)]) [0 1])))) 3)
   '(1 1 2))

;다시 Max를 보니 map, take 순서보다는 take map 순서이 약간 더 직관적. 이게 최종!
(= (
     (fn [n]
       (take n (map last (iterate (fn [[f s]] [s (+ f s)]) [0 1])))) 3)
   '(1 1 2))

;4clojure에는 이런 풀이도 있다.Programming Clojure 책에서 지연 시퀀스를 이용하는 방법과 동일. 기억해둘만하다. 책 발췌한다. "map 과 iterate가 지연 시퀀스를 반환하기 때문에 map과 iterate로 짜여진 fibo역시 지연 시퀀스를 반환하게 된다. 또한 fibo는 지금까지 살펴본 피보나치 수열의 구현가눙데 가장 짧고도 간단하다". 내생각: 이 문제에 대해서는 iterate 과 lazy-seq 버전의 크기는 거의 같다. 그런데 나에겐 iterate가 약간 더 쉽다. 더 구체적이라 그런 것 같다.
#(take % ((fn fib [x y] (lazy-seq (cons x (fib y (+ x y))))) 1 1))

;medium의 p60 푼 다음 다시 생각해 보니 lazy-seq 버전이 원래 fib의 간결한 정의를 그대로 구현한 것이다. 생각해 낼 수만 있으면 lazy-seq 버전이 더 simple.


;; 4Clojure Question 38
;;
;; Write a function which takes a variable number of parameters and returns the maximum value.
;;
;; Restrictions (please don't use these function(s)): max, max-key
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1 8 3 4) 8)

(= (__ 30 20) 30)

(= (__ 45 67 11) 67)

;새로운 답
(= (
     (fn [& x] (reduce #(if (> %1 %2) %1 %2) x))
     1 8 3 4) 8)

;이전 답. 간단한 대신 계산이 더 많이 수행된다.
(fn [& x] (last (sort x)))

;Max. 여기서 배울 점은 list로 바꾸기 위해서 %& 를 하면 된다는 것.
#(last (sort %&))

;그렇다면 이게 효율도 좋고 코드도 깔금. 최종
;chouser's solution:
#(reduce (fn [a b] (if (> a b) a b)) %&)

;; 4Clojure Question 29
;;
;; Write a function which takes a string and returns a new string containing only the capital letters.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (
     #(apply str (re-seq #"[A-Z]" %))
       "HeLlO, WoRlD!") "HLOWRD")

(empty? (__ "nothing"))

(= (__ "$#A(*&987Zf") "AZ")

;여러개를 한꺼번에 찾아 리스트로 만드는데 re-seq. 만약 하나만 찾는다면 re-find.
(apply str (re-seq #"[A-Z]" "HeLlO, WoRlD!"))

;; 4Clojure Question 32
;;
;; Write a function which duplicates each element of a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(interleave % %) [1 2 3]) '(1 1 2 2 3 3))

(= (__ [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))

(= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

(= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;내가 쓴 답.
( #(interleave % % ) [1 2 3])

(take 5 (repeat "5"))

(+ 1 2)

;Max 이게 더 나은 것은 아니다.
(mapcat #(list % %) [1 2 3])

;netpyoung: partial 사용의 좋은 예인듯. 여기에 다시 #(...)를 사용할 수는 없으므로.
#(mapcat (partial repeat 2) %)




;; 4Clojure Question 48
;;
;; The some function takes a predicate function and a collection.  It returns the first logical true value of (predicate x) where x is an item in the collection.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= __ (some #{2 7 6} [5 6 7 8]))

(= __ (some #(when (even? %) %) [5 6 7 8]))

(some #{2 7 6} [5 6 7 8])

(#{2 7 6} 5) ;=>nil
(#{2 7 6} 6) ;=>6

;some은 첫번째 nil이 아닌 return 값이다.

;; 4Clojure Question 34
;;
;; Write a function which creates a list of all integers in a given range.
;;
;; Restrictions (please don't use these function(s)): range
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(take (- %2 %1) (iterate inc %1)) 1 4) '(1 2 3))

(= (#(take (- %2 %1) (iterate inc %1)) -2 2) '(-2 -1 0 1))

(= (__ 5 8) '(5 6 7))

#(take (- %2 %1) (iterate inc %1))
;우와 Max와 답이 같다.

;; 4Clojure Question 28
;;
;; Write a function which flattens a sequence.
;;
;; Restrictions (please don't use these function(s)): flatten
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn flat
      [[h & t :as x]]
      (when x
        (if (coll? h)
          (concat (flat h) (flat t))
          (cons h (flat t))
          )))
     '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(= ((fn flat
      [[h & t :as x]]
      (when x
        (if (coll? h)
          (concat (flat h) (flat t))
          (cons h (flat t))
          ))) ["a" ["b"] "c"]) '("a" "b" "c"))

(= ((fn flat
      [[h & t :as x]]
      (when x
        (if (coll? h)
          (concat (flat h) (flat t))
          (cons h (flat t))
          ))) '((((:a))))) '(:a))

(defn flat
  [[h & t :as x]]
  (print ["flat->" h t x])
  (when x
    (if (coll? h)
      (concat (flat h) (flat t))
      (cons h (flat t))
      )))

(flat nil)

(= [1] (flat [1]))
(= [1 2] (flat [1 2]))
(= [1 2 3] (flat [[1] 2 3]))
(= [1 2 3] (flat [[1] [2 3]]))
(= [1 2 3] (flat [[[1]] [2 3]]))

(fn flat
  [[h & t :as x]]
  (when x
    (if (coll? h)
      (concat (flat h) (flat t))
      (cons h (flat t))
      )))

;이전 답
#(letfn
  [
   (flat [x y]
         (cond
           (sequential? y)
           (reduce flat x y)
           :else
           (conj x y)
           )
         )
   ]
  (flat [] %)
  )

;이 문제에서 배운점
; 1. 잘 안풀리면 test case를 만들어서 풀자.
; 2. :as의 용법

;Max와 비슷. 단지 Max는 h를 사용. x가 nil이면 h도 nil인점 때문에 이렇게 해도 된다. 그러나 비슷
(fn f [[h & t]]
  (if h
    (if (coll? h)
      (concat (f h) (f t))
      (cons h (f t)))))

;그러나 좀더 좋은 답은 daowen. flat을 부분에 대해서 다시 적용한다는 점이 명료하다!
(fn flat [x]
  (if (coll? x)
    (mapcat flat x)
    [x]))


;; 4Clojure Question 39
;;
;; Write a function which takes two sequences and returns the first item from each, then the second item from each, then the third, etc.
;;
;; Restrictions (please don't use these function(s)): interleave
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (mapcat list [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))

(= (__ [1 2] [3 4 5 6]) '(1 3 2 4))

(= (__ [1 2 3 4] [5]) [1 5])

(= (__ [30 20] [25 15]) [30 25 20 15])

;이건 쉽다. 그런데 모든 사람들이 답이 같지는 않네.

;; 4Clojure Question 42
;;
;; Write a function which calculates factorials.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1) 1)

(= (#(reduce * (take % (iterate inc 1))) 3) 6)

(= (#(reduce * (range 1 (inc %))) 5) 120)

(= (__ 8) 40320)

#(reduce * (take % (iterate inc 1)))

;이전답
#(reduce *(map inc (range %)))

;이전답을 보니 이게 더 좋을듯. 이걸 4clojure에 넣어보자.
#(reduce * (range 1 (inc %)))

;norman. 이 답도 좋다.
#(apply * (range 1 (inc %)))

;; 4Clojure Question 30
;;
;; Write a function which removes consecutive duplicates from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (apply str (__ "Leeeeeerrroyyy")) "Leroy")

(= (reduce #(if (= (last %1) %2) %1 (conj %1 %2)) [] [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))

(= (__ [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

(reduce #(if (= (last %1) %2) %1 (conj %1 %2)) [] [1 1 2 3 3 2 2 3])

;이전답
reduce (fn [x y] (if (= (last x) y) x(conj x y)))[]

;지금답. 이전답과 별 차이는 없다.
reduce #(if (= (last %1) %2) %1 (conj %1 %2)) []

;chouser 이게 좋다. 이 문제는 이게 갑. 이런게 clojure's way 인듯.
#(map first (partition-by identity %))
(partition-by identity [1 1 2 3 3 2 2 3])


;; 4Clojure Question 47
;;
;; The contains? function checks if a KEY is present in a given collection.  This often leads beginner clojurians to use it incorrectly with numerically indexed collections like vectors and lists.
;;
;; Use M-x 4clojure-check-answers when you're done!

(contains? #{4 5 6} 4)

(contains? [1 1 1 1 1] __)

(contains? {4 :a 2 :b} __)

(not (contains? '(1 2 4) __))

(contains? [1 1 1 2 2] 5)

;답이 4구나
vector인 경우는 값이 아니라 index를 판단하는 구나.

;; 4Clojure Question 33
;;
;; Write a function which replicates each element of a sequence a variable number of times.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [coll n] (mapcat #(repeat n %) coll)) [1 2 3] 2) '(1 1 2 2 3 3))

(= (__ [:a :b] 4) '(:a :a :a :a :b :b :b :b))

(= (__ [4 5 6] 1) '(4 5 6))

(= (__ [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))

(= (__ [44 33] 2) [44 44 33 33])

((fn [coll n] (mapcat #(repeat n %) coll)) [1 2 3] 2)

;max 이건 나랑 동일
(fn [x n] (mapcat #(repeat n %) x))

;norman 이게 좀더 짧아서 좋다. 함수가 겹친 경우 #...과 fn대신, #...과 partial을 사용하는 것도 알아두자.
#(mapcat (partial repeat %2) %1)

(#(mapcat (partial repeat %2) %1) [1 2 3] 2)


;; 4Clojure Question 45
;;
;; The iterate function can be used to produce an infinite lazy sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [1 4 7 10 13] (take 5 (iterate #(+ 3 %) 1)))

;등차수열은 iterate로 만들 수 있구나.

;; 4Clojure Question 40
;;
;; Write a function which separates the items of a sequence by an arbitrary value.
;;
;; Restrictions (please don't use these function(s)): interpose
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(rest (interleave (repeat %1) %2)) 0 [1 2 3]) [1 0 2 0 3])

(= (apply str (__ ", " ["one" "two" "three"])) "one, two, three")

(= (__ :z [:a :b :c :d]) [:a :z :b :z :c :z :d])

;지금
(#(rest (interleave (repeat %1) %2)) 0 [1 2 3])

;이전
#(drop-last (interleave %2 (repeat %1)))

;max. butlast는 마지막 하나뺀 것이고 drop-last는 마지막 n개 빼는 것이다. 이 경우는 butlast가 더 낫다.
#(butlast (interleave %2 (repeat %1)))


;; 4Clojure Question 31
;;
;; Write a function which packs consecutive duplicates into sub-lists.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (partition-by identity [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))

(= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))

(= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

;이건 뭐 이 답이 최선

;; 4Clojure Question 41
;;
;; Write a function which drops every Nth item from a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn [c x]
      (keep-indexed
        #(when (not= 0 (rem (inc %1) x))
          %2) c))
      [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])

(= (__ [:a :b :c :d :e :f] 2) [:a :c :e])

(= (__ [1 2 3 4 5 6] 4) [1 2 3 5 6])

(keep-indexed #(if (odd? %1) %2) [:a :b :c :d :e])

;keep은 some처럼 filter보다 더 general하게 사용할 수 있다. 여기에 index정보를 추가한 것인 keep-index이다. 좋다.
(fn [c x]
  (keep-indexed
    #(when (not= 0 (rem (inc %1) x))
      %2) c))

(rem 9 3)

;Max 오 이게 더 Clojure's way이다.
;partition-all 은 partition인데 마지막도 남기는 것.
;partition-all n step coll: coll에서 step만큼 뛰면서 n개씩 가져오기.
;이전 p30에서 partition-by 가 Clojure's way였는데 partition이 Clojure를 잘 사용하는 것의 key 이군.
#(apply concat (partition-all (- %2 1) %2 %))

(#(apply concat (partition-all (dec %2) %2 %)) [1 2 3 4 5 6 7 8] 3)


;; 4Clojure Question 52
;;
;; Let bindings and function parameter lists support destructuring.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [2 4] (let [[a b c d e f g] (range)] [c e]))

(take 5 (range))

;vector/list destructuring은 [] 를 기억해두자.

;; 4Clojure Question 49
;;
;; Write a function which will split a sequence into two parts.
;;
;; Restrictions (please don't use these function(s)): split-at
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(vector (take %1 %2) (drop %1 %2)) 3 [1 2 3 4 5 6]) [[1 2 3] [4 5 6]])

(= (__ 1 [:a :b :c :d]) [[:a] [:b :c :d]])

(= (__ 2 [[1 2] [3 4] [5 6]]) [[[1 2] [3 4]] [[5 6]]])

(take 3 [1 2 3 4 5 6])
(take-last 2 [1 2 3 4 5 6])

;이건 틀린답 take-last는 뒤에서 n개 빼오는 거구나
#(vector (take %1 %2) (take-last %1 %2))

;이게 맞는 답
#(vector (take %1 %2) (drop %1 %2))

;norman. 이게 좀더 깔끔해 보이긴 함
(fn [n x] [(take n x) (drop n x)])

;max. 좋은 거 배웠다. juxt 는 juxtaposition(병렬) 형태로 함수 결과값을 받으려고 할 때 사용: ((juxt a b c) x) => [(a x) (b x) (c x)]
(juxt take drop)

;; 4Clojure Question 51
;;
;; Here is an example of some more sophisticated destructuring.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] __] [a b c d]))

;답
[1 2 3 4 5]

; :as를 다시한번 기억하자.

;; 4Clojure Question 83
;;
;; Write a function which takes a variable number of booleans.  Your function should return true if some of the parameters are true, but not all of the parameters are true.  Otherwise your function should return false.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= false ((fn [& c] (and (apply not= c) (some true? c))) false false))

(= true (__ true false))

(= false (__ true))

(= true (__ false true false))

(= false (__ true true true))

(= true (__ true true true false))

((fn [& c] (and (apply not= c) (some true? c))) true false)

(apply not= [true false])

;이전답. 이건 테스트만 통과할 뿐 (not= 1 1) => true 이므로 문제의 설명에는 맞지 않다.
not=

;이것도 가능. %& 기억해 두자.
#(and (apply not= %&) (some true? %&))

(#(and (apply not= %&) (some true? %&)) true false)

;; 4Clojure Question 61
;;
;; Write a function which takes a vector of keys and a vector of values and constructs a map from them.
;;
;; Restrictions (please don't use these function(s)): zipmap
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(into {} (map vector %1 %2)) [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})

(= (__ [1 2 3 4] ["one" "two" "three"]) {1 "one", 2 "two", 3 "three"})

(= (__ [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo", :bar "bar"})

;zipmap이 이런 의미였군. 다시한번 기억.

(into {} '([:a 1] [:b 2]))

(into {} ['(:a 1) '(:b 2)]) ;=> 이건 에러. 테스트해보니 {}로 변경은 vector만 가능

(into [] ['(:a 1) '(:b 2)]) ;=> [(:a 1) (:b 2)]

#(into {} (map vector %1 %2))

;; 4Clojure Question 66
;;
;; Given two integers, write a function which
;;
;; returns the greatest common divisor.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (
     (fn gcd [a b]
       (let [x (max a b)
             y (min a b)]
         (if (= 0 y)
           x
           (gcd b (rem a b))))) 2 4) 2)

(= (__ 10 5) 5)

(= (__ 5 7) 1)

(= (__ 1023 858) 33)

(fn gcd [a b]
  (let [x (max a b)
        y (min a b)]
    (if (= 0 y)
      x
      (gcd b (rem a b)))))

;max 이게 제일 좋다. max, min이 rem의 특성에 의해 없어도 된다.
(fn g [a b] (if (= b 0) a (g b (rem a b))))
(rem 5 7) ;=> 5

(mod 5 7) ;=> 5 이건 정의를 보니까 rem에 minus infinity에 대한 처리를 추가해 준것이므로 가능하면 rem을 사용하는 것이 낫겠다.

;; 4Clojure Question 166
;;
;; For any orderable data type it's possible to derive all of the basic comparison operations (&lt;, &le;, =, &ne;, &ge;, and &gt;) from a single operation (any operator but = or &ne; will work). Write a function that takes three arguments, a <var>less than</var> operator for the data and two items to compare. The function should return a keyword describing the relationship between the two items. The keywords for the relationship between <var>x</var> and <var>y</var> are as follows:
;;
;; 
;;
;; <ul>
;;
;; <li><var>x</var> = <var>y</var> &rarr; :eq</li>
;;
;; <li><var>x</var> &gt; <var>y</var> &rarr; :gt</li>
;;
;; <li><var>x</var> &lt; <var>y</var> &rarr; :lt</li>
;;
;; </ul>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= :gt ((fn [op a b] (if (op a b) :lt (if (op b a) :gt :eq))) < 5 1))

(= :eq (__ (fn [x y] (< (count x) (count y))) "pear" "plum"))

(= :lt (__ (fn [x y] (< (mod x 5) (mod y 5))) 21 3))

(= :gt (__ > 0 2))

;지금
(fn [op a b] (if (op a b) :lt (if (op b a) :gt :eq)))

;이전
(fn [f x y]
  (let [v1 (f x y) v2 (f y x)]
    (cond
      (and (= true v1) (= false v2)) :lt
      (and (= false v1) (= true v2)) :gt
      :else :eq)))

;이전보다 깔끔해졌네.

;norman. 이게 굿.
;cond가 잘 모르겠다는 생각을 버리자. 조건문과 실행식의 나열일뿐. 만약 아무것도 만족하는 조건이 없으면 nil을 반환. :else는 특별한 조건문.
(fn [op x y]
  (cond (op x y) :lt (op y x) :gt :else :eq))

;; 4Clojure Question 81
;;
;; Write a function which returns the intersection of two sets.  The intersection is the sub-set of items that each set has in common.
;;
;; Restrictions (please don't use these function(s)): intersection
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(set (filter (partial get %1) %2)) #{0 1 2 3} #{2 3 4 5}) #{2 3})

(= (#(set (filter (partial get %1) %2)) #{0 1 2} #{3 4 5}) #{})

(= (__ #{:a :b :c :d} #{:c :e :a :f :d}) #{:a :c :d})

(#(set (filter (partial get %1) %2)) #{0 1 2 3} #{2 3 4 5})

;이전
(fn [s1 s2] (set (filter #(get s2 %) s1)))

;max. 그렇구나. 집합 자체도 함수라는 것을 잊고 있었다.
#(set (filter % %2))

;; 4Clojure Question 62
;;
;; Given a side-effect free function f and an initial value x write a function which returns an infinite lazy sequence of x, (f x), (f (f x)), (f (f (f x))), etc.
;;
;; Restrictions (please don't use these function(s)): iterate
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (take 5 ((fn k [f x]
              (cons x (lazy-seq (k f (f x))))) #(* 2 %) 1)) [1 2 4 8 16])

(= (take 100 (__ inc 0)) (take 100 (range)))

(= (take 9 (__ #(inc (mod % 3)) 1)) (take 9 (cycle [1 2 3])))


(take 3 (
          (fn k [f x]
            (cons x (lazy-seq (k f (f x)))))
            inc 0))

;lazy-seq는 잘 적응이 되지 않는다. 어떻게 하면 될까? 이 경우는 x가 들어가는 자리에 (f x) 가 들어가도록 함수 호출하는 방식이다.

;그렇다면 피보나치 수열은?
(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))
(take 7 (fib 1 1)) ;=> (1 1 2 3 5 8 13)
(defn fib1 [a b] (cons b (lazy-seq (fib1 b (+ b a)))))
(take 7 (fib1 1 1)) ;=> (1 2 3 5 8 13 21)
(defn fib2 [a b] (cons (+ a b) (lazy-seq (fib2 b (+ b a)))))
(take 7 (fib2 1 1)) ;=> (2 3 5 8 13 21 34)
;공통점은 재귀적 식이 두번째 인자로 들어간다. 그리고 초기값은 a, b, (+ a b) 무엇이든 자유롭게 만들 수 있다.

(defn fib3 [a b] (cons a (lazy-seq (fib3 a (+ b a)))))
(take 7 (fib3 1 1)) ;=> (1 1 1 1 1 1 1)
;두번째 호출에서 1 2 가 들어가지만 더 이상 재귀가 되지 않는다. 무얼 의미하는 것일까?

;; 4Clojure Question 107
;;
;; <p>Lexical scope and first-class functions are two of the most basic building blocks of a functional language like Clojure. When you combine the two together, you get something very powerful called <strong>lexical closures</strong>. With these, you can exercise a great deal of control over the lifetime of your local bindings, saving their values for use later, long after the code you're running now has finished.</p>
;;
;; 
;;
;; <p>It can be hard to follow in the abstract, so let's build a simple closure. Given a positive integer <i>n</i>, return a function <code>(f x)</code> which computes <i>x<sup>n</sup></i>. Observe that the effect of this is to preserve the value of <i>n</i> for use outside the scope in which it is defined.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 256 (((fn [n] (fn [x] (apply * (repeat n x)))) 2) 16),
       (((fn [n] (fn [x] (apply * (repeat n x)))) 8) 2))

(= [1 8 27 64] (map (__ 3) [1 2 3 4]))

(= [1 2 4 8 16] (map #((__ %) 2) [0 1 2 3 4]))

;지금. x n 이 잘 보이도록 일부러 fn을 두번 썼다.
(fn [n] (fn [x] (apply * (repeat n x))))

;이전
#(fn [x] (reduce * (repeat % x)))

;norman. Math/pow도 있구나. Cheat Sheet에 없는 걸로 보아 라이브러리.
#(fn [n] (Math/pow n %))

(Math/pow 2 8)

;http://en.wikipedia.org/wiki/Closure_(computer_programming)
;closure는 함수이거나 함수의 레퍼런드이다(이 예에서는 (fn [x] (apply * (repeat n x))). 그런데 자신의 범위를 벗어난 변수(이 예에서는 n)도 환경으로 보존하고 있는 함수 또는 함수의 레퍼런스이다.

;; 4Clojure Question 99
;;
;; Write a function which multiplies two numbers and returns the result as a sequence of its digits.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(reverse(
               (fn k [x]
                 (if (= x 0)
                   '()
                   (cons (rem x 10) (lazy-seq (k (int (/ x 10)))))))
               (* %1 %2))) 1 1) [1])

(= (#((fn k [x]
        (if (= x 0)
          []
          (conj (k (quot x 10)) (rem x 10))))
      (* %1 %2)) 99 9) [8 9 1])

(= (__ 999 99) [9 8 9 0 1])

(rem 423 10)
(int (/ 423 10))
(int (/ 3 10))

;지금
#(reverse(
           (fn k [x]
             (if (= x 0)
               '()
               (conj (lazy-seq (k (int (/ x 10)))) (rem x 10))))
           (* %1 %2)))

;이전
#(letfn
  [(_pd [x]
        (if (= x 0)
          []
          (conj (_pd (quot x 10)) (rem x 10))))]
  (_pd (* %1 %2)))


;이전과 비교해보니 이 경우는 무한으로 나가는게 아니므로 lazy sequence가 필요없다.
;recursive를 이용한다면 이게 최선.
;그리고 몫은 quot라는 것을 기억해두자.
#((fn k [x]
    (if (= x 0)
      []
      (conj (k (quot x 10)) (rem x 10))))
  (* %1 %2))

;psk810. 그러나 str로 변환하는 것을 떠올릴 수 있다면 이렇게 간단.
(fn [x y] (map #(- (int %) (int \0)) (str (* x y))))

;; 4Clojure Question 90
;;
;; Write a function which calculates the <a href="http://en.wikipedia.org/wiki/Cartesian_product"> Cartesian product</a> of two sets.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(set (for [x %1
                y %2]
            [x y])) #{"ace" "king" "queen"} #{"&#9824;" "&#9829;" "&#9830;" "&#9827;"})
   #{["ace"   "&#9824;"] ["ace"   "&#9829;"] ["ace"   "&#9830;"] ["ace"   "&#9827;"]
     ["king"  "&#9824;"] ["king"  "&#9829;"] ["king"  "&#9830;"] ["king"  "&#9827;"]
     ["queen" "&#9824;"] ["queen" "&#9829;"] ["queen" "&#9830;"] ["queen" "&#9827;"]})

(= (__ #{1 2 3} #{4 5})
   #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]})

(= 300 (count (__ (into #{} (range 10))
                  (into #{} (range 30)))))

#(set (for [x %1
            y %2]
        [x y]))

;이건 뭐 모두 비슷.

;; 4Clojure Question 63
;;
;; Given a function f and a sequence s, write a function which returns a map.  The keys should be the values of f applied to each item in s.  The value at each key should be a vector of corresponding items in the order they appear in s.
;;
;; Restrictions (please don't use these function(s)): group-by
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (
     (fn [f col]
       (reduce
         (fn [prev-col x]
           (let [fx (f x)]
             (assoc-in prev-col [fx]
                       (if (contains? prev-col fx)
                         (concat (get prev-col fx) [x])
                         [x]))))
         {} col))
     #(> % 5) [1 3 6 8]) {false [1 3], true [6 8]})

(= (__ #(apply / %) [[1 2] [2 4] [4 6] [3 6]])
   {1/2 [[1 2] [2 4] [3 6]], 2/3 [[4 6]]})

(= (__ count [[1] [1 2] [3] [1 2 3] [2 3]])
   {1 [[1] [3]], 2 [[1 2] [2 3]], 3 [[1 2 3]]})

(reduce + {} [3 4 5])

(get {3 5} 3)
(get {} 3)

(let [v (get {} 3)]
  (assoc-in {} [3] (if v
                     (concat v [1])
                     [1])))

;지금
(fn [f col]
  (reduce
    (fn [prev-col x]
      (let [fx (f x)]
        (assoc-in prev-col [fx]
                  (if (contains? prev-col fx)
                    (concat (get prev-col fx) [x])
                    [x]))))
            {} col))

(assoc-in {false 2 true 3} [false] 3)
(get {1 2} 1)
(contains? {false 2} false)

;이전
(fn [f x](apply merge-with concat (map #(hash-map %1 [%2]) (map f x) x)))

;Max. 정말 훌륭.
(fn [f c]
  (reduce #(assoc %
                  (f %2)
                  (conj (get % (f %2) []) %2))
          {}
          c))

;지금부터 Max쪽으로 가보자.
;지금에서 assoc으로 대치한다면
(fn [f col]
  (reduce
    (fn [prev-col x]
      (let [fx (f x)]
        (assoc prev-col fx
                  (if (contains? prev-col fx)
                    (concat (get prev-col fx) [x])
                    [x]))))
    {} col))

;여기서 col대신 c를 쓴 후, #로 대체
(fn [f c]
  (reduce
    #(let [fx (f %2)]
        (assoc %1 fx
               (if (contains? %1 fx)
                 (concat (get %1 fx) [%2])
                 [%2])))
    {} c))

;get에 not-found 파라미터가 있는 것은 꼭 기억해둘만하다.
(fn [f c]
  (reduce
    #(let [fx (f %2)]
      (assoc %1 fx
             (concat (get %1 fx []) [%2])
             ))
    {} c))

;이것도 테스트는 통과는 하지만 conj로 하면 vector를 보존. 이제서야 Max에 다다랐다.
(fn [f c]
  (reduce
    #(let [fx (f %2)]
      (assoc %1 fx
             (conj (get %1 fx []) %2)
             ))
    {} c))

;daowen. 또다른길
(
(fn [f vs]
  (reduce #(merge-with concat % {(f %2) [%2]}) {} vs))
  #(> % 5) [1 3 6 8])

(merge-with concat
            {1 [2]}
            {1 [3]}) ;=> {1 (2 3)}

(merge-with into
            {1 [2]}
            {1 [3]}) ;=> {1 [2 3]}

(concat [2] [3]) ;=> (2 3)
(into [2] [3]) ;=> [2 3]

;기억해두자. merge-with가 있다는 사실과 into를 쓰면 기존 자료구조를 보존하면서 합칠 수 있다는 것을.

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



;; 4Clojure Question 88
;;
;; Write a function which returns the symmetric difference of two sets.  The symmetric difference is the set of items belonging to one but not both of the two sets.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1)) #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 7})

(= (__ #{:a :b :c} #{}) #{:a :b :c})

(= (__ #{} #{4 5 6}) #{4 5 6})

(= (__ #{[1 2] [2 3]} #{[2 3] [3 4]}) #{[1 2] [3 4]})

(#{1 3 5 7} 2)

;지금
#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1))

;이전
(fn [a b]
  (letfn [(_diff [x y]
                 (filter #(= nil (x %)) y))]
    (set (concat (_diff a b) (_diff b a)))))

;Max
#(set `(~@(% %2 %3) ~@(% %3 %2))) remove

(remove #{1 2 3} #{2 3 4}) ;=> (4)
;~@는 괄호를 없애는 역활. 괄호를 없애서 합친후 다시 리스트를 만들어 set에 넣었구나.
;한개 더 배울 점은 함수도 인자로 사용하는 것.

;chouser. 이게 Max보다 좋아 보인다. into에서 앞쪽이 set이면 뒤의 것도 자동으로 set으로 합쳐진다.
#(into (set (remove %2 %)) (remove % %2))

;; 4Clojure Question 143
;;
;; Create a function that computes the <a href="http://en.wikipedia.org/wiki/Dot_product#Definition">dot product</a> of two sequences. You may assume that the vectors will have the same length.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 0 (#(apply + (map * %1 %2)) [0 1 0] [1 0 0]))

(= 3 (__ [1 1 1] [1 1 1]))

(= 32 (__ [1 2 3] [4 5 6]))

(= 256 (__ [2 5 6] [100 10 1]))

#(apply + (map * %1 %2))
;이건 이전 답과 동일.

;그리고 Max 와 동일

;이런답도 있군.
;chouser.
#(reduce + (map * % %2))

;; 4Clojure Question 126
;;
;; Enter a value which satisfies the following:
;;
;; Use M-x 4clojure-check-answers when you're done!

(let [x java.lang.Class]
  (and (= (class x) x) x))

(class java.lang.Class)
(type 2)

;type과 class 모두 clojure.core에 있는 함수.
;type의 정의: Returns the :type metadata of x, or its Class if none
;내 느낌은 type이 좀더 많이 사용되는 것 같다. 정의상 더 빠를 것 같아.



;; 4Clojure Question 135
;;
;; Your friend Joe is always whining about Lisps using the prefix notation for math. Show him how you could easily write a function that does math using the infix notation. Is your favorite language that flexible, Joe?
;;
;; 
;;
;; Write a function that accepts a variable length mathematical expression consisting of numbers and the operations +, -, *, and /. Assume a simple calculator that does not do precedence and instead just calculates left to right.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 7  ((fn [x & xs]
         (reduce #((first %2) % (second %2)) x (partition 2 xs))) 2 + 5))

(= 42 ((fn [x & xs]
         (reduce #((first %2) % (second %2)) x (partition 2 xs))) 38 + 48 - 2 / 2))

(= 8  (__ 10 / 2 - 1 * 2))

(= 72 (__ 20 / 2 + 2 + 4 + 8 - 6 - 10 * 9))

;지금. reduce의 초기값을 이용하는 지금이 더 간결
(fn [x & xs]
  (reduce #((first %2) % (second %2)) x (partition 2 xs)))

;이전
(fn [x & y] (reduce #((first %2) %1 (last %2)) (conj (partition 2 y) x)))

;hypirion. destructuring 기억해둘만 하다.
(fn [a & r]
  (reduce
    (fn [a [op b]] (op a b))
    a (partition 2 r)))


;; 4Clojure Question 157
;;
;; Transform a sequence into a sequence of pairs containing the original elements along with their index.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (map-indexed (fn [i x] [x i]) [:a :b :c]) [[:a 0] [:b 1] [:c 2]])

(= (__ [0 1 3]) '((0 0) (1 1) (3 2)))

(= (__ [[:foo] {:bar :baz}]) [[[:foo] 0] [{:bar :baz} 1]])


;지금
map-indexed (fn [i x] [x i])

;이전
(fn [s]
  (partition 2 (interleave s (range))))

;max. range를 사용할 수도 있구나.
#(map list % (range))

(take 5 (range))

;; 4Clojure Question 97
;;
;; <a href="http://en.wikipedia.org/wiki/Pascal%27s_triangle">Pascal's triangle</a> is a triangle of numbers computed using the following rules:<br/></br>- The first row is 1.</br>- Each successive row is computed by adding together adjacent numbers in the row above, and adding a 1 to the beginning and end of the row.<br/><br/>Write a function which returns the nth row of Pascal's Triangle.
;;
;; 
;;
;; 
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ 1) [1])

(= (map  (fn [i] (nth (iterate #(vec (map + (cons 0 %) (conj % 0))) [1]) (dec i))) (range 1 6))
   [     [1]
        [1 1]
       [1 2 1]
      [1 3 3 1]
     [1 4 6 4 1]])

(= (__ 11)
   [1 10 45 120 210 252 210 120 45 10 1])

(#(vec (map + (cons 0 %) (conj % 0))) [1 2 1])

;지금. 이전과 비교하면 iterate 구현하지 않고 사용한 것이 간결.
(fn [i] (nth (iterate #(vec (map + (cons 0 %) (conj % 0))) [1]) (dec i)))


;이전
(fn [n] (nth ((fn _iterate [f x] (cons x (lazy-seq (_iterate f (f x)))))
               #(map + (concat [0] %) (concat % [0])) [1]) (dec n)))

;조금더 이쁘려면 cons, conj 를 concat으로 바꿈.
(fn [i] (nth (iterate #(vec (map + (concat [0] %) (concat % [0]))) [1]) (dec i)))

;chouser
(fn p [x]
  (if (= x 1)
    [1]
    `[1 ~@(map + (p (- x 1)) (next (p (- x 1)))) 1]))

;rest 와 next의 차이
;rest is generally preferred over next. See the Clojure.org documentation on writing lazy functions.
;Also, the topic is covered on StackOverflow.com: rest vs. next.

((fn [x] `[1 ~@x 1]) [3 4 5]) ;=> [1 3 4 5 1] chouser에서 배울 것은 이거다. 이것 이외에 해 자체는 모르겠다. 넘기자.
(#(`[1 ~@% 1]) [3 4 5]) ;=> 이건 왠지 모르겠지만 에러가 난다.

;; 4Clojure Question 118
;;
;; <p>Map is one of the core elements of a functional programming language. Given a function <code>f</code> and an input sequence <code>s</code>, return a lazy sequence of <code>(f x)</code> for each element <code>x</code> in <code>s</code>.
;;
;; Restrictions (please don't use these function(s)): map, map-indexed, mapcat, for
;;
;; Use M-x 4clojure-check-answers when you're done!

(= [3 4 5 6 7]
   ((fn k [f col]
      (if (empty? col)
        col
        (cons (f (first col)) (lazy-seq (k f (rest col)))))) inc [2 3 4 5 6]))

(= (repeat 10 nil)
   (__ (fn [_] nil) (range 10)))

(= [1000000 1000001]
   (->> (__ inc (range))
        (drop (dec 1000000))
        (take 2)))

(
(fn k [f col]
  (if (empty? col)
    col
    (cons (f (first col)) (lazy-seq (k f (rest col))))))
  inc [2 3 4 5 6])

;지금. 이전이랑 거의 똑같네.
(fn k [f col]
  (if (empty? col)
    col
    (cons (f (first col)) (lazy-seq (k f (rest col))))))

;이전
(fn _map [f col]
  (if (empty? col)
    []
    (cons (f (first col)) (lazy-seq (_map f (rest col))))))

;max. 여기서 배울 점 destructuring.
(fn m [f [h & t :as v]]
  (if (empty? v)
    ()
    (lazy-seq (cons (f h) (m f t)))))

;chouser. 여기서 배울 점 if를 lazy-seq 안에서 쓸 수 있다는 점.
(fn l [f [a & m]]
  (lazy-seq
    (cons (f a) (if m (l f m)))))

;; 4Clojure Question 120
;;
;; Write a function which takes a collection of integers as an argument.  Return the count of how many elements are smaller than the sum of their squared component digits.  For example: 10 is larger than 1 squared plus 0 squared; whereas 15 is smaller than 1 squared plus 5 squared.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 8 ((fn [col]
        (letfn [(k [x]
                   (let [r (rem x 10)
                         q (quot x 10)]
                     (+ (* r r) (if (= 0 q)
                                  0
                                  (k q)))))]
          (count (filter #(< % (k %)) col)))) (range 10)))

(= 19 (__ (range 30)))

(= 50 (__ (range 100)))

(= 50 (__ (range 1000)))

(
(fn k [x]
  (let [r (rem x 10)
        q (quot x 10)]
    (+ (* r r) (if (= 0 q)
                 0
                 (k q)))))
  32)

(rem 32 10)
(quot 327 10)
(quot 3 10)

(
(fn [col]
  (letfn [(k [x]
             (let [r (rem x 10)
                   q (quot x 10)]
               (+ (* r r) (if (= 0 q)
                            0
                            (k q)))))]
    (count (filter #(< % (k %)) col))))
  (range 10))

;지금
(fn [col]
  (letfn [(k [x]
             (let [r (rem x 10)
                   q (quot x 10)]
               (+ (* r r) (if (= 0 q)
                            0
                            (k q)))))]
    (count (filter #(< % (k %)) col))))

;이전
(fn [list]
  (count
    (filter
      (fn [x] (<  x
                  (reduce #(+ %1 (* %2 %2)) 0 (map #(- (int %) 48) (str x)))))
      list)))

;max. 이 문제는 (- (int %) 48)을 쓰는게 k를 구현하는 것보다는 약간 더 간단. Math/pow도 기억해두자.
(fn [s]
  (count (filter (fn [n] (< n
                            (apply +
                                   (map #(Math/pow (- (int %) 48) 2)
                                        (str n)))))
                 s)))

;; 4Clojure Question 95
;;
;; Write a predicate which checks whether or not a given sequence represents a <a href="http://en.wikipedia.org/wiki/Binary_tree">binary tree</a>.  Each node in the tree must have a value, a left child, and a right child.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= ((fn k [x]
      (if (coll? x)
        (let [[a b c] x]
          (if (and (= 3 (count x))
                   (k a)
                   (k b)
                   (k c))
            true
            false))
        (not (false? x)))) '(:a (:b nil nil) nil))
   true)

(= ((fn k [x]
      (if (coll? x)
        (let [[a b c] x]
          (if (and (= 3 (count x))
                   (k a)
                   (k b)
                   (k c))
            true
            false))
        (not (false? x)))) '(:a (:b nil nil)))
   false)

(= (__ [1 nil [2 [3 nil nil] [4 nil nil]]])
   true)

(= (__ [1 [2 nil nil] [3 nil nil] [4 nil nil]])
   false)

(= (__ [1 [2 [3 [4 nil nil] nil] nil] nil])
   true)

(= (__ [1 [2 [3 [4 false nil] nil] nil] nil])
   false)

(= (__ '(:a nil ()))
   false)

;이전
(fn _tree
  ([] false)
  ([x]
    (if (coll? x)
      (apply _tree x)
      (not= x false)))
  ([x y] false)
  ([x y z] (and (_tree x) (_tree y) (_tree z)))
  ([x y z & more] false)
  )

;지금. 이전보다는 좀더 깔끔.
(fn k [x]
  (if (coll? x)
    (let [[a b c] x]
      (if (and (= 3 (count x))
               (k a)
               (k b)
               (k c))
        true
        false))
    (not (false? x))))

;max
(fn ? [t]
  (or (and (coll? t)
           (= (count t) 3)
           (every? ? (rest t)))
      (nil? t)))
;나와 차이점
;- leaf node에 대해서 nil?로만 체크
;- every? 사용
;- every? 에 rest만 체크
;- if 대신 or 사용. 이건 생각하기 쉽진 않다.
;결론: 이 문제에 대해서는 max가 깔끔하게 구현했다. every? 기억하자.

;; 4Clojure Question 128
;;
;; <p>A standard American deck of playing cards has four suits - spades, hearts, diamonds, and clubs - and thirteen cards in each suit. Two is the lowest rank, followed by other integers up to ten; then the jack, queen, king, and ace.</p>
;;
;; 
;;
;; <p>It's convenient for humans to represent these cards as suit/rank pairs, such as H5 or DQ: the heart five and diamond queen respectively. But these forms are not convenient for programmers, so to write a card game you need some way to parse an input string into meaningful components. For purposes of determining rank, we will define the cards to be valued from 0 (the two) to 12 (the ace)</p>
;;
;; 
;;
;; <p>Write a function which converts (for example) the string "SJ" into a map of <code>{:suit :spade, :rank 9}</code>. A ten will always be represented with the single character "T", rather than the two characters "10".</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= {:suit :diamond :rank 10} (__ "DQ"))

(= {:suit :heart :rank 3} (__ "H5"))

(= {:suit :club :rank 12} (__ "CA"))

(= (range 13) (map (comp :rank __ str)
                   '[S2 S3 S4 S5 S6 S7
                     S8 S9 ST SJ SQ SK SA]))


(
(fn [x]
  {:suit ({\D :diamond \H :heart \C :club \S :spade} (first x))
   :rank ({\2 0 \3 1 \4 2 \5 3 \6 4 \7 5 \8 6 \9 7 \T 8 \J 9 \Q 10 \K 11 \A 12} (second x))})
"DQ")

;지금
(fn [x]
  {:suit ({\D :diamond \H :heart \C :club \S :spade} (first x))
   :rank ({\2 0 \3 1 \4 2 \5 3 \6 4 \7 5 \8 6 \9 7 \T 8 \J 9 \Q 10 \K 11 \A 12} (second x))})

;이전
#(hash-map
  :suit ({\S :spade \H :heart \D :diamond \C :club} (first %))
  :rank ({\2 0 \3 1 \4 2 \5 3 \6 4 \7 5 \8 6 \9 7 \T 8 \J 9 \Q 10 \K 11 \A 12} (last %)))

;max는 이 와중에서 deconstructing을 잘 썼군.
(fn [[s r]]
  (let [rs {\2 0 \3 1  \4 2
            \5 3 \6 4  \7 5
            \8 6 \9 7  \T 8
            \J 9 \Q 10 \K 11 \A 12}

        ds {\S :spade   \H :heart
            \D :diamond \C :club}]

    {:suit (ds s) :rank (rs r)}))

;; 4Clojure Question 100
;;
;; Write a function which calculates the <a href="http://en.wikipedia.org/wiki/Least_common_multiple">least common multiple</a>.  Your function should accept a variable number of positive integers or ratios. 
;;
;; Use M-x 4clojure-check-answers when you're done!

(== (__ 2 3) 6)

(== (__ 5 3 7) 105)

(== (__ 1/3 2/5) 2)

(== (__ 3/4 1/6) 3/2)

(== (__ 7 5/7 2 3/5) 210)

(defn gcd [a b]
  (let [x (max a b)
        y (min a b)]
    (if (= 0 y)
      x
      (gcd b (rem a b)))))

(gcd 1/3 2/5)
(/ (* 1/3 2/5) (gcd 1/3 2/5))

(gcd (gcd (gcd 7 5/7) 2) 3/5)

(* 7 5/7 2 3/5)

(
(fn [& c]
  (letfn [(gcd [a b]
               (let [x (max a b)
                     y (min a b)]
                 (if (= 0 y)
                   x
                   (gcd b (rem a b)))))]
    (/
      (apply * c)
      (reduce gcd c))))
  3 3 6)

;지금
(fn [& c]
  (letfn [(gcd [a b]
               (let [x (max a b)
                     y (min a b)]
                 (if (= 0 y)
                   x
                   (gcd b (rem a b)))))]
    (/
      (apply * c)
      (reduce gcd c))))

;이전
(fn [& vs]
  (letfn [(gcd [x y] (if (= y 0)
                       x
                       (gcd y (rem x y))))]
    (reduce #(/ (* %1 %2) (gcd %1 %2)) vs)))
;이건 이전이 더 낫다.

;psk810. 이걸 보니 letfn보다 let이 가독성이 더 좋다. 다음에는 let으로 하자.
;p96을 보니 이런 방식은 이전버전의 clojure에서는 재귀 호출이 안되는 문제가 있는 듯 하다.
(fn [& nums]
  (let [
        gcd (fn [x y] (cond
                        (zero? x) y
                        (zero? y) x
                        :else (recur y (mod x y))))
        lcm (fn [x y] (/ (* x y) (gcd x y)))]
    (reduce #(lcm % %2) nums)))


;; 4Clojure Question 173
;;
;; Sequential destructuring allows you to bind symbols to parts of sequential things (vectors, lists, seqs, etc.): <a href="http://clojure.org/special_forms#Special Forms--(let [bindings* ] exprs*)">(let [bindings* ] exprs*)</a>
;;
;; 
;;
;; Complete the bindings so all let-parts evaluate to 3.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 3
  (let [[__] [+ (range 3)]] (apply __))
  (let [[[__] b] [[+ 1] 2]] (__ b))
  (let [[__] [inc 2]] (__)))

x y
;이건 쉽다. 형태 맞추기. 배울점: 함수도 destructuring이 된다는 것.

;; 4Clojure Question 147
;;
;; <p>Write a function that, for any given input vector of numbers, returns an infinite lazy sequence of vectors, where each next one is constructed from the previous following the rules used in <a href="http://en.wikipedia.org/wiki/Pascal's_triangle">Pascal's Triangle</a>. For example, for [3 1 2], the next row is [3 4 3 2].</p>
;;
;; <p>Beware of arithmetic overflow! In clojure (since version 1.3 in 2011), if you use an arithmetic operator like + and the result is too large to fit into a 64-bit integer, an exception is thrown. You can use +' to indicate that you would rather overflow into Clojure's slower, arbitrary-precision bigint.</p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (second (__ [2 3 2])) [2 5 5 2])

(= (take 5 (__ [1])) [[1] [1 1] [1 2 1] [1 3 3 1] [1 4 6 4 1]])

(= (take 2 (__ [3 1 2])) [[3 1 2] [3 4 3 2]])

(= (take 100 (__ [2 4 2])) (rest (take 101 (__ [2 2]))))

;지금. p97에서 가져와서 +'만 바꿨.
iterate #(vec (map +' (concat [0] %) (concat % [0])))

;이전
(fn [col]
  ((fn _iterate [f x] (cons x (lazy-seq (_iterate f (f x)))))
    #(map +' (concat [0] %) (concat % [0])) col))

;max `[0 ~@%] 기억할만 하다.
(fn [s] (iterate  #(map + `[0 ~@%] `[~@% 0]) s))

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


;; 4Clojure Question 146
;;
;; <p>Because Clojure's <code>for</code> macro allows you to "walk" over multiple sequences in a nested fashion, it is excellent for transforming all sorts of sequences. If you don't want a sequence as your final output (say you want a map), you are often still best-off using <code>for</code>, because you can produce a sequence and feed it into a map, for example.</p>
;;
;; 
;;
;; <p>For this problem, your goal is to "flatten" a map of hashmaps. Each key in your output map should be the "path"<sup>1</sup> that you would have to take in the original map to get to a value, so for example <code>{1 {2 3}}</code> should result in <code>{[1 2] 3}</code>. You only need to flatten one level of maps: if one of the values is a map, just leave it alone.</p>
;;
;; 
;;
;; <p><sup>1</sup> That is, <code>(get-in original [k1 k2])</code> should be the same as <code>(get result [k1 k2])</code></p>
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ '{a {p 1, q 2}
         b {m 3, n 4}})
   '{[a p] 1, [a q] 2
     [b m] 3, [b n] 4})

(= (__ '{[1] {a b c d}
         [2] {q r s t u v w x}})
   '{[[1] a] b, [[1] c] d,
     [[2] q] r, [[2] s] t,
     [[2] u] v, [[2] w] x})

(= (__ '{m {1 [a b c] 3 nil}})
   '{[m 1] [a b c], [m 3] nil})

(first
(second
(first '{a {p 1, q 2}
         b {m 3, n 4}})))

(let [[k v] (first '{a {p 1, q 2}
                 b {m 3, n 4}})]
  (into {}
        (map (fn [[a b]]
                  {[k a] b}) v)))

;지금
#(into {}
      (mapcat (fn [[k v]]
                (map (fn [[a b]]
                       {[k a] b}) v)) %))

;이전
(fn [m]
  (into {}
        (mapcat (fn [x y] (map #(vector (vector x (first %)) (last %)) y))
                (keys m) (vals m))))

;max.
#(into {} (for [[i j] % [k l] j] [[i k] l]))
;이렇게 간단하기 때문에 for를 힌트로 줬구나. for는 하나를 풀어헤치고, 풀어헤친 그것을 다시 풀어해칠때 매우 편리하다!

;; 4Clojure Question 153
;;
;; 
;;
;; <p>
;;
;; Given a set of sets, create a function which returns <code>true</code> 
;;
;; if no two of those sets have any elements in common<sup>1</sup> and <code>false</code> otherwise. 
;;
;; Some of the test cases are a bit tricky, so pay a little more attention to them. 
;;
;; </p>
;;
;; 
;;
;; <p>
;;
;; <sup>1</sup>Such sets are usually called <i>pairwise disjoint</i> or <i>mutually disjoint</i>.
;;
;; </p>
;;
;; 
;;
;; Use M-x 4clojure-check-answers when you're done!

(= (__ #{#{\U} #{\s} #{\e \R \E} #{\P \L} #{\.}})
   true)

(= (__ #{#{:a :b :c :d :e}
         #{:a :b :c :d}
         #{:a :b :c}
         #{:a :b}
         #{:a}})
   false)

(= (__ #{#{[1 2 3] [4 5]}
         #{[1 2] [3 4 5]}
         #{[1] [2] 3 4 5}
         #{1 2 [3 4] [5]}})
   true)

(= (__ #{#{'a 'b}
         #{'c 'd 'e}
         #{'f 'g 'h 'i}
         #{''a ''c ''f}})
   true)

(= (__ #{#{'(:x :y :z) '(:x :y) '(:z) '()}
         #{#{:x :y :z} #{:x :y} #{:z} #{}}
         #{'[:x :y :z] [:x :y] [:z] [] {}}})
   false)

(= (__ #{#{(= "true") false}
         #{:yes :no}
         #{(class 1) 0}
         #{(symbol "true") 'false}
         #{(keyword "yes") ::no}
         #{(class '1) (int \0)}})
   false)

(= (__ #{#{distinct?}
         #{#(-> %) #(-> %)}
         #{#(-> %) #(-> %) #(-> %)}
         #{#(-> %) #(-> %) #(-> %)}})
   true)

(= (__ #{#{(#(-> *)) + (quote mapcat) #_ nil}
         #{'+ '* mapcat (comment mapcat)}
         #{(do) set contains? nil?}
         #{, , , #_, , empty?}})
   false)

(
#(let [l (for [a % b a] b)]
  (= (count l) (count (set l))))
  #{#{:a :b :c :d :e}
    #{:a :b :c :d}
    #{:a :b :c}
    #{:a :b}
    #{:a}})

;지금. p146에서 배운 for를 사용.
#(let [l (for [a % b a] b)]
  (= (count l) (count (set l))))
;chouser와 답이 같네ㅎ

;이전
(fn [a]
  (every? empty?
          (for [x a
                y a]
            (if (= x y)
              #{}
              (clojure.set/intersection x y)))))

;max
#(apply distinct? (mapcat seq %))

(mapcat seq #{#{:a :b :c :d :e}
              #{:a :b :c :d}
              #{:a :b :c}
              #{:a :b}
              #{:a}})
;=> (:c :b :a :e :c :b :d :a :c :b :d :a :b :a :a)
;distinct? := "Returns true if no two of the arguments are ="
;max는 distinct?를 많이 사용해 봤기때문에 이런 답을 떠올렸을 것이다.