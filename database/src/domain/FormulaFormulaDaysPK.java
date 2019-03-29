package domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FormulaFormulaDaysPK implements Serializable {
    private int formulaId;
    private int formulaDayId;

    @Column(name = "FormulaId")
    @Id
    public int getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(int formulaId) {
        this.formulaId = formulaId;
    }

    @Column(name = "FormulaDayId")
    @Id
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
        FormulaFormulaDaysPK that = (FormulaFormulaDaysPK) o;
        return formulaId == that.formulaId &&
                formulaDayId == that.formulaDayId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formulaId, formulaDayId);
    }
}
