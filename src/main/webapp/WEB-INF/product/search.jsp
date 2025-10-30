<%--@elvariable id="sortBy" type="model"--%>
<%--@elvariable id="search" type="model"--%>
<%--@elvariable id="prodotto" type="model.Prodotto"--%>
<%--@elvariable id="utente" type="model.Utente"--%>
<%--@elvariable id="prodotti" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<html>
<head>
    <title>${search} - Need Instruments</title>

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

        .sort-options {
            margin-bottom: 20px;
        }

        .product {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('sort-by').addEventListener('change', function() {
                fetch(`search-servlet?search=${search}&sortBy=${sortBy}`)
                    .then(response => response.text())
                    .then(data => {
                        let resultsElement = document.getElementById('results');
                        let tempDiv = document.createElement('div');
                        tempDiv.innerHTML = data;
                        resultsElement.innerHTML = tempDiv.querySelector('#results').innerHTML;
                    })
                    .catch(error => console.error('Error fetching data:', error));
            });
        });
    </script>

</head>
<body>
<header>
    <a href="homepage"><img src="./icons/logoWhite.png" alt="NeedInstruments Logo" style="height: 50px; margin-right: 10px;"></a>
    <nav class="horizontal">

        <form action="search-servlet">

            <label>
                <input type="text" name="search" class="search"
                       value="${search}">
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
            <a href="prodotti-servlet?strumento=Batteria&tipo=Elettrica">Batterie elettriche</a>
        </div>

        <a href="prodotti-servlet?strumento=Tastiera"><b>TASTIERE</b></a>
        <div class="submenu">
            <a href="prodotti-servlet?strumento=Tastiera&tipo=Pianoforte">Pianoforti</a>
            <a href="prodotti-servlet?strumento=Tastiera&tipo=Elettrica">Tastiere elettriche</a>
            <a href="prodotti-servlet?strumento=Tastiera&tipo=Amplificatore">Amplificatori per tastiere</a>
        </div>
    </nav>

    <div class="content">
        <div class="sort-options">
            <label for="sort-by">Ordina per:</label>
            <select id="sort-by" name="sort-by">
                <option value="prezzo-asc">Prezzo crescente</option>
                <option value="prezzo-desc">Prezzo decrescente</option>
                <option value="alfabetico">Ordine alfabetico</option>
            </select>
        </div>

        <div id="results">
            <div class="section-title">Risultati della ricerca: <b>${search}</b></div>
            <div class="products">
                <c:choose>
                    <c:when test="${prodotti != null && !prodotti.isEmpty()}">
                        <c:forEach var="prodotto" items="${prodotti}">
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
                                <form action="update-fav-servlet" target="_blank">
                                    <input type="hidden" name="id" value="${prodotto.id}">
                                    <button type="submit" class="add-fav">Aggiungi ai preferiti</button>
                                </form>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Nessun prodotto trovato per la ricerca: <b>${search}</b></p>
                    </c:otherwise>
                </c:choose>
            </div>
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        $('#sort-by').change(function() {
            let sortBy = $(this).val();
            $.ajax({
                url: 'search-servlet',
                type: 'GET',
                data: {
                    search: '${search}',
                    sortBy: sortBy
                },
                success: function(response) {
                    $('.products').html(response);
                },
                error: function(error) {
                    console.log("Error: ", error);
                }
            });
        });
    });
</script>

</body>
</html>
