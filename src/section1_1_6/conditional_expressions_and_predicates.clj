(ns section1-1-6.conditional-expressions-and-predicates)

(println "Exercise 1.1.1")

(def exercise111-col
  ["10\n"
   "(+ 5 3 4)\n"
   "(- 9 1)\n"
   "(/ 6 2)\n"
   "(+ (* 2 4) (- 4 6))\n"
   "(def a 3)\n"
   "(def b (+ a 1))\n"
   "(+ a b (* a b))\n"
   "(= a b)\n"
   "(if (and (> b a) (< b (* a b))) b a)\n"
   "(cond (= a 4) 6 (= b 4) (+ 6 7 a) :else 25)\n"
   "(+ 2 (if (> b a) b a))\n"
   "(* (cond (> a b) a (< a b) b :else -1) (+ a 1))"])

(doall 
  (map (fn[form] 
         (let [nform (read-string form)]
           (println nform)
           (println (str ">> " (eval nform)))))
       exercise111-col))

(println "Exercise 1.1.2")

(double (/ (+ 5 1 (- 2 3 (+ 6 (/ 1 5)))) (* 3 (- 6 2) (- 2 7)))) 