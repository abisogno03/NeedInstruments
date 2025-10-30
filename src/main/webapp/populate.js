function populateTipo() {

    let strumento = document.getElementById("strumento").value;
    let tipoDropdown = document.getElementById("tipo");

    // Rimuovi le opzioni attuali
    tipoDropdown.innerHTML = "";

    // Ottieni le opzioni per il tipo dall'oggetto JSON
    let tipoOptions = productTypes[strumento];

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
    let marcaOptions = productTypes[strumento][tipo];

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
