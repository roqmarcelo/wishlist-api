package domain.product;

class Product {

    private Long id;
    private Long customerId;
    private String productId;
    private String title;
    private String image;
    private Double price;
    private Integer reviewScore;

    Product(final String productId, final String title, final String image, final Double price, final Integer reviewScore) {
        this.productId = productId;
        this.title = title;
        this.image = image;
        this.price = price;
        this.reviewScore = reviewScore;
    }

    public Long getId() {
        return id;
    }

    Long getCustomerId() {
        return customerId;
    }

    void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    String getProductId() {
        return productId;
    }

    String getTitle() {
        return title;
    }

    String getImage() {
        return image;
    }

    Double getPrice() {
        return price;
    }

    Integer getReviewScore() {
        return reviewScore;
    }
}