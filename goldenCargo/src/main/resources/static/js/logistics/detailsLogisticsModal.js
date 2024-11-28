document.addEventListener("DOMContentLoaded", () => {
    const modalContent = document.querySelector("#detailsLogisticsModal .modal-content");

    function openDetailsModal(button) {
        const logisticId = button.getAttribute("data-id");
        if (!logisticId) {
            console.error("Logistic ID is null or undefined.");
            return;
        }

        fetch(`/logistics/details/${logisticId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to load details modal. Status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                modalContent.innerHTML = html;
                const modal = new bootstrap.Modal(document.getElementById("detailsLogisticsModal"));
                modal.show();
            })
            .catch(error => console.error("Error loading modal content:", error));
    }

    document.querySelectorAll("button[onclick^='openDetailsModal']").forEach(button => {
        button.addEventListener("click", () => openDetailsModal(button));
    });
});
