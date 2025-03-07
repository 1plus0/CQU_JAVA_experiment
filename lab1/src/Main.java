
import java.util.*;


import entity.Course;
import entity.Score;
import entity.Student;
import entity.Teacher;
import entity.TeachingClass;
import manager.CourseManager;

public class Main {
    private static List<Student> students = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("===== 学生成绩管理系统 =====");
            System.out.println("1. 生成初始化数据");
            System.out.println("2. 学生进行选课");
            System.out.println("3. 获得成绩");
            System.out.println("4. 按学号查询学生成绩");
            System.out.println("5. 按姓名查询学生成绩");
            System.out.println("6. 按学号排序学生");
            System.out.println("7. 按课程总成绩排序学生");
            System.out.println("8. 统计成绩分布");
            System.out.println("9. 按课程平均分排序学生");
            System.out.println("10. 按教学班显示学生成绩");
            System.out.println("11. 查询教师授课班级");
            System.out.println("12. 退出系统");
            System.out.print("请选择操作：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    generateInitialData();
                    break;
                case 2:
                    StudentChooseClass();
                    break;
                case 3:
                    generateScores();
                    break;
                case 4:
                    searchStudentById(scanner);
                    break;
                case 5:
                    searchStudentByName(scanner);
                    break;
                case 6:
                    sortStudentsById();
                    break;
                case 7:
                    sortStudentsByTotalScore();
                    break;
                case 8:
                    getScoreDistribution();
                    break;
                case 9:
                    sortStudentsByAverageScore(students);
                    break;
                case 10:
                    displayByTeachingClass(scanner);
                    break;
                case 11:
                    searchTCByTeacherId(scanner);
                    break;
                case 12:
                    exit = true;
                    break;
                default:
                    System.out.println("无效选项，请重新选择！");
            }
        }
    }

//查询教师授课班级
    private static void searchTCByTeacherId(Scanner scanner) {
        System.out.print("请输入教师编号：");
        String teacherId = scanner.nextLine();
        List<TeachingClass> classes = CourseManager.getTeachingClassesByTeacherId(teacherId);
        for (TeachingClass tc : classes) {
            System.out.println(tc.getTeachingClassId()+" "+tc.getCourse().getCourseName()+" "+tc.getTeacher().getName()+" "+tc.getSemester());
        }
    }

//数据初始化
    private static void generateInitialData() {
        // 初始化教师
        teachers.add(new Teacher("T01", "王老师"));
        teachers.add(new Teacher("T02", "李老师"));
        teachers.add(new Teacher("T03", "张老师"));
        teachers.add(new Teacher("T04", "刘老师"));
        teachers.add(new Teacher("T05", "陈老师"));
        teachers.add(new Teacher("T06", "黄老师"));
        teachers.add(new Teacher("T07", "周老师"));
        teachers.add(new Teacher("T08", "姚老师"));

        //System.out.println(teachers);
        // 初始化课程
        CourseManager.addCourse(new Course("C01", "数学"));
        CourseManager.addCourse(new Course("C02", "英语"));
        CourseManager.addCourse(new Course("C03", "计算机"));
        CourseManager.addCourse(new Course("C04", "政治"));
        

        // 每门课添加两位老师
        CourseManager.addTeacherToCourse("C01", teachers.get(0)); // 数学 - 王老师
        CourseManager.addTeacherToCourse("C01", teachers.get(1)); // 数学 - 李老师
        CourseManager.addTeacherToCourse("C02", teachers.get(2)); // 英语 - 张老师
        CourseManager.addTeacherToCourse("C02", teachers.get(3)); // 英语 - 刘老师
        CourseManager.addTeacherToCourse("C03", teachers.get(4)); // 计算机 - 陈老师
        CourseManager.addTeacherToCourse("C03", teachers.get(5)); // 计算机 - 黄老师
        CourseManager.addTeacherToCourse("C04", teachers.get(6)); // 政治 - 周老师
        CourseManager.addTeacherToCourse("C04", teachers.get(7)); // 政治 - 姚老师
        //System.out.println(CourseManager.getAllCourses());
        

        // 初始化学生
        for (int i = 1; i <= 100; i++) {
            students.add(new Student(String.format("S%03d", i), "学生" + i, i % 2 == 0 ? "男" : "女"));
        }

        // 初始化教学班
        CourseManager.addTeachingClass("TC01", CourseManager.getAllCourses().get(0), teachers.get(0), "2024春");
        CourseManager.addTeachingClass("TC02", CourseManager.getAllCourses().get(0), teachers.get(1), "2024春");
        CourseManager.addTeachingClass("TC03", CourseManager.getAllCourses().get(1), teachers.get(2), "2024春");
        CourseManager.addTeachingClass("TC04", CourseManager.getAllCourses().get(1), teachers.get(3), "2024春");
        CourseManager.addTeachingClass("TC05", CourseManager.getAllCourses().get(2), teachers.get(4), "2024春");
        CourseManager.addTeachingClass("TC06", CourseManager.getAllCourses().get(2), teachers.get(5), "2024春");
        CourseManager.addTeachingClass("TC07", CourseManager.getAllCourses().get(3), teachers.get(6), "2024春");
        CourseManager.addTeachingClass("TC08", CourseManager.getAllCourses().get(3), teachers.get(7), "2024春");

        System.out.println("初始化数据生成成功！");
    }
        
