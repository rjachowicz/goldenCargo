document.addEventListener('DOMContentLoaded', function () {
    const openModalBtn = document.getElementById('openTransportModal');

    if (openModalBtn) {
        openModalBtn.addEventListener('click', function () {
            const selectedRadio = document.querySelector('input[name="selectedTransportOrder"]:checked');
            const hiddenField = document.getElementById('transportOrderId');
            hiddenField.value = selectedRadio.value;
        });
    }
});
