package com.auction.eni_auction.bo;

public class Retrait {

    private ArticleVendu article;

    private String rue;

    private String postalCode;

    private String city;

    public Retrait(ArticleVendu article, String rue, String postalCode, String city) {
		super();
		this.article = article;
		this.rue = rue;
		this.postalCode = postalCode;
		this.city = city;
	}

	public ArticleVendu getArticle() {
        return article;
    }

    public void setArticle(ArticleVendu article) {
        this.article = article;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
