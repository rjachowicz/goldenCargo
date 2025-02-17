document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('orderForm');

    form.addEventListener('submit', function(e) {
        const clientSelected = form.querySelector('input[type="radio"][name="clientId"]:checked');
        const goodSelected = form.querySelector('input[type="checkbox"][name="goodsIds"]:checked');

        let errors = [];

        if (!clientSelected) {
            errors.push("Please select the customer");
        }
        if (!goodSelected) {
            errors.push("Please select at least one goods");
        }

        if (errors.length > 0) {
            e.preventDefault();
            e.stopPropagation();
            const modalMessage = document.getElementById('validationModalMessage');
            modalMessage.innerHTML = errors.join("<br>");
            var validationModal = new bootstrap.Modal(document.getElementById('validationModal'));
            validationModal.show();
        }
    });
});
