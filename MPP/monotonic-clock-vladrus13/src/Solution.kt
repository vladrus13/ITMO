import kotlin.math.absoluteValue

/**
 * В теле класса решения разрешено использовать только переменные делегированные в класс RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author Vladislav Kuznetsov
 */
class Solution : MonotonicClock {
    private var c1 by RegularInt(0)
    private var c2 by RegularInt(0)
    private var c3 by RegularInt(0)

    private var l1 by RegularInt(0)
    private var l2 by RegularInt(0)

    override fun write(time: Time) {
        // left-to-right
        c1 = time.d1
        c2 = time.d2
        c3 = time.d3

        // write right-to-left
        l2 = time.d2
        l1 = time.d1
    }

    override fun read(): Time {
        // read left-to-right
        val l1V = l1
        val l2V = l2
        // read right-to-left
        val c3V = c3
        val c2V = c2
        val c1V = c1
        // he-he-he
        return Time(c1V, if (c1V == l1V) c2V else 0, if (c1V == l1V && c2V == l2V) c3V else 0)
    }
}