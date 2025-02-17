document.addEventListener('DOMContentLoaded', function () {
    const openTransportOrderModalBtn = document.getElementById('openTransportOrderModalBtn');

    function updateClientOrderButtonState() {
        const selectedOrders = document.querySelectorAll('input[name="selectedClientOrders"]:checked');
        if (selectedOrders.length === 0) {
            openTransportOrderModalBtn.classList.add('disabled-btn');
            openTransportOrderModalBtn.removeAttribute('data-bs-toggle');
            openTransportOrderModalBtn.removeAttribute('data-bs-target');
        } else {
            openTransportOrderModalBtn.classList.remove('disabled-btn');
            openTransportOrderModalBtn.setAttribute('data-bs-toggle', 'modal');
            openTransportOrderModalBtn.setAttribute('data-bs-target', '#addTransportOrderModal');
        }
    }

    updateClientOrderButtonState();

    const clientCheckboxes = document.querySelectorAll('input[name="selectedClientOrders"]');
    clientCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', updateClientOrderButtonState);
    });

    openTransportOrderModalBtn.addEventListener('click', function (e) {
        const selectedOrders = document.querySelectorAll('input[name="selectedClientOrders"]:checked');
        if (selectedOrders.length === 0) {
            e.preventDefault();
            const errorModal = new bootstrap.Modal(document.getElementById('errorModalTransport'));
            errorModal.show();
        }
    });

    const openTransportModalBtn = document.getElementById('openTransportModal');

    function updateTransportButtonState() {
        const selectedTransport = document.querySelector('input[name="selectedTransportOrder"]:checked');
        if (!selectedTransport) {
            openTransportModalBtn.classList.add('disabled-btn');
            openTransportModalBtn.removeAttribute('data-bs-toggle');
            openTransportModalBtn.removeAttribute('data-bs-target');
        } else {
            openTransportModalBtn.classList.remove('disabled-btn');
            openTransportModalBtn.setAttribute('data-bs-toggle', 'modal');
            openTransportModalBtn.setAttribute('data-bs-target', '#addTransportModal');
        }
    }

    updateTransportButtonState();

    const transportRadios = document.querySelectorAll('input[name="selectedTransportOrder"]');
    transportRadios.forEach(function (radio) {
        radio.addEventListener('change', updateTransportButtonState);
    });

    openTransportModalBtn.addEventListener('click', function (e) {
        const selectedTransport = document.querySelector('input[name="selectedTransportOrder"]:checked');
        if (!selectedTransport) {
            e.preventDefault();
            const errorModalTransport = new bootstrap.Modal(document.getElementById('errorModalTransport'));
            errorModalTransport.show();
        }
    });
});
