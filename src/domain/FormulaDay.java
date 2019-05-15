package domain;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "FormulaDay")
public class FormulaDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formulaDayId;
    private int day;
    private Time startTime;
    private Time endTime;

    @Column(name = "FormulaDayId")
    public int getFormulaDayId() {
        return formulaDayId;
    }

    public void setFormulaDayId(int formulaDayId) {
        this.formulaDayId = formulaDayId;
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
    @Column(name = "StartTime")
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "EndTime")
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormulaDay that = (FormulaDay) o;
        return formulaDayId == that.formulaDayId &&
                day == that.day /*&&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formulaDayId, day/*, startTime, endTime*/);
    }
}
