<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Profilo - Need Instruments</title>
    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <!--<link href="./css/navVer.css" rel="stylesheet" type="text/css">-->
    <link href="./css/footer.css" rel="stylesheet" type="text/css">
    <link href="./css/profile.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <a href="homepage"><img src="./icons/logoWhite.png" alt="NeedInstruments Logo" style="height: 50px; margin-right: 10px;"></a>
    <nav class="horizontal">
        <form action="search-servlet">
            <label>
                <input type="text" name="search" class="search"
                       placeholder="Cerca tra più di 10.000 prodotti e 20 marchi ">
            </label>
            <button type="submit">Cerca</button>
        </form>
        <a class="horizontal" href="offerte-servlet">OFFERTE</a>

        <a href="cart-servlet">
            <img src="./icons/carrello.png" alt="Carrello" style="height: 20px; margin-right: 5px;">
        </a>
        <a href="preferiti-servlet">
            <img src="./icons/preferiti.png" alt="Preferiti" style="height: 20px; margin-right: 5px;">
        </a>
        <c:choose>
            <c:when test="${sessionScope.utente != null}">
                <p>Ciao <b>${sessionScope.utente.nome}</b></p>
            </c:when>
            <c:otherwise>
                <a href="./login.jsp" id="login">ACCEDI</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<div class="content">
    <div class="section-title">I miei dati</div>
    <div class="profiles">
        <div class="profile"><br>
            <b>Nome:</b>            ${sessionScope.utente.nome}<br>
            <b>Cognome:</b>         ${sessionScope.utente.cognome}<br>
            <b>Email: </b>          ${sessionScope.utente.email}<br>
            <b>Data di nascita:</b> ${sessionScope.utente.data_nascita}<br>
        </div>
    </div>

    <div class="section-title">Il tuo profilo</div>
    <div class="profiles">

        <form action="${pageContext.request.contextPath}/from-profile-to-mod-datas">
            <button type="submit" class="modify">Modifica dati</button>
        </form>

        <form action="${pageContext.request.contextPath}/miei-acquisti-servlet">
            <button type="submit" class="modify">I miei acquisti</button>
        </form>

        <form action="${pageContext.request.contextPath}/logout-servlet" method="post">
            <button type="submit" class="logout">ESCI</button>
        </form>

    </div>
<c:choose>
    <c:when test="${sessionScope.utente.admin == true}">
        <div class="section-title">ADMIN - Utente</div>
        <div class="profiles">

            <form action="${pageContext.request.contextPath}/list-users-servlet">
                <button type="submit" class="modify">Lista utenti</button>
            </form>

            <form action="${pageContext.request.contextPath}/new-user-form">
                <button type="submit" class="modify">Aggiungi utente</button>
            </form>

        </div>

        <div class="section-title">ADMIN - Prodotti</div>
        <div class="profiles">

            <form action="${pageContext.request.contextPath}/list-prods-servlet">
                <button type="submit" class="modify">Lista prodotti</button>
            </form>

            <form action="${pageContext.request.contextPath}/new-prod-form">
                <button type="submit" class="modify">Aggiungi prodotto</button>
            </form>

            <form action="list-acquisto-servlet">
                <button type="submit" class="modify">Lista acquisti</button>
            </form>

        </div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
</div>

<footer>
    <div class="contact-section">
        <h3>Contatti</h3>
        <p>Telefono: +39 0123456789</p>
        <p>Email: info@needinstruments.com</p>
        <p>Indirizzo: Via dei Musicisti, 123 - 00123, Città</p>
        <div class="social-media">
            <a href="#"><img src="./icons/facebook.png" alt="Facebook" class="icon"></a>
            <a href="#"><img src="./icons/meta.png" alt="Meta" class="icon"></a>
            <a href="#"><img src="./icons/instagram.png" alt="Instagram" class="icon"></a>
        </div>
    </div>
    <div class="copyright">
        <p>&copy; 2024 NeedInstruments. Tutti i diritti riservati.</p>
    </div>
</footer>
</body>
</html>
