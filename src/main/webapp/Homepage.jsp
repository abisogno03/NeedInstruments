<%--@elvariable id="utente" type="model.Utente"--%>
<%--@elvariable id="topSoldProducts" type="java.util.List"--%>
<%--@elvariable id="lastSoldProducts" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Need Instruments</title>
    <link rel="stylesheet" href="./css/general.css">
    <link rel="stylesheet" href="./css/navHor.css">
    <link rel="stylesheet" href="./css/navVer.css">
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
            <a href="prodotti-servlet?strumento=Basso&tipo=Corde">Corde per bassi</a>
            <a href="prodotti-servlet?strumento=Basso&tipo=Amplificatore">Amplificatori per bassi</a>
        </div>

        <a href="prodotti-servlet?strumento=Percussione"><b>PERCUSSIONI</b></a>
        <div class="submenu">
            <a href="prodotti-servlet?strumento=Batteria&tipo=Acustica">Batterie acustiche</a>
            <a href="prodotti-servlet?strumento=Batteria&tipo=Elettronica">Batterie elettronica</a>
        </div>

        <a href="prodotti-servlet?strumento=Tastiera"><b>TASTIERE</b></a>
        <div class="submenu">
            <a href="prodotti-servlet?strumento=Tastiera&tipo=Digitale">Tastiere digitali</a>
            <a href="prodotti-servlet?strumento=Tastiera&tipo=Elettrica">Tastiere elettriche</a>
            <a href="prodotti-servlet?strumento=Tastiera&tipo=Amplificatore">Amplificatori per tastiere</a>
        </div>
    </nav>

    <div class="content">
        <div class="section-title" id="brand-title">I nostri marchi principali</div>
        <div class="brands">
            <a href="https://it.wikipedia.org/wiki/Fender"><img src="./brands/fender.png" alt="Fender" class="brand"></a>
            <a href="https://it.wikipedia.org/wiki/Gibson_Guitar_Corporation"><img src="./brands/gibson.png" alt="Gibson" class="brand"></a>
            <a href="https://en.wikipedia.org/wiki/Jackson_Guitars"><img src="./brands/jackson.png" alt="Jackson" class="brand"></a>
            <img src="./brands/jose-torres.png" alt="JoseTorres" class="brand">
        </div>

        <div class="section-title">Le ultime vendite</div>
        <div class="products">
            <c:forEach var="lastSold" items="${lastSoldProducts}">
                <div class="product">
                    <h3>${lastSold.marca} ${lastSold.modello}</h3>
                    <img src="${pageContext.request.contextPath}/prod_images/${lastSold.image}" alt="${lastSold.marca}" class="brand"><br>
                    <c:choose>
                        <c:when test="${lastSold.sconto != 0}">
                            <s>${lastSold.prezzo}</s> <b><span style="color: #8b0000; ">${lastSold.sconto}</span></b>
                        </c:when>
                        <c:otherwise>
                            <b>€${lastSold.prezzo}</b>
                        </c:otherwise>
                    </c:choose>
                    <br><br>
                    <form action="update-cart-servlet" target="_blank">
                        <input type="hidden" name="id" value="${lastSold.id}">
                        <button type="submit" class="add-cart">Aggiungi al carrello</button>
                    </form>
                    <form action="update-fav-servlet" target="_blank">
                        <input type="hidden" name="id" value="${lastSold.id}">
                        <button type="submit" class="add-fav">Aggiungi ai preferiti</button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <div class="section-title">Più venduti</div>
        <div class="products">
            <c:forEach var="topsold" items="${topSoldProducts}">
                <div class="product">
                    <h3>${topsold.marca} ${topsold.modello}</h3>
                    <img src="./prod_images/${topsold.image}" alt="${topsold.marca}" class="brand"><br>
                    <c:choose>
                        <c:when test="${topsold.sconto != 0}">
                            <s>${topsold.prezzo}</s> <b><span style="color: #8b0000; ">${topsold.sconto}</span></b>
                        </c:when>
                        <c:otherwise>
                            <b>€${topsold.prezzo}</b>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <form action="update-cart-servlet" target="_blank">
                        <input type="hidden" name="id" value="${topsold.id}">
                        <button type="submit" class="add-cart">Aggiungi al carrello</button>
                    </form>
                    <form action="update-fav-servlet" target="_blank">
                        <input type="hidden" name="id" value="${topsold.id}">
                        <button type="submit" class="add-fav">Aggiungi ai preferiti</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
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
