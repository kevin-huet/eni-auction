
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
        <h2 class="header"></h2>
        <p class="grey-text text-darken-3 lighten-3">
        </p>

        <div class="row">



            <c:set var="i" value="0" />

            <div class="col s6">
                <div class="card">
                    <div class="card-image">

                    </div>
                    <div class="card-content">
                        <form method="post" action="<%=request.getContextPath()+"/category"%>">
                            <div class="input-field">
                                <input name="nom" id="nom" type="text" class="validate">
                                <label for="nom">Nom</label>
                            </div>
                            <button class="btn" type="submit">Valider</button>
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

                        <ul class="collection">
                            <c:forEach items="${requestScope['categories']}" var="category">
                                <li class="collection-item"> ${category.libelle}</li>
                            </c:forEach>
                        </ul>

                    </div>
                    <div class="card-action">
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