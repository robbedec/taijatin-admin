package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Formula_FormulaDays", schema = "dbo", catalog = "G10_ProjectDotNet")
@IdClass(FormulaFormulaDaysPK.class)
public class FormulaFormulaDay {
    @Id
    private int formulaId;
    @Id
    private int formulaDayId;

    @ManyToOne
    //@JoinColumn(name = "FormulaId")
    private Formula formulasByFormulaId;

    @ManyToOne
    //@JoinColumn(name = "FormulaDayId")
    private FormulaDay formulaDayByFormulaDayId;

    @Column(name = "FormulaId")
    public int getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(int formulaId) {
        this.formulaId = formulaId;
    }

    @Column(name = "FormulaDayId")
    public int getFormulaDayId() {
        return formulaDayId;
    }

    public void setFormulaDayId(int formulaDayId) {
        this.formulaDayId = formulaDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormulaFormulaDay that = (FormulaFormulaDay) o;
        return formulaId == that.formulaId &&
                formulaDayId == that.formulaDayId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formulaId, formulaDayId);
    }

    public Formula getFormulasByFormulaId() {
        return formulasByFormulaId;
    }

    public void setFormulasByFormulaId(Formula formulasByFormulaId) {
        this.formulasByFormulaId = formulasByFormulaId;
    }

    public FormulaDay getFormulaDayByFormulaDayId() {
        return formulaDayByFormulaDayId;
    }

    public void setFormulaDayByFormulaDayId(FormulaDay formulaDayByFormulaDayId) {
        this.formulaDayByFormulaDayId = formulaDayByFormulaDayId;
    }
}
