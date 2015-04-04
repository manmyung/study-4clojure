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

;maximental
#(let [r {1000 "M"
          900  "CM"
          500  "D"
          400  "CD"
          100  "C"
          90   "XC"
          50   "L"
          40   "XL"
          10   "X"
          9    "IX"
          5    "V"
          4    "IV"
          1    "I"
          0    ""}]
  ((fn f [m [k & s] a]
     (if s
       (if (>= m k)
         (f (- m k) (cons k s) (str a (r k)))
         (f m s a))
       a))
    % (sort > (keys r)) ""))