<%--@elvariable id="utente" type="model.Utente"--%>
<%--@elvariable id="listaProdotti" type="model"--%>
<%--@elvariable id="prodotto" type="model.Prodotto"--%>
<%--@elvariable id="errorMessage" type="controller"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagamento - NeedInstruments</title>
    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <link href="./css/navVer.css" rel="stylesheet" type="text/css">
    <link href="./css/footer.css" rel="stylesheet" type="text/css">
    <link href="./css/pagamento.css" rel="stylesheet" type="text/css">
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

    <script>
        function validateForm() {
            let isValid = true;
            const fields = ['carta', 'cvc', 'via'];
            fields.forEach(field => {
                const input = document.getElementById(field);
                if (!input.value) {
                    input.classList.add('error');
                    isValid = false;
                } else {
                    input.classList.remove('error');
                }
            });
            return isValid;
        }

        function formatCardNumber(input) {
            let value = input.value.replace(/\s+/g, '');
            let formattedValue = '';
            for (let i = 0; i < value.length; i++) {
                if (i > 0 && i % 4 === 0) {
                    formattedValue += ' ';
                }
                formattedValue += value[i];
            }
            input.value = formattedValue;
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            document.getElementById('carta').addEventListener('input', function () {
                formatCardNumber(this);
            });
        });

        function updateTotal() {
            let total = 0;
            const quantities = document.querySelectorAll('input[name^="quantita-"]');
            quantities.forEach(input => {
                const price = parseFloat(input.dataset.discountPrice) || parseFloat(input.dataset.price);
                const quantity = parseInt(input.value);
                total += price * quantity;
            });
            document.getElementById('total').innerText = total.toFixed(2);
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            document.getElementById('carta').addEventListener('input', function () {
                formatCardNumber(this);
            });
            const quantities = document.querySelectorAll('input[name^="quantita-"]');
            quantities.forEach(input => {
                input.addEventListener('input', updateTotal);
            });
            updateTotal(); // Initial total calculation
        });

    </script>

</head>
<body>
<header>
    <a href="homepage"><img src="./icons/logoWhite.png" alt="NeedInstruments Logo" style="height: 50px; margin-right: 10px;"></a>
    <nav class="horizontal">
        <form action="search-servlet">
            <label>
                <input type="text" name="search" class="search" placeholder="Cerca tra più di 10.000 prodotti e 20 marchi">
            </label>
            <button type="submit">Cerca</button>
        </form>
        <a class="horizontal" id="offerte" href="#">OFFERTE</a>
        <a class="horizontal" id="nuoviarrivi" href="#">NUOVI ARRIVI</a>
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
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<div class="container">
    <a href="homepage"><img src="${pageContext.request.contextPath}/icons/logoBlack.png" alt="NeedInstruments Logo" class="logo"></a>
    <div class="section-title">Riepilogo degli acquisti</div><br>
    <div class="content">
        <div class="products">
            <c:choose>
                <c:when test="${not empty listaProdotti}">
                    <form action="acquisto-servlet" method="post" onsubmit="return validateForm();">
                        <c:forEach var="prodotto" items="${listaProdotti}">
                            <div class="product">
                                <h3>${prodotto.marca} ${prodotto.modello}</h3>
                                <img src="${pageContext.request.contextPath}/prod_images/${prodotto.image}" alt="${prodotto.marca}" class="brand">
                                <c:choose>
                                    <c:when test="${prodotto.sconto != 0}">
                                        <s>€${prodotto.prezzo}</s> <b><span style="color: #8b0000; ">€${prodotto.sconto}</span></b>
                                    </c:when>
                                    <c:otherwise>
                                        <b>€${prodotto.prezzo}</b>
                                    </c:otherwise>
                                </c:choose>
                                <br><label for="quantita-${prodotto.id}">Quantità:</label>
                                <br><input type="number" id="quantita-${prodotto.id}" name="quantita-${prodotto.id}" min="1" max="${prodotto.quantita}" value="1"
                                       data-price="${prodotto.prezzo}" data-discount-price="${prodotto.sconto}" />
                            </div>
                        </c:forEach>

                        <img src="icons/credit_cards.png" alt="credit-card"><br>
                        <label for="carta">Carta di Credito:</label>
                        <input type="text" id="carta" name="carta" pattern="\d{4} \d{4} \d{4} \d{4}" required>
                        <br>
                        <label for="cvc">CVC:</label>
                        <input type="text" id="cvc" name="cvc" pattern="\d{3}" required>
                        <br>
                        <label for="via">Via:</label>
                        <input type="text" id="via" name="via" required>
                        <br>
                        Totale: €<b id="total"></b>
                        <input type="hidden" id="listaProdotti" name="listaProdotti" value="${listaProdotti}">
                        <input type="submit" value="Conferma pagamento">
                        <c:if test="${not empty errorMessage}">
                            <p style="color: red;">${errorMessage}</p>
                        </c:if>
                    </form>
                </c:when>
                <c:otherwise>
                    <p>Non è possibile effettuare l'acquisto. Non sono presenti prodotti nel carrello.<br>
                        Ritorna al <a href="cart-servlet">carrello</a> </p>
                </c:otherwise>
            </c:choose>
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
