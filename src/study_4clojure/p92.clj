;; 4Clojure Question 92
;;
;; Roman numerals are easy to recognize, but not everyone knows all the rules necessary to work with them. Write a function to parse a Roman-numeral string and return the number it represents.
;;
;; <br /><br />
;;
;; You can assume that the input will be well-formed, in upper-case, and follow the <a href="http://en.wikipedia.org/wiki/Roman_numerals#Subtractive_principle">subtractive principle</a>. You don't need to handle any numbers greater than MMMCMXCIX (3999), the largest number representable with ordinary letters.
;;
;; Use M-x 4clojure-check-answers when you're done!

(= 14 (__ "XIV"))

(= 827 (__ "DCCCXXVII"))

(= 3999 (__ "MMMCMXCIX"))

(= 48 (__ "XLVIII"))

;me
(let [m {"M" 1000 "CM" 900 "D" 500 "CD" 400 "C" 100 "XC" 90 "L" 50 "XL" 40 "X" 10 "IX" 9 "V" 5 "IV" 4 "I" 1}]
  (fn k [s]
    (let [v2 (m (apply str (take 2 s)))
          v1 (m (apply str (take 1 s)))]
      (cond
        v2 (+ v2 (k (drop 2 s)))
        v1 (+ v1 (k (drop 1 s)))
        :else 0
        ))))

;max. 로마자 규칙을 깊이 음미하다보면 이렇게 만들 수 있을 듯.
#(reduce
  (fn [m [x y]] ((if y (if (< x y) - +) +) m x))
  0
  (partition-all 2 1 (map {\I 1 \V 5 \X 10 \L 50 \C 100 \D 500 \M 1000} %)))

;amalloy. 이것도 max와 비슷
(fn [s]
  (reduce (fn [sum [a b]]
            ((if (< a b) - +) sum a))
          0
          (partition 2 1 (concat (map {\I 1
                                       \V 5
                                       \X 10
                                       \L 50
                                       \C 100
                                       \D 500
                                       \M 1000} s)
                                 [0]))))