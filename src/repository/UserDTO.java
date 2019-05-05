package repository;

import domain.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDTO {
    public int id;
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
    private Collection<Attendance> attendancesById;
    private Collection<CommentReply> commentRepliesById;
    private Collection<Comment> commentsById;
    private Collection<CourseModuleViewer> courseModuleViewersById;
    private Collection<Formula> formulasById;
    private Address addressByAddressId;
    private Formula formulasByFormulaId;
    private Collection<Activity> activityById;

    public UserDTO() { }

    public UserDTO(int id, String userName, String email, String firstname, String lastname, Integer gender, String nationalInsuranceNumber, Date registrationdate, String bornIn, Date birthday, String mobilePhoneNumber, String phoneNumber, String emailParent, boolean agreeWithBylaws, boolean agreeWithPicturesAndAudio, boolean receiveClubinfo, boolean receiveInfoAboutPromotionsAndFederalMatters, String type, Integer score, Integer grade, Collection<Attendance> attendancesById, Collection<CommentReply> commentRepliesById, Collection<Comment> commentsById, Collection<CourseModuleViewer> courseModuleViewersById, Collection<Formula> formulasById, Address addressByAddressId, Formula formulasByFormulaId, Collection<Activity> activityById) {
        this.id = id;
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

    public User toUser() {
        return new User(this.getUserName(), this.getEmail(), this.getFirstname(), this.getLastname(), this.getGender(), this.getNationalInsuranceNumber(), this.getRegistrationdate(), this.getBornIn(), this.getBirthday(), this.getMobilePhoneNumber(), this.getPhoneNumber(), this.getEmailParent(), this.isAgreeWithBylaws(), this.isAgreeWithPicturesAndAudio(), this.isReceiveClubinfo(), this.isReceiveInfoAboutPromotionsAndFederalMatters(), this.getType(), this.getScore(), this.getGrade(), this.getAttendancesById(), this.getCommentRepliesById(), this.getCommentsById(), this.getCourseModuleViewersById(), this.getFormulasById(), this.getAddressByAddressId(), this.getFormulasByFormulaId(), this.getActivityById());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (empty(userName)) {
            throw new CRuntimeException("Username can not be empty!");
        }
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(empty(email)) {
            throw new CRuntimeException("Email can not be empty!");
        } else if (email.isEmpty() || email != null) {
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new CRuntimeException("Validation error in email.");
            } else {
                this.email = email;
            }
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (empty(firstname)) {
            throw new CRuntimeException("Firstname can not be empty!");
        }
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (empty(lastname)) {
            throw new CRuntimeException("Lastname can not be empty!");
        }
        this.lastname = lastname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        if (gender == 0) {
            throw new CRuntimeException("Gender can not be empty!");
        }
        this.gender = gender;
    }

    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
        try {
            if(empty(nationalInsuranceNumber)) {
                throw new CRuntimeException("Email can not be empty!");
            } else if (nationalInsuranceNumber.isEmpty() || nationalInsuranceNumber != null) {
                String regex = "^[0-9]{2}.[0-9]{2}.[0-9]{2}-[0-9]{3}.[0-9]{2}$"; // bv. 99.04.05-233.75
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(nationalInsuranceNumber);
                if (!matcher.matches()) {
                    throw new CRuntimeException("Validation error in national insurance number. Requires: fex. 99.04.05-233.75");
                } else {
                    this.nationalInsuranceNumber = nationalInsuranceNumber;

                    // Set birthday with number
                    String year = this.nationalInsuranceNumber.substring(0,2);
                    String month = this.nationalInsuranceNumber.substring(3, 5);
                    String day = this.nationalInsuranceNumber.substring(6, 8);
                    String date = day+"/"+month+"/"+year;
                    java.util.Date birthday = new SimpleDateFormat("dd/MM/yy").parse(date);
                    this.setBirthday(new java.sql.Date(birthday.getTime()));
                    System.out.println("Birthday: " + birthday);

                    // Set gender
                    int gender = Integer.parseInt(this.nationalInsuranceNumber.substring(9, 12));
                    if (gender % 2 == 0) {
                        System.out.println("Vrouw: " + gender);
                        this.setGender(2);
                    } else {
                        System.out.println("Man: " + gender);
                        this.setGender(1);
                    }

                }
            }
        } catch (NullPointerException nullp) {
            return;
        } catch (CRuntimeException cr) { System.out.println(cr.getMessage()); } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    public String getBornIn() {
        return bornIn;
    }

    public void setBornIn(String bornIn) {
        if (empty(bornIn)) {
            throw new CRuntimeException("Born in place can not be empty!");
        }
        this.bornIn = bornIn;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        if (empty(mobilePhoneNumber)) {
            throw new CRuntimeException("Mobile phone number is required!");
        }
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailParent() {
        return emailParent;
    }

    public void setEmailParent(String emailParent) {
        this.emailParent = emailParent;
    }

    public boolean isAgreeWithBylaws() {
        return agreeWithBylaws;
    }

    public void setAgreeWithBylaws(boolean agreeWithBylaws) {
        this.agreeWithBylaws = agreeWithBylaws;
    }

    public boolean isAgreeWithPicturesAndAudio() {
        return agreeWithPicturesAndAudio;
    }

    public void setAgreeWithPicturesAndAudio(boolean agreeWithPicturesAndAudio) {
        this.agreeWithPicturesAndAudio = agreeWithPicturesAndAudio;
    }

    public boolean isReceiveClubinfo() {
        return receiveClubinfo;
    }

    public void setReceiveClubinfo(boolean receiveClubinfo) {
        this.receiveClubinfo = receiveClubinfo;
    }

    public boolean isReceiveInfoAboutPromotionsAndFederalMatters() {
        return receiveInfoAboutPromotionsAndFederalMatters;
    }

    public void setReceiveInfoAboutPromotionsAndFederalMatters(boolean receiveInfoAboutPromotionsAndFederalMatters) {
        this.receiveInfoAboutPromotionsAndFederalMatters = receiveInfoAboutPromotionsAndFederalMatters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public Collection<Activity> getActivityById() {
        return activityById;
    }

    public void setActivitiesById(Collection<Activity> activityById) {
        this.activityById = activityById;
    }

    private boolean empty(String string) {
        if (string.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }

    }
}
