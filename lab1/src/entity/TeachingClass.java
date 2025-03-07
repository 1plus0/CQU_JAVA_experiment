package entity;

import java.util.*;

public class TeachingClass {
    private String classId;
    private Course course;
    private Teacher teacher;
    private String semester;
    private List<Student> students;

    public TeachingClass(String classId, Course course, Teacher teacher, String semester) {
        this.classId = classId;
        this.course = course;
        this.teacher = teacher;
        this.semester = semester;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    //从教学班中输入成绩
    public void generateScores() {
        for (Student student : students) {
            Score score = student.getCoursesAndScores().get(course);
            score.generateScores();
        }
    }

    
//获得教学班中的学生 //未使用
public List<Student> getStudents() {
    return students;
}
//

    @Override
    public String toString() {
        return "TeachingClass{" + "classId='" + classId + '\'' + ", course=" + course + ", teacher=" + teacher + ", semester='" + semester + '\'' + ", students=" + students + '}';
    }

    public String getTeachingClassId() {
        return classId;
    }

    public Teacher getTeacher(){
        return teacher;
    }
}
