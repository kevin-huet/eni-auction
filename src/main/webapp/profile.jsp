<%@ page import="java.util.List" %>
<%@ page import="com.auction.eni_auction.bo.ArticleVendu" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<head>
    <title>Auction - Profil</title>
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
            <div class="col s12">
                <div class="card">
                        <form class="col s12 center" style="margin-top: 10em" method="post" action="<%=request.getContextPath()+"/profile"%>">
                            <p>Credits : ${sessionScope["user"].credit}</p>

                            <div class="input-field col s4" hidden>
                                    <input value="${sessionScope["user"].noUtilisateur}" id="id" name="id" type="text" class="validate">
                                    <label for="id">id</label>
                                </div>
                                <div class="input-field col s4">
                                    <input value="${sessionScope["user"].pseudo}" id="pseudo" name="pseudo" type="text" class="validate">
                                    <label for="pseudo">Pseudo</label>
                                </div>
                                <div class="input-field col s4">
                                    <input value="${sessionScope["user"].nom}" id="nom" name="nom" type="text" class="validate">
                                    <label for="nom">Last Name</label>
                                </div>
                                <div class="input-field col s4">
                                    <input value="${sessionScope["user"].prenom}" id="prenom" name="prenom" type="text" class="validate">
                                    <label for="prenom">Prenom</label>
                                </div>
                                <div class="input-field col s6">
                                    <input value="${sessionScope["user"].email}" id="email" name="email" type="text" class="validate">
                                    <label for="email">Email</label>
                                </div>
                                <div class="input-field col s6">
                                    <input value="${sessionScope["user"].motDePasse}" id="password" name="password" type="password" class="validate">
                                    <label for="password">password</label>
                                </div>
                                <div class="input-field col s3">
                                    <input value="${sessionScope["user"].telephone}" id="telephone" name="telephone" type="text" class="validate">
                                    <label for="telephone">Telephone</label>
                                </div>
                                <div class="input-field col s3">
                                    <input value="${sessionScope["user"].rue}" id="rue" name="rue" type="text" class="validate">
                                    <label for="rue">Rue</label>
                                </div>
                                <div class="input-field col s3">
                                    <input value="${sessionScope["user"].codePostal}" id="codepostal" name="codepostal" type="text" class="validate">
                                    <label for="codepostal">Code postal</label>
                                </div>
                                <div class="input-field col s3">
                                    <input value="${sessionScope["user"].ville}" id="ville" name="ville" type="text" class="validate">
                                    <label for="ville">Ville</label>
                                </div>

                            <button class="btn">Valider</button>
                        </form>

                </div>
            </div>


            <div class="col s12">
                <div class="card">
                    <div class="card-image">
                    </div>
                    <div class="card-content center">
                        <a href="<%=request.getContextPath()+"/delete"%>" class="btn red">Supprimer mon compte</a>
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