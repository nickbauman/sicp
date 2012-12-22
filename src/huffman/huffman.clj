(ns huffman)

(declare symbols symbol-leaf weight)

(defn make-leaf 
	[symbowl weight]
  (list :leaf symbowl weight))

(defn leaf? 
	[object]
  (= (first object) :leaf))

(defn symbol-leaf 
	[x] 
	(second x))

(defn weight-leaf 
	[x] 
	(nth x 2))

(defn left-branch 
  [tree] 
	(first tree))

(defn right-branch 
  [tree] 
	(second tree))

(defn symbols 
	[tree]
  (if (leaf? tree)
      (list (symbol-leaf tree))
      (nth tree 2)))

(defn weight 
	[tree]
 (println "tree" tree)
 (if (leaf? tree)
   (weight-leaf tree)
   (nth tree 3)))

(defn choose-branch 
	[bit branch]
  (cond (= bit 0) (left-branch branch)
        (= bit 1) (right-branch branch)
        :else (throw (RuntimeException. (str "bad bit -- CHOOSE-BRANCH " bit)))))

(defn encode-symbol
  [tree token]
  (let [has-symbol (fn[branch] (some #(= token %) (symbols branch)))] 
    (if (has-symbol tree)
      (loop [t tree
             encoded-list []]
        (if (leaf? t)
          encoded-list
          (if (has-symbol (right-branch t))
            (recur (right-branch t)
                   (conj encoded-list 1))
            (recur (left-branch t)
                   (conj encoded-list 0)))))
      (throw (RuntimeException. (str "huffman tree cannot encode token '" token "'"))))))

(defn encode 
  [message tree]
    (mapcat (partial encode-symbol tree) message))

(defn make-code-tree 
	[left right]
  (list left
        right
        (into (symbols right) (symbols left))
        (+ (weight left) (weight right))))

(defn adjoin-set 
	[x set]
 (cond (not (seq set)) (list x)
       (< (weight x) (weight (first set))) (cons x set)
       :else (cons (first set)
                   (adjoin-set x (rest set)))))

(defn make-leaf-set 
  [pairs]
  (if (nil? pairs)
    ()
    (let [pair (first pairs)]
      (adjoin-set (make-leaf (first pair)    ; symbol
                             (second pair))  ; frequency
                  (make-leaf-set (rest pairs))))))

(defn make-leaf-set 
  [pairs]
  (loop [[pair & remaining] pairs
         t ()]
    (if pair
      (recur remaining 
             (concat t (adjoin-set (make-leaf (first pair) (second pair)) (make-leaf-set remaining))))
      t)))

(defn successive-merge
  [x]
  ;; TODO implement this (make-code-tree ...
  )

(defn generate-huffman-tree 
  [pairs]
  (successive-merge (make-leaf-set pairs)))

(defn decode
  [bits tree]
  (loop [my-bits bits
         current-branch tree
         decoded-seq []]
    (if (not (seq my-bits))
      decoded-seq
      (let [next-branch (choose-branch (first my-bits) current-branch)]
        (if (leaf? next-branch)
          (recur (rest my-bits) tree (conj decoded-seq (symbol-leaf next-branch)))
          (recur (rest my-bits) next-branch decoded-seq))))))

(def sample-tree
  (make-code-tree (make-leaf \A 4)
                  (make-code-tree
                    (make-leaf \B 2)
                    (make-code-tree (make-leaf \D 1)
                                    (make-leaf \C 1)))))

(def sample-message (list 0 1 1 0 0 1 0 1 0 1 1 1 0))

(time (with-open [rdr (java.io.BufferedReader. 
                 (java.io.FileReader. "/Users/nickbauman/no_sql_non-starter.txt"))]
  (let [seq (line-seq rdr)
        str-seq (apply str seq)]
    (frequencies str-seq))))

