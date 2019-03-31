package domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AttendancesPK implements Serializable {

    private int sessionId;
    private int memberId;

    @Column(name = "SessionId")
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

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
        AttendancesPK that = (AttendancesPK) o;
        return sessionId == that.sessionId &&
                memberId == that.memberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, memberId);
    }
}
