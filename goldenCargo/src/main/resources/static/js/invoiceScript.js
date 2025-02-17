(function () {
    'use strict';

    function generateUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }

    function initValidation(form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    }

    document.addEventListener('DOMContentLoaded', function () {
        var today = new Date().toISOString().split('T')[0];
        document.getElementById('dateIssued').setAttribute('min', today);
        document.getElementById('dueDate').setAttribute('min', today);
        var invoiceNumberInput = document.getElementById('invoiceNumber');
        if (invoiceNumberInput && !invoiceNumberInput.value) {
            invoiceNumberInput.value = 'FV-' + generateUUID();
        }
        var forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms).forEach(function (form) {
            initValidation(form);
        });
    });
})();