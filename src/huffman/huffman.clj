(ns huffman
  (:require [clojure.test :as test]))

(declare symbols symbol-leaf weight)

(defn make-leaf
  "Creates a leaf node from a symbol and its leaf"
	[symbowl weight]
  (list :leaf symbowl weight))

(defn leaf? 
  "Returns whether a node is a leaf"
	[object]
  (= (first object) :leaf))

(defn symbol-leaf 
  "Returns the symbol of this leaf"
	[x] 
	(second x))

(defn weight-leaf
  "Returns the weight of a given node"
	[x] 
	(nth x 2))

(defn left-branch 
  "Returns the left branch of the current huffman tree"
  [tree] 
	(first tree))

(defn right-branch
  "Returns the right branch of the current huffman tree"
  [tree] 
	(second tree))

(defn symbols
  "Returns the current branch's symbol"
	[tree]
  (if (leaf? tree)
      (list (symbol-leaf tree))
      (nth tree 2)))

(defn weight 
  "Returns the branch's weight"
	[tree]
 (println "tree" tree)
 (if (leaf? tree)
   (weight-leaf tree)
   (nth tree 3)))

(defn choose-branch
  "Takes the current branch of a huffman tree and returns the next branch. For decoding."
	[bit branch]
  (cond (= bit 0) (left-branch branch)
        (= bit 1) (right-branch branch)
        :else (throw (RuntimeException. (str "bad bit -- CHOOSE-BRANCH " bit)))))

(defn encode-symbol
  "Takes a huffman tree and a token, traverses the tree and returns the branch 
  directions as a list of traversals to find the token"
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
  "Returns a seq of 1s and 0s taking a message seq and an adaquate huffman tree
  for that sequence. Throws an exception if the huffman tree does not contain 
  a particular element of the message sequence."
  [message tree]
    (mapcat (partial encode-symbol tree) message))

(defn make-code-tree 
  "Joins two nodes by creating a parent node from a seq of their combined 
  symbols and the sum of their weights in the tree"
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
  "Takes a set of frequency pairs and generates a huffman tree datastructure from it"
  [pairs]
  (successive-merge (make-leaf-set pairs)))

(defn decode
  "Decode numeric bits represented as either a 1 (right branch) or a 0 (left 
  branch) traversing to a left or a right node util a terminal node is 
  reached, which is the character to convert to."
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


;;;;;;
;; End of Huffman implementation. The rest of this is code to exercise it.
;;;;;;

;; Some frobbing around with huffman

(def sample-tree
  (make-code-tree (make-leaf \A 4)
                  (make-code-tree
                    (make-leaf \B 2)
                    (make-code-tree (make-leaf \D 1)
                                    (make-leaf \C 1)))))

(def sample-message (list 0 1 1 0 0 1 0 1 0 1 1 1 0))

(def sample-decoded-message [\A \D \A \B \B \C \A])

(test/deftest test-simple-decode
              (test/is (= sample-decoded-message (decode sample-message sample-tree))))

(test/deftest test-simple-encode
              (test/is (= sample-message (encode sample-decoded-message sample-tree))))

;; A mechanism to generate random strings for testing huffman trees

;; A sample set of characters with dupes to influence commonly used characters in English
(def keyboard "`1234567890-=qweeeertttyuuuiiiioooopp[]\\aaassssssddffghhjjkl;'zxccvbbnnmm,./        ")

(defn sel-rand-char
  "Select a random character from string"
  [string]
  (nth string (int (rand (count keyboard)))))

(defn gen-rand-str
  "Create a random string of a certain size that relies on 'keyboard' value for a sample gamut"
  [size]
  (apply str (map (fn[x] (sel-rand-char keyboard)) (range size))))

(def sample-string (gen-rand-str 6000))

(def sample-frequencies (sort (comparator (fn[[a x] [b y]] (> x y))) (frequencies sample-string)))

