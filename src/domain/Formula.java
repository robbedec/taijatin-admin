package domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Formulas")
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formulaId;
    private String formulaName;

    @OneToMany(mappedBy = "formulasByFormulaId")
    private Collection<FormulaFormulaDay> formulaFormulaDaysByFormulaId;

    @ManyToOne
    @JoinColumn(name = "TeacherId", referencedColumnName = "Id")
    private User usersByTeacherId;

    @OneToMany(mappedBy = "formulasByFormulaId")
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

    public Collection<FormulaFormulaDay> getFormulaFormulaDaysByFormulaId() {
        return formulaFormulaDaysByFormulaId;
    }

    public void setFormulaFormulaDaysByFormulaId(Collection<FormulaFormulaDay> formulaFormulaDaysByFormulaId) {
        this.formulaFormulaDaysByFormulaId = formulaFormulaDaysByFormulaId;
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
