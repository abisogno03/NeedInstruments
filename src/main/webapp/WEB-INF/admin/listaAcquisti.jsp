<%--@elvariable id="productList" type="controller"--%>
<%--@elvariable id="prodottos" type="controller"--%>
<%--@elvariable id="prodotto" type="model.Prodotto"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Lista acquisti - Need Instruments</title>

    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <link href="./css/navVer.css" rel="stylesheet" type="text/css">
    <link href="./css/footer.css" rel="stylesheet" type="text/css">

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
            <button type="submit">Cerca</button>
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

    <div class="section-title">Lista degli acquisti</div><br>
    <div class="products">
        <c:choose>
            <c:when test="${not empty productList && not empty prodottos}">
                <c:forEach var="acquisto" items="${productList}" varStatus="status">
                    <div class="product">
                        <c:set var="prodotto" value="${prodottos[status.index]}"/>
                        <h3>${prodotto.marca} ${prodotto.modello}</h3>
                        <img src="${pageContext.request.contextPath}/prod_images/${prodotto.image}" alt="${prodotto.marca}" class="brand"><br>
                        <p>Strumento: <b>${prodotto.strumento}</b></p>
                        <p>Tipo: <b>${prodotto.tipo}</b></p>
                        <c:choose>
                            <c:when test="${prodotto.sconto != 0}">
                                <s>€${prodotto.prezzo}</s> <b><span style="color: #8b0000; ">€${prodotto.sconto}</span></b>
                            </c:when>
                            <c:otherwise>
                                <b>€${prodotto.prezzo}</b>
                            </c:otherwise>
                        </c:choose>
                        <p>Quantità: <b>${acquisto.numero}</b></p>
                        <p>Acquirente: <b>${acquisto.utente.username}</b></p>
                        <p>Data acquisto: <b>${acquisto.data}</b></p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Non sono presenti acquisti. Torna al <a href="profile-servlet">profilo</a> .</p>
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
