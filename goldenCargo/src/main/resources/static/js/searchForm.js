function filterTable(inputId, tableId) {
    let input, filter, table, tr, td, i, txtValue;
    input = document.getElementById(inputId);
    filter = input.value.toUpperCase();
    table = document.getElementById(tableId);
    tr = table.getElementsByTagName("tr");

    for (i = 1; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            tr[i].style.display = txtValue.toUpperCase().indexOf(filter) > -1 ? "" : "none";
        }
    }
}

function resetTable(inputId, tableId) {
    let input = document.getElementById(inputId);
    input.value = "";
    filterTable(inputId, tableId);
}

function selectItemAndReset(inputId, tableId) {
    resetTable(inputId, tableId);
}