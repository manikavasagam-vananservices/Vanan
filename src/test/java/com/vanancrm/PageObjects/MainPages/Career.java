package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Career extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    public Career(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "career_name")
    private WebElement name;
    @FindBy(id = "career_last_name")
    private WebElement lastname;
    @FindBy(name = "career_email")
    private WebElement emailid;
    @FindBy(id = "career_country")
    private WebElement country;
    @FindBy(id = "career_zip")
    private WebElement zipcode;
    @FindBy(id = "career_phone")
    private WebElement Phone;
    @FindBy(id = "career_city")
    private WebElement City;
    @FindBy(id = "career_state")
    private WebElement State;
    @FindBy(id = "career_address")
    private WebElement Address;
    @FindBy(id = "career_nationality")
    private WebElement Nationality;
    @FindBy(id = "career_transc_skype")
    private WebElement Skypeid;
    @FindBy(id = "career_transc_language")
    private WebElement Language;
    @FindBy(id = "career_tranc_rate")
    private WebElement RatePerHour;
    @FindBy(id = "career_tranc_experience")
    private WebElement Experienc ;
    @FindBy(id = "career_tranc_experience_year")
    private WebElement Yearexperienc ;
    @FindBy(id = "career_tranc_workingdays")
    private WebElement Workingdays;
    @FindBy(id = "career_tranc_nativelanguage")
    private WebElement Nativelangugae;
    @FindBy(id = "career_comments")
    private WebElement Comments ;
    @FindBy(id = "privacy_policy")
    private WebElement Terms;
    @FindBy(id = "career_transcription")
    private WebElement submit;


    public void firstName(String cname) {
        try {
            enterTestBoxValues(name, cname);
        } catch (Exception e) {
            System.out.println("Unable to enter a name value " + e);
        }
    }
    public void LastName(String cname) {
        try {
            enterTestBoxValues(lastname, cname);
        } catch (Exception e) {
            System.out.println("Unable to enter a name value " + e);
        }
    }
    public void enterEmail(String cemail) {
        try {
            enterTestBoxValues(emailid, cemail);
        } catch (Exception e) {
            System.out.println("Unable to enter a Email ID value " + e);
        }
    }
    public void selectCountry(String cselectcountry) {
        try {
            selectDropDown(country, cselectcountry);

        } catch (Exception e) {
            System.out.println("Unable to select the country " + e);
        }
    }
    public void enterZipcode(String ccode) {
        try {
            enterTestBoxValues(zipcode, ccode);
        } catch (Exception e) {
            System.out.println("Unable to enter zipcode " + e);
        }
    }
    public void enterphoneno(String cphonenum) {
        try {
            enterTestBoxValues(Phone, cphonenum);
        } catch (Exception e) {
            System.out.println("Unable to enter phone number " + e);
        }
    }
    public void entercity(String ccity) {
        try {
            enterTestBoxValues(City, ccity);
        } catch (Exception e) {
            System.out.println("Unable to enter city name " + e);
        }
    }
    public void enterstate(String cstate) {
        try {
            enterTestBoxValues(State, cstate);
        } catch (Exception e) {
            System.out.println("Unable to enter State name " + e);
        }
    }
    public void enteradress(String cadress) {
        try {
            enterTestBoxValues(Address, cadress);
        } catch (Exception e) {
            System.out.println("Unable to enter Street Address" + e);
        }
    }

    public void enterNationality(String cnationality) {
        try {
            enterTestBoxValues(Nationality, cnationality);
        } catch (Exception e) {
            System.out.println("Unable to enter Nationality " + e);
        }
    }
    public void enterSkypeid(String cskypeid) {
        try {
            enterTestBoxValues(Skypeid, cskypeid);
        } catch (Exception e) {
            System.out.println("Unable to enter a skype id or name " + e);
        }
    }
    public void enterLanguage(String langauge) {
        try {
            enterTestBoxValues(Language, langauge);
        } catch (Exception e) {
            System.out.println("Unable to enter language " + e);
        }
    }
    public void enterRatePerHour(String rate) {
        try {
            enterTestBoxValues(RatePerHour, rate);
        } catch (Exception e) {
            System.out.println("Unable to enter rate " + e);
        }
    }
    public void selectExperienc(String cExperienced) {
        try {
            selectDropDown(Experienc, cExperienced);

        } catch (Exception e) {
            System.out.println("Unable to  select experience" + e);
        }
    }

    public void enterYearexperienc(String yrExperienced) {
        try {
            enterTestBoxValues(Yearexperienc, yrExperienced);

        } catch (Exception e) {
            System.out.println("Unable to enter year of experience" + e);
        }
    }
    public void selectWorkingdays(String cWorkingdays) {
        try {
            selectDropDown(Workingdays, cWorkingdays);

        } catch (Exception e) {
            System.out.println("Unable to enter country " + e);
        }
    }
    public void enterNativelanguage(String cNatLanguage) {
        try {
            enterTestBoxValues(Nativelangugae, cNatLanguage);

        } catch (Exception e) {
            System.out.println("Unable to enter native language " + e);
        }
    }
    public void enterComments(String cComments) {
        try {
            enterTestBoxValues(Comments, cComments);

        } catch (Exception e) {
            System.out.println("Unable to enter comments " + e);
        }
    }
    public void clickTermsConditions(String cTerms) {
        try {
            enterTestBoxValues(Terms, cTerms);

        } catch (Exception e) {
            System.out.println("Unable to enter comments " + e);
        }
    }
    public void clicksubmit(String csubmit) {
        try {


        } catch (Exception e) {
            System.out.println("Unable to enter comments " + e);
        }
    }

}
