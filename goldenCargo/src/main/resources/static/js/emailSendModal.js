document.getElementById("sendEmailButton").addEventListener("click", function () {
    const email = document.getElementById("emailAddress").value;
    const vehicleId = document.getElementById("vehicleId").value;
    const clientId = document.getElementById("clientId").value;

    const emailModal = bootstrap.Modal.getInstance(document.getElementById("emailModal"));
    const loadingModal = new bootstrap.Modal(document.getElementById("loadingModal"));

    emailModal.hide();

    loadingModal.show();

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    fetch("/email/send-pdf", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            to: email,
            vehicleId: vehicleId,
            clientId: clientId
        })
    }).then(response => {
        loadingModal.hide();
        if (response.ok) {
            const successModal = new bootstrap.Modal(document.getElementById("successModal"));
            successModal.show();
        } else {
            alert("Failed to send email. Please try again.");
        }
    }).catch(error => {
        loadingModal.hide();
        alert("An error occurred while sending the email.");
    });
});