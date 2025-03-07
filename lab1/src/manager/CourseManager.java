package manager;


import java.util.ArrayList;
import java.util.List;

import entity.Course;

import entity.Teacher;
import entity.TeachingClass;

public class CourseManager {
    private static List<Course> courses = new ArrayList<>();
    private static List<TeachingClass> teachingClasses=new ArrayList<>();
//新增课程
    public static void addCourse(Course course) {
        courses.add(course);
    }

//新增教学班
    public static void addTeachingClass(String classId, Course course, Teacher teacher, String semester) {
        TeachingClass teachingClass = new TeachingClass(classId, course, teacher, semester);
        teachingClasses.add(teachingClass);
    }

//获取所有存在的课程
    public static List<Course> getAllCourses() {
        return courses;
    }

//获取所有存在的教学班
public static List<TeachingClass> getAllTeachingClasses() {
    return teachingClasses;
}

//给课程增加任课老师
    public static void addTeacherToCourse(String courseId, Teacher teacher) {
        for (Course course : courses) {
            if (courseId.equals(course.getCourseId())) {
                course.addTeacher(teacher);
                return; // 找到课程后立即返回
                }
            }
        }



        
    
        // 根据教师ID查找教学班
        public static List<TeachingClass> getTeachingClassesByTeacherId(String teacherId) {
            List<TeachingClass> result = new ArrayList<>();
            for (TeachingClass tc : teachingClasses) {
                if (tc.getTeacher().getTeacherId().equals(teacherId)) {
                    result.add(tc);
                }
            }
            return result;
        }

    

}