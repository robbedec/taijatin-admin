package domain;

import javax.ejb.Local;
import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "FormulaDay")
@NamedQueries({
        @NamedQuery(name = "FormulaDay.findByDay", query = "select f from FormulaDay f where f.day = :day")
})
public class FormulaDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formulaDayId;
    private int day;
    private LocalTime startTime;
    private LocalTime endTime;

    @Column(name = "FormulaDayId")
    public int getFormulaDayId() {
        return formulaDayId;
    }

    public void setFormulaDayId(int formulaDayId) {
        this.formulaDayId = formulaDayId;
    }

    @Column(name = "Day")
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Basic
    @Column(name = "StartTime")
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "EndTime")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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
