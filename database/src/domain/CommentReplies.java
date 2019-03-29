package domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CommentReplies {
    private int commentReplyId;
    private String replyText;

    @Id
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
        CommentReplies that = (CommentReplies) o;
        return commentReplyId == that.commentReplyId &&
                Objects.equals(replyText, that.replyText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentReplyId, replyText);
    }
}
