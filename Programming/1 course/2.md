# Тесты к курсу «Парадигмы программирования»

[Условия домашних заданий](http://www.kgeorgiy.info/courses/paradigms/homeworks.html)


## Домашнее задание 11. Ассоциативные массивы на Prolog

Модификации
 * *Простая*
    * Код должен находиться в файле `sorted-list-map.pl`.
    * [Исходный код тестов](prolog/prtest/list/PrologListTest.java)
        * Запускать c аргументом `sorted`
 * *Сложная*
    * Код должен находиться в файле `tree-map.pl`.
    * [Исходный код тестов](prolog/prtest/tree/PrologTreeTest.java)
        * Запускать c аргументом `hard` или `bonus`
 * *Replace*
    * Добавьте правило `map_replace(Map, Key, Value, Result)`,
        заменяющего значения ключа на указанное, если ключ присутствует.
    * Исходный код тестов:
        [простые](prolog/prtest/list/PrologListReplaceTest.java),
        [сложные](prolog/prtest/tree/PrologTreeReplaceTest.java)
 * *Size*
    * Добавьте правило `map_size(Map, Size)`, возвращающее размер дерева
    * Исходный код тестов:
        [простые](prolog/prtest/list/PrologListSizeTest.java),
        [сложные](prolog/prtest/tree/PrologTreeSizeTest.java)
 * *floorKey*
    * Добавьте правило `map_floorKey(Map, Key, FloorKey)`,
      вовзращающее максимальный ключ, меньший либо равный заданному.
    * Исходный код тестов:
        [простые](prolog/prtest/list/PrologListFloorTest.java),
        [сложные](prolog/prtest/tree/PrologTreeFloorTest.java)


## Исходный код к лекциям по Prolog

Запуск Prolog
 * [Windows](prolog/RunProlog.cmd)
 * [*nix](prolog/RunProlog.sh)

Лекция 1. Введение в пролог
 * [Учебный план](prolog/examples/1_plan.pl)
 * [Вычисления](prolog/examples/2_calc.pl)
 * [Списки](prolog/examples/3_lists.pl)
 * [Задача о расстановке ферзей](prolog/examples/4_queens.pl)
 * [Загадка Эйнштейна](prolog/examples/5_einstein.pl)
 * [Арифметические выражения](prolog/examples/6_expressions.pl)


## Домашнее задание 10. Комбинаторные парсеры
Модификации
 * *Базовая*
    * Код должен находиться в файле `expression.clj`.
    * [Исходный код тестов](clojure/cljtest/parsing/ClojureObjectParsingTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *Variables*. Дополнительно реализовать поддержку:
    * Переменных, состоящих из произвольного количества букв `XYZ` в любом регистре
        * Настоящее имя переменной определяется первой буквой ее имени
    * [Исходный код тестов](clojure/cljtest/parsing/ClojureVariablesParsingTest.java)
 * *PowLog*. Дополнительно реализовать поддержку:
    * Бинарных правоассоциативных операций максимального приоритета:
        * `Pow` (`**`) – возведения в степень:
            `4 ** 3 ** 2` равно `4 ** (3 ** 2)` равно 262144
        * `Log` (`//`) – взятия логарифма:
            `8 // 9 // 3` равно `8 // (9 // 3)` равно 3
    * [Исходный код тестов](clojure/cljtest/parsing/ClojurePowLogParsingTest.java)
 * *Bitwise*. Дополнительно реализовать поддержку:
    * Побитовых операций
        * `And` (`&`) – и: `5 & 6` равно 4
        * `Or` (`|`) - или: `5 & 6` равно 7
        * `Xor` (`^`) - исключающее: `5 ^ 6` примерно равно 1.66881E-308
        * для реализации операций используйте
            [doubleToLongBits](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Double.html#doubleToLongBits(double))
            и [longBitsToDouble](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Double.html#longBitsToDouble(long))
        * операции по увеличиению приоритета: `^`, `|`, `&`, `+` и `-`, `*` и `/`
    * [Исходный код тестов](clojure/cljtest/parsing/ClojureBitwiseParsingTest.java)


## Домашнее задание 9. Объектные выражения на Clojure

Модификации
 * *Базовая*
    * Код должен находиться в файле `expression.clj`.
    * [Исходный код тестов](clojure/cljtest/object/ClojureObjectExpressionTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *SinhCosh*. Дополнительно реализовать поддержку:
    * унарных операций:
        * `Sinh` (`sinh`) – гиперболический синус, `(sinh 3)` немного больше 10;
        * `Cosh` (`cosh`) – гиперболический косинус, `(cosh 3)` немного меньше 10.
    * [Исходный код тестов](clojure/cljtest/object/ClojureObjectSinhCoshTest.java)
 * *SquareSqrt*. Дополнительно реализовать поддержку:
    * унарных операций:
        * `Square` (`square`) – возведение в квадрат, `(square 3)` равно 9;
        * `Sqrt` (`sqrt`) – извлечение квадратного корня из модуля аргумента, `(sqrt -9)` равно 3.
    * [Исходный код тестов](clojure/cljtest/object/ClojureObjectSquareSqrtTest.java)


## Домашнее задание 8. Функциональные выражения на Clojure

Модификации
 * *Базовая*
    * Код должен находиться в файле `expression.clj`.
    * [Исходный код тестов](clojure/cljtest/functional/ClojureFunctionalExpressionTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *SquareSqrt*. Дополнительно реализовать поддержку:
    * унарных операций:
        * `square` (`square`) – возведение в квадрат, `(square 3)` равно 9;
        * `sqrt` (`sqrt`) – извлечение квадратного корня из модуля аргумента, `(sqrt -9)` равно 3.
    * [Исходный код тестов](clojure/cljtest/functional/ClojureFunctionalSquareSqrtTest.java)
 * *MinMax*. Дополнительно реализовать поддержку:
    * операций:
        * `min` (`min`) – минимум, `(min 1 2 6)` равно 1;
        * `max` (`max`) – максимум, `(min 1 2 6)` равно 6;
    * [Исходный код тестов](clojure/cljtest/functional/ClojureFunctionalMinMaxTest.java)
 * *MedAvg*. Дополнительно реализовать поддержку:
    * операций:
        * `med` (`med`) – медиана, `(med 1 2 6)` равно 2;
        * `avg` (`max`) – среднее, `(min 1 2 6)` равно 3;
    * [Исходный код тестов](clojure/cljtest/functional/ClojureFunctionalMedAvgTest.java)


## Домашнее задание 7. Линейная алгебра на Clojure

Модификации
 * *Базовая*
    * Код должен находиться в файле `linear.clj`.
    * Исходный код тестов
        * [Простой вариант](clojure/cljtest/linear/LinearBinaryTest.java)
        * [Сложный вариант](clojure/cljtest/linear/LinearNaryTest.java)
 * *Shapeless*
    * Добавьте операции поэлементного сложения (`s+`),
        вычитания (`s-`) и умножения (`s*`) чисел и
        векторов любой (в том числе, переменной) формы.
        Например, `(s+ [[1 2] 3] [[4 5] 6])` должно быть равно `[[5 7] 9]`.
    * [Исходный код тестов](clojure/cljtest/linear/LinearShapelessTest.java)
 * *Tensor*
    * Назовем _тензором_ многомерную прямоугольную таблицу чисел.
    * Добавьте операции поэлементного сложения (`t+`),
        вычитания (`t-`) и умножения (`t*`) тензоров.
        Например, `(s+ [[1 2] [3 4]] [[5 6] [7 8]])` должно быть равно `[[6 8] [10 12]]`.
    * [Исходный код тестов](clojure/cljtest/linear/LinearTensorTest.java)
 * *Broadcast*
    * Назовем _тензором_ многомерную прямоугольную таблицу чисел.
    * _Форма_ тензора – последовательность чисел
        (_s_<sub>1..n</sub>)=(_s_<sub>1</sub>, _s_<sub>2</sub>, …, _s<sub>n</sub>_), где
        _n_ – размерность тензора, а _s<sub>i</sub>_ – число элементов
        по _i_-ой оси.
      Например, форма тензора `[ [ [2 3 4] [5 6 7] ] ]`  равна (1, 2, 3),
      а форма `1` равна ().
    * Тензор формы (_s_<sub>1.._n_</sub>) может быть _распространен_ (broadcast)
      до тензора формы (_u_<sub>1.._m_</sub>), если (_s_<sub>i.._n_</sub>) является
      суффиксом (_u<sub>1..m</sub>_). Для этого, исходный тензор копируется
      по недостающим осям.
      Например, распространив тензор `[ [2] [3] ]` формы (2, 1) до
      формы (3, 2, 1) получим `[ [ [2] [3] ] [ [2] [3] ] [ [2] [3] ] ]`,
      а распространив `1` до формы (2, 3) получим `[ [1 1 1] [1 1 1] ]`.
    * Тензоры называются совместимыми, если один из них может быть распространен
      до формы другого.
      Например, тензоры формы (3, 2, 1) и (2, 1) совместимы, а
      (3, 2, 1) и (1, 2) – нет. Числа совместимы с тензорами любой формы.
    * Добавьте операции поэлементного сложения (`b+`),
      вычитания (`b-`) и умножения (`b*`) совместимых тензоров.
      Если формы тензоров не совпадают, то тензоры меньшей размерности
      должны быть предварительно распространены до тензоров большей размерности.
      Например, `(b+ 1 [ [10 20 30] [40 50 60] ] [100 200 300] )` должно
      быть равно `[ [111 221 331] [141 251 361] ]`.
    * [Исходный код тестов](clojure/cljtest/linear/LinearBroadcastTest.java)


## Исходный код к лекциям по Clojure

Запуск Clojure
 * Консоль: [Windows](clojure/RunClojure.cmd), [*nix](clojure/RunClojure.sh)
    * Интерактивный: `RunClojure`
    * С выражением: `RunClojure --eval "<выражение>"`
    * Скрипт: `RunClojure <файл скрипта>`
    * Справка: `RunClojure --help`
 * IDE
    * IntelliJ Idea: [плагин Cursive](https://cursive-ide.com/userguide/)
    * Eclipse: [плагин Counterclockwise](https://doc.ccw-ide.org/documentation.html)

[Скрипт со всеми примерами](clojure/examples.clj)

Лекция 1. Функции
 * [Введение](clojure/examples/1_1_intro.clj)
 * [Функции](clojure/examples/1_2_functions.clj)
 * [Списки](clojure/examples/1_3_lists.clj)
 * [Вектора](clojure/examples/1_4_vectors.clj)
 * [Функции высшего порядка](clojure/examples/1_5_functions-2.clj)

Лекция 2. Внешний мир
 * [Ввод-вывод](clojure/examples/2_1_io.clj)
 * [Разбор и гомоиконность](clojure/examples/2_2_read.clj)
 * [Порядки вычислений](clojure/examples/2_3_evaluation-orders.clj)
 * [Потоки](clojure/examples/2_4_streams.clj)
 * [Отображения и множества](clojure/examples/2_5_maps.clj)

Лекция 3. Объекты и вычисления
 * [Прототипное наследование](clojure/examples/3_1_js-objects.clj)
 * [Классы](clojure/examples/3_2_java-objects.clj)
 * [Изменяемое состояние](clojure/examples/3_3_mutable-state.clj)
 * [Числа Чёрча](clojure/examples/3_4_church.clj)

Лекция 4. Комбинаторные парсеры
 * [Базовые функции](clojure/examples/4_1_base.clj)
 * [Комбинаторы](clojure/examples/4_2_combinators.clj)
 * [JSON](clojure/examples/4_3_json.clj)
 * [Макросы](clojure/examples/4_4_macro.clj)

## Домашнее задание 6. Обработка ошибок на JavaScript

Модификации
 * *Базовая*
    * Код должен находиться в файле `objectExpression.js`.
    * [Исходный код тестов](javascript/jstest/prefix/PrefixParserTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *PrefixSumAvg*. Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `Sum` (`sum`) – сумма, `(sum 1 2 3)` равно 6;
        * `Avg` (`avg`) – арифметическое среднее, `(avg 1 2 3)` равно 2;
    * [Исходный код тестов](javascript/jstest/prefix/PrefixSumAvgTest.java)
 * *PostfixSumAvg*. Дополнительно реализовать поддержку:
    * выражений в постфиксной записи: `(2 3 +)` равно 5
    * унарных операций:
        * `Sum` (`sum`) – сумма, `(1 2 3 sum)` равно 6;
        * `Avg` (`avg`) – арифметическое среднее, `(1 2 3 avg)` равно 2;
    * [Исходный код тестов](javascript/jstest/prefix/PostfixSumAvgTest.java)
 * *PrefixSumexpSoftmax*. Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `Sumexp` (`sumexp`) – сумма экспонент, `(8 8 9)` примерно равно 14065;
        * `Softmax` (`softmax`) – softmax первого аргумента, `(softmax 1 2 3)` примерно равно 9;
    * [Исходный код тестов](javascript/jstest/prefix/PrefixSumexpSoftmaxTest.java)
 * *PostfixSumexpSoftmax*. Дополнительно реализовать поддержку:
    * выражений в постфиксной записи: `(2 3 +)` равно 5
    * унарных операций:
        * `Sumexp` (`sumexp`) – сумма экспонент, `(8 8 9 sumexp)` примерно равно 14065;
        * `Softmax` (`softmax`) – softmax первого аргумента, `(1 2 3 softmax)` примерно 9;
    * [Исходный код тестов](javascript/jstest/prefix/PostfixSumexpSoftmaxTest.java)
 * *PostfixSumsqLength*. Дополнительно реализовать поддержку:
    * выражений в постфиксной записи: `(2 3 +)` равно 5
    * унарных операций:
        * `Sumsq` (`sumsq`) – сумма квадратов, `(1 2 3 sumsq)` равно 14;
        * `Length` (`length`у) – длина вектора, `(3 4 length)` равно 5;
    * [Исходный код тестов](javascript/jstest/prefix/PostfixSumsqLengthTest.java)


## Домашнее задание 5. Объектные выражения на JavaScript

Модификации
 * *Базовая*
    * Код должен находиться в файле `objectExpression.js`.
    * [Исходный код тестов](javascript/jstest/object/ObjectExpressionTest.java)
        * Запускать c аргументом `easy`, `hard` или `bonus`.
 * *ArcTan*. Дополнительно реализовать поддержку:
    * функций:
        * `ArcTan` (`atan`) – арктангенс, `1256 atan` примерно равно 1.57;
        * `ArcTan2` (`atan2`) – арктангенс, `841 540 atan2` примерно равно 1;
    * [Исходный код тестов](javascript/jstest/object/ObjectArcTanTest.java)
 * *MinMax*. Дополнительно реализовать поддержку:
    * функций:
        * `Min3` (`min3`) – минимум из трех аргументов, `1 2 3 min` равно 1;
        * `Max5` (`max5`) – максимум из пяти аргументов, `1 2 3 4 5 max` равно 5;
    * [Исходный код тестов](javascript/jstest/object/ObjectMinMaxTest.java)
 * *SinhCosh*. Дополнительно реализовать поддержку:
    * унарных функций:
        * `Sinh` (`sinh`) – гиперболический синус, `3 sinh` немного больше 10;
        * `Cosh` (`cosh`) – гиперболический косинус, `3 cosh` немного меньше 10;
    * [Исходный код тестов](javascript/jstest/object/ObjectSinhCoshTest.java)


## Исходный код к лекциям по JavaScript

[Скрипт с примерами](javascript/examples.js)

Запуск примеров
 * [В браузере](javascript/RunJS.html)
 * Из консоли
    * [на Java](javascript/RunJS.java): [RunJS.cmd](javascript/RunJS.cmd), [RunJS.sh](javascript/RunJS.sh)
    * [на node.js](javascript/RunJS.node.js): `node RunJS.node.js`

Лекция 1. Типы и функции
 * [Типы](javascript/examples/1_1_types.js)
 * [Функции](javascript/examples/1_2_functions.js)
 * [Функции высшего порядка](javascript/examples/1_3_functions-hi.js).
   Обратите внимание на реализацию функции `mCurry`.

Лекция 2. Объекты и методы
 * [Объекты](javascript/examples/2_1_objects.js)
 * [Замыкания](javascript/examples/2_2_closures.js)
 * [Модули](javascript/examples/2_3_modules.js)
 * [Пример: стеки](javascript/examples/2_4_stacks.js)

Лекция 3. Другие возможности
 * [Обработка ошибок](javascript/examples/3_1_errors.js)
 * [Чего нет в JS](javascript/examples/3_2_no.js)
 * [Стандартная библиотека](javascript/examples/3_3_builtins.js)
 * [Работа со свойствами](javascript/examples/3_4_properties.js)
 * [JS 6+](javascript/examples/3_5_js6.js)

## Домашнее задание 4. Функциональные выражения на JavaScript

Модификации
 * *Базовая*
    * Код должен находиться в файле `functionalExpression.js`.
    * [Исходный код тестов](javascript/jstest/functional/FunctionalExpressionTest.java)
        * Запускать c аргументом `hard` или `easy`;
 * *PieAvgMed*. Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * констант:
        * `pi` – π;
        * `e` – основание натурального логарифма;
    * операций:
        * `avg5` – арифметическое среднее пяти аргументов, `1 2 3 4 5 avg5` равно 7.5;
        * `med3` – медиана трех аргументов, `1 2 -10 med3` равно 1.
    * [Исходный код тестов](javascript/jstest/functional/FunctionalPieAvgMedTest.java)
        * Запускать c аргументом `hard`
 * *Variables*. Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * [Исходный код тестов](javascript/jstest/functional/FunctionalVariablesTest.java)
        * Запускать c аргументом `easy`
 * *OneIffAbs*. Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * констант:
        * `one` – 1;
        * `two` – 2;
    * операций:
        * `abs` – абсолютное значение, `-2 abs` равно 2;
        * `iff` – условный выбор:
            если первый аргумент неотрицательный,
            вернуть второй аргумент,
            иначе вернуть первый третий аргумент.
            * `iff one two 3` равно 2
            * `iff -1 -2 -3` равно -3
            * `iff 0 one two` равно 1;
    * [Исходный код тестов](javascript/jstest/functional/FunctionalOneIffAbsTest.java)
        * Запускать c аргументом `hard`
 * *IffAbs*. Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * операций:
        * `abs` – абсолютное значение, `-2 abs` равно 2;
        * `iff` – условный выбор:
            если первый аргумент неотрицательный,
            вернуть второй аргумент,
            иначе вернуть первый третий аргумент:
            * `iff 1 2 3` равно 2
            * `iff -1 -2 -3` равно -3
            * `iff 0 1 2` равно 1;
    * [Исходный код тестов](javascript/jstest/functional/FunctionalIffAbsTest.java)
        * Запускать c аргументом `hard`
 * *OneTwo*. Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * констант:
        * `one` – 1;
        * `two` – 2;
    * [Исходный код тестов](javascript/jstest/functional/FunctionalOneTwoTest.java)
        * Запускать c аргументом `easy`


Запуск тестов
 * Для запуска тестов используется [GraalVM](https://www.graalvm.org/)
 * Для запуска тестов можно использовать скрипты [TestJS.cmd](javascript/TestJS.cmd) и [TestJS.sh](javascript/TestJS.sh)
    * Репозиторий должен быть скачан целиком.
    * Скрипты должны находиться в каталоге `javascript` (их нельзя перемещать, но можно вызывать из других каталогов).
 * Для самостоятельно запуска из консоли необходимо использовать командную строку вида:
    `java -ea -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI --module-path=<js>/graal --upgrade-module-path=<js>/graal/compiler.jar --class-path <js> jstest.functional.FunctionalExpressionTest {hard|easy}`, где
    * `-ea` – включение проверок времени исполнения;
    * `-XX:+UnlockExperimentalVMOptions` и `-XX:+EnableJVMCI` – опции необходимые для запуска Graal;
    * `--module-path=<js>/graal` путь к модулям Graal (здесь и далее `<js>` путь к каталогу `javascript` этого репозитория);
    * `--upgrade-module-path=<js>/graal/compiler.jar` путь к JIT-компилятору Graal;
    * `--class-path <js>` путь к откомпилированным тестам;
    * {`hard`|`easy`} указание тестируемой модификации.
 * При запуске из IDE, обычно не требуется указывать `--class-path`, так как он формируется автоматически.
   Остальные опции все равно необходимо указать.
 * Troubleshooting
    * `Error occurred during initialization of boot layer java.lang.module.FindException: Module org.graalvm.truffle not found, required by jdk.internal.vm.compiler` – неверно указан `--module-path`;
    * `ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider com.oracle.truffle.js.scriptengine.GraalJSEngineFactory could not be instantiated` – неверно указан `--upgrade-module-path` или не указана опция `-XX:+EnableJVMCI`;
    * `Graal.js not found` – неверно указаны `--module-path` и `--upgrade-module-path`
    * `Error: Could not find or load main class jstest.functional.FunctionalExpressionTest` – неверно указан `--class-path`;
    * `Error: Could not find or load main class <other class>` – неверно указано полное имя класса теста;
    * `Exception in thread "main" java.lang.AssertionError: You should enable assertions by running 'java -ea jstest.functional.FunctionalExpressionTest'` – не указана опция `-ea`;
    * `Error: VM option 'EnableJVMCI' is experimental and must be enabled via -XX:+UnlockExperimentalVMOptions.` – не указана опция `-XX:+UnlockExperimentalVMOptions`;
    * `First argument should be one of: "easy", "hard", found: XXX` – неверно указана сложность;
    * `Exception in thread "main" jstest.EngineException: Script 'functionalExpression.js' not found` – в текущем каталоге отсутствует решение (`functionalExpression.js`)


## Домашнее задание 3. Вычисление в различных типах

Модификации
 * *Базовая*
    * Класс `GenericTabulator` должен реализовывать интерфейс
      [Tabulator](java/expression/generic/Tabulator.java) и
      сроить трехмерную таблицу значений заданного выражения.
        * `mode` – режим вычислений:
           * `i` – вычисления в `int` с проверкой на переполнение;
           * `d` – вычисления в `double` без проверки на переполнение;
           * `bi` – вычисления в `BigInteger`.
        * `expression` – выражение, для которого надо построить таблицу;
        * `x1`, `x2` – минимальное и максимальное значения переменной `x` (включительно)
        * `y1`, `y2`, `z1`, `z2` – аналогично для `y` и `z`.
        * Результат: элемент `result[i][j][k]` должен содержать
          значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`.
          Если значение не определено (например, по причине переполнения),
          то соответствующий элемент должен быть равен `null`.
    * [Исходный код тестов](java/expression/generic/GenericTest.java)
 * *AsmUfb*
    * Дополнительно реализовать унарные операции:
        * `abs` – модуль числа, `abs -5` равно 5;
        * `square` – возведение в квадрат, `square 5` равно 25.
    * Дополнительно реализовать бинарную операцию (максимальный приоритет):
        * `mod` – взятие по модулю, приоритет как у умножения (`1 + 5 mod 3` равно `1 + (5 mod 3)` равно `3`).
    * Дополнительно реализовать поддержку режимов:
        * `u` – вычисления в `int` без проверки на переполнение;
        * `f` – вычисления в `float` без проверки на переполнение;
        * `b` – вычисления в `byte` без проверки на переполнение.
    * [Исходный код тестов](java/expression/generic/GenericAsmUfbTest.java)
 * *Ls*
    * Дополнительно реализовать поддержку режимов:
        * `l` – вычисления в `long` без проверки на переполнение;
        * `s` – вычисления в `short` без проверки на переполнение.
    * [Исходный код тестов](java/expression/generic/GenericLsTest.java)
 * *Ufb*
    * Дополнительно реализовать поддержку режимов:
        * `u` – вычисления в `int` без проверки на переполнение;
        * `f` – вычисления в `float` без проверки на переполнение;
        * `b` – вычисления в `byte` без проверки на переполнение.
    * [Исходный код тестов](java/expression/generic/GenericUfbTest.java)


## Домашнее задание 2. Markdown to HTML

Модификации
 * *Базовая*
    * [Исходный код тестов](java/md2html/Md2HtmlTest.java)
    * [Откомпилированные тесты](artifacts/md2html/Md2HtmlTest.jar)
 * *Link*
    * Добавьте поддержку ```[ссылок с _выделением_](https://kgeorgiy.info)```:
        ```&lt;a href='https://kgeorgiy.info'>ссылок с &lt;em>выделением&lt;/em>&lt;/a>```
    * [Исходный код тестов](java/md2html/Md2HtmlLinkTest.java)
    * [Откомпилированные тесты](artifacts/md2html/Md2HtmlLinkTest.jar)
 * *Underline*
    * Добавьте поддержку `++подчеркивания++`: `<u>подчеркивания</u>`
    * [Исходный код тестов](java/md2html/Md2HtmlUnderlineTest.java)
    * [Откомпилированные тесты](artifacts/md2html/Md2HtmlUnderlineTest.jar)
 * *Image*
    * Добавьте поддержку ```![картинок](http://www.ifmo.ru/images/menu/small/p10.jpg)```:
        ```&lt;img alt='картинок' src='http://www.ifmo.ru/images/menu/small/p10.jpg'&gt;```
    * [Исходный код тестов](java/md2html/Md2HtmlImageTest.java)
    * [Откомпилированные тесты](artifacts/md2html/Md2HtmlImageTest.jar)
 * *Mark*
    * Добавьте поддержку `~выделения цветом~`: `<mark>выделения цветом</mark>`
    * [Исходный код тестов](java/md2html/Md2HtmlMarkTest.java)
    * [Откомпилированные тесты](artifacts/md2html/Md2HtmlMarkTest.jar)
 * *All*
    * Добавьте поддержку всех вышеперечисленных модификаций
    * [Исходный код тестов](java/md2html/Md2HtmlAllTest.java)
    * [Откомпилированные тесты](artifacts/md2html/Md2HtmlAllTest.jar)


## Домашнее задание 1. Обработка ошибок

Модификации
 * *Базовая*
    * Класс `ExpressionParser` должен реализовывать интерфейс
        [Parser](java/expression/exceptions/Parser.java)
    * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`,
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс
        [TripleExpression](java/expression/TripleExpression.java)
    * Нельзя использовать типы `long` и `double`
    * Нельзя использовать методы классов `Math` и `StrictMath`
    * [Исходный код тестов](java/expression/exceptions/ExceptionsTest.java)
 * *HighLow*
    * Дополнительно реализовать унарные операции:
        * `high` – значение, у которого оставлен только самый старший
          установленный бит `high -4` равно `Integer.MIN_VALUE`;
        * `low` – значение, у которого оставлен только самый младший
          установленный бит `low 18` равно `2`.
    * [Исходный код тестов](java/expression/exceptions/ExceptionsHighLowTest.java)
