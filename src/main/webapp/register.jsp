<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<head>
    <title>Auction - Connexion</title>
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
<div class="container" style="min-height:85vh;">
    <div class="row" id="alert_box">

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
    <div class="row">
        <form class="col s12 center" style="margin-top: 10em" method="post" action="<%=request.getContextPath()+"/register"%>">
            <div class="row">
                <div class="input-field col s4">
                    <input id="pseudo" name="pseudo" type="text" class="validate">
                    <label for="pseudo">Pseudo</label>
                </div>
                <div class="input-field col s4">
                    <input id="nom" name="nom" type="text" class="validate">
                    <label for="nom">Last Name</label>
                </div>
                <div class="input-field col s4">
                    <input id="prenom" name="prenom" type="text" class="validate">
                    <label for="prenom">Prenom</label>
                </div>
                <div class="input-field col s6">
                    <input id="email" name="email" type="text" class="validate">
                    <label for="email">Email</label>
                </div>
                <div class="input-field col s6">
                    <input id="password" name="password" type="password" class="validate">
                    <label for="password">password</label>
                </div>
                <div class="input-field col s3">
                    <input id="telephone" name="telephone" type="text" class="validate">
                    <label for="telephone">Telephone</label>
                </div>
                <div class="input-field col s3">
                    <input id="rue" name="rue" type="text" class="validate">
                    <label for="rue">Rue</label>
                </div>
                <div class="input-field col s3">
                    <input id="codepostal" name="codepostal" type="text" class="validate">
                    <label for="codepostal">Code postal</label>
                </div>
                <div class="input-field col s3">
                    <input id="ville" name="ville" type="text" class="validate">
                    <label for="ville">Ville</label>
                </div>

            </div>
            <button class="btn">Valider</button>
        </form>
    </div>
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