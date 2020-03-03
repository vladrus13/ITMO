package ru.ifmo.rain.kuznetsov.studentQuery;

import info.kgeorgiy.java.advanced.student.AdvancedStudentGroupQuery;
import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements AdvancedStudentGroupQuery {

    // return sorted list with students
    private List<Group> getSpecialSortedList(Stream<Map.Entry<String, List<Student>>> groupsStream, UnaryOperator<List<Student>> sort) {
        return groupsStream.map(elem -> new Group(elem.getKey(), sort.apply(elem.getValue())))
                .collect(Collectors.toList());
    }

    private String fullName(Student student) {
        return student.getFirstName() + ' ' + student.getLastName();
    }

    private Stream<Map.Entry<String, List<Student>>> getStream(Collection<Student> students, Supplier<Map<String, List<Student>>> mapSupplier) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, mapSupplier, Collectors.toList()))
                .entrySet().stream();
    }

    private String getBiggestGroupFilter(Stream<Map.Entry<String, List<Student>>> groupsStream, ToIntFunction<List<Student>> filter) {
        return groupsStream
                .max(Comparator.comparingInt((Map.Entry<String, List<Student>> group) -> filter.applyAsInt(group.getValue()))
                        .thenComparing(Map.Entry::getKey, Collections.reverseOrder(String::compareTo)))
                .map(Map.Entry::getKey).orElse("");
    }

    private <T, C extends Collection<T>> C StudentsCollectionMapped(Collection<Student> students, Function<Student, T> mapper, Supplier<C> collection) {
        return students.stream().map(mapper).collect(Collectors.toCollection(collection));
    }

    // return collection students in list
    private <T> List<T> StudentsSpecialMappedList(Collection<Student> students, Function<Student, T> mapper) {
        return StudentsCollectionMapped(students, mapper, ArrayList::new);
    }

    private List<Student> sortedStudents(Stream<Student> studentStream, Comparator<Student> cmp) {
        return studentStream.sorted(cmp).collect(Collectors.toList());
    }

    private List<Student> simplySortedStudents(Collection<Student> students, Comparator<Student> cmp) {
        return sortedStudents(students.stream(), cmp);
    }

    private Stream<Student> filteredSpecialStudentsStream(Collection<Student> students, Predicate<Student> predicate) {
        return students.stream().filter(predicate);
    }

    // comparator for sort on LastName, after by FirstName, after
    private final Comparator<Student> dataComp = Comparator.comparing(Student::getLastName, String::compareTo)
            .thenComparing(Student::getFirstName, String::compareTo)
            .thenComparingInt(Student::getId);

    private List<Student> filterAndSortByName(Collection<Student> students, Predicate<Student> predicate) {
        return sortedStudents(filteredSpecialStudentsStream(students, predicate), dataComp);
    }

    private Predicate<Student> getGroupPredicate(String group) {
        return student -> group.equals(student.getGroup());
    }

    // returns student groups, groups and students within a group are ordered by name
    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getSpecialSortedList(getStream(students, TreeMap::new), this::sortStudentsByName);
    }

    // Returns student groups, groups are ordered by name, and students within a group are ordered by id
    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getSpecialSortedList(getStream(students, TreeMap::new), this::sortStudentsById);
    }

    // returns name of the group containing maximum number of students
    // If there are more than one largest group, the one with smallest name is returned
    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getBiggestGroupFilter(getStream(students, HashMap::new), List::size);
    }

    // returns name of the group containing maximum number of students with distinct first names
    // if there are more than one largest group, the one with smallest name is returned
    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getBiggestGroupFilter(getStream(students, HashMap::new), studentsList -> getDistinctFirstNames(studentsList).size());
    }

    // returns students first names
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return StudentsSpecialMappedList(students, Student::getFirstName);
    }

    // returns students last names
    @Override
    public List<String> getLastNames(List<Student> students) {
        return StudentsSpecialMappedList(students, Student::getLastName);
    }

    // returns students groups
    @Override
    public List<String> getGroups(List<Student> students) {
        return StudentsSpecialMappedList(students, Student::getGroup);
    }

    // returns full student names
    @Override
    public List<String> getFullNames(List<Student> students) {
        return StudentsSpecialMappedList(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    // returns distinct students first names in alphabetical order
    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return StudentsCollectionMapped(students, Student::getFirstName, TreeSet::new);
    }

    // returns name of the student with minimal id.
    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse("");
    }

    // returns list of students sorted by id
    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return simplySortedStudents(students, Student::compareTo);
    }

    // returns list of students sorted by lastName, after by firstName, after by id
    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return simplySortedStudents(students, dataComp);
    }

    // returns list of students having specified first name and ordered
    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return filterAndSortByName(students, student -> name.equals(student.getFirstName()));
    }

    // returns list of students having specified last name and ordered
    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return filterAndSortByName(students, student -> name.equals(student.getLastName()));
    }

    // returns list of students having specified groups and ordered
    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return filterAndSortByName(students, getGroupPredicate(group));
    }

    // Returns map of group's student last names mapped to minimal first name
    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return filteredSpecialStudentsStream(students, getGroupPredicate(group))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }

    @Override
    public String getMostPopularName(Collection<Student> students) {
        return students.stream().collect(Collectors.groupingBy(this::fullName, Collectors.mapping(Student::getGroup, Collectors.toSet())))
                .entrySet().stream().max(Map.Entry.<String, Set<String>>comparingByValue(Comparator.comparingInt(Set::size))
                        .thenComparing(Map.Entry.comparingByKey(String::compareTo)))
                .map(Map.Entry::getKey).orElse("");
    }

    private List<String> getObj(List<String> names, int[] indices) {
        return Arrays.stream(indices).mapToObj(names::get).collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(Collection<Student> students, int[] indices) {
        return getObj(StudentsSpecialMappedList(students, Student::getFirstName), indices);
    }

    @Override
    public List<String> getLastNames(Collection<Student> students, int[] indices) {
        return getObj(StudentsSpecialMappedList(students, Student::getLastName), indices);
    }

    @Override
    public List<String> getGroups(Collection<Student> students, int[] indices) {
        return getObj(StudentsSpecialMappedList(students, Student::getGroup), indices);
    }

    @Override
    public List<String> getFullNames(Collection<Student> students, int[] indices) {
        return getObj(StudentsSpecialMappedList(students, this::fullName), indices);
    }
}
