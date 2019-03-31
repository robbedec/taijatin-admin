package domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FormulaFormulaDaysPK implements Serializable {
    @Column(name = "FormulaId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int formulaId;
    @Column(name = "FormulaDayId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int formulaDayId;

    public int getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(int formulaId) {
        this.formulaId = formulaId;
    }

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
