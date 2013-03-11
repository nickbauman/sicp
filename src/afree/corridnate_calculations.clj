(ns afree.corridnate-calculations)

;; This is a little ditty about finding the furthest-apart two coordinates in a
;; graph. It could be made more efficient, but it works.

;; First a little bit of math
(defn- scale
  [x y]
  (if (or (zero? x) (zero? y))
    1
    (Math/abs x)))

(defn float=
  ([x y] (float= x y 0.00001))
  ([x y epsilon] (<= (Math/abs (- x y))
                     (* (scale x y) epsilon))))

(defn sqrt
  ([c] (sqrt c 1e-15))
  ([c epsilon]
     (loop [t (double c)]
       (if (float= t (/ c t) epsilon)
         t
         (recur (double (/ (+ t (/ c t)) 2)))) )))

;; Now a little domain for our coordinates
(defn make-point
  [x y]
  (list x y))

(defn x
  [coord]
  (first coord))

(defn y
  [coord]
  (second coord))

;; Finally find the furthest distance itself:

(defn find-distance
  [a-coord b-coord]
  (let [xsize (- (x a-coord) (x b-coord))
        ysize (- (y a-coord) (y b-coord))]
    (sqrt (+ (* xsize xsize) (* ysize ysize)))))

(defn find-furthest-point
  [pt pt-list]
  (first (reverse (sort (map (fn [coord] [(find-distance pt coord) pt coord]) pt-list)))))

(defn find-furthest-points 
  [pt-list]
  (first 
    (sort 
      (comparator (fn[[distance-a _ _] [distance-b _ _]] (> distance-a distance-b))) 
                      (map (fn[coord] (find-furthest-point coord pt-list)) pt-list))))

;; Run it.
(find-furthest-points (list (make-point 1 2) (make-point -17 93) (make-point 90 -2) (make-point 20 76)))