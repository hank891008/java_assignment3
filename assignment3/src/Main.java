import java.util.*;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toSet;

public class Main {
    public enum City {TAIPEI, TAICHUNG, KAOHSIUNG, OTHER};
    public enum Gender {MALE, FEMALE};
    static class Person{
        private String FirstName;
        private String LastName;
        private String BirthDay;
        private City city;
        private Gender gender;
        Person(String FirstName, String LastName, City city, String BirthDay, Gender gender){
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.city = city;
            this.BirthDay = BirthDay;
            this.gender = gender;
        }
        public String getFirstName() {
            return FirstName;
        }
        public String getLastName() {
            return LastName;
        }
        public String getBirthDay() {
            return BirthDay;
        }
        public City getCity() {
            return city;
        }
        public Gender getGender() {
            return gender;
        }
    }
    static class ScoreSheet{
        private int Math;             //數學成績 0 ~ 100
        private int English;             // 英文成績 0 ~ 100
        private int Chinese;            // 中文成績 0 ~ 100
        private double Average;   //  3 科平均分數
        ScoreSheet(int Math, int English, int Chinese){
            this.Math = Math;
            this.English = English;
            this.Chinese = Chinese;
            Average = (Math + English + Chinese) / 3;
        }
        public int getMath() {
            return Math;
        }
        public int getChinese() {
            return Chinese;
        }
        public int getEnglish() {
            return English;
        }
        public double getAverage() {
            return Average;
        }
    }
    static class Student{
        private String Id;            //學號9位數字:  405261112
        private Person info;
        private ScoreSheet Scores;
        Student(String Id, String FirstName, String LastName, City city, String BirthDay, Gender gender, int Math, int English, int Chinese){
            this.Id = Id;
            this.info = new Person(FirstName, LastName, city, BirthDay, gender);
            this.Scores = new ScoreSheet(Math, English, Chinese);
        }
        public String getId() {
            return Id;
        }
        public Person getInfo() {
            return info;
        }
        public ScoreSheet getScores() {
            return Scores;
        }
        @Override
        public String toString() {
            return this.Id + " " + getInfo().getFirstName() + " " + getInfo().getLastName() + " " + getInfo().getCity() + " " + getInfo().getBirthDay()
                    + " " + getInfo().getGender() + " " + getScores().getMath() + " " + getScores().getEnglish() + " " + getScores().getChinese() + " " + getScores().getAverage();
        }
    }
    public static void main(String[] args){
        List<Student> student = Arrays.asList(
                new Student("408262201","Leonardo", "DiCaprio", City.TAICHUNG, "1974/11/11", Gender.MALE, 59, 100, 0),
                new Student("408262208", "hank", "hsu",City.TAIPEI, "2000/10/08",Gender.MALE, 35, 100, 100),
                new Student("408262202", "catherine", "tsai", City.TAIPEI, "2001/01/31", Gender.FEMALE, 100, 100, 100)
        );
        /*for(int i = 0; i < student.size(); i++){
            System.out.println(student.get(i));
        }*/
        //System.out.println(student.get(0).getId());

        System.out.println("----------------------Q1----------------------");
        student.stream()
                .sorted(comparing(Student::getId))
                .forEach(System.out::println);
        System.out.println("");
        student.stream()
                .sorted(comparing(s -> s.getInfo().getBirthDay()))
                .forEach(s -> System.out.println(s.getInfo().getFirstName() + " " + s.getInfo().getLastName()));

        System.out.println("----------------------Q2----------------------");
        Set<City> city_of_student = student.stream().map(s -> s.getInfo().getCity()).collect(toSet());
        city_of_student.forEach(System.out::println);

        System.out.println("----------------------Q3----------------------");
        Map<City, List<Student>> student_grouping_by_city =
                student.stream()
                .collect(Collectors.groupingBy(s -> s.getInfo().getCity()));
        System.out.println(student_grouping_by_city);

        System.out.println("----------------------Q4----------------------");
        Map<Boolean, List<Student>> student_partition_by_gender =
                student.stream().collect(Collectors.partitioningBy(s -> s.getInfo().getGender() == Gender.MALE));
        System.out.println(student_partition_by_gender);

        System.out.println("----------------------Q5----------------------");
        IntSummaryStatistics mathStatistics = student.stream().collect(summarizingInt(s->s.getScores().getMath()));
        IntSummaryStatistics englishStatistics = student.stream().collect(summarizingInt(s->s.getScores().getEnglish()));
        IntSummaryStatistics chineseStatistics = student.stream().collect(summarizingInt(s->s.getScores().getChinese()));
        System.out.println("math : average = " +mathStatistics.getAverage() + " maximum = " + mathStatistics.getMax() + " minimum = " + mathStatistics.getMin());
        System.out.println("english : average = " + englishStatistics.getAverage() + " maximum = " + englishStatistics.getMax() + " minimum = " + englishStatistics.getMin());
        System.out.println("chinese : average = " + chineseStatistics.getAverage() + " maximum = " + chineseStatistics.getMax() + " minimum = " + chineseStatistics.getMin());

        System.out.println("----------------------Q6----------------------");
        System.out.println("數學不及格：");
        student.stream().filter(s->s.getScores().getMath() < 60).forEach(s -> System.out.println(s.getId() + " " + s.getInfo().getFirstName() + " " + s.getInfo().getLastName()));
        System.out.println("英文不及格：");
        student.stream().filter(s->s.getScores().getEnglish() < 60).forEach(s -> System.out.println(s.getId() + " " + s.getInfo().getFirstName() + " " + s.getInfo().getLastName()));
        System.out.println("國文不及格：");
        student.stream().filter(s->s.getScores().getChinese() < 60).forEach(s -> System.out.println(s.getId() + " " + s.getInfo().getFirstName() + " " + s.getInfo().getLastName()));

        System.out.println("----------------------Q7----------------------");
        student.stream().sorted(comparing(s -> -(s.getScores().getAverage()))).forEach(s -> System.out.println(s.getInfo().getFirstName() + " " + s.getInfo().getLastName() + " " + s.getScores().getAverage()));

        System.out.println("----------------------Q8----------------------");
        Map<Boolean, List<Student>> student_partition_by_math =
                student.stream().collect(Collectors.partitioningBy(s -> s.getScores().getMath() > 60));
        System.out.println(student_partition_by_math);
        Map<Boolean, List<Student>> student_partition_by_english =
                student.stream().collect(Collectors.partitioningBy(s -> s.getScores().getEnglish() > 60));
        System.out.println(student_partition_by_english);
        Map<Boolean, List<Student>> student_partition_by_chinese =
                student.stream().collect(Collectors.partitioningBy(s -> s.getScores().getChinese() > 60));
        System.out.println(student_partition_by_chinese);
    }
}
