document.addEventListener("DOMContentLoaded", () => {
    const detailsModal = document.getElementById("detailsTransportModal");
    const modalContent = detailsModal.querySelector(".modal-content");

    window.openDetailsModal = function (button) {
        const transportId = button.getAttribute("data-id");
        fetch(`/transports/details/${transportId}`)
            .then(response => response.text())
            .then(html => {
                modalContent.innerHTML = html;
                new bootstrap.Modal(detailsModal).show();
            })
            .catch(err => console.error(err));
    };
});
