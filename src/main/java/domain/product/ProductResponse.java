package domain.product;

class ProductResponse {

    private String id;
    private String title;
    private String image;
    private Double price;
    private Integer reviewScore;

    public String getId() {
        return id;
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

    Product asProduct() {
        return new Product(id, title, image, price, reviewScore);
    }
}