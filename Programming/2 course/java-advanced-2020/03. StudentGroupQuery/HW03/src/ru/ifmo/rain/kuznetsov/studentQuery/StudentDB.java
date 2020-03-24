package ru.ifmo.rain.kuznetsov.studentQuery;

import info.kgeorgiy.java.advanced.student.AdvancedStudentGroupQuery;
import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Collector;

public class StudentDB implements AdvancedStudentGroupQuery {

    private static final Comparator<Student> STUDENT_FULL_NAME_COMPARATOR =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getId);

    private <T> List<T> mapToList(Collection<Student> students, Function<Student, T> mapMaker) {
        return mapToCollection(students, mapMaker, ArrayList::new);
    }

    private <T, C extends Collection<T>> C mapToCollection(Collection<Student> students, Function<Student, T> mapMaker, Supplier<C> collector) {
        return students.stream().map(mapMaker).collect(Collectors.toCollection(collector));
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return mapToList(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return mapToList(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return mapToList(students, Student::getGroup);
    }

    private String getFullName(Student student) {
        return student.getFirstName() + " " + student.getLastName();
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return mapToList(students, this::getFullName);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return mapToCollection(students, Student::getFirstName, TreeSet::new);
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse("");
    }

    private List<Student> getSortedToList(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return getSortedToList(students, Comparator.comparingInt(Student::getId));
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return getSortedToList(students, STUDENT_FULL_NAME_COMPARATOR);
    }

    private List<Student> getFilterSortedListStudentsNames(Collection<Student> students, Predicate<Student> predicate) {
        return students.stream().filter(predicate).collect(Collectors.toList()).stream().sorted(STUDENT_FULL_NAME_COMPARATOR).collect(Collectors.toList());
    }

    private Predicate<Student> getPredicateByFunction(String name, Function<Student, String> func) {
        return s -> Objects.equals(func.apply(s), name);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return getFilterSortedListStudentsNames(students, getPredicateByFunction(name, Student::getFirstName));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return getFilterSortedListStudentsNames(students, getPredicateByFunction(name, Student::getLastName));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return getFilterSortedListStudentsNames(students, getPredicateByFunction(group, Student::getGroup));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(getPredicateByFunction(group, Student::getGroup))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }

    private Set<Map.Entry<String, List<Student>>> groupToEntrySet(Stream<Student> studentsStream, Supplier<Map<String, List<Student>>> supply) {
        return studentsStream
                .collect(Collectors.groupingBy(Student::getGroup, supply, Collectors.toList()))
                .entrySet();
    }

    private List<Group> sortToGroupList(Collection<Student> students, Comparator<Student> comparator) {
        return groupToEntrySet(students.stream().sorted(comparator), HashMap::new).stream()
                .map(gr -> new Group(gr.getKey(), gr.getValue()))
                .sorted(Comparator.comparing(Group::getName, String::compareTo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return sortToGroupList(students, STUDENT_FULL_NAME_COMPARATOR);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return sortToGroupList(students, Comparator.comparingInt(Student::getId));
    }

    private String getLargestGroupByComparator(Stream<Map.Entry<String, List<Student>>> groups,
                                               Comparator<List<Student>> comparator) {
        return groups.max(Map.Entry.<String, List<Student>>comparingByValue(comparator)
                .thenComparing(Map.Entry::getKey, (Collections.reverseOrder(String::compareTo)))).map(Map.Entry::getKey)
                .orElse("");
    }


    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getLargestGroupByComparator(groupToEntrySet(students.stream(), TreeMap::new).stream(),
                Comparator.comparingInt(List::size));
    }

    private <K, V> Stream<Map.Entry<K, V>> collectionToStream(
 	Collection<Student> students, Function<Student, K> func, Collector<Student, ?, V> collector) {
 	return students.stream().collect(Collectors.groupingBy(func, collector)).entrySet().stream();
    }

    private <V> String maxWithComparator(Stream<Map.Entry<String, V>> stream, Comparator<? super Map.Entry<String, V>> comp) {
 	return stream.max(comp).map(Map.Entry::getKey).orElse("");
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return maxWithComparator(collectionToStream(students, Student::getGroup,
 		Collectors.mapping(Student::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), Set::size))),
 		Map.Entry.<String, Integer>comparingByValue().
 	thenComparing(Map.Entry.<String, Integer>comparingByKey().reversed()));
    }

    @Override
    public String getMostPopularName(Collection<Student> students) {
        return students.stream().collect(Collectors.groupingBy(this::getFullName, Collectors.mapping(Student::getGroup, Collectors.toSet())))
                .entrySet().stream().max(Map.Entry.<String, Set<String>>comparingByValue(Comparator.comparingInt(Set::size)) // теперь здесь сет, и работает не за квадрат
                        .thenComparing(Map.Entry.comparingByKey(String::compareTo)))
                .map(Map.Entry::getKey).orElse("");
    }


    private <T> List<String> getOrderedStudentsNames(Collection<Student> students, int[] indices, Function<Student, String> func) {
        return Arrays.stream(indices).mapToObj(List.copyOf(students)::get).map(func).collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(Collection<Student> students, int[] indices) {
        return getOrderedStudentsNames(students, indices, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(Collection<Student> students, int[] indices) {
        return getOrderedStudentsNames(students, indices, Student::getLastName);
    }

    @Override
    public List<String> getGroups(Collection<Student> students, int[] indices) {
        return getOrderedStudentsNames(students, indices, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(Collection<Student> students, int[] indices) {
        return getOrderedStudentsNames(students, indices, this::getFullName);
    }
}
