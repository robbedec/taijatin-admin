package domain;

import repository.UserDTO;

import javax.persistence.*;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Users")
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT b FROM User b"),
        @NamedQuery(name = "Users.findByMail", query = "SELECT b FROM User b WHERE b.email = :email")
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

    public User() {  }

    public User(String userName, String email, String firstname, String lastname, Integer gender, String nationalInsuranceNumber, Date registrationdate, String bornIn, Date birthday, String mobilePhoneNumber, String phoneNumber, String emailParent, boolean agreeWithBylaws, boolean agreeWithPicturesAndAudio, boolean receiveClubinfo, boolean receiveInfoAboutPromotionsAndFederalMatters, String type, Integer score, Integer grade, Collection<Attendance> attendancesById, Collection<CommentReply> commentRepliesById, Collection<Comment> commentsById, Collection<CourseModuleViewer> courseModuleViewersById, Collection<Formula> formulasById, Address addressByAddressId, Formula formulasByFormulaId, Collection<Activity> activityById) {
        this.userName = userName;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.nationalInsuranceNumber = nationalInsuranceNumber;
        this.registrationdate = registrationdate;
        this.bornIn = bornIn;
        this.birthday = birthday;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.phoneNumber = phoneNumber;
        this.emailParent = emailParent;
        this.agreeWithBylaws = agreeWithBylaws;
        this.agreeWithPicturesAndAudio = agreeWithPicturesAndAudio;
        this.receiveClubinfo = receiveClubinfo;
        this.receiveInfoAboutPromotionsAndFederalMatters = receiveInfoAboutPromotionsAndFederalMatters;
        this.type = type;
        this.score = score;
        this.grade = grade;
        this.attendancesById = attendancesById;
        this.commentRepliesById = commentRepliesById;
        this.commentsById = commentsById;
        this.courseModuleViewersById = courseModuleViewersById;
        this.formulasById = formulasById;
        this.addressByAddressId = addressByAddressId;
        this.formulasByFormulaId = formulasByFormulaId;
        this.activityById = activityById;
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUserName(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getGender(), user.getNationalInsuranceNumber(), user.getRegistrationdate(), user.getBornIn(), user.getBirthday(), user.getMobilePhoneNumber(), user.getPhoneNumber(), user.getEmailParent(), user.isAgreeWithBylaws(), user.isAgreeWithPicturesAndAudio(), user.isReceiveClubinfo(), user.isReceiveInfoAboutPromotionsAndFederalMatters(), user.getType(), user.getScore(), user.getGrade(), user.getAttendancesById(), user.getCommentRepliesById(), user.getCommentsById(), user.getCourseModuleViewersById(), user.getFormulasById(), user.getAddressByAddressId(), user.getFormulasByFormulaId(), user.getActivityById());
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

    @OneToMany(mappedBy = "usersByTeacherId")
    private Collection<Formula> formulasById;

    @ManyToMany
    private Collection<Activity> activityById;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AddressId")
    private Address addressByAddressId;

    @ManyToOne
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
        this.userName = userName;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        subject.firePropertyChange("firstname", this.firstname, firstname);
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "Lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
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
        this.gender = gender;
    }

    @Basic
    @Column(name = "NationalInsuranceNumber")
    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
        this.nationalInsuranceNumber = nationalInsuranceNumber;
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

    public Collection<Activity> getActivityById(){ return activityById; }

    public void setActivityById(Collection<Activity> activityById){
        this.activityById = activityById;
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
        return formulasByFormulaId;
    }

    public void setFormulasByFormulaId(Formula formulasByFormulaId) {
        this.formulasByFormulaId = formulasByFormulaId;
    }

    @Override
    public String toString(){
        return String.format("%s ",this.userName);
    }
}
