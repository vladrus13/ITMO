(defn constant [x] (fn [args] x))                              ;
(defn variable [name] (fn [args] (args name)))              ;
(defn unary-operation
  [function]
  (fn [operand]
    (fn [vars] (function (operand vars)))))                 ;
(defn opera
  [f]
  (fn [& operands]
    (fn [vars] (apply f (mapv (fn [x] (x vars)) operands)))))                        ;
(def add (opera +))                                     ;
(def subtract (opera -))                                ;
(def multiply (opera *))                                ;
(def divide (opera (fn [a b] (/ (double a) (double b))))) ;
(def negate (unary-operation -))                                  ;
(def Operators {
                '+ add '- subtract '* multiply '/ divide 'negate negate}) ;
(defn parse [expr] (cond
                     (seq? expr) (apply (Operators (first expr)) (mapv parse (rest expr)))
                     (number? expr) (constant expr)
                     :else (variable (str expr))))          ;
(def parseFunction (comp parse read-string))                ;