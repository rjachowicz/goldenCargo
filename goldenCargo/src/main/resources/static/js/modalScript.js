document.addEventListener("DOMContentLoaded", () => {
    function openModal(button, modalId, endpoint) {
        const entityId = button.getAttribute("data-id");
        if (!entityId) {
            console.error("Entity ID is null or undefined.");
            return;
        }

        const modalContent = document.querySelector(`#${modalId} .modal-content`);
        if (!modalContent) {
            console.error(`Modal content not found for ID: ${modalId}`);
            return;
        }

        fetch(`/${endpoint}/${entityId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to load modal content. Status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                modalContent.innerHTML = html;
                const modal = new bootstrap.Modal(document.getElementById(modalId));
                modal.show();
            })
            .catch(error => console.error("Error loading modal content:", error));
    }

    document.querySelectorAll("[data-modal-action]").forEach(button => {
        button.addEventListener("click", () => {
            const modalId = button.getAttribute("data-modal-id");
            const endpoint = button.getAttribute("data-endpoint");
            openModal(button, modalId, endpoint);
        });
    });
});
