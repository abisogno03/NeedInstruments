<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Modifica dati - Need Instruments</title>
    <link href="./css/general.css" rel="stylesheet" type="text/css">
    <link href="./css/navHor.css" rel="stylesheet" type="text/css">
    <!--<link href="./css/navVer.css" rel="stylesheet" type="text/css">-->
    <link href="./css/footer.css" rel="stylesheet" type="text/css">
    <link href="./css/profile.css" rel="stylesheet" type="text/css">
    <link href="./css/mod.css" rel="stylesheet" type="text/css">

    <script>
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
        <h2>Modifica i dati</h2>
        <form action="mod-datas-servlet" onsubmit="return validateForm();">

             <label for="nome">Nome</label>
             <input type="text" id="nome" name="nome" value="${sessionScope.utente.nome}" required><br>

             <label for="cognome">Cognome</label>
             <input type="text" id="cognome" name="cognome" value="${sessionScope.utente.cognome}" required><br>

             <label for="data_nascita">Data di Nascita</label>
             <input type="date" id="data_nascita" name="data_nascita" value="${sessionScope.utente.data_nascita}" required><br>

             <label for="email">Email</label>
             <input type="email" id="email" name="email" value="${sessionScope.utente.email}" required><br>

             <input type="submit" value="Salva">
         </form>
    </div>
</div>

</body>
</html>
