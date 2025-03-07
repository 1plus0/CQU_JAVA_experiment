package entity;


import java.util.*;

public class Student {
    private String studentId;
    private String name;
    private String gender;
    private List<TeachingClass> teachingClasses;
    private Map<Course, Score> courseScores;

    public Student(String studentId, String name, String gender) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.teachingClasses=new ArrayList<>();
        this.courseScores = new HashMap<>();
    }
//加入学生选择的课程
    public void selectCourse(Course course) {
        this.courseScores.put(course, new Score());
    }
//加入学生选择的教学班
    public void addTeachingClass(TeachingClass teachingClass){
        this.teachingClasses.add(teachingClass);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public Map<Course, Score> getCoursesAndScores() {
        return courseScores;
    }

    public List<TeachingClass> getTeachingClasses() {
        return teachingClasses;
    }
    
    public double getTotalScore() {
        return courseScores.values().stream().mapToDouble(Score::getTotalScore).sum();
    }

    public double getAverageScore() {
        double[] averageScore=new double[1];
        averageScore[0]=0;
        courseScores.forEach((course, score) -> {
            averageScore[0]+=score.getTotalScore();
        });
        // 计算平均分
        double average = averageScore[0] / courseScores.size();
        return average;
    }

    @Override
    public String toString() {
        return "Student{" + "studentId='" + studentId + '\'' + ", name='" + name + '\'' + ", gender='" + gender + '\'' + '}';
    }
}