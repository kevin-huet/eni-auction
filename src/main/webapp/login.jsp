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

<div class="container" style="min-height:85vh;">
    <div class="row" id="alert_box">

        <c:if test="${requestScope['sucess'] != null}">
            <div class="col s12 m12">
                <div class="card green darken-1">
                    <div class="row">
                        <div class="col s12 m10">
                            <div class="card-content white-text">
                                <c:out value="${requestScope['sucess']}"/>
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

        <form class="col s12 center" method="post" style="margin-top: 10em" action="<%=request.getContextPath()+"/login"%>">

            <div class="row">
                <div class="input-field col s12">
                    <input  type="text" id="email" name="email" class="validate">
                    <label for="email">Email</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input id="password" type="password" name="password" class="validate">
                    <label for="password">Password</label>
                </div>
            </div>

            <button class="btn">Valider</button>
        </form>
        <div class="col s12 center">
            <a class="btn-large"> Pas de compte ? S'inscrire</a>
        </div>
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