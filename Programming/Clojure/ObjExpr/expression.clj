;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
;//////////////////////////////////     O P E R A T I O N S    /////////////////////////////////////////////////////////
;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

;///////////// STARTS

(defn evaluate [ex vars] ((.evaluate ex) vars))
(defn toString [ex] (.toString ex))
(defn diff [ex vars] ((.diff ex) vars))

;///////////// INTERFACE

(definterface Opera (evaluate []) (toString []) (diff []))

;///////////// CONST

(declare ZERO)

(deftype Const [v]
  Opera
  (evaluate [x] (fn [vars] v))
  (toString [x] (format "%.1f" (double v)))
  (diff [x] (fn [vars] ZERO)))

(def ZERO (Const. 0))
(def ONE (Const. 1))
(defn Constant [val] (Const. val))

;///////////// VARIABLE

(deftype Var [name]
  Opera
  (evaluate [x] #(get % name))
  (toString [x] name)
  (diff [x] #(if (= % name) ONE ZERO)))

(defn Variable [name] (Var. name))

;////////////// OPERATIONS

;//// FATHERS

(deftype OperationFather [opera stringer differ])

(deftype OperaProducer [father args]
  Opera
  (evaluate [y] #(apply (.opera father) (map (fn [x] (evaluate x %)) args)))
  (toString [y] (str "(" (.stringer father) " " (clojure.string/join " " (map toString args)) ")"))
  (diff [y] #(apply (.differ father) (concat args (map (fn [x] (diff x %)) args)))))

;//// ADD

(declare Add)
(def AddFather (OperationFather. + "+" (fn [a b fa fb] (Add fa fb))))
(defn Add [& args] (OperaProducer. AddFather args))

;//// SUBTRACT

(declare Subtract)
(def SubtractFather (OperationFather. - "-" (fn [a b fa fb] (Subtract fa fb))))
(defn Subtract [& args] (OperaProducer. SubtractFather args))

;//// MULTIPLY

(declare Multiply)
(def MultiplyFather (OperationFather. * "*" (fn [a b fa fb] (Add (Multiply fa b) (Multiply a fb)))))
(defn Multiply [& args] (OperaProducer. MultiplyFather args))

;//// DIVIDE

(declare Divide)
(def DivideFather (OperationFather. #(/ (double %1) (double %2)) "/" (fn [a b da db] (Divide (Subtract (Multiply da b) (Multiply a db)) (Multiply b b)))))

(defn Divide [& args] (OperaProducer. DivideFather args))

;//// NEGATE
(declare Negate)
(def NegateFather (OperationFather. - "negate" (fn [a da] (Negate da))))
(defn Negate [& args] (OperaProducer. NegateFather args))

;//// SQRT
(declare Sqrt)
(def SqrtFather (OperationFather. (fn [x] (Math/sqrt (Math/abs x))) "sqrt" (fn [a da] (Divide (Multiply da a) (Multiply (Constant 2) (Sqrt (Multiply a a a)))))))
(defn Sqrt [& args] (OperaProducer. SqrtFather args))

;//// SQUARE
(declare Square)
(def SquareFather (OperationFather. (fn [x] (* x x)) "square" (fn [a da] (Multiply (Constant 2) da a))))
(defn Square [& args] (OperaProducer. SquareFather args))

;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
;//////////////////////////////////       P A R S E R      /////////////////////////////////////////////////////////////
;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

;///////////////////////// STARTS MAPS

(def Operaions {
                "+" Add,
                "-" Subtract,
                "*", Multiply,
                "/", Divide,
                "negate" Negate,
                "sqrt" Sqrt,
                "square" Square
                })

(def Variables {
                'x (Variable "x"),
                'y (Variable "y"),
                'z (Variable "z"),
                'h (Variable "h")
                })

;///////////////////////// OBJECTS PARSE

(defn parse [expression]
  (cond
    (symbol? expression) (get Variables expression)
    (number? expression) (Constant expression)
    :else (let [op (first expression) args (rest expression)] (apply (get Operaions (str op)) (map parse args)))))

(defn parseObject [expression]
  (parse (read-string expression)))