package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "CourseModules")
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseModuleId;
    private String name;
    private String url;
    private String imageUrl;
    private String imageAlt;
    private String text;

    @OneToMany(mappedBy = "courseModulesByCourseModuleId")
    private Collection<Comment> commentsByCourseModuleId;

    @OneToMany(mappedBy = "courseModulesByCourseModuleId")
    private Collection<CourseModuleViewer> courseModuleViewersByCourseModuleId;

    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course coursesByCourseId;

    @Id
    @Column(name = "CourseModuleId")
    public int getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(int courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "ImageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "ImageAlt")
    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    @Basic
    @Column(name = "Text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModule that = (CourseModule) o;
        return courseModuleId == that.courseModuleId &&
                Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(imageAlt, that.imageAlt) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseModuleId, name, url, imageUrl, imageAlt, text);
    }


    public Collection<Comment> getCommentsByCourseModuleId() {
        return commentsByCourseModuleId;
    }

    public void setCommentsByCourseModuleId(Collection<Comment> commentsByCourseModuleId) {
        this.commentsByCourseModuleId = commentsByCourseModuleId;
    }

    public Collection<CourseModuleViewer> getCourseModuleViewersByCourseModuleId() {
        return courseModuleViewersByCourseModuleId;
    }

    public void setCourseModuleViewersByCourseModuleId(Collection<CourseModuleViewer> courseModuleViewersByCourseModuleId) {
        this.courseModuleViewersByCourseModuleId = courseModuleViewersByCourseModuleId;
    }

    public Course getCoursesByCourseId() {
        return coursesByCourseId;
    }

    public void setCoursesByCourseId(Course coursesByCourseId) {
        this.coursesByCourseId = coursesByCourseId;
    }
}
