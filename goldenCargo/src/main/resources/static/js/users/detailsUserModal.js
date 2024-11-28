document.addEventListener("DOMContentLoaded", () => {
    const detailsModal = document.getElementById("detailsUserModal");
    const modalContent = detailsModal.querySelector(".modal-content");

    window.openDetailsModal = function (button) {
        const userId = button.getAttribute("data-id");

        fetch(`/users/details/${userId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Failed to load details modal for user ID: ${userId}`);
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
                alert("An error occurred while loading the user details. Please try again.");
            });
    };
});
