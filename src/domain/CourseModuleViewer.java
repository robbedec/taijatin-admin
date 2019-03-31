package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CourseModuleViewers")
public class CourseModuleViewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseModuleViewerId;

    @ManyToOne
    @JoinColumn(name = "CourseModuleId")
    private CourseModule courseModulesByCourseModuleId;

    @ManyToOne
    @JoinColumn(name = "MemberId")
    private User usersByMemberId;

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
        CourseModuleViewer that = (CourseModuleViewer) o;
        return courseModuleViewerId == that.courseModuleViewerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseModuleViewerId);
    }


    public CourseModule getCourseModulesByCourseModuleId() {
        return courseModulesByCourseModuleId;
    }

    public void setCourseModulesByCourseModuleId(CourseModule courseModulesByCourseModuleId) {
        this.courseModulesByCourseModuleId = courseModulesByCourseModuleId;
    }

    public User getUsersByMemberId() {
        return usersByMemberId;
    }

    public void setUsersByMemberId(User usersByMemberId) {
        this.usersByMemberId = usersByMemberId;
    }
}
