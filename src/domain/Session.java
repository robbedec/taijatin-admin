package domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;
    private int day;
    private Date date;
    private String stateSerialized;

    @OneToMany(mappedBy = "sessionsBySessionId")
    private Collection<Attendance> attendancesBySessionId;

    @Id
    @Column(name = "SessionId")
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "Day")
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Basic
    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "StateSerialized")
    public String getStateSerialized() {
        return stateSerialized;
    }

    public void setStateSerialized(String stateSerialized) {
        this.stateSerialized = stateSerialized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session sessions = (Session) o;
        return sessionId == sessions.sessionId &&
                day == sessions.day &&
                Objects.equals(date, sessions.date) &&
                Objects.equals(stateSerialized, sessions.stateSerialized);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, day, date, stateSerialized);
    }

    public Collection<Attendance> getAttendancesBySessionId() {
        return attendancesBySessionId;
    }

    public void setAttendancesBySessionId(Collection<Attendance> attendancesBySessionId) {
        this.attendancesBySessionId = attendancesBySessionId;
    }
}
