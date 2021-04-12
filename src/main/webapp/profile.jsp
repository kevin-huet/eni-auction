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

<div class="section white">
    <div class="row container">

        <c:if test="${sessionScope.user != null}">
        <h5 class="center">Mon Profil</h5>
        <div class="col s6">
            <div class="card">
                <div class="card-image">
                </div>
                <div class="card-content">
                    <p> Pseudo : ${sessionScope.user.getPseudo()} </p>
                    <p> Nom : ${sessionScope.user.getNom()} </p>
                    <p> Prenom : ${sessionScope.user.getPrenom()} </p>
                    <p> Email : ${sessionScope.user.getEmail()} </p>
                    <p> Credit : ${sessionScope.user.getCredit()} </p>

                    <form class="" method="post" style="margin-top: 2em" action="<%=request.getContextPath()+"/login"%>">

                        <div class="input-field col s12">
                            <input  type="text" id="email" name="email" class="validate">
                            <label for="email">Email</label>
                        </div>
                        <div class="input-field col s12">
                            <input id="password" type="password" name="password" class="validate">
                            <label for="password">Password</label>
                        </div>

                        <button class="btn">Valider</button>
                    </form>
                </div>
                <div class="card-action">
                </div>
            </div>
        </div>

            <div class="col s6">
                <div class="card">
                    <div class="card-image">
                    </div>
                    <div class="card-content">
                        <form class="" method="post" style="margin-top: 2em" action="<%=request.getContextPath()+"/login"%>">

                            <div class="input-field col s12">
                                <input  type="text" id="email" name="email" class="validate">
                                <label for="email">Email</label>
                            </div>
                            <div class="input-field col s12">
                                <input id="password" type="password" name="password" class="validate">
                                <label for="password">Password</label>
                            </div>

                            <button class="btn">Valider</button>
                        </form>
                    </div>
                    <div class="card-action">
                    </div>
                </div>
            </div>

            <div class="col s12">
                <div class="card">
                    <div class="card-image">
                    </div>
                    <div class="card-content center">
                        <a href="" class="btn red">Supprimer mon compte</a>
                    </div>
                    <div class="card-action">
                    </div>
                </div>
            </div>
        </c:if>
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