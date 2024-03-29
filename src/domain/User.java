package domain;

import repository.UserDTO;

import javax.persistence.*;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "Users")
@NamedQueries({
        @NamedQuery(name = "Users.findAllButNoMembers", query = "SELECT b FROM User b WHERE b.isNoMember != true or b.isNoMember is null"),
        @NamedQuery(name = "Users.findAll", query = "select b from User b"),
        @NamedQuery(name = "Users.findByMail", query = "SELECT b FROM User b WHERE b.email = :email"),
        @NamedQuery(name = "Users.findByUserName", query = "SELECT b FROM User b WHERE b.userName = :userName"),
        @NamedQuery(name = "Users.findAllAdmins", query = "select b from User b where b.type = 'Beheerder'"),
        @NamedQuery(name = "Users.findAllTeachers", query = "select b from User b where b.type = 'Lesgever'")
})
public class User implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String email;
    private String firstname;
    private String lastname;
    private Integer gender;
    private String nationalInsuranceNumber;
    private Date registrationdate;
    private String bornIn;
    private Date birthday;
    private String mobilePhoneNumber;
    private String phoneNumber;
    private String emailParent;
    private boolean agreeWithBylaws;
    private boolean agreeWithPicturesAndAudio;
    private boolean receiveClubinfo;
    private boolean receiveInfoAboutPromotionsAndFederalMatters;
    private String type;
    private Integer score;
    private Integer grade;
    private Boolean isNoMember;

    public User() {  }

    public User(String userName){
        this.userName = userName;
        setIdNoMember(true);
    }

    public User(String userName, String email, String firstname, String lastname, Integer gender, String nationalInsuranceNumber, Date registrationdate, String bornIn, Date birthday, String mobilePhoneNumber, String phoneNumber, String emailParent, boolean agreeWithBylaws, boolean agreeWithPicturesAndAudio, boolean receiveClubinfo, boolean receiveInfoAboutPromotionsAndFederalMatters, String type, Integer score, Integer grade, Collection<Attendance> attendancesById, Collection<CommentReply> commentRepliesById, Collection<Comment> commentsById, Collection<CourseModuleViewer> courseModuleViewersById, Collection<Formula> formulasById, Address addressByAddressId, Formula formulasByFormulaId) {
        setUserName(userName);
        setEmail(email);
        setFirstname(firstname);
        setLastname(lastname);
        setGender(gender);
        setNationalInsuranceNumber(nationalInsuranceNumber);
        setRegistrationdate(registrationdate);
        setBornIn(bornIn);
        setBirthday(birthday);
        setMobilePhoneNumber(mobilePhoneNumber);
        setPhoneNumber(phoneNumber);
        setEmailParent(emailParent);
        setAgreeWithBylaws(agreeWithBylaws);
        setAgreeWithPicturesAndAudio(agreeWithPicturesAndAudio);
        setReceiveClubinfo(receiveClubinfo);
        setReceiveInfoAboutPromotionsAndFederalMatters(receiveInfoAboutPromotionsAndFederalMatters);
        setType(type);
        setScore(score);
        setGrade(grade);
        setAttendancesById(attendancesById);
        setCommentRepliesById(commentRepliesById);
        setCommentsById(commentsById);
        setCourseModuleViewersById(courseModuleViewersById);
        setFormulasByFormulaId(formulasByFormulaId);
        setFormulasById(formulasById);
        setAddressByAddressId(addressByAddressId);
        setIdNoMember(false);
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.id, user.getUserName(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getGender(), user.getNationalInsuranceNumber(), user.getRegistrationdate(), user.getBornIn(), user.getBirthday(), user.getMobilePhoneNumber(), user.getPhoneNumber(), user.getEmailParent(), user.isAgreeWithBylaws(), user.isAgreeWithPicturesAndAudio(), user.isReceiveClubinfo(), user.isReceiveInfoAboutPromotionsAndFederalMatters(), user.getType(), user.getScore(), user.getGrade(), user.getAttendancesById(), user.getCommentRepliesById(), user.getCommentsById(), user.getCourseModuleViewersById(), user.getFormulasById(), user.getAddressByAddressId(), user.getFormulasByFormulaId());
    }

    @Transient
    private PropertyChangeSupport subject = new PropertyChangeSupport(this);

    @OneToMany(mappedBy = "usersByMemberId")
    private Collection<Attendance> attendancesById;

    @OneToMany(mappedBy = "usersByMemberId")
    private Collection<CommentReply> commentRepliesById;

    @OneToMany(mappedBy = "usersByMemberId")
    private Collection<Comment> commentsById;

    @OneToMany(mappedBy = "usersByMemberId")
    private Collection<CourseModuleViewer> courseModuleViewersById;

    @OneToMany(mappedBy = "usersByTeacherId", cascade = CascadeType.PERSIST)
    private Collection<Formula> formulasById;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AddressId")
    private Address addressByAddressId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FormulaId")
    private Formula formulasByFormulaId;

    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (empty(userName)) {
            throw new CRuntimeException("Gebruikersnaam kan niet leeg zijn!");
        }else if(!userName.isEmpty() || userName != null){
            String regex = "^[A-Z]{1}[A-Za-z]+([0-9]*)?([A-Za-z]*)?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userName);
            if(!matcher.matches()){
                throw new CRuntimeException("Validatie error bij gebruikersnaam: Moet starten met een hoofdletter gevolgd door minstens 1 kleine letter en mag geen spaties bevatten. Mag cijfers hebben.");
            }
        }
        this.userName = userName;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(empty(email)) {
            throw new CRuntimeException("Email kan niet leeg zijn!");
        } else if (!email.isEmpty() || email != null) {
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new CRuntimeException("Validatie error in email.");
            } else {
                this.email = email;
            }
        }
    }

    @Basic
    @Column(name = "Firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (empty(firstname)) {
            throw new CRuntimeException("Voornaam kan niet leeg zijn!");
        }
        subject.firePropertyChange("firstname", this.firstname, firstname);
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "Lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (empty(lastname)) {
            throw new CRuntimeException("Naam kan niet leeg zijn!");
        }
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "Gender")
    public Integer getGender() {
        if(gender == null){
            return 0;
        }
        return gender;
    }

    public void setGender(Integer gender) {
        if (gender == 0) {
            throw new CRuntimeException("Geslacht kan niet leeg zijn!");
        }
        this.gender = gender;
    }

    @Basic
    @Column(name = "NationalInsuranceNumber")
    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
        try {
            if(empty(nationalInsuranceNumber)) {
                throw new CRuntimeException("Rijksregisternummer kan niet leeg zijn!");
            } else if (!nationalInsuranceNumber.isEmpty() || nationalInsuranceNumber != null) {
                String regex = "^[0-9]{2}.[0-9]{2}.[0-9]{2}-[0-9]{3}.[0-9]{2}$"; // bv. 99.04.05-233.75
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(nationalInsuranceNumber);
                if (!matcher.matches()) {
                    throw new CRuntimeException("Validatie error in rijksregisternummer. Vereiste: vb. 99.04.05-233.75");
                } else {
                    this.nationalInsuranceNumber = nationalInsuranceNumber;
                }
            }
        } catch (NullPointerException nullp) {
            return;
        } catch (CRuntimeException cr) { System.out.println(cr.getMessage()); }
    }

    @Basic
    @Column(name = "Registrationdate")
    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    @Basic
    @Column(name = "BornIn")
    public String getBornIn() {
        return bornIn;
    }

    public void setBornIn(String bornIn) {
        if (empty(bornIn)) {
            throw new CRuntimeException("Geboorteplaats kan niet leeg zijn!");
        }
        this.bornIn = bornIn;
    }

    @Basic
    @Column(name = "Birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "MobilePhoneNumber")
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        if (empty(mobilePhoneNumber)) {
            throw new CRuntimeException("Gsmnummer is verplicht!");
        }
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    @Basic
    @Column(name = "PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "EmailParent")
    public String getEmailParent() {
        return emailParent;
    }

    public void setEmailParent(String emailParent) {
        this.emailParent = emailParent;
    }

    @Basic
    @Column(name = "AgreeWithBylaws")
    public boolean isAgreeWithBylaws() {
        return agreeWithBylaws;
    }

    public void setAgreeWithBylaws(boolean agreeWithBylaws) {
        this.agreeWithBylaws = agreeWithBylaws;
    }

    @Basic
    @Column(name = "AgreeWithPicturesAndAudio")
    public boolean isAgreeWithPicturesAndAudio() {
        return agreeWithPicturesAndAudio;
    }

    public void setAgreeWithPicturesAndAudio(boolean agreeWithPicturesAndAudio) {
        this.agreeWithPicturesAndAudio = agreeWithPicturesAndAudio;
    }

    @Basic
    @Column(name = "ReceiveClubinfo")
    public boolean isReceiveClubinfo() {
        return receiveClubinfo;
    }

    public void setReceiveClubinfo(boolean receiveClubinfo) {
        this.receiveClubinfo = receiveClubinfo;
    }

    @Basic
    @Column(name = "ReceiveInfoAboutPromotionsAndFederalMatters")
    public boolean isReceiveInfoAboutPromotionsAndFederalMatters() {
        return receiveInfoAboutPromotionsAndFederalMatters;
    }

    public void setReceiveInfoAboutPromotionsAndFederalMatters(boolean receiveInfoAboutPromotionsAndFederalMatters) {
        this.receiveInfoAboutPromotionsAndFederalMatters = receiveInfoAboutPromotionsAndFederalMatters;
    }

    @Basic
    @Column(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "Score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "Grade")
    public Integer getGrade() {
        if(grade == null){
            return 0;
        }
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Column(name = "IsNoMember")
    public Boolean getIsNoMember(){
        if(isNoMember == null){
            return false;
        }
        return isNoMember;
    }

    public void setIdNoMember(boolean isNoMember){
        this.isNoMember = isNoMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User users = (User) o;
        return id == users.id &&
                gender == users.gender &&
                agreeWithBylaws == users.agreeWithBylaws &&
                agreeWithPicturesAndAudio == users.agreeWithPicturesAndAudio &&
                receiveClubinfo == users.receiveClubinfo &&
                receiveInfoAboutPromotionsAndFederalMatters == users.receiveInfoAboutPromotionsAndFederalMatters &&
                Objects.equals(userName, users.userName) &&
                Objects.equals(email, users.email) &&
                Objects.equals(firstname, users.firstname) &&
                Objects.equals(lastname, users.lastname) &&
                Objects.equals(nationalInsuranceNumber, users.nationalInsuranceNumber) &&
                Objects.equals(registrationdate, users.registrationdate) &&
                Objects.equals(bornIn, users.bornIn) &&
                Objects.equals(birthday, users.birthday) &&
                Objects.equals(mobilePhoneNumber, users.mobilePhoneNumber) &&
                Objects.equals(phoneNumber, users.phoneNumber) &&
                Objects.equals(emailParent, users.emailParent) &&
                Objects.equals(type, users.type) &&
                Objects.equals(score, users.score) &&
                Objects.equals(grade, users.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, firstname, lastname, gender, nationalInsuranceNumber, registrationdate, bornIn, birthday, mobilePhoneNumber, phoneNumber, emailParent, agreeWithBylaws, agreeWithPicturesAndAudio, receiveClubinfo, receiveInfoAboutPromotionsAndFederalMatters, type, score, grade);
    }

    public Collection<Attendance> getAttendancesById() {
        return attendancesById;
    }

    public void setAttendancesById(Collection<Attendance> attendancesById) {
        this.attendancesById = attendancesById;
    }

    public Collection<CommentReply> getCommentRepliesById() {
        return commentRepliesById;
    }

    public void setCommentRepliesById(Collection<CommentReply> commentRepliesById) {
        this.commentRepliesById = commentRepliesById;
    }

    public Collection<Comment> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<Comment> commentsById) {
        this.commentsById = commentsById;
    }


    public Collection<CourseModuleViewer> getCourseModuleViewersById() {
        return courseModuleViewersById;
    }

    public void setCourseModuleViewersById(Collection<CourseModuleViewer> courseModuleViewersById) {
        this.courseModuleViewersById = courseModuleViewersById;
    }

    public Collection<Formula> getFormulasById() {
        return formulasById;
    }

    public void setFormulasById(Collection<Formula> formulasById) {
        this.formulasById = formulasById;
    }

    public Address getAddressByAddressId() {
        return addressByAddressId;
    }

    public void setAddressByAddressId(Address addressByAddressId) {
        this.addressByAddressId = addressByAddressId;
    }

    public Formula getFormulasByFormulaId() {
        if(formulasByFormulaId == null){
            formulasByFormulaId = new Formula();
            formulasByFormulaId.setFormulaName("Geen");
            return formulasByFormulaId;
        }
        return formulasByFormulaId;
    }

    public void setFormulasByFormulaId(Formula formulasByFormulaId) {
        this.formulasByFormulaId = formulasByFormulaId;
    }

    @Override
    public String toString(){
        return String.format("%s ",this.userName);
    }

    private boolean empty(String string) {
        if (string.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
