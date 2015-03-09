java -cp ~/.m2/repository/org/clojure/clojure/1.6.0/clojure-1.6.0.jar clojure.main src/concat_files.clj
git add .
commit_msg="~p$1"
git commit -am $commit_msg
git push