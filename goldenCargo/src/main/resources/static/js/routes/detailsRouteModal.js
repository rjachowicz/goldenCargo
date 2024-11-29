document.addEventListener("DOMContentLoaded", () => {
    const modalContent = document.querySelector("#detailsRouteModal .modal-content");

    function openDetailsModal(button) {
        const routeId = button.getAttribute("data-id");
        if (!routeId) {
            console.error("Route ID is null or undefined.");
            return;
        }

        fetch(`/routes/details/${routeId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to load details modal. Status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                modalContent.innerHTML = html;
                const modal = new bootstrap.Modal(document.getElementById("detailsRouteModal"));
                modal.show();
            })
            .catch(error => console.error("Error loading modal content:", error));
    }

    document.querySelectorAll("button[onclick^='openDetailsModal']").forEach(button => {
        button.addEventListener("click", () => openDetailsModal(button));
    });
});
