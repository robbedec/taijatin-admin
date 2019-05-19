package domain;

import javafx.util.converter.LocalTimeStringConverter;

import javax.ejb.Local;
import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Formulas")
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formulaId;
    private String formulaName;

    public Formula(){}

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "Formula_FormulaDays",
            joinColumns = {@JoinColumn(name = "FormulaId")},
            inverseJoinColumns = {@JoinColumn(name = "FormulaDayId") })
    private Collection<FormulaDay> formulaDaysByFormulaId;

    @ManyToOne
    @JoinColumn(name = "TeacherId")
    private User usersByTeacherId;

    @OneToMany(mappedBy = "formulasByFormulaId", cascade = CascadeType.PERSIST)
    private Collection<User> usersByFormulaId;

    @Id
    @Column(name = "FormulaId")
    public int getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(int formulaId) {
        this.formulaId = formulaId;
    }

    @Basic
    @Column(name = "FormulaName")
    public String getFormulaName() {
        if(formulaName == null || formulaName == ""){
            return "";
        }
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formula formulas = (Formula) o;
        return formulaId == formulas.formulaId &&
                Objects.equals(formulaName, formulas.formulaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formulaId, formulaName);
    }

    public Collection<FormulaDay> getFormulaDaysByFormulaId() {
        return formulaDaysByFormulaId;
    }

    public void setFormulaFormulaDaysByFormulaId(Collection<FormulaDay> formulaDaysByFormulaId) {
        this.formulaDaysByFormulaId = formulaDaysByFormulaId;
    }

    public User getUsersByTeacherId() {
        return usersByTeacherId;
    }

    public void setUsersByTeacherId(User usersByTeacherId) {
        this.usersByTeacherId = usersByTeacherId;
    }

    public Collection<User> getUsersByFormulaId() {
        return usersByFormulaId;
    }

    public void setUsersByFormulaId(Collection<User> usersByFormulaId) {
        this.usersByFormulaId = usersByFormulaId;
    }

}
