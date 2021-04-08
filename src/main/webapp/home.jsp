<%@ page import="java.util.List" %>
<%@ page import="com.auction.eni_auction.bo.ArticlesVendus" %>
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

<jsp:include page="navbar.jsp" />

<div class="parallax-container">
    <div class="parallax"><img src="https://i.pinimg.com/originals/c0/83/7a/c0837a3f2855c632be8dbde4b7eeccba.jpg"></div>
</div>
<div class="section white">
    <div class="row container">
        <h2 class="header">Liste des dernirèes enchères <%= request.getAttribute("test") %></h2>
        <p class="grey-text text-darken-3 lighten-3">
        </p>

        <div class="row">



            <c:set var="i" value="0" />
            <c:forEach items="${requestScope['articles']}" var="articlesVendus">

            <div class="col s3">
                <div class="card">
                    <div class="card-image">
                        <img src="https://i.pinimg.com/originals/c0/83/7a/c0837a3f2855c632be8dbde4b7eeccba.jpg">
                        <span class="card-title">test</span>
                    </div>
                    <div class="card-content">
                        <p>test</p>
                    </div>
                    <div class="card-action">
                        <a href="#">Voir plus de détails</a>
                    </div>
                </div>
            </div>
                <c:set var="i" value="${i+1}" />
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

    });

</script>
</html>