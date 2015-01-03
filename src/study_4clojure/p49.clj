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