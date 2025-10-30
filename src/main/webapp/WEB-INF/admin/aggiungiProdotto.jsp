<%--@elvariable id="jsonProductTypes" type="model"--%>
<%--@elvariable id="productTypes" type="model"--%>
<%--@elvariable id="strumento" type="model"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Aggiungi prodotto - Need Instrument</title>
    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <!--<link href="./css/navVer.css" rel="stylesheet" type="text/css">-->
    <link href="./css/footer.css" rel="stylesheet" type="text/css">
    <link href="./css/profile.css" rel="stylesheet" type="text/css">
    <link href="./css/mod.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/populate.js"></script>

    <script type="text/javascript">
        let productTypes = ${jsonProductTypes};
    </script>
</head>
<body>

<div class="content">
    <div class="mod-container">
        <br><br>
        <a href="homepage"><img src="${pageContext.request.contextPath}/icons/logoBlack.png" alt="NeedInstruments Logo"></a>
        <h2>Aggiungi prodotto</h2>
        <form action="add-prod-servlet" method="post" enctype="multipart/form-data">

            <label for="strumento">Strumento</label>
            <select id="strumento" name="strumento" onchange="populateTipo()">
                <option value="Chitarra">Chitarra</option>
                <option value="Basso">Basso</option>
                <option value="Batteria">Batteria</option>
                <option value="Tastiera">Tastiera</option>
            </select><br>

            <label for="tipo">Tipo</label>
            <select id="tipo" name="tipo" onchange="populateMarca()" required>
                <!-- popolazione dinamica javascript -->
            </select><br>

            <label for="marca">Marca</label>
            <select id="marca" name="marca" required>
                <!-- popolazione dinamica javascript -->
            </select><br>

            <label for="modello">Modello</label>
            <input type="text" id="modello" name="modello" required><br>

            <label for="prezzo">Prezzo</label>
            <input type="number" id="prezzo" name="prezzo" step="0.01" required><br>

            <label for="quantita">Quantità</label>
            <input type="number" id="quantita" name="quantita" required><br><br>

            <label for="image">Immagine</label>
            <input type="file" id="image" name="image" accept="image/*">

            <input type="submit" value="Salva">
        </form>
    </div>
</div>

<script type="text/javascript">
    function populateTipo() {
        let strumento = document.getElementById("strumento").value;
        let tipoDropdown = document.getElementById("tipo");

        // Rimuovi le opzioni attuali
        tipoDropdown.innerHTML = "";

        // Ottieni le opzioni per il tipo dall'oggetto JSON
        let tipoOptions = productTypes.types[strumento];

        // Aggiungi le nuove opzioni a tipo
        for (let tipo in tipoOptions) {
            let optionElement = document.createElement("option");
            optionElement.value = tipo;
            optionElement.textContent = tipo;
            tipoDropdown.appendChild(optionElement);
        }

        // Chiamata a populateMarca per aggiornare le opzioni della marca
        populateMarca();
    }

    function populateMarca() {
        let strumento = document.getElementById("strumento").value;
        let tipo = document.getElementById("tipo").value;
        let marcaDropdown = document.getElementById("marca");

        // Rimuovi le opzioni attuali
        marcaDropdown.innerHTML = "";

        // Ottieni le opzioni per la marca dall'oggetto JSON
        let marcaOptions = productTypes.types[strumento][tipo];

        // Aggiungi le nuove opzioni a marca
        marcaOptions.forEach(function(option) {
            let optionElement = document.createElement("option");
            optionElement.value = option;
            optionElement.textContent = option;
            marcaDropdown.appendChild(optionElement);
        });
    }

    // Chiamata iniziale per popolare il menu tipo all'avvio della pagina
    document.addEventListener("DOMContentLoaded", function() {
        populateTipo();
    });
</script>

</body>
</html>