//选课
    private static void StudentChooseClass(){
        

        Random random = new Random();
        Map<Student, Set<Course>> studentCoursesMap = new HashMap<>();
    
        // 为每个班级分配学生
        for (TeachingClass tc : CourseManager.getAllTeachingClasses()) {
            Set<Student> selectedStudents = new HashSet<>();
    
            // 随机分配至少20名学生到每个班级
            while (selectedStudents.size() < 20) {
                Student student = students.get(random.nextInt(students.size()));
                // 检查学生是否已选该课程的班级
                if (studentCoursesMap.computeIfAbsent(student, k -> new HashSet<>()).add(tc.getCourse())) {
                    selectedStudents.add(student);
                }
            }
    
            for (Student s : selectedStudents) {
                tc.addStudent(s);
                s.selectCourse(tc.getCourse());
                s.addTeachingClass(tc);
            }
        }
    
        // 确保每个学生至少选择了三门课
        for (Student s : students) {
            while (s.getTeachingClasses().size() < 3) {
                TeachingClass newTc = null;
                boolean found = false;
    
                // 尝试选择一个新的教学班
                for (TeachingClass tc : CourseManager.getAllTeachingClasses()) {
                    final Course newCourse = tc.getCourse(); // 将newCourse定义为final
                    // 如果学生还未选择该课程，则选择该班级
                    if (!s.getTeachingClasses().stream().anyMatch(existingTc -> existingTc.getCourse().equals(newCourse))) {
                        newTc = tc;
                        found = true;
                        break; // 找到一个合适的班级，退出循环
                    }
                }
    
                // 如果没有找到新的班级，退出循环以防无限循环
                if (!found) {
                    break;
                }
    
                // 添加新班级
                s.selectCourse(newTc.getCourse());
                s.addTeachingClass(newTc);
                newTc.addStudent(s); // 把学生加到新班级
            }
        }
    
        System.out.println("学生选课成功！");

    
    }
   
//生成成绩
    private static void generateScores() {
        //假设教师从教学班登入成绩
        for (TeachingClass tc : CourseManager.getAllTeachingClasses()) {
            tc.generateScores();
        }
        System.out.println("成绩获取成功！");
    }

//按学号查找学生
    private static void searchStudentById(Scanner scanner) {
        System.out.print("请输入学号：");
        String studentId = scanner.nextLine();
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                System.out.println(student);
                student.getCoursesAndScores().forEach((course, score) -> {
                    System.out.println(course.getCourseName() + ": " + score);
                });
                return;
            }
        }
        System.out.println("未找到该学号的学生！");
    }

//按姓名查找学生
    private static void searchStudentByName(Scanner scanner) {
        System.out.print("请输入姓名：");
        String name = scanner.nextLine();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                System.out.println(student);
                student.getCoursesAndScores().forEach((course, score) -> {
                    System.out.println(course.getCourseName() + ": " + score);
                });
                return;
            }
        }
        System.out.println("未找到该姓名的学生！");
    }

