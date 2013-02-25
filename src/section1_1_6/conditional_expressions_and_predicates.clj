(ns section1-1-6.conditional-expressions-and-predicates
  (:require [clojure.math.numeric-tower :as math]))

(println "Exercise 1.1.1")
(println "Below is a sequence of expressions. What is the result printed by the")
(println "interpreter in response to each expression?")
(println "Assume that the sequence is to be evaluated in the order in which it is presented.")

(def exercise111-col
  ["10"
   "(+ 5 3 4)"
   "(- 9 1)"
   "(/ 6 2)"
   "(+ (* 2 4) (- 4 6))"
   "(def a 3)"
   "(def b (+ a 1))"
   "(+ a b (* a b))"
   "(= a b)"
   "(if (and (> b a) (< b (* a b))) b a)"
   "(cond (= a 4) 6 (= b 4) (+ 6 7 a) :else 25)"
   "(+ 2 (if (> b a) b a))"
   "(* (cond (> a b) a (< a b) b :else -1) (+ a 1))"])

; This will show what each expression does in sequence and show what its 
; results are, preceeded by a ">> " like how a REPL does it
(doall 
  (map (fn[form] 
         (let [nform (read-string form)]
           (println nform)
           (println (str ">> " (eval nform)))))
       exercise111-col))

(println "Exercise 1.1.2")
(println "Translate the following expression [see text] into prefix form")

; This is the translated sequence:
(println (double (/ (+ 5 1 (- 2 3 (+ 6 (/ 1 5)))) (* 3 (- 6 2) (- 2 7)))))

(println "Exercise 1.1.3")
(println "Define a procedure that takes three numbers as arguments and returns") 
(println "the sum of the squares of the two larger numbers.")

(defn procedure113
  [x y z]
  (let [[_ a b] (sort [x y z])]
    (+ (* a a) (* b b))))

(println (procedure113 2 5 3))

(println "Exercise 1.1.4")

(println "Observe that our model of evaluation allows for combinations whose")
(println "operators are compound expressions. Use this observation to describe")
(println "the behavior of the following procedure:\n")

(defn a-plus-abs-b [a b]  ((if (> b 0) + -) a b))

(println "The if special form returns a function (in this case the function")
(println "+ or -) and executes it against the parameters a and b. The effect")
(println "is to always result in the addition of the absolute value of b and")
(println "a, as follows:\n")

(println (a-plus-abs-b 6 7))
(println (a-plus-abs-b 6 -7))
 