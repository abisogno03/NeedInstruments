<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Modifica dati - Need Instruments</title>
    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <link href="./css/footer.css" rel="stylesheet" type="text/css">
    <link href="./css/profile.css" rel="stylesheet" type="text/css">
    <link href="./css/mod.css" rel="stylesheet" type="text/css">

    <script>

        function verificaPasswordVecchia() {
            let passwordVecchiaInserita = document.getElementById("passwordVecchia").value;
            let passwordVecchiaReale = "<%= request.getAttribute("passwordVecchia") %>";

            if (passwordVecchiaInserita !== passwordVecchiaReale) {
                alert("La vecchia password inserita è errata.");
                return false;
            }
            return true;
        }

        function validateForm() {
            let isValid = true;

            const fields = ['nome', 'cognome', 'data_nascita', 'email'];
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
    </script>
</head>
<body>

<div class="content">
    <div class="mod-container">
        <a href="homepage"><img src="${pageContext.request.contextPath}/icons/logoBlack.png" alt="NeedInstruments Logo"></a>
        <h3>Modifica la password</h3>
        <form action="mod-pass-servlet" onsubmit="return verificaPasswordVecchia()">
            <label for="passwordVecchia">Vecchia Password:</label>
            <input type="password" id="passwordVecchia" name="passwordVecchia" required><br><br>

            <label for="passwordNuova">Nuova Password:</label>
            <input type="password" id="passwordNuova" name="passwordNuova" required><br><br>

            <label for="confermaPasswordNuova">Conferma Nuova Password:</label>
            <input type="password" id="confermaPasswordNuova" name="confermaPasswordNuova" required><br><br>

            <button type="submit">Cambia Password</button>
        </form>
    </div>
</div>

</body>
</html>
