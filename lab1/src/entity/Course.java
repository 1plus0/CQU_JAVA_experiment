package entity;

import java.util.*;

public class Course {
    private String courseId;
    private String courseName;
    private List<Teacher> teachers;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teachers = new ArrayList<>();
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return "Course{" + "courseId='" + courseId + '\'' + ", courseName='" + courseName + '\'' + '}';
    }

    public String getCourseId() {
        return courseId;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return this.courseId.equals(other.courseId);
    }

    @Override
    public int hashCode() {
        return courseId.hashCode();
    }

    
}
