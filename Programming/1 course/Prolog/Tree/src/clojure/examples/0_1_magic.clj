(defn lecture [name]
  (println)
  (let [line (clojure.string/join (repeat (+ 16 (count name)) "="))]
    (println line)
    (println "=== Lecture" name "===")
    (println line)))

(defn chapter [name]
  (println)
  (println "==========" name "=========="))

(defn section [name]
  (println)
  (println "---" name "---"))


(defn safe-eval [expression]
  (try
    (eval expression)
    (catch Throwable e (str (.getSimpleName (type e)) ": " (.getMessage e)))))

(defmacro with-out-str-and-value [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*out* s#]
       (let [v# ~@body]
         (vector (str s#) v#)))))

(defn example' [description & expressions]
  {:pre [(not (empty? expressions))]}
  (println (str "    " description ": "))
  (letfn [(run [expression]
            (let [[output value] (with-out-str-and-value
                                   (safe-eval expression))]
              (println "      " (pr-str expression) "->" value)
              (if (not (empty? output))
                (mapv #(println "            >" %) (clojure.string/split-lines output)))))]
    (mapv run expressions)))

(defmacro example [description & expressions]
  `(apply example' ~description (quote ~expressions)))

(defn with-in-file [file action]
  (with-in-str (slurp file) (action)))
