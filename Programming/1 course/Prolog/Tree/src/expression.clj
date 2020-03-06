; <FROM LAST HW>
(deftype Var [variable])
(deftype ConstOpera [value])
(deftype DivideOpera [arguments])
(deftype AndOpera [arguments])
(deftype OrOpera [arguments])
(deftype XorOpera [arguments])
(deftype UnaryOpera [name operation argument diff])
(deftype BinaryOpera [name operation arguments diff])
(declare Variable Constant Negate Add Subtract Multiply Divide)

(defmulti toString class)
(defmulti toStringSuffix class)
(defmulti toStringInfix class)
(defmulti evaluate (fn [exp _] (class exp)))
(defmulti diff (fn [exp _] (class exp)))

(defmethod toString Var [exp] (.-variable exp))
(defmethod toStringSuffix Var [exp] (.-variable exp))
(defmethod toStringInfix Var [exp] (.-variable exp))
(defmethod evaluate Var [exp vars] ((comp vars clojure.string/lower-case str) (nth (.-variable exp) 0)))
(defmethod diff Var [exp var] (if (= var ((comp clojure.string/lower-case str) (nth (.-variable exp) 0))) (Constant 1) (Constant 0)))
(defmethod toString ConstOpera [exp]
  (str (format "%.0f.0" (.-value exp))))
(defmethod toStringSuffix ConstOpera [exp]
  (str (format "%.0f.0" (.-value exp))))
(defmethod toStringInfix ConstOpera [exp]
  (str (format "%.0f.0" (.-value exp))))
(defmethod evaluate ConstOpera [exp _]
  (.-value exp))
(defmethod diff ConstOpera [_ _] (Constant 0))

(defmethod toString UnaryOpera [exp] (str "(" (.-name exp) " " (toString (.-argument exp)) ")"))
(defmethod toStringSuffix UnaryOpera [exp] (str "(" (toStringSuffix (.-argument exp)) " " (.-name exp) ")"))
(defmethod toStringInfix UnaryOpera [exp] (str (.-name exp) "(" (toStringInfix (.-argument exp)) ")"))
(defmethod evaluate UnaryOpera [exp vars] ((.-operation exp) (evaluate (.-argument exp) vars)))
(defmethod diff UnaryOpera [exp var] ((.-diff exp) (.-argument exp) var))
(defmethod toString DivideOpera [exp] (str "(/" (reduce #(str %1 " " (toString %2)) "" (.-arguments exp)) ")"))
(defmethod toStringSuffix DivideOpera [exp] (str "(" (reduce #(str %1 (toStringSuffix %2) " ") "" (.-arguments exp)) "/)"))
(defmethod toStringInfix DivideOpera [exp] (str "(" (reduce #(str %1 " / " (toStringInfix %2)) (toStringInfix (first (.-arguments exp))) (rest (.-arguments exp))) ")"))
(defmethod evaluate DivideOpera [exp vars] (reduce #(/ (double %1) (double %2)) (map #(evaluate % vars) (.-arguments exp))))
(defmethod diff DivideOpera [exp var] (let [d (rest (.-arguments exp))] (Divide (Subtract (Multiply (diff (first (.-arguments exp)) var) (apply Multiply d)) (Multiply (first (.-arguments exp)) (diff (apply Multiply d) var))) (apply Multiply (into d d)))))

(defmethod toString AndOpera [exp] (str "(&" (reduce #(str %1 " " (toString %2)) "" (.-arguments exp)) ")"))
(defmethod toStringSuffix AndOpera [exp] (str "(" (reduce #(str %1 (toStringSuffix %2) " ") "" (.-arguments exp)) "&)"))
(defmethod toStringInfix AndOpera [exp] (str "(" (reduce #(str %1 " & " (toStringInfix %2)) (toStringInfix (first (.-arguments exp))) (rest (.-arguments exp))) ")"))
(defmethod evaluate AndOpera [exp vars] (reduce #(Double/longBitsToDouble (bit-and (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))) (map #(evaluate % vars) (.-arguments exp))))
(defmethod diff AndOpera [exp var] (let [d (rest (.-arguments exp))] (Divide (Subtract (Multiply (diff (first (.-arguments exp)) var) (apply Multiply d)) (Multiply (first (.-arguments exp)) (diff (apply Multiply d) var))) (apply Multiply (into d d)))))

(defmethod toString OrOpera [exp] (str "(|" (reduce #(str %1 " " (toString %2)) "" (.-arguments exp)) ")"))
(defmethod toStringSuffix OrOpera [exp] (str "(" (reduce #(str %1 (toStringSuffix %2) " ") "" (.-arguments exp)) "|)"))
(defmethod toStringInfix OrOpera [exp] (str "(" (reduce #(str %1 " | " (toStringInfix %2)) (toStringInfix (first (.-arguments exp))) (rest (.-arguments exp))) ")"))
(defmethod evaluate OrOpera [exp vars] (reduce #(Double/longBitsToDouble (bit-or (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))) (map #(evaluate % vars) (.-arguments exp))))
(defmethod diff OrOpera [exp var] (let [d (rest (.-arguments exp))] (Divide (Subtract (Multiply (diff (first (.-arguments exp)) var) (apply Multiply d)) (Multiply (first (.-arguments exp)) (diff (apply Multiply d) var))) (apply Multiply (into d d)))))

(defmethod toString XorOpera [exp] (str "(^" (reduce #(str %1 " " (toString %2)) "" (.-arguments exp)) ")"))
(defmethod toStringSuffix XorOpera [exp] (str "(" (reduce #(str %1 (toStringSuffix %2) " ") "" (.-arguments exp)) "^)"))
(defmethod toStringInfix XorOpera [exp] (str "(" (reduce #(str %1 " ^ " (toStringInfix %2)) (toStringInfix (first (.-arguments exp))) (rest (.-arguments exp))) ")"))
(defmethod evaluate XorOpera [exp vars] (reduce #(Double/longBitsToDouble (bit-xor (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))) (map #(evaluate % vars) (.-arguments exp))))
(defmethod diff XorOpera [exp var] (let [d (rest (.-arguments exp))] (Divide (Subtract (Multiply (diff (first (.-arguments exp)) var) (apply Multiply d)) (Multiply (first (.-arguments exp)) (diff (apply Multiply d) var))) (apply Multiply (into d d)))))

(defmethod toString BinaryOpera [exp] (str "(" (.-name exp) (reduce #(str %1 " " (toString %2)) "" (.-arguments exp)) ")"))
(defmethod toStringSuffix BinaryOpera [exp] (str "(" (reduce #(str %1 (toStringSuffix %2) " ") "" (.-arguments exp)) (.-name exp) ")"))
(defmethod toStringInfix BinaryOpera [exp] (str "(" (reduce #(str %1 " " (.-name exp) " " (toStringInfix %2)) (toStringInfix (first (.-arguments exp))) (rest (.-arguments exp))) ")"))
(defmethod evaluate BinaryOpera [exp vars] (case (count (.-arguments exp)) 0 ((.-operation exp)) 1 ((.-operation exp) (evaluate (first (.-arguments exp)) vars)) (reduce (.-operation exp) (map #(evaluate % vars) (.-arguments exp)))))
(defmethod diff BinaryOpera [exp var] ((.-diff exp) (.-arguments exp) var))

(defn Variable [var] (Var. var))
(defn Constant [value] (ConstOpera. value))
(defn Negate [argument] (UnaryOpera. "negate" - argument (fn [arg var] (Negate (diff arg var)))))
(defn Divide [& arguments] (DivideOpera. arguments))
(defn And [& arguments] (AndOpera. arguments))
(defn Or [& arguments] (OrOpera. arguments))
(defn Xor [& arguments] (XorOpera. arguments))

(defn Add [& arguments] (BinaryOpera. "+" + arguments (fn [args var] (apply Add (map #(diff % var) args)))))
(defn Subtract [& arguments] (BinaryOpera. "-" - arguments (fn [args var] (apply Subtract (map #(diff % var) args)))))
(defn Multiply [& arguments] (BinaryOpera. "*" * arguments (fn [args var] (Add (apply Multiply (diff (first args) var) (rest args)) (if (== (count args) 1) (Constant 0) (Multiply (first args) (diff (apply Multiply (rest args)) var)))))))
(defn Pow [& arguments] (BinaryOpera. "**" #(Math/pow %1 %2) arguments #()))
(defn Log [& arguments] (BinaryOpera. "//" #(/ (Math/log (Math/abs %2)) (Math/log (Math/abs %1))) arguments #()))
(def operations {'+ Add,
                 '- Subtract,
                 '* Multiply,
                 '/ Divide,
                 '& And,
                 (symbol "^") Xor,
                 '| Or,
                 'negate Negate,
                 '** Pow,
                 (symbol "//") Log
                 })

(defn parseExpression [expr] (cond (number? expr) (Constant expr) (symbol? expr) (Variable (str expr)) :else (apply (operations (first expr)) (map parseExpression (rest expr)))))

(def parseObject (comp parseExpression read-string))

; </FROM LAST HW>

; <wrote GOSHA>

(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _show [result]
  (if (-valid? result) (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
                       "!"))
(defn tabulate [parser inputs]
  (println)
  (run! (fn [input] (printf "    %-10s %s\n" input (_show (parser input)))) inputs))

(defn _empty [value] (partial -return value))
(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))
(defn _map [f]
  (fn [result]
    (if (-valid? result)
      (-return (f (-value result)) (-tail result)))))
(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        ((_map (partial f (-value ar)))
          ((force b) (-tail ar)))))))
(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (_map f) parser))
(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))

(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))

(defn +or [p & ps]
  (reduce (partial _either) p ps))
(defn +opt [p]
  (+or p (_empty nil)))
(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

;</wrote GOSHA>

(declare *valueSuffix)
(def *all-chars (mapv char (range 0 256)))
(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+seqf #(into (vec (cons %1 %2)) (vec (cons %3 %4))) (+opt (+char "+-")) (+plus *digit) (+opt (+char ".")) (+opt (+plus *digit))))))
(def *spaces (apply str (filter #(or (Character/isWhitespace %) (= \, %)) *all-chars)))
(def *space (+char *spaces))
(def *symbol (+map symbol (+str (+or (+map list (+char (str (symbol "^") "|&+-/*"))) (+seqf cons (+char-not (str *spaces \u0000 (symbol "^") "|&()+-/*.1234567890")) (+star (+char-not (str *spaces (symbol "^") "^|&()+-/*." \u0000))))))))
(def *whitespaces (+ignore (+star *space)))
(defn *seq [begin p end]
  (+seqn 1 (+char begin) (+opt (+seqf cons *whitespaces p (+star (+seqn 0 *whitespaces p)))) *whitespaces (+char end)))

(defn *list [] (+map #(cons (last %) (drop-last %)) (*seq "(" (delay (*valueSuffix)) ")")))
(defn *valueSuffix [] (+or *number *symbol (*list)))

(def parserObjectSuffix (+parser (+seqn 0 *whitespaces (*valueSuffix) *whitespaces)))
(defn parseObjectSuffix [str] (parseExpression (parserObjectSuffix str)))

(declare *valueInfix)
(defn *parse_many [p sign] (+map (partial reduce #(list (first %2) %1 (second %2))) (+seqf cons *whitespaces p (+star (+seq *whitespaces sign *whitespaces p)) *whitespaces)))
(defn *parse_many_right [p sign] (+map (fn [s] (reduce #(list (second %2) (first %2) %1) (second s) (reverse (first s)))) (+seq *whitespaces (+star (+seq p *whitespaces sign *whitespaces)) p *whitespaces)))
(defn *Nsymbol [symbols] (fn [input] (if (not (symbols (-value (*symbol input)))) (*symbol input))))
(defn *Esymbol [symbols] (fn [input] (if (symbols (-value (*symbol input))) (*symbol input))))
(def *variables (+map symbol (+str (+plus (+char "XYZxyz")))))
(def *plusOtherSymbol (*Esymbol #{'+ '-}))
(def *divOtherSymbol (*Esymbol #{'* '/}))
(def *AndSymbol (*Esymbol #{'&}))
(def *XorSymbol (*Esymbol #{(symbol "^")}))
(def *OrSymbol (*Esymbol #{'|}))
(def *PowOtherSymbol (+map symbol (+str (+or (+seq (+char "/") (+char "/")) (+seq (+char "*") (+char "*"))))))

(def *FSymbol (+map symbol (+str (+seqf cons (+char-not (str *spaces \u0000 (symbol "^") "xyzXYZ|&+-/*().1234567890")) (+star (+char-not (str *spaces "() \u0000")))))))
(defn *listInfix [] (+seqn 1 (+char "(") *whitespaces (delay (*valueInfix)) *whitespaces (+char ")")))
(defn *F [] (+map #(list (first %) (second %)) (+seq *FSymbol *whitespaces (+or (delay (*listInfix)) (delay (*F)) (delay *number) (delay *variables)))))
(defn *powOther [] (*parse_many_right (+or (delay (*listInfix)) (delay (*F)) *number *variables) *PowOtherSymbol))
(defn *divOther [] (*parse_many (+or (delay (*powOther)) (delay (*listInfix)) (delay (*F)) *number *variables) *divOtherSymbol))
(defn *addOther [] (*parse_many (+or (delay (*divOther)) (delay (*powOther)) (delay (*listInfix)) (delay (*F)) *number *variables) *plusOtherSymbol))
(defn *and [] (*parse_many (+or (delay (*addOther)) (delay (*divOther)) (delay (*powOther)) (delay (*listInfix)) (delay (*F)) *number *variables) *AndSymbol))
(defn *or [] (*parse_many (+or (delay (*and)) (delay (*addOther)) (delay (*divOther)) (delay (*powOther)) (delay (*listInfix)) (delay (*F)) *number *variables) *OrSymbol))
(defn *xor [] (*parse_many (+or (delay (*or)) (delay (*and)) (delay (*addOther)) (delay (*divOther)) (delay (*powOther)) (delay (*listInfix)) (delay (*F)) *number *variables) *XorSymbol))

(defn *valueInfix [] (+or (delay (*xor)) (delay (*or)) (delay (*and))(delay (*addOther)) (delay (*divOther)) (delay (*powOther)) (delay (*listInfix)) (delay (*F)) *number *variables))

(def parserObjectInfix (+parser (+seqn 0 *whitespaces (*valueInfix) *whitespaces)))
(defn parseObjectInfix [str] (parseExpression (parserObjectInfix str)))
