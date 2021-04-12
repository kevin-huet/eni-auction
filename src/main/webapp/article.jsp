<%@ page import="java.util.List" %>
<%@ page import="com.auction.eni_auction.bo.ArticleVendu" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<head>
    <title>JSP - Hello World</title>
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
<div class="section white">
    <div class="row container">
        <h2 class="header">${requestScope['article'].getNom()}</h2>
        <p class="grey-text text-darken-3 lighten-3">
        </p>

        <div class="row">



            <c:set var="i" value="0" />

                <div class="col s12">
                    <div class="card">
                        <div class="card-image">
                            <img src="https://i.pinimg.com/originals/c0/83/7a/c0837a3f2855c632be8dbde4b7eeccba.jpg">
                            <span class="card-title">Créer un article</span>
                        </div>
                        <div class="card-content">
                            <form method="post" action="<%=request.getContextPath()+"/article/create"%>${requestScope['article'].getNoArticle()}">
                                <div class="input-field">
                                    <input name="nom" id="nom" type="text" class="validate">
                                    <label for="nom">Nom</label>
                                </div>
                                <div class="input-field">
                                    <input name="desc" id="desc" type="text" class="validate">
                                    <label for="desc">Description</label>
                                </div>
                                <div class="input-field">
                                    <input name="date" id="date" type="date" class="validate">
                                    <label for="date">Date de fin</label>
                                </div>
                                <div class="input-field">
                                    <input name="price" id="price" type="number" class="validate">
                                    <label for="price">Prix</label>
                                </div>
                                <div class="input-field col s12">
                                    <select name="cat" id="cat">
                                        <option value="" disabled selected>Choisissez une catégorie</option>
                                        <c:forEach items="${requestScope['categories']}" var="category">
                                            <option value="${category.noCategorie}">${category.libelle}</option>
                                        </c:forEach>

                                    </select>
                                    <label for="cat">Catégorie</label>
                                </div>
                                <button class="btn" type="submit">Valider</button>
                            </form>
                        </div>
                        <div class="card-action">
                            <p>Dernieres encheres de ${requestScope['derniereEnchere'].getUtilisateur().getPseudo()}: ${requestScope['derniereEnchere'].montantEnchere}</p>
                        </div>
                    </div>
                </div>
                <c:set var="i" value="${i+1}" />


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

    });

</script>
</html>