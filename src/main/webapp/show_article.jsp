<%@ page import="java.util.List" %>
<%@ page import="com.auction.eni_auction.bo.ArticleVendu" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<head>
    <title>Auction - Article</title>
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
    <div class="container">
        <c:if test="${requestScope['success'] != null}">
        <div class="col s12 m12">
            <div class="card green darken-1">
                <div class="row">
                    <div class="col s12 m10">
                        <div class="card-content white-text">
                            <c:out value="${requestScope['success']}"/>
                        </div>
                    </div>
                    <div class="col s12 m2">
                        <i class="fa fa-times icon_style" id="alert_close" aria-hidden="true"></i>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${requestScope['error'] != null}">
        <div class="col s12 m12">
            <div class="card red darken-1">
                <div class="row">
                    <div class="col s12 m10">
                        <div class="card-content white-text">
                            <c:out value="${requestScope['error']}"/>
                        </div>
                    </div>
                    <div class="col s12 m2">
                        <i class="fa fa-times icon_style" id="alert_close" aria-hidden="true"></i>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    </div>
    <div class="row container">
        <h2 class="header">${requestScope['article'].getNom()}</h2>
        <p class="grey-text text-darken-3 lighten-3">
        </p>

        <div class="row">



            <c:set var="i" value="0" />

            <div class="col s7">
                <div class="card">
                    <div class="card-image">
                        <img src="https://i.pinimg.com/originals/c0/83/7a/c0837a3f2855c632be8dbde4b7eeccba.jpg">
                        <span class="card-title">${requestScope['article'].getNom()}</span>
                    </div>
                    <div class="card-content">
                        <p>${requestScope['article'].getDescription()}</p>
                        <form method="post" action="<%=request.getContextPath()+"/enchere?id="%>${requestScope['article'].getNoArticle()}">
                            <div class="input-field">
                                <input name="price" id="price" type="text" class="validate">
                                <label for="price">Enchérir</label>
                            </div>
                            <div class="input-field" hidden>
                                <input value="${requestScope['article'].getNoArticle()}" id="articleId" name="articleId" type="text" class="validate">
                                <label class="active" for="articleId">articleId</label>
                            </div>
                            <button class="btn" type="submit">Valider</button>
                        </form>
                    </div>
                    <div class="card-action">
                        <p>Dernieres encheres de ${requestScope['derniereEnchere'].utilisateur.getPseudo()}: ${requestScope['derniereEnchere'].montantEnchere}</p>
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

    });

</script>
</html>