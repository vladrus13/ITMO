# hw04

Ну тут будет очень полезная информация. Вероятно, она даже поможет понять решение. Если нельзя на русском - можно воспользоваться Google Translate (сюда переписывать не стал)). 

Билд:

stack build --pedantic --test --bench --haddock --no-run-tests --no-run-benchmarks

Запуск (только для задания Comonad-19):

stack run

Тесты:

stack test

Бенчмарки:

stack bench (не будет генерирования html)

Нахождение: src/"CLASS"

Тесты: test/"CLASS"

Бенчмарки: measurement/"CLASS", если таковой есть. Рядом лежит shell-файл, который запускает запись в html файл (не нашел в интернете, как его генерить из stack)

Зачем так сложно, почему не просто положить все файлы в одну папку?
Потому что это будет неудобно при просмотре этих же файлов, создатель не был уверен, что в итоге у него не выйдет 100 файлов

Задание 1. Геометрия (CLASS = Geometry).

Есть два метода взятия корня - итерационная формула Герона и встроенная хаскель.
Первая на маленьких числах дает лучший результат (файл MonteCarloBenchRandom0.html)
Вторая на больших числах дает худший результат (файл MonteCarlo).

Задание 2. Интегрируемся (CLASS = MonteCarlo).

Есть класс Expr, который управляет выражениями и их вычислениями. В качестве равномерно распределенной функции выбрана функция, которая делает равные расстояния между точками (но это по прежнему Монте-Карло, а не только метод трапеций). Может странно выглядить связка "функция"-"выполнить функцию" для разных методов Монте Карло и тестов, но там лучше, когда их будет много

Задание 3. Хеш-таблица (CLASS = HashTable).

Задание 4-5. HalyavaScript (Class = HalyavaScript)

Задание 6-7. FileSystem Lens (Class = FileSystem)

Задание 8. Comonad-19 (Class = Comonad)