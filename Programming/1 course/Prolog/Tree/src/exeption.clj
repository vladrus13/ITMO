(defn checkVect [vectors]
  (or (every? (fn [x] (== (count x) 3)) vectors)
      (throw (Exception. "Cross product is possible only for vectors length 3."))))

(defn checkScalar [s]
  (or (every? number? s)
      (throw (Exception. "this operation applies only to multiplication by scalar."))))

(defn checkVectors [vectors]
  (and (every? vector? vectors) (every? (fn [x] (every? number? x)) vectors)))

(defn checkVectorsWithMessage [vectors]
  (or (checkVectors vectors)
      (throw (Exception. "This operation applies only for vectors."))))

(defn equalsVectors [vectors]
  (or (== (count vectors) 0)
      (== (/ (apply + (map count vectors)) (count vectors))
          (count (first vectors)))))

(defn equalsVectorsWithMessage [vectors]
  (or (equalsVectors vectors)
      (throw (Exception. "Vectors are not equivalent."))))

(defn checkMatrix [matrixs]
  (and (every? vector? matrixs)
       (every? (fn [x] (and (checkVectors x) (equalsVectors x))) matrixs)))

(defn checkMatrixWithMessage [matrixs]
  (or (checkMatrix matrixs)
      (throw (Exception. "This operation applies only to matrix."))))

(defn equalsMatrix [matrixs]
  (or (and (checkMatrix matrixs)
           (every? true? (mapv (fn [x] (equalsVectors x)) (apply mapv vector matrixs))))
      (throw (Exception. "Matrix are not equivalent."))))

(defn checkMulMatrix [matrixs]
  (or (loop [element (first matrixs) tail (rest matrixs)]
        (if (true? element)
          false
          (if (empty? tail)
            true
            (recur (if (== (count element) (count (apply mapv vector (first tail))))
                     (first tail)
                     false)
                   (rest tail)))))
      (throw (Exception. "Multiplication impractical for this matrices."))))