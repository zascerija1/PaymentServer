package ba.unsa.etf.si.payment.request.filters;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class DateFilterRequest {
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Sarajevo", lenient = OptBoolean.FALSE)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Sarajevo", lenient = OptBoolean.FALSE)
    private Date endDate;

    public DateFilterRequest() {
    }

    public DateFilterRequest(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
