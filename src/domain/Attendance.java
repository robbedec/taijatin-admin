package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Attendances")
@IdClass(AttendancesPK.class)
public class Attendance {
    @Id
    private int sessionId;
    @Id
    private int memberId;

    @ManyToOne
    //@JoinColumn(name = "SessionId")
    private Session sessionsBySessionId;

    @ManyToOne
    //@JoinColumn(name = "MemberId")
    private User usersByMemberId;

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
        Attendance that = (Attendance) o;
        return sessionId == that.sessionId &&
                memberId == that.memberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, memberId);
    }
/*
    public Session getSessionsBySessionId() {
        return sessionsBySessionId;
    }

    public void setSessionsBySessionId(Session sessionsBySessionId) {
        this.sessionsBySessionId = sessionsBySessionId;
    }

    public User getUsersByMemberId() {
        return usersByMemberId;
    }

    public void setUsersByMemberId(User usersByMemberId) {
        this.usersByMemberId = usersByMemberId;
    }*/
}
