package domain;

import javafx.collections.FXCollections;
import main.StartUpGUI;

import javax.persistence.*;
import java.util.ArrayList;
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
        if(formulaName == null || formulaName == ""){
            return "";
        }
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        addFormulaDays(formulaName);
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

    private void addFormulaDays(String formulaName){
        formulaFormulaDaysByFormulaId = new ArrayList<>();
        FormulaDay dinsdag = new FormulaDay();
        dinsdag.setDay(1);
        FormulaDay woensdag = new FormulaDay();
        woensdag.setDay(2);
        FormulaDay donderdag = new FormulaDay();
        donderdag.setDay(3);
        FormulaDay zaterdag = new FormulaDay();
        zaterdag.setDay(5);
        FormulaDay zondag = new FormulaDay();
        zondag.setDay(6);
        switch (formulaName){
            case "DI_DO":
                FormulaFormulaDay di_dodinsdag = new FormulaFormulaDay();
                di_dodinsdag.setFormulaDayByFormulaDayId(dinsdag);
                FormulaFormulaDay di_dodonderdag = new FormulaFormulaDay();
                di_dodonderdag.setFormulaDayByFormulaDayId(donderdag);
                formulaFormulaDaysByFormulaId.add(di_dodinsdag);
                formulaFormulaDaysByFormulaId.add(di_dodonderdag);
            case "DI_ZA":
                FormulaFormulaDay di_zadinsdag = new FormulaFormulaDay();
                di_zadinsdag.setFormulaDayByFormulaDayId(dinsdag);
                FormulaFormulaDay di_zazaterdag = new FormulaFormulaDay();
                di_zazaterdag.setFormulaDayByFormulaDayId(zaterdag);
                formulaFormulaDaysByFormulaId.add(di_zadinsdag);
                formulaFormulaDaysByFormulaId.add(di_zadinsdag);
            case "WO_ZA":
                FormulaFormulaDay wo_zawoensdag = new FormulaFormulaDay();
                wo_zawoensdag.setFormulaDayByFormulaDayId(woensdag);
                FormulaFormulaDay wo_zazaterdag = new FormulaFormulaDay();
                wo_zazaterdag.setFormulaDayByFormulaDayId(zaterdag);
                formulaFormulaDaysByFormulaId.add(wo_zawoensdag);
                formulaFormulaDaysByFormulaId.add(wo_zazaterdag);
            case "WO":
                FormulaFormulaDay wowoensdag = new FormulaFormulaDay();
                wowoensdag.setFormulaDayByFormulaDayId(woensdag);
                formulaFormulaDaysByFormulaId.add(wowoensdag);
            case "ZA":
                FormulaFormulaDay zazaterdag = new FormulaFormulaDay();
                zazaterdag.setFormulaDayByFormulaDayId(zaterdag);
                formulaFormulaDaysByFormulaId.add(zazaterdag);
            case "ZO":
                FormulaFormulaDay zozondag = new FormulaFormulaDay();
                zozondag.setFormulaDayByFormulaDayId(zondag);
                formulaFormulaDaysByFormulaId.add(zozondag);
            case "Geen":
                default:

        }
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
