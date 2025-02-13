(function () {
    'use strict'
    function initValidation(form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    }

    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function (form) {
        initValidation(form);
    });

    document.addEventListener('shown.bs.modal', function (event) {
        var modal = event.target;
        var modalForms = modal.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(modalForms).forEach(function (form) {
            initValidation(form);
        });
    });
})();
