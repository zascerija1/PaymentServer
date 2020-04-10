package ba.unsa.etf.si.payment.request.filters;

public class PriceFilterRequest {
    private Double minPrice;
    private Double maxPrice;

    public PriceFilterRequest() {
    }

    public PriceFilterRequest(Double minPrice, Double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
