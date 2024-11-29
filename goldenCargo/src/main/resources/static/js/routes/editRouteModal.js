document.addEventListener("DOMContentLoaded", () => {
    const modalContent = document.querySelector("#editRouteModal .modal-content");

    function openEditModal(button) {
        const routeId = button.getAttribute("data-id");
        if (!routeId) {
            console.error("Route ID is null or undefined.");
            return;
        }

        fetch(`/routes/edit/${routeId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to load edit modal. Status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                modalContent.innerHTML = html;
                const modal = new bootstrap.Modal(document.getElementById("editRouteModal"));
                modal.show();
            })
            .catch(error => console.error("Error loading modal content:", error));
    }

    document.querySelectorAll("button[onclick^='openEditModal']").forEach(button => {
        button.addEventListener("click", () => openEditModal(button));
    });
});
