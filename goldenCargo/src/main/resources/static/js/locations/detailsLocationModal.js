document.addEventListener("DOMContentLoaded", () => {
    const detailsModal = document.getElementById("detailsLocationModal");
    const modalContent = detailsModal.querySelector(".modal-content");

    window.openDetailsModal = function (button) {
        const locationId = button.getAttribute("data-id");

        fetch(`/locations/details/${locationId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Failed to load details modal for location ID: ${locationId}`);
                }
                return response.text();
            })
            .then((html) => {
                modalContent.innerHTML = html;
                const bootstrapModal = new bootstrap.Modal(detailsModal);
                bootstrapModal.show();
            })
            .catch((error) => {
                console.error("Error loading details modal content:", error);
                alert("An error occurred while loading the location details. Please try again.");
            });
    };
});
