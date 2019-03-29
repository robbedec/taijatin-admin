package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(AttendancesPK.class)
public class Attendances {
    private int sessionId;
    private int memberId;

    @Id
    @Column(name = "SessionId")
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Id
    @Column(name = "MemberId")
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendances that = (Attendances) o;
        return sessionId == that.sessionId &&
                memberId == that.memberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, memberId);
    }
}
