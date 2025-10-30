<%--@elvariable id="utente" type="model.Utente"--%>
<%--@elvariable id="listaPreferiti" type="java.util.List"--%>
<%--@elvariable id="listaProdotti" type="java.util.List"--%>
<%--@elvariable id="usersList" type="model"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Lista utenti - Need Instruments</title>

    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <link href="./css/navVer.css" rel="stylesheet" type="text/css">
    <link href="./css/footer.css" rel="stylesheet" type="text/css">
    <link href="./css/adminlist.css" rel="stylesheet" type="text/css">

    <style>
        .brand {
            width: 150px;
            height: 120px;
        }

        .icon {
            width: 20px;
            height: 20px;
        }
    </style>
</head>

<body>

<header>
    <a href="homepage"><img src="./icons/logoWhite.png" alt="NeedInstruments Logo" style="height: 50px; margin-right: 10px;"></a>
    <nav class="horizontal">
        <form action="search-servlet">
            <label>
                <input type="text" name="search" class="search"
                       placeholder="Cerca tra più di 10.000 prodotti e 20 marchi">
            </label>
            <button type="submit" style="display: none">Cerca</button>
            <img src="./icons/search.png" alt="Search" style="height: 20px; cursor: pointer;">
        </form>
        <a class="horizontal" id="offerte" href="offerte-servlet">OFFERTE</a>

        <a href="cart-servlet">
            <img src="./icons/carrello.png" alt="Carrello" style="height: 20px; margin-right: 5px;">
        </a>
        <a href="preferiti-servlet">
            <img src="./icons/preferiti.png" alt="Preferiti" style="height: 20px; margin-right: 5px;">
        </a>
        <c:choose>
            <c:when test="${sessionScope.utente != null}">
                <p>Ciao <a href="profile-servlet"><b>${sessionScope.utente.nome}</b></a></p>
            </c:when>
            <c:otherwise>
                <a href="./login.jsp" id="login">ACCEDI</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<div class="container">

    <div class="section-title">Lista degli utenti</div><br>
    <div class="products">
        <c:choose>
            <c:when test="${not empty usersList}">
                <c:forEach var="utente" items="${usersList}">
                    <div class="product">
                        <h3>${utente.username}</h3>
                        <p>Nome: ${utente.nome}</p>
                        <p>Cognome: ${utente.cognome}</p>
                        <p>Data di nascita: ${utente.data_nascita}</p>
                        <p>Email: ${utente.email}</p>
                        <p>Admin: ${utente.admin}</p><br>

                        <form action="from-list-to-mod-user" target="_blank">
                            <input type="hidden" name="username" value="${utente.username}">
                            <input type="hidden" name="nome" value="${utente.nome}">
                            <input type="hidden" name="cognome" value="${utente.cognome}">
                            <input type="hidden" name="data_nascita" value="${utente.data_nascita}">
                            <input type="hidden" name="email" value="${utente.email}">
                            <input type="hidden" name="admin" value="${utente.admin}">
                            <button type="submit" class="modify">Modifica utente</button>
                        </form>

                        <form action="remove-user-servlet" target="_blank">
                            <input type="hidden" name="username" value="${utente.username}">
                            <button type="submit" class="remove">Rimuovi utente</button>
                        </form>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Non sono presenti utenti. <a href="new-user-form">Aggiungi un nuovo utente</a> .</p>
            </c:otherwise>
        </c:choose>
    </div>
    <br>
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
