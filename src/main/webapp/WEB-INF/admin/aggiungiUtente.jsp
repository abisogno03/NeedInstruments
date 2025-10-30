<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggiungi utente - NeedInstruments</title>
    <link href="./css/register.css"
          rel="stylesheet" type="text/css"/>
    <script>
        function validateForm() {
            let isValid = true;

            const fields = ['username', 'nome', 'cognome', 'data_nascita', 'email', 'password', 'conferma_password'];
            fields.forEach(field => {
                const input = document.getElementById(field);
                if (!input.value) {
                    input.classList.add('error');
                    isValid = false;
                } else {
                    input.classList.remove('error');
                }
            });

            const password = document.getElementById('password');
            const conferma_password = document.getElementById('conferma_password');
            if (password.value !== conferma_password.value) {
                password.classList.add('error');
                conferma_password.classList.add('error');
                document.getElementById('passwordError').innerText = 'Le password non coincidono.';
                isValid = false;
            } else {
                password.classList.remove('error');
                conferma_password.classList.remove('error');
                document.getElementById('passwordError').innerText = '';
            }

            return isValid;
        }
    </script>
</head>

<body>
<div class="register-container"><br><br><br><br><br><br>
    <a href="homepage"><img src="${pageContext.request.contextPath}/icons/logoBlack.png" alt="NeedInstruments Logo"></a>
    <h2>Creazione nuovo utente</h2>
    <form action="add-user-servlet" onsubmit="return validateForm();">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>

        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" required>

        <label for="cognome">Cognome</label>
        <input type="text" id="cognome" name="cognome" required>

        <label for="data_nascita">Data di Nascita</label>
        <input type="date" id="data_nascita" name="data_nascita" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>

        <label for="admin">Admin</label><br>
        <input type="checkbox" id="admin" name="admin"><br>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" value="1234" required>

        <label for="conferma_password">Conferma Password</label>
        <input type="password" id="conferma_password" name="conferma_password" value="1234" required>
        <div id="passwordError" class="error-message"></div>

        <input type="submit" value="Salva nuovo utente">
    </form>
</div>
</body>
</html>
