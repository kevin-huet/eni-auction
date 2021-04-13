<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<nav class="blue darken-1">
    <div class="nav-wrapper container">
        <a href="<%=request.getContextPath()+"/"%>" class="brand-logo">ENI Auction</a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
            <li><a href="<%=request.getContextPath()+"/"%>">Enchères</a></li>
            <li><a href="<%=request.getContextPath()+"/article/create"%>">Créer un article</a></li>

            <li><a href="<%=request.getContextPath()+"/profile?id="%>${sessionScope.user.getNoUtilisateur()}">Mon compte</a></li>
            <li><a href="<%=request.getContextPath()+"/logout"%>">Deconnexion</a></li>

        </ul>
    </div>
</nav>

<ul class="sidenav" id="mobile-demo">
    <li><a href="<%=request.getContextPath()+"/"%>">Enchères</a></li>
    <li><a href="<%=request.getContextPath()+"/article/create"%>">Créer un article</a></li>

    <li><a href="<%=request.getContextPath()+"/profile?id="%>${sessionScope.user.getNoUtilisateur()}">Mon compte</a></li>
    <li><a href="<%=request.getContextPath()+"/logout"%>">Deconnexion</a></li>

</ul>