<%--@elvariable id="search" type="model"--%>
<%--@elvariable id="prodotti" type="model"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<div class="products">
    <c:choose>
        <c:when test="${prodotti != null && !prodotti.isEmpty()}">
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
        </c:when>
        <c:otherwise>
            <p>Nessun prodotto trovato per la ricerca: ${search}</p>
        </c:otherwise>
    </c:choose>
</div>
