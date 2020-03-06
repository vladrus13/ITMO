(chapter "Input and output")
(example "Hello, world"
         (defn hello-world []
           (let [input (read-line)]
             (if (not (empty? input))
               (do
                 (println "Hello," input)
                 (recur)))))
         (with-in-file "data/hello.in" hello-world))
(example "Running sum"
         (defn running-sum []
           (loop [sum 0]
             (let [input (read-line)]
               (if (not (empty? input))
                 (let [sum' (+ sum (read-string input))]
                   (do
                     (println sum')
                     (recur sum')))
                 sum))))
         (with-in-file "data/sum.in" running-sum))
(example "A+B"
         (defn aplusb [in out]
           (let [data (mapv read-string (clojure.string/split (slurp in) #" "))]
             (spit out (apply + data))))
         (aplusb "data/aplusb.in" "data/aplusb.out"))
(example "Intel"
         (defn intel [in out]
           (let [lines (clojure.string/split-lines (slurp in))
                 [n & points] (mapv #(mapv read-string (clojure.string/split % #" ")) lines)
                 segments (partition 2 1 (conj points (last points)))
                 [xs ys] (apply mapv vector points)
                 length (fn [[x1 y1] [x2 y2]] (Math/abs (+ x2 (- x1) y2 (- y1))))
                 perimeter (apply + (mapv (partial apply length) segments))
                 diff #(- (apply max %) (apply min %))]
             (spit out (- perimeter (* 2 (+ (diff xs) (diff ys)))))))
         (intel "data/intel.in" "data/intel.out"))
