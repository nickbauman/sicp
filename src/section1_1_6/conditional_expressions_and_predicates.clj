(ns section1-1-6.conditional-expressions-and-predicates
  (:require [clojure.math.numeric-tower :as math]))

(println "Exercise 1.1.1")
(println "Below is a sequence of expressions. What is the result printed by the interpreter in response to each expression?\nAssume that the sequence is to be evaluated in the order in which it is presented.")

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

(doall 
  (map (fn[form] 
         (let [nform (read-string form)]
           (println nform)
           (println (str ">> " (eval nform)))))
       exercise111-col))

(println "Exercise 1.1.2")
(println "Translate the following expression [see text] into prefix form")

(println (double (/ (+ 5 1 (- 2 3 (+ 6 (/ 1 5)))) (* 3 (- 6 2) (- 2 7)))))

(println "Exercise 1.1.3")
(println "Define a procedure that takes three numbers as arguments and returns the sum of the squares of the two larger numbers")

(defn procedure
  [x y z]
  (let [[_ a b] (sort [x y z])]
    (+ (* a a) (* b b))))

(println (procedure 2 5 3))