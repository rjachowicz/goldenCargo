document.addEventListener('DOMContentLoaded', function () {
    const openModalBtn = document.getElementById('openTransportOrderModalBtn');

    if (openModalBtn) {
        openModalBtn.addEventListener('click', function () {
            const checkedBoxes = document.querySelectorAll('input[name="selectedClientOrders"]:checked');
            const selectedIds = Array.from(checkedBoxes).map(cb => cb.value);
            const hiddenField = document.getElementById('selectedClientOrders');
            hiddenField.value = selectedIds.join(',');
        });
    }
});
