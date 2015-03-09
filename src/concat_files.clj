(ns concat-files)

(require '[clojure.string :as str])

(def log
  (with-open [rdr (clojure.java.io/reader "log.md")]
    (doall (line-seq rdr))))

(def easy-problems
  (map read-string (str/split (nth log 6) #" ")))