package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseId")
    private int courseId;
    private int minGrade;

    @OneToMany(mappedBy = "coursesByCourseId")
    private Collection<CourseModule> courseModulesByCourseId;

    @Id
    @Column(name = "CourseId")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "MinGrade")
    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course courses = (Course) o;
        return courseId == courses.courseId &&
                minGrade == courses.minGrade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, minGrade);
    }

    public Collection<CourseModule> getCourseModulesByCourseId() {
        return courseModulesByCourseId;
    }

    public void setCourseModulesByCourseId(Collection<CourseModule> courseModulesByCourseId) {
        this.courseModulesByCourseId = courseModulesByCourseId;
    }
}
