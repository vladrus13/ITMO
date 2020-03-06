(chapter "Vectors")
(example "Vectors"
         (vector 1 2)
         (vector 1 2 "Hello" 3 4)
         [1 2]
         (def vect [1 2 "Hello" 3 4]))
(example "Queries"
         (count vect)
         (nth vect 2)
         (vect 2)
         (vect 10))
(example "Modifications"
         (conj [1 2] 3 4)
         (peek [1 2])
         (pop [1 2])
         (assoc [1 2] 0 10)
         (assoc [1 2 3] 0 10 2 20))
