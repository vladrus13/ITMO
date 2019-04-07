(defn tensor_shape_shape
  [tensor]
  (if (number? tensor)
    []
    (let [inner (map tensor_shape_shape tensor)]
      (if (and (apply = inner) (not (nil? (first inner))))
        (vec (cons (count tensor) (first inner)))))))

(defn shape_shape
  [tensor]
  (vec ((fn inner [t]
          (if (number? t)
            []
            (cons (count t) (inner (first t))))) tensor)))

(defn equals_shape
  [& tensors]
  (apply = (map shape_shape tensors)))

(defn vector_const_size
  ([_]
   true)
  ([size & vectors]
   (apply = [size] (map shape_shape vectors))))

(defn matrix_connect
  [matrix1 matrix2]
  {:pre [(not= 0 (count matrix1))]}
  (= (count (first matrix1)) (count matrix2)))

(defn isntensor
  [tensor]
  (not= (tensor_shape_shape tensor) nil))

(defn isnvector
  [vector]
  (and (vector? vector) (every? number? vector)))

(defn isnmatrix
  [matrix]
  (and (vector? matrix) (every? isnvector matrix) (not (empty? matrix))))

(defn go_reshape
  [shape1 shape2]
  (and (>= (count shape1) (count shape2))
       (let [s1 (reverse shape1)
             s2 (reverse shape2)]
         (every? identity (map (fn [i] (== (nth s1 i) (nth s2 i))) (range (count s2)))))))

(defn wider
  [sub-shape tensor]
  (if (empty? sub-shape)
    tensor
    (vec (repeat (first sub-shape)
                 (wider (rest sub-shape) tensor)))))

(defn resizer
  [shape tensor]
  {:pre [(isntensor tensor)
         (isnvector shape)
         (go_reshape shape (shape_shape tensor))]}
  (wider (drop-last (count (shape_shape tensor)) shape) tensor))

(defn better_of
  [tensor-array]
  (apply max-key (fn [tensor] (count (shape_shape tensor))) tensor-array))

(defn another_shape_op
  [fun tensor-array]
  (if (every? number? tensor-array)
    (apply fun tensor-array)
    (apply mapv (fn [& sub-tensors] (another_shape_op fun sub-tensors)) tensor-array)))

(defn tensor_op
  [fun tensor-array]
  (let [g (shape_shape (better_of tensor-array))]
    {:pre [(every? isntensor tensor-array)
           (every? (fn [tensor]
                     (go_reshape g (shape_shape tensor))) tensor-array)]}
    (another_shape_op fun (mapv (fn [tensor] (resizer g tensor)) tensor-array))))

(defn vector_op
  [fun vector-array]
  {:pre [(every? isnvector vector-array)
         (apply equals_shape vector-array)]}
  (another_shape_op fun vector-array))

(defn matrix_op
  [fun matrix-array]
  {:pre [(every? isnmatrix matrix-array)
         (apply equals_shape matrix-array)]}
  (another_shape_op fun matrix-array))

(defn tensor_scalar_op
  ([fun tensor scalar]
   {:pre [(isntensor tensor)
          (number? scalar)]}
   (mapv (fn [element] (fun element scalar)) tensor))
  ([fun tensor scalar & scalars]
   (apply tensor_scalar_op fun (tensor_scalar_op fun tensor scalar) scalars)))

(defn v+
  [& vectors]
  (vector_op + vectors))
(defn v-
  [& vectors]
  (vector_op - vectors))
(defn v*
  [& vectors]
  (vector_op * vectors))

(defn scalar
  [& vectors]
  (apply + (apply v* vectors)))
(defn vect
  ([vector]
   {:pre [(isnvector vector)
          (vector_const_size 3 vector)]}
   vector)
  ([vector1 vector2]
   {:pre [(isnvector vector1)
          (isnvector vector2)
          (vector_const_size 3 vector1 vector2)]}
   (mapv (fn [i] (let [l (mod (+ i 1) 3)
                       r (mod (+ i 2) 3)]
                   (- (* (vector1 l) (vector2 r))
                      (* (vector1 r) (vector2 l)))))
         (range 0 3)))
  ([vector1 vector2 & vectors]
   (apply vect (vect vector1 vector2) vectors)))

(defn v*s
  ([vector]
   {:pre [(isnvector vector)]}
   vector)
  ([vec & scalars]
   (apply tensor_scalar_op * vec scalars)))

(defn m+
  [& matrixes]
  (matrix_op + matrixes))
(defn m-
  [& matrixes]
  (matrix_op - matrixes))
(defn m*
  [& matrixes]
  (matrix_op * matrixes))

(defn m*s
  ([matrix]
   {:pre [(isnmatrix matrix)]}
   matrix)
  ([matrix & scalars]
   (apply tensor_scalar_op v*s matrix scalars)))
(defn m*v
  [matrix vector]
  {:pre [(isnmatrix matrix)]}
  (mapv (fn [row] (scalar row vector)) matrix))
(defn transpose
  [matrix]
  {:pre [(isnmatrix matrix)]}
  (apply mapv vector matrix))
(defn m*m
  ([matrix]
   {:pre [(isnmatrix matrix)]}
   matrix)
  ([matrix1 matrix2]
   {:pre [(isnmatrix matrix1)
          (isnmatrix matrix2)
          (matrix_connect matrix1 matrix2)]}
   (let [t-matrix2 (transpose matrix2)]
     (mapv (fn [i] (mapv (fn [j] (scalar (matrix1 i) (t-matrix2 j)))
                         (range 0 (count t-matrix2))))
           (range 0 (count matrix1)))))
  ([matrix1 matrix2 & matrixes]
   (apply m*m (m*m matrix1 matrix2) matrixes)))

(defn t+
  [& tensors]
  (tensor_op + tensors))
(defn t-
  [& tensors]
  (tensor_op - tensors))
(defn t*
  [& tensors]
  (tensor_op * tensors))