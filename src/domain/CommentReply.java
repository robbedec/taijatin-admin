package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CommentReplies")
public class CommentReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentReplyId;
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "CommentId", referencedColumnName = "CommentId")
    private Comment commentsByCommentId;

    @ManyToOne
    @JoinColumn(name = "MemberId", referencedColumnName = "Id")
    private User usersByMemberId;

    @Column(name = "CommentReplyId")
    public int getCommentReplyId() {
        return commentReplyId;
    }

    public void setCommentReplyId(int commentReplyId) {
        this.commentReplyId = commentReplyId;
    }

    @Basic
    @Column(name = "ReplyText")
    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentReply that = (CommentReply) o;
        return commentReplyId == that.commentReplyId &&
                Objects.equals(replyText, that.replyText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentReplyId, replyText);
    }

    public Comment getCommentsByCommentId() {
        return commentsByCommentId;
    }

    public void setCommentsByCommentId(Comment commentsByCommentId) {
        this.commentsByCommentId = commentsByCommentId;
    }

    public User getUsersByMemberId() {
        return usersByMemberId;
    }

    public void setUsersByMemberId(User usersByMemberId) {
        this.usersByMemberId = usersByMemberId;
    }
}
