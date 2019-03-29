package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Formula_FormulaDays", schema = "dbo", catalog = "G10_ProjectDotNet")
@IdClass(FormulaFormulaDaysPK.class)
public class FormulaFormulaDays {
    private int formulaId;
    private int formulaDayId;

    @Id
    @Column(name = "FormulaId")
    public int getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(int formulaId) {
        this.formulaId = formulaId;
    }

    @Id
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
        FormulaFormulaDays that = (FormulaFormulaDays) o;
        return formulaId == that.formulaId &&
                formulaDayId == that.formulaDayId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formulaId, formulaDayId);
    }
}
