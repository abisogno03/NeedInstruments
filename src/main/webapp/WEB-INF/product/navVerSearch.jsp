<%--@elvariable id="tipo" type="controller"--%>
<%--@elvariable id="strumento" type="controller"--%>
<%--@elvariable id="prodotto" type="model.Prodotto"--%>
<%--@elvariable id="utente" type="model.Utente"--%>
<%--@elvariable id="prodotti" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<html>
<head>
    <title>${strumento} ${tipo} - Need Instruments</title>

    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <link href="./css/navVer.css" rel="stylesheet" type="text/css">
    <link href="./css/footer.css" rel="stylesheet" type="text/css">

    <style>
        .brand {
            width: 150px;
            height: 120px;
        }

        .icon{
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
                       placeholder="Cerca tra più di 10.000 prodotti e 20 marchi ">
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
                <p>Ciao <a href="profile-servlet"><b>${sessionScope.utente.nome}</b></a> </p>
            </c:when>
            <c:otherwise>
                <a href="./login.jsp" id="login">ACCEDI</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<div class="container">
    <div class="content">

        <nav class="vertical">
            <a href="prodotti-servlet?strumento=Chitarra"><b>CHITARRE</b></a>
            <div class="submenu">
                <a href="prodotti-servlet?strumento=Chitarra&tipo=Elettrica">Chitarre Elettriche</a>
                <a href="prodotti-servlet?strumento=Chitarra&tipo=Acustica">Chitarre Acustiche</a>
                <a href="prodotti-servlet?strumento=Chitarra&tipo=Classica">Chitarre Classiche</a>
                <a href="prodotti-servlet?strumento=Chitarra&tipo=Corde">Corde per chitarre</a>
                <a href="prodotti-servlet?strumento=Chitarra&tipo=Amplificatore">Amplificatori per chitarra</a>
            </div>

            <a href="prodotti-servlet?strumento=Basso"><b>BASSI</b></a>
            <div class="submenu">
                <a href="prodotti-servlet?strumento=Basso&tipo=Elettrico">Bassi Elettrici</a>
                <a href="prodotti-servlet?strumento=Basso&tipo=Acustico">Bassi Acustici</a>
                <a href="prodotti-servlet?strumento=Basso&tipo=Corda">Corde per bassi</a>
                <a href="prodotti-servlet?strumento=Basso&tipo=Amplificatore">Amplificatori per bassi</a>
            </div>

            <a href="prodotti-servlet?strumento=Percussione"><b>PERCUSSIONI</b></a>
            <div class="submenu">
                <a href="prodotti-servlet?strumento=Batteria&tipo=Acustica">Batterie acustiche</a>
                <a href="prodotti-servlet?strumento=Batteria&tipo=Elettronica">Batterie elettronica</a>
            </div>

            <a href="prodotti-servlet?strumento=Tastiera"><b>TASTIERE</b></a>
            <div class="submenu">
                <a href="prodotti-servlet?strumento=Tastiera&tipo=Pianoforte">Pianoforti</a>
                <a href="prodotti-servlet?strumento=Tastiera&tipo=Elettronica">Tastiere elettroniche</a>
                <a href="prodotti-servlet?strumento=Tastiera&tipo=Amplificatore">Amplificatori per tastiere</a>
            </div>
        </nav>

    <div class="section-title">${strumento} ${tipo}</div>
    <div class="products">
        <c:forEach var="prodotto" items="${prodotti}">
            <div class="product">
                <h3>${prodotto.marca} ${prodotto.modello}</h3>
                <img src="${pageContext.request.contextPath}/prod_images/${prodotto.image}" alt="${prodotto.marca}" class="brand"><br>
                <c:choose>
                    <c:when test="${prodotto.sconto != 0}">
                        <s>€${prodotto.prezzo}</s> <b><span style="color: #8b0000; ">€${prodotto.sconto}</span></b>
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
                <form action="update-fav-servlet" target="_blank">
                    <input type="hidden" name="id" value="${prodotto.id}">
                    <button type="submit" class="add-fav">Aggiungi ai preferiti</button>
                </form>
            </div>
        </c:forEach>
    </div>

</div>

</div>
</body>
</html>
