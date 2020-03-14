package info.kgeorgiy.java.advanced.student;

import info.kgeorgiy.java.advanced.base.BaseTest;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Tests for easy version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-student">Student</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentQueryTest extends BaseTest implements StudentQuery {
    protected static final Random RANDOM = new Random(8458243580324875285L);
    private static final List<String> FIRST_NAMES = List.of("Азат", "Александр", "Александра", "Алексей", "Анастасия", "Анатолий", "Андрей", "Анна", "Антон", "Арсений", "Артем", "Аслан", "Ася", "Благой", "Борис", "Валерия", "Варвара", "Виктор", "Виталий", "Владислав", "Всеволод", "Вячеслав", "Георгий", "Глеб", "Григорий", "Даниил", "Данил", "Дарья", "Демид", "Демьян", "Денис", "Дмитрий", "Евгений", "Егор", "Елизавета", "Захар", "Зоя", "Ибрагим", "Иван", "Идрис", "Илона", "Ильдар", "Илья", "Ирина", "Кирилл", "Константин", "Любовь", "Мадияр", "Максим", "Марина", "Марк", "Михаил", "Мухаммаджон", "Наталья", "Никита", "Николай", "Олег", "Олеся", "Петр", "Равиль", "Рамазан", "Роман", "Руслан", "Семён", "Сергей", "Станислав", "Тарас", "Темирлан", "Тимофей", "Элина", "Эрика", "Юлия", "Юрий", "Ян", "Янис", "Ярослав"
    );
    private static final List<String> LAST_NAMES = List.of("Агеев", "Акимов", "Аксенов", "Ашихмин", "Бадикова", "Баженов", "Барляев", "Батырова", "Бобров", "Богер", "Боже", "Бочарников", "Бричев", "Бусыгин", "Бусюк", "Варфоломеев", "Власова", "Вознов", "Воробьев", "Галиев", "Глазов", "Гнатюк", "Голиков", "Градобоев", "Гречишкина", "Грибанов", "Гунькин", "Даниловский", "Деляну", "Джиблави", "Димитров", "Должанский", "Дорофеев", "Дроздов", "Дунидин", "Елфимов", "Жарский", "Желенский", "Желобкович", "Жуверцев", "Жуков", "Загретдинов", "Иванова", "Ившин", "Исаев", "Исаева", "Каримов", "Карлина", "Карлукова", "Кирпичев", "Клёпов", "Ковешников", "Коновалов", "Коробков", "Королев", "Кочергин", "Краснощеков", "Крухмалев", "Крылов", "Кубанцев", "Кузнецов", "Кузьминов", "Кулиев", "Курбатов", "Кухтикова", "Лаптев", "Ларионов", "Лебедев", "Левашов", "Лемешкова", "Луконин", "Любавина", "Максимов", "Мальцев", "Маслов", "Можевитин", "Мозжевилов", "Мошников", "Наумов", "Неверов", "Николаева", "Нургазин", "Осадчий", "Ота", "Отт", "Пак", "Панкратов", "Паночевных", "Первеев", "Потапов", "Пренас", "Рахматуллин", "Рашо", "Резниченко", "Романенко", "Роскошный", "Сакулина", "Селиверстов", "Сердюков", "Симонович", "Скаженик", "Скрыль", "Смирнов", "Стародубцев", "Сухарев", "Тананыкин", "Тарасов", "Теблоев", "Тептин", "Тер-Матевосян", "Тимербулатов", "Тимофеев", "Тихонов", "Ткач", "Ткаченко", "Толепбек", "Третьякова", "Трибрат", "Усс", "Фадеев", "Федоров", "Фролова", "Хакимов", "Хлытин", "Цуциев", "Чеботарева", "Чежегов", "Шаблов", "Шалдин", "Шапошников", "Шарепо", "Шефер", "Шишкин", "Шлапко", "Яндаров", "Ярлыченко"
            );
    private static final List<String> GROUPS = List.of("M3234", "M3235", "M3236", "M3237", "M3238", "M3239");

    public static final int COUNT = 300;
    protected static final List<Student> STUDENTS = Stream.generate(() -> new Student(
                    RANDOM.nextInt(),
                    random(FIRST_NAMES),
                    random(LAST_NAMES),
                    random(GROUPS)
            ))
            .limit(COUNT)
            .collect(Collectors.toUnmodifiableList());

    private static final List<List<Student>> INPUTS = IntStream.range(0, STUDENTS.size())
            .mapToObj(size -> {
                final List<Student> students = new ArrayList<>(STUDENTS);
                Collections.shuffle(students, RANDOM);
                return new ArrayList<>(students.subList(0, size));
            })
            .collect(Collectors.toList());

    private static <T> T random(final List<T> values) {
        return values.get(RANDOM.nextInt(values.size()));
    }

    private final StudentQuery db = createCUT();

    @Test
    public void test01_testGetFirstNames() {
        test(this::getFirstNames, db::getFirstNames);
    }

    @Test
    public void test02_testGetLastNames() {
        test(this::getLastNames, db::getLastNames);
    }

    @Test
    public void test03_testGetGroups() {
        test(this::getGroups, db::getGroups);
    }

    @Test
    public void test04_testGetFullNames() {
        test(this::getFullNames, db::getFullNames);
    }

    @Test
    public void test05_testGetDistinctFirstNames() {
        test(this::getDistinctFirstNames, db::getDistinctFirstNames);
    }

    @Test
    public void test06_testGetMinStudentFirstName() {
        test(this::getMinStudentFirstName, db::getMinStudentFirstName);
    }

    @Test
    public void test07_testSortStudentsById() {
        test(this::sortStudentsById, db::sortStudentsById);
    }

    @Test
    public void test08_testSortStudentsByName() {
        test(this::sortStudentsByName, db::sortStudentsByName);
    }

    @Test
    public void test09_testFindStudentsByFirstName() {
        testBi(this::findStudentsByFirstName, db::findStudentsByFirstName, FIRST_NAMES);
    }

    @Test
    public void test10_testFindStudentsByLastName() {
        testBi(this::findStudentsByLastName, db::findStudentsByLastName, FIRST_NAMES);
    }

    @Test
    public void test11_testFindStudentsByGroup() {
        testBi(this::findStudentsByGroup, db::findStudentsByGroup, GROUPS);
    }

    @Test
    public void test12_findStudentNamesByGroup() {
        testBi(this::findStudentNamesByGroupList, db::findStudentNamesByGroupList, GROUPS);
    }

    public static <R> void test(final Function<List<Student>, R> reference, final Function<List<Student>, R> tested) {
        for (final List<Student> input : INPUTS) {
            final R actual = tested.apply(input);
            Assert.assertEquals("For " + input, reference.apply(input), actual);
        }
    }

    protected static <T, U> void testBi(
            final BiFunction<List<Student>, U, T> reference,
            final BiFunction<List<Student>, U, T> tested,
            final List<U> values
    ) {
        for (final U value : values.subList(0, Math.min(values.size(), 10))) {
            System.err.println("\tTesting " + value);
            try {
                test(input -> reference.apply(input, value), input -> tested.apply(input, value));
            } catch (final AssertionError e) {
                throw new AssertionError("Value " + value + ": " + e.getMessage(), e);
            }
        }
    }

    // Reference implementation follows
    // This implementation is intentionally poorly-written and contains a lot of copy-and-paste

    @Override
    public List<String> getFirstNames(final List<Student> students) {
        final List<String> result = new ArrayList<>();
        for (final Student student : students) {
            result.add(student.getFirstName());
        }
        return result;
    }

    @Override
    public List<String> getLastNames(final List<Student> students) {
        final List<String> result = new ArrayList<>();
        for (final Student student : students) {
            result.add(student.getLastName());
        }
        return result;
    }

    @Override
    public List<String> getGroups(final List<Student> students) {
        final List<String> result = new ArrayList<>();
        for (final Student student : students) {
            result.add(student.getGroup());
        }
        return result;
    }

    @Override
    public List<String> getFullNames(final List<Student> students) {
        final List<String> result = new ArrayList<>();
        for (final Student student : students) {
            result.add(student.getFirstName() + " " + student.getLastName());
        }
        return result;
    }

    @Override
    public Set<String> getDistinctFirstNames(final List<Student> students) {
        return new TreeSet<>(getFirstNames(students));
    }

    @Override
    public String getMinStudentFirstName(final List<Student> students) {
        int minId = Integer.MAX_VALUE;
        String minName = "";
        for (final Student student : students) {
            if (minId > student.getId()) {
                minId = student.getId();
                minName = student.getFirstName();
            }
        }
        return minName;
    }

    @Override
    public List<Student> sortStudentsById(final Collection<Student> students) {
        final ArrayList<Student> sorted = new ArrayList<>(students);
        Collections.sort(sorted);
        return sorted;
    }

    private static final Comparator<Student> STUDENT_COMPARATOR = (a, b) -> {
        final int last = a.getLastName().compareTo(b.getLastName());
        if (last != 0) {
            return last;
        }
        final int first = a.getFirstName().compareTo(b.getFirstName());
        if (first != 0) {
            return first;
        }
        return Integer.compare(a.getId(), b.getId());
    };

    @Override
    public List<Student> sortStudentsByName(final Collection<Student> students) {
        final ArrayList<Student> sorted = new ArrayList<>(students);
        sorted.sort(STUDENT_COMPARATOR);
        return sorted;
    }

    @Override
    public List<Student> findStudentsByFirstName(final Collection<Student> students, final String firstName) {
        final ArrayList<Student> result = new ArrayList<>(students);
        result.removeIf(student -> !student.getFirstName().equals(firstName));
        result.sort(STUDENT_COMPARATOR);
        return result;
    }

    @Override
    public List<Student> findStudentsByLastName(final Collection<Student> students, final String lastName) {
        final ArrayList<Student> result = new ArrayList<>(students);
        result.removeIf(student -> !student.getLastName().equals(lastName));
        result.sort(STUDENT_COMPARATOR);
        return result;
    }

    @Override
    public List<Student> findStudentsByGroup(final Collection<Student> students, final String group) {
        final ArrayList<Student> result = new ArrayList<>(students);
        result.removeIf(student -> !student.getGroup().equals(group));
        result.sort(STUDENT_COMPARATOR);
        return result;
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(final Collection<Student> students, final String group) {
        final Map<String, String> result = new HashMap<>();
        for (final Student student : findStudentsByGroup(students, group)) {
            result.merge(student.getLastName(), student.getFirstName(), BinaryOperator.minBy(Comparable::compareTo));
        }
        return result;
    }
}
