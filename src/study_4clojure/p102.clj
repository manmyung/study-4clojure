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