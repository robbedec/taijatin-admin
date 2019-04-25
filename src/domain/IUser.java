package domain;

import java.util.Date;

public interface IUser {
    public int getId();
    public String getUserName();
    public String getEmail();
    public String getFirstname();
    public String getLastname();
    public Integer getGender();
    public String getNationalInsuranceNumber();
    public Date getRegistrationdate();
    public String getBornIn();
    public Date getBirthday();
    public String getMobilePhoneNumber();
    public String getPhoneNumber();
    public String getEmailParent();
    public boolean isAgreeWithBylaws();
    public boolean isAgreeWithPicturesAndAudio();
    public boolean isReceiveClubinfo();
    public boolean isReceiveInfoAboutPromotionsAndFederalMatters();
    public String getType();
    public Integer getScore();
    public Integer getGrade();
}
