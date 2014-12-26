# study-4clojure

## 문제 가져오기
4clojure.el(https://github.com/losingkeys/4clojure.el)과 밑의 스크립트를 이용하면 4clojure 사이트에서 일일이 복사해 가져오는 것보다 편리하다.
- 설치: Emacs에 4clojure.el 설치한 후, 밑의 스크립트에서 'load-path, file-path, log-path 를 각자에 맞게 수정하여 .emacs에 추가
- 사용: Emacs에서 `M-x 4clojure-save`
```
(add-to-list 'load-path "~/.emacs.d/elpa/4clojure-20131014.1507")
(require '4clojure)
(defun 4clojure-save ()
  (interactive)
  (let ((num (read-from-minibuffer "Which 4clojure question? "))
        (file-path "~/project/study-4clojure/src/study_4clojure/")
		(log-path "~/project/study-4clojure/problem_order.md"))
    (4clojure/start-new-problem num)
    (write-file (concat file-path "p" num ".clj"))
    (append-to-file (concat " " num) nil log-path) ;추가한 순서를 기록. 필요없으면 주석 처리하세요.
    ))
```

## 문제 풀기
편리한 에디터로 p*.clj를 편집하여 문제 풀고 저장. 나의 경우 IntelliJ의 Cursive 플러그인 사용함.