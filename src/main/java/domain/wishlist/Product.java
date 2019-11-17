package domain.wishlist;

class Product {
    private Long id;
    private Long customerId;
    private String productId;
    private String title;
    private String image;
    private Double price;
    private Integer reviewScore;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getReviewScore() {
        return reviewScore;
    }
}