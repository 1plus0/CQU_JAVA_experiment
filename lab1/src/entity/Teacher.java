package entity;

public class Teacher {
    private String teacherId;
    private String name;

    public Teacher(String teacherId, String name) {
        this.teacherId = teacherId;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    @Override
    public String toString() {
        return "Teacher{" + "teacherId='" + teacherId + '\'' + ", name='" + name + '\'' + '}';
    }
}