//按学号排列学生
    private static void sortStudentsById() {
        students.sort(Comparator.comparing(Student::getStudentId));
        int []i=new int[1];//解决lambda表达式访问外部变量变量不可变
        i[0]=0;
        students.forEach(student -> {
            System.out.print((i[0]+1)+":"+student+"\n");
            student.getCoursesAndScores().forEach((course, score) -> {
                System.out.println(course.getCourseName() + ": " + score
                );
            });
        i[0]++;
        });
    }

//按各科成绩排序学生
    private static void sortStudentsByTotalScore() {
        
    List<Course> allCourses = CourseManager.getAllCourses(); // 获取所有课程
    System.out.println(allCourses);
    for (Course courses : allCourses) {
        // 根据当前课程的成绩对学生进行排序
        students.sort(Comparator.comparing(student -> {
            Score score = ((Student) student).getCoursesAndScores().get(courses);
            return (score != null) ? score.getTotalScore() : 0; // 如果课程成绩不存在则返回0
        }).reversed());

        // 输出课程名称
        System.out.println("课程名称: " + courses.getCourseName());
        // 输出每个学生及其对应课程的成绩
        int []i=new int[1];//解决lambda表达式访问外部变量变量不可变
        i[0]=0;
        students.forEach(student -> {
            Score score = student.getCoursesAndScores().get(courses);
            double courseScore = (score != null) ? score.getTotalScore() : 0; // 如果课程成绩不存在则返回0
            if(courseScore!=0)
            {
                System.out.println((i[0]+1)+":"+student + ", 该课程成绩: " + courseScore);
            }
            i[0]++;
        });
        System.out.println(); // 每门课程之间留一行空行
    }
    }
    
//统计各科成绩分布
    private static void getScoreDistribution() {
        List<Course> allCourses = CourseManager.getAllCourses(); // 获取所有课程
        for (Course courses : allCourses) {
            // 输出课程名称
            System.out.println("课程名称: " + courses.getCourseName());
            // 
            Map<String, Integer> distribution = new HashMap<>();
        for (Student student : students) {
            if(student.getCoursesAndScores().get(courses)!=null){
                double totalScore = student.getCoursesAndScores().get(courses).getTotalScore();
            String grade = "";
            if (totalScore >= 90) grade = "90-100";
            else if (totalScore >= 80) grade = "80-89";
            else if (totalScore >= 70) grade = "70-79";
            else if (totalScore >= 60) grade = "60-69";
            else grade = "0-59";
            distribution.put(grade, distribution.getOrDefault(grade, 0) + 1);
            }
            
        }

        distribution.forEach((k, v) -> System.out.println(k + ": " + v + "人"));
            
            System.out.println(); // 每门课程之间留一行空行
        }
        
    }

//按平均分排序学生
    public static void sortStudentsByAverageScore(List<Student> students) {
        // 计算每个学生的平均分并排序
        students.sort(Comparator.comparing(Student::getAverageScore).reversed());
        int []i=new int[1];//解决lambda表达式访问外部变量变量不可变
        i[0]=0;
        students.forEach(student -> {
            if(student.getAverageScore()<=100){
            System.out.print((i[0]+1)+":"+student+"\n");
            System.out.printf("平均成绩: %.2f\n", student.getAverageScore());
        i[0]++;}
        });
    }

//按教学班显示成绩
    public static void displayByTeachingClass(Scanner scanner){
        System.out.print("请输入教学班号：");
        String teachingClassId = scanner.nextLine();
        for (TeachingClass teachingClass : CourseManager.getAllTeachingClasses()) {
            if (teachingClass.getTeachingClassId().equals(teachingClassId)) {
                System.out.print(teachingClass.getTeachingClassId()+" "+teachingClass.getCourse().getCourseName()+" "+teachingClass.getTeacher().getName()+"\n");
                //获取教学班学生信息
                List<Student> s=new ArrayList<>();
                s=teachingClass.getStudents();
                s.sort(Comparator.comparing(Student::getStudentId));
                int []i=new int[1];//解决lambda表达式访问外部变量变量不可变
                i[0]=0;
                s.forEach(student -> {
                    System.out.print((i[0]+1)+":"+student+"\n");
                    System.out.print(student.getCoursesAndScores().get(teachingClass.getCourse())+"\n");
                i[0]++;
                });
                return;
            }
        }
        
    }
}

