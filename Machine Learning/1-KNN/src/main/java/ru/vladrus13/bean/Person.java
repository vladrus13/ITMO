package ru.vladrus13.bean;

import java.util.*;
import java.util.function.ToIntFunction;

/**
 * Person class
 */
@SuppressWarnings("FieldCanBeLocal")
public class Person {
    /**
     * Age
     */
    private double age;
    /**
     * Workclass
     */
    private double workclass;
    /**
     * Education number
     */
    private double educationNum;
    /**
     * Education type
     */
    private double education;
    /**
     * Relationship
     */
    private double relationship;
    /**
     * Sex
     */
    private double sex;
    /**
     * Capital gain on start
     */
    private double capitalGain;
    /**
     * Hours her week work
     */
    private double hoursPerWeek;
    /**
     * Result (more or less than 50K)
     */
    private int result;

    /**
     * To integer
     */
    final ToIntFunction<String> toInteger = Integer::parseInt;

    /**
     * Education converter
     */
    private final HashMap<String, Integer> getEducation = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Preschool", 2),
            new AbstractMap.SimpleEntry<>("1st-4th", 3),
            new AbstractMap.SimpleEntry<>("5th-6th", 5),
            new AbstractMap.SimpleEntry<>("7th-8th", 7),
            new AbstractMap.SimpleEntry<>("9th", 9),
            new AbstractMap.SimpleEntry<>("10th", 10),
            new AbstractMap.SimpleEntry<>("11th", 11),
            new AbstractMap.SimpleEntry<>("12th", 12),
            new AbstractMap.SimpleEntry<>("Assoc-acdm", 13),
            new AbstractMap.SimpleEntry<>("Assoc-voc", 14),
            new AbstractMap.SimpleEntry<>("Bachelors", 15),
            new AbstractMap.SimpleEntry<>("Doctorate", 16),
            new AbstractMap.SimpleEntry<>("HS-grad", 17),
            new AbstractMap.SimpleEntry<>("Masters", 18),
            new AbstractMap.SimpleEntry<>("Prof-school", 19),
            new AbstractMap.SimpleEntry<>("Some-college", 20),
            new AbstractMap.SimpleEntry<>("?", -1)
    ));

    /**
     * Relationship converter
     */
    private final HashMap<String, Integer> getRelationship = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Husband", 4),
            new AbstractMap.SimpleEntry<>("Not-in-family", 8),
            new AbstractMap.SimpleEntry<>("Other-relative", 5),
            new AbstractMap.SimpleEntry<>("Own-child", 1),
            new AbstractMap.SimpleEntry<>("Unmarried", 3),
            new AbstractMap.SimpleEntry<>("Wife", 6),
            new AbstractMap.SimpleEntry<>("?", -1)
    ));

    /**
     * Sex converter
     */
    private final HashMap<String, Integer> getSex = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Male", 0),
            new AbstractMap.SimpleEntry<>("Female", 1),
            new AbstractMap.SimpleEntry<>("?", -1)
    ));

    /**
     * Result converter
     */
    private final HashMap<String, Integer> getResult = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("<=50K", 0),
            new AbstractMap.SimpleEntry<>(">50K", 1),
            new AbstractMap.SimpleEntry<>("?", -1)
    ));

    /**
     * Workclass converter
     */
    private final HashMap<String, Integer> getWorkclass = new HashMap<>(Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Federal-gov", 10),
            new AbstractMap.SimpleEntry<>("Local-gov", 9),
            new AbstractMap.SimpleEntry<>("Never-worked", 1),
            new AbstractMap.SimpleEntry<>("Private", 8),
            new AbstractMap.SimpleEntry<>("Self-emp-inc", 2),
            new AbstractMap.SimpleEntry<>("Self-emp-not-inc", 3),
            new AbstractMap.SimpleEntry<>("State-gov", 6),
            new AbstractMap.SimpleEntry<>("Without-pay", 4),
            new AbstractMap.SimpleEntry<>("?", -1)
    ));

    /**
     * Constructor for class
     *
     * @param line line form base .csv
     */
    public Person(String[] line) {
        age = toInteger.applyAsInt(line[0].trim());
        String workclassString = line[1].trim();
        String educationString = line[3].trim();
        educationNum = toInteger.applyAsInt(line[4].trim());
        String relationshipString = line[7].trim();
        String sexString = line[9].trim();
        capitalGain = toInteger.applyAsInt(line[10].trim());
        hoursPerWeek = toInteger.applyAsInt(line[12].trim());
        String resultString = line[14].trim();
        if (!getWorkclass.containsKey(workclassString)) {
            throw new IllegalArgumentException("Can't find workclass: " + educationString);
        }
        workclass = getWorkclass.get(workclassString);
        if (!getEducation.containsKey(educationString)) {
            throw new IllegalArgumentException("Can't find education: " + educationString);
        }
        education = getEducation.get(educationString);
        if (!getRelationship.containsKey(relationshipString)) {
            throw new IllegalArgumentException("Can't find relationship: " + relationshipString);
        }
        relationship = getRelationship.get(relationshipString);
        if (!getSex.containsKey(sexString)) {
            throw new IllegalArgumentException("Can't find sex: " + sexString);
        }
        sex = getSex.get(sexString);
        if (!getResult.containsKey(resultString)) {
            throw new IllegalArgumentException("Can't find result: " + resultString);
        }
        result = getResult.get(resultString);
        Random random = new Random();
        if (education == -1) {
            education = random.nextInt(17) + 3;
        }
        if (relationship == -1) {
            relationship = random.nextInt(6) + 1;
        }
        if (sex == -1) {
            sex = random.nextInt(2) + 1;
        }
        if (result == -1) {
            result = random.nextInt(2);
        }
        if (workclass == -1) {
            workclass = random.nextInt(10) + 1;
        }
        age /= 100;
        workclass /= 10;
        educationNum /= 20;
        education /= 20;
        relationship /= 8;
        capitalGain /= 100000;
        hoursPerWeek /= 100;
        result = random.nextInt(4) == 2 ? 2 : result;
    }

    /**
     * Constructor class
     *
     * @param age          age
     * @param workclass    workclass
     * @param educationNum education number
     * @param education    education type
     * @param relationship relationship
     * @param sex          sex
     * @param capitalGain  capital gain on stert
     * @param hoursPerWeek hours per week work
     * @param result       result
     */
    public Person(double age, double workclass, double educationNum, double education, double relationship, double sex, double capitalGain, double hoursPerWeek, int result) {
        this.age = age;
        this.workclass = workclass;
        this.educationNum = educationNum;
        this.education = education;
        this.relationship = relationship;
        this.sex = sex;
        this.capitalGain = capitalGain;
        this.hoursPerWeek = hoursPerWeek;
        this.result = result;
    }

    /**
     * @return copy of current person
     */
    public Person copy() {
        return new Person(age, workclass, educationNum, education, relationship, sex, capitalGain, hoursPerWeek, result);
    }

    /**
     * Get classes
     *
     * @return classes
     */
    public ArrayList<Double> getPosition() {
        return new ArrayList<>(List.of(age, workclass, educationNum, education, relationship, sex, capitalGain, hoursPerWeek));
    }

    /**
     * Get result
     *
     * @return result
     */
    public int getResult() {
        return result;
    }

    /**
     * Setter for result
     *
     * @param result result
     */
    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                workclass == person.workclass &&
                educationNum == person.educationNum &&
                education == person.education &&
                relationship == person.relationship &&
                sex == person.sex &&
                capitalGain == person.capitalGain &&
                hoursPerWeek == person.hoursPerWeek &&
                result == person.result;
    }
}
