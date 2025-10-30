<%--@elvariable id="utente" type="model.Utente"--%>
<%--@elvariable id="listaProdotti" type="model"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Preferiti - NeedInstruments</title>

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

    <div class="section-title">I tuoi preferiti</div><br>
    <div class="content">
        <div class="products">
            <c:choose>
                <c:when test="${not empty listaProdotti}">
                    <c:forEach var="prodotto" items="${listaProdotti}">
                        <div class="product">
                            <h3>${prodotto.marca} ${prodotto.modello}</h3>
                            <img src="${pageContext.request.contextPath}/prod_images/${prodotto.image}" alt="${prodotto.marca}" class="brand"><br>
                            <c:choose>
                                <c:when test="${prodotto.sconto != 0}">
                                    <s>€${prodotto.prezzo}</s> <b><span
                                        style="color: #8b0000; ">€${prodotto.sconto}</span></b>
                                </c:when>
                                <c:otherwise>
                                    <b>€${prodotto.prezzo}</b>
                                </c:otherwise>
                            </c:choose>
                            <br><br>
                            <form action="update-cart-servlet" target="_blank">
                                <input type="hidden" name="id" value="${prodotto.id}">
                                <button type="submit" class="add-cart">Aggiungi al carrello</button>
                            </form>

                            <form action="remove-from-fav-servlet" target="_blank">
                                <input type="hidden" name="id" value="${prodotto.id}">
                                <button type="submit" class="remove-cart">Rimuovi dai preferiti</button>
                            </form>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>Non sono presenti prodotti nei preferiti. Inizia a cercare per aggiungerne uno.</p>
                </c:otherwise>
            </c:choose>
        </div>
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
