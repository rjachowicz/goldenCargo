document.addEventListener("DOMContentLoaded", () => {
    const modalContent = document.querySelector("#editLogisticsModal .modal-content");

    function openEditModal(button) {
        const logisticId = button.getAttribute("data-id");
        if (!logisticId) {
            console.error("Logistic ID is null or undefined.");
            return;
        }

        fetch(`/logistics/edit/${logisticId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to load edit modal. Status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                modalContent.innerHTML = html;
                const modal = new bootstrap.Modal(document.getElementById("editLogisticsModal"));
                modal.show();
            })
            .catch(error => console.error("Error loading modal content:", error));
    }

    document.querySelectorAll("button[onclick^='openEditModal']").forEach(button => {
        button.addEventListener("click", () => openEditModal(button));
    });
});
