document.addEventListener("DOMContentLoaded", () => {
    const editModal = document.getElementById("editLocationModal");
    const modalContent = editModal.querySelector(".modal-content");

    window.openEditModal = function (button) {
        const locationId = button.getAttribute("data-id");

        fetch(`/locations/edit/${locationId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Failed to load edit modal for location ID: ${locationId}`);
                }
                return response.text();
            })
            .then((html) => {
                modalContent.innerHTML = html;
                const bootstrapModal = new bootstrap.Modal(editModal);
                bootstrapModal.show();
            })
            .catch((error) => {
                console.error("Error loading modal content:", error);
                alert("An error occurred while loading the modal. Please try again.");
            });
    };
});
