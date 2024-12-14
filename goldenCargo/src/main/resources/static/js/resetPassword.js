document.querySelector("form").addEventListener("submit", function (event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action, {
        method: "POST",
        body: new URLSearchParams(formData),
    })
        .then(response => {
            if (response.ok) {
                const successModal = new bootstrap.Modal(document.getElementById("successModal"));
                successModal.show();

                const successButton = document.querySelector("#successModal .btn-primary");
                successButton.addEventListener("click", function () {
                    window.location.href = "/login";
                });
            } else {
                alert("Failed to change password. Please try again.");
            }
        })
        .catch(error => {
            alert("An error occurred while changing the password.");
        });
});
