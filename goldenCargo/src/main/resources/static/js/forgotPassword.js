document.getElementById("sendForgotPasswordEmail").addEventListener("click", function () {
    const email = document.getElementById("email").value;
    const csrfParameterName = document.querySelector("input[name=_csrf]").name;
    const csrfToken = document.querySelector("input[name=_csrf]").value;

    const loadingModal = new bootstrap.Modal(document.getElementById("loadingModal"));
    loadingModal.show();

    fetch("/users/forgot-password", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `${csrfParameterName}=${csrfToken}&email=${encodeURIComponent(email)}`
    })
        .then(response => {
            loadingModal.hide();
            if (response.ok) {
                const successModal = new bootstrap.Modal(document.getElementById("successModal"));
                successModal.show();

                const successButton = document.querySelector("#successModal .btn-primary");
                successButton.addEventListener("click", function () {
                    window.location.href = "/login";
                });
            } else {
                alert("Failed to send reset link. Please try again.");
            }
        })
        .catch(error => {
            loadingModal.hide();
            alert("An error occurred while sending the email.");
        });
});
