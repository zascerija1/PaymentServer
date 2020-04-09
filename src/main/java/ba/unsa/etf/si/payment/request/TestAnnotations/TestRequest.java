package ba.unsa.etf.si.payment.request.TestAnnotations;

import ba.unsa.etf.si.payment.annotation.DoubleValidation;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class TestRequest {

    @NotBlank //isto kao NotNull za ostale
    private String testString;

    @NotNull //u slucaju da je "" ili null
    @DecimalMin("0.0") //mora biti vece od 0
    //ako se unese neki znak (a da nije .) ili slovo (bilo pod nadovnicima ili ne)
    //baci svakako bad request jer je invalid JSON input
    private Double testDouble;

    @NotNull //u slucaju da je "" ili null
    @Min(0) //posto se za vecinu requesta u projektu traze vrijednosti >= 0
    //ako se unese neki znak ili slovo (bilo pod nadovnicima ili ne)
    //baci svakako bad request jer je invalid JSON input
    private Long testLong;

    @NotNull
    //radi za sve vrijednosti od 36 karaktera i 4 -
    //za sve ostalo bude ovaj bad request sto je okej
    private UUID testUUID;

    @NotNull
    //za sve sto nije true/false baca bad request
    private Boolean testBool;

    @NotNull
    @Min(0)
    private Integer testInteger;

    @NotNull //radi za "" i null
    //todo provjeriti ovaj pattern...
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd.MM.yyyy hh:mm:ss",timezone="Europe/Sarajevo")
    private Date testDate;

    public TestRequest() {
    }

    public TestRequest(@NotBlank String testString, @NotNull @DecimalMin("0.0") Double testDouble, @NotNull @Min(0) Long testLong, @NotNull UUID testUUID, @NotNull Boolean testBool, @NotNull @Min(0) Integer testInteger, @NotNull Date testDate) {
        this.testString = testString;
        this.testDouble = testDouble;
        this.testLong = testLong;
        this.testUUID = testUUID;
        this.testBool = testBool;
        this.testInteger = testInteger;
        this.testDate = testDate;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public Long getTestLong() {
        return testLong;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

    public UUID getTestUUID() {
        return testUUID;
    }

    public void setTestUUID(UUID testUUID) {
        this.testUUID = testUUID;
    }

    public Boolean getTestBool() {
        return testBool;
    }

    public void setTestBool(Boolean testBool) {
        this.testBool = testBool;
    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }
}
