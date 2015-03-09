(ns concat-files)

(require '[clojure.string :as str])

(def log
  (with-open [rdr (clojure.java.io/reader "log.md")]
    (doall (line-seq rdr))))

(defn problems [line]
  (str/split (nth log line) #" "))

(defn concat-str [problems]
  (apply str
         (interpose "\n\n"
                    (map #(slurp (str "src/study_4clojure/p" % ".clj")) problems))))

(spit "src/elementary.clj" (concat-str (problems 3)))
(spit "src/easy.clj" (concat-str (problems 6)))
(spit "src/medium.clj" (concat-str (problems 9)))