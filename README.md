# SCIP in Clojure

## Clojure exercises for the book _Structure and Interpretation of Computer Programs._

The goal here is to convert all the encountered source from Scheme to Clojure 
as directly as possible while implementing the exercises in Clojure as well. 

SICP tends to use recursion a lot more than seems strictly necessary in some 
places. However, it works well for Scheme because of its excellent tail call 
optimization, which currently isn't as far along in Clojure (because of 
limitations in the JVM.) So the challenge often seems to be avoiding the
temptation of declaring and using recursive functions that rely on TCO which
will either not compile in Clojure or cause the stack to blow (indeed, 
depending on the size of the data, even the Scheme code from SICP will blow
the stack, too.)

What you end up with is, often, simpler than the original Scheme implementation
and often gives Clojure an edge by using functions that are inherently *easily
parallelizable.*

### Notes

### Completed Huffman Trees Exercises (Section 2.3.4)