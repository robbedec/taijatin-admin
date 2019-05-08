package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class FormulaDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formulaDayId;
    private int day;
    //private Object startTime;
    //private Object endTime;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "formulaDayByFormulaDayId")
    private Collection<FormulaFormulaDay> formulaFormulaDaysByFormulaDayId;

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
/*
    @Basic
    @Column(name = "StartTime")
    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "EndTime")
    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }
*/
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

    public Collection<FormulaFormulaDay> getFormulaFormulaDaysByFormulaDayId() {
        return formulaFormulaDaysByFormulaDayId;
    }

    public void setFormulaFormulaDaysByFormulaDayId(Collection<FormulaFormulaDay> formulaFormulaDaysByFormulaDayId) {
        this.formulaFormulaDaysByFormulaDayId = formulaFormulaDaysByFormulaDayId;
    }
}
