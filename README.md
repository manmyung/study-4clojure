# study-4clojure

## Usage

### 문제 가져오기
4clojure 사이트에서 복사해와도 되지만, Emacs의 4clojure.el 사용하면 편리하다.
- M-x 4clojure-open-question 또는 M-x 4clojure-next-question 으로 문제 연다.

### 문제 저장
1. C-x C-w 로 p*_q.clj로 저장.
2. C-x C-w 로 p*_a.clj로 저장.

또는 다음 코드에서 각자에 맞게 path를 수정하여 .emacs에 추가하면 M-x 4clojure-save 로 손쉽게 저장할 수 있다.
```
(defun 4clojure-save ()
  (interactive)
  (let ((num (read-from-minibuffer "Which 4clojure question? "))
        (path "~/project/study-4clojure/src/study_4clojure/"))
    (write-file (concat path "p" num "_q.clj"))
    (write-file (concat path "p" num "_a.clj"))))
```

### 문제 풀기
편리한 에디터로 p*_a.cljs 에 문제 풀고 저장. 나의 경우 Cursive Clojure를 사용함.