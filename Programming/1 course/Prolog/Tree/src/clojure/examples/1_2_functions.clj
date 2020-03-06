(chapter "Custom Functions")

(section "Simple Functions")
(example "Define function"
         (defn square [x] (* x x)))
(example "Use function"
         (square 8))
(example "Nullary function"
         (defn nullary [] 10)
         (nullary))
(example "Anonymous functions"
         ((fn [x] (+ x x)) 10)
         (#(+ % %) 10)
         (#(+ %1 %2) 10 20))
(example "Functions as values"
         (defn twice [f] (fn [a] (f (f a))))
         ((twice square) 3))

(section "Recursive Functions")
(example "Recursive Fibonacci"
         (defn rec-fib [n]
           (cond
             (== 0 n) 1
             (== 1 n) 1
             :else (+ (rec-fib (- n 1)) (rec-fib (- n 2)))))
         (rec-fib 40))
(example "Memoized Fibonacci"
         (def mem-fib
           (memoize
             (fn [n]
               (cond
                 (== 0 n) 1
                 (== 1 n) 1
                 :else (+ (mem-fib (- n 1)) (mem-fib (- n 2)))))))
         (mem-fib 90))(d)
(example "Tail-recursive Fibonacci"
         (defn iter-fib [n]
           (letf[(iter-fib' [n a b]
                     (if (== 0 n)
                       a
                       (iter-fib' (- n 1) b (+' a b))))]
             (iter-fib' n 1 1)))
         (iter-fib 90))
(example "Explicit loop Fibonacci"
         (defn loop-fib [n]
           (loop [n n a 1 b 1]
             (if (== 0 n)
               a
               (recur (- n 1) b (+ a b)))))
         (loop-fib 90))

(section "Pre and Post conditions")
(example "Fast power"
         (defn power [a b]
           {:pre [(<= 0 b)]
            :post [(or (== 0 b) (== 0 a) (== 0 (rem % a)))]}
           (cond
             (= 0 b) 1
             (= 1 b) a
             (even? b) (power (* a a) (quot b 2))
             (odd? b) (* a (power a (- b 1))))))
(example "Pre and posconditions ok"
         (power 2 5)
         (power 2 0)
         (power 0 2))
(example "Precondition violated"
         (power 2 -5))
(example "Invalid postcondition"
         (defn ipower
           [a b]
           {:pre [(<= 0 b)]
            :post [(= 0 (rem % a)) (<= 0 %)]}
           (power a b)))
(example "First part of invalid postcondition violated"
         (ipower 2 0)
         (power -2 3))
(example "Second part of invalid postcondition violated"
         (ipower -2 3))
