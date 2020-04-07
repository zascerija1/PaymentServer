package ba.unsa.etf.si.payment.request.filters;

import java.util.Date;

public class DateFilterRequest {
    private Date startDate;
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
