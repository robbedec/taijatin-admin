package domain;

import javafx.collections.FXCollections;
import main.StartUpGUI;

import javax.persistence.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Formulas")
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formulaId;
    private String formulaName;

    public Formula(){
    }

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "Formula_FormulaDays",
            joinColumns = {@JoinColumn(name = "FormulaDayId")},
            inverseJoinColumns = {@JoinColumn(name = "FormulaId") })
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
        formulaDaysByFormulaId = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        FormulaDay dinsdag = new FormulaDay();
        dinsdag.setDay(2);
        dinsdag.setStartTime(Time.valueOf("18:00:00"));
        dinsdag.setEndTime(Time.valueOf("20:00:00"));
        FormulaDay woensdag = new FormulaDay();
        woensdag.setDay(3);
        woensdag.setStartTime(Time.valueOf("14:00:00"));
        woensdag.setEndTime(Time.valueOf("15:30:00"));
        FormulaDay donderdag = new FormulaDay();
        donderdag.setDay(4);
        donderdag.setStartTime(Time.valueOf("18:00:00"));
        donderdag.setEndTime(Time.valueOf("20:00:00"));
        FormulaDay zaterdag = new FormulaDay();
        zaterdag.setDay(6);
        zaterdag.setStartTime(Time.valueOf("10:00:00"));
        zaterdag.setEndTime(Time.valueOf("11:30:00"));
        FormulaDay zondag = new FormulaDay();
        zondag.setDay(7);
        zondag.setStartTime(Time.valueOf("11:00:00"));
        zondag.setEndTime(Time.valueOf("12:30:00"));

        switch (formulaName){
            case "DI_DO":
                formulaDaysByFormulaId.add(dinsdag);
                formulaDaysByFormulaId.add(donderdag);
            case "DI_ZA":
                formulaDaysByFormulaId.add(dinsdag);
                formulaDaysByFormulaId.add(zaterdag);
            case "WO_ZA":
                formulaDaysByFormulaId.add(woensdag);
                formulaDaysByFormulaId.add(zaterdag);
            case "WO":
                formulaDaysByFormulaId.add(woensdag);
            case "ZA":
                formulaDaysByFormulaId.add(zaterdag);
            case "ZO":
                formulaDaysByFormulaId.add(zondag);
            case "Geen":
                default:

        }
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
