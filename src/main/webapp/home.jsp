<%@ page import="java.util.List" %>
<%@ page import="com.auction.eni_auction.bo.ArticleVendu" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<head>
    <title>Auction - Accueil</title>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</head>
<body>
<c:if test="${sessionScope.user == null}">
    <jsp:include page="navbar.jsp" />
</c:if>
<c:if test="${sessionScope.user != null}">
    <jsp:include page="navbar_auth.jsp" />
</c:if>
<div class="parallax-container">
    <div class="parallax"><img src="https://i.pinimg.com/originals/c0/83/7a/c0837a3f2855c632be8dbde4b7eeccba.jpg"></div>
</div>
<div class="section white">
    <div class="row container">
        <h2 class="header">Liste des enchères </h2>
        <p class="grey-text text-darken-3 lighten-3">
        </p>

        <div class="row">



            <div class="col s12">
                <div class="card">
                        <form method="post" action="<%=request.getContextPath()+"/"%>">
                            <div class="input-field col s8">
                                <input  name="search" id="search" type="search" class="validate">
                                <label class="active" for="search">Rechercher</label>
                            </div>
                            <div class="input-field col s4">
                                <select name="cat" id="cat">
                                    <option value="" disabled selected>Choisissez une catégorie</option>
                                    <c:forEach items="${requestScope['categories']}" var="category">
                                        <option value="${category.noCategorie}">${category.libelle}</option>
                                    </c:forEach>
                                </select>
                                <label for="cat">Catégorie</label>
                            </div>
                            <c:if test="${sessionScope.user != null}">

                            <div class="input-field col s6">
                                <p>
                                    <label>
                                        <input id="buy_radio" name="buy" type="radio" checked />
                                        <span>Achats</span>
                                    </label>
                                </p>
                                <p>
                                    <label>
                                        <input name="open_auction" type="checkbox" class="buy" />
                                        <span>Enchères ouvertes</span>
                                    </label>
                                </p>
                                <p>
                                    <label>
                                        <input name="actual_auction" type="checkbox" class="buy" />
                                        <span>Mes enchères en cours</span>
                                    </label>
                                </p>
                                <p>
                                    <label>
                                        <input name="win_auction" type="checkbox" class="buy" />
                                        <span>Mes enchères remportées</span>
                                    </label>
                                </p>
                            </div>
                            <div class="input-field col s6">
                                <p>
                                    <label>
                                        <input id="sell_radio" name="sell" type="radio"/>
                                        <span>Mes ventes</span>
                                    </label>
                                </p>
                                <p>
                                    <label>
                                        <input name="my_sales" class="sell" type="checkbox" disabled />
                                        <span>Mes ventes en cours</span>
                                    </label>
                                </p>
                                <p>
                                    <label>
                                        <input name="not_started_sales" class="sell" type="checkbox" disabled />
                                        <span>Ventes non débutées</span>
                                    </label>
                                </p>
                                <p>
                                    <label>
                                        <input name="sales_over" class="sell" type="checkbox" disabled/>
                                        <span>Ventes terminées</span>
                                    </label>
                                </p>
                            </div>
                            </c:if>
                            <div class="center"><button class="btn" type="submit">Rechercher</button></div>
                        </form>
                </div>
            </div>
            <c:forEach items="${requestScope['articles']}" var="articlesVendus">
            <div class="col s12 m6 l4">
                <div class="card">
                    <div class="card-image">
                        <img src="https://i.pinimg.com/originals/c0/83/7a/c0837a3f2855c632be8dbde4b7eeccba.jpg">
                        <span class="card-title">${articlesVendus.getNom()}</span>
                    </div>
                    <div class="card-content">
                        <p><b>Motant de l'enchère actuelle : </b>${articlesVendus.getSellPrice()}</p>
                        <p><b>Description : </b></p>
                        <p>${articlesVendus.getDescription()}</p>
                    </div>
                    <div class="card-action">
                        <a href="<%=request.getContextPath()+"/article?id="%>${articlesVendus.getNoArticle()}">Voir plus de détails</a>
                    </div>
                </div>
            </div>
            </c:forEach>


        </div>
    </div>
</div>
<div class="parallax-container">
    <div class="parallax"><img src="https://i.pinimg.com/originals/b2/94/f3/b294f38e3819084aaafae027c21cec90.jpg"></div>
</div>

<jsp:include page="footer.jsp" />

</body>
<script>
    $(document).ready(function(){
        $('.sidenav').sidenav();
        $('.parallax').parallax();
        $('select').formSelect();

        let checkboxBuy = $('.buy');
        let checkboxSell = $('.sell');
        $('#buy_radio').change(function() {
            if(this.checked) {
                $('#sell_radio').prop('checked', false);
                checkboxSell.each(function() {
                    $(this).prop('checked', false);
                    $(this).prop('disabled', true);
                });
                checkboxBuy.each(function() {
                    $(this).prop('disabled', false);
                });
            }
        });
        $('#sell_radio').change(function() {
            if(this.checked) {
                $('#buy_radio').prop('checked', false);

                checkboxBuy.each(function() {
                    $(this).prop('checked', false);
                    $(this).prop('disabled', true);
                });
                checkboxSell.each(function() {
                    $(this).prop('disabled', false);
                });
            }
        });

    });

</script>
</html>