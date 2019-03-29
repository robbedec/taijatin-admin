package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CourseModuleViewers {
    private int courseModuleViewerId;

    @Id
    @Column(name = "CourseModuleViewerId")
    public int getCourseModuleViewerId() {
        return courseModuleViewerId;
    }

    public void setCourseModuleViewerId(int courseModuleViewerId) {
        this.courseModuleViewerId = courseModuleViewerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModuleViewers that = (CourseModuleViewers) o;
        return courseModuleViewerId == that.courseModuleViewerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseModuleViewerId);
    }
}
