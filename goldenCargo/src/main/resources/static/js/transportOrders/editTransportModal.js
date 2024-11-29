document.addEventListener("DOMContentLoaded", () => {
    const editModal = document.getElementById("editTransportModal");
    const modalContent = editModal.querySelector(".modal-content");

    window.openEditModal = function (button) {
        const transportId = button.getAttribute("data-id");
        fetch(`/transports/edit/${transportId}`)
            .then(response => response.text())
            .then(html => {
                modalContent.innerHTML = html;
                new bootstrap.Modal(editModal).show();
            })
            .catch(err => console.error(err));
    };
});
