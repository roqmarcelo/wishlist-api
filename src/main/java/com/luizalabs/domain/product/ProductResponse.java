package com.luizalabs.domain.product;

class ProductResponse {

    private String id;
    private String title;
    private String image;
    private Double price;
    private Integer reviewScore;

    ProductResponse() {
    }

    ProductResponse(final String id, final String title, final String image, final Double price, final Integer reviewScore) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.reviewScore = reviewScore;
    }

    public String getId() {
        return id;
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

    Product asProduct() {
        return new Product(id, title, image, price, reviewScore);
    }
}