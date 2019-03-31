package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private String commentText;

    @OneToMany(mappedBy = "commentsByCommentId")
    private Collection<CommentReply> commentRepliesByCommentId;

    @ManyToOne
    @JoinColumn(name = "CourseModuleId", referencedColumnName = "CourseModuleId", nullable = false)
    private CourseModule courseModulesByCourseModuleId;

    @ManyToOne
    @JoinColumn(name = "MemberId", referencedColumnName = "Id", nullable = false)
    private User usersByMemberId;

    @Column(name = "CommentId")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "CommentText")
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comments = (Comment) o;
        return commentId == comments.commentId &&
                Objects.equals(commentText, comments.commentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText);
    }

    public Collection<CommentReply> getCommentRepliesByCommentId() {
        return commentRepliesByCommentId;
    }

    public void setCommentRepliesByCommentId(Collection<CommentReply> commentRepliesByCommentId) {
        this.commentRepliesByCommentId = commentRepliesByCommentId;
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
