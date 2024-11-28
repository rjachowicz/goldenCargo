document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("editUserModal");
    const modalContent = modal.querySelector(".modal-content");

    window.openEditModal = function (button) {
        const userId = button.getAttribute("data-id");

        fetch(`/users/edit/${userId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Failed to load edit modal for user ID: ${userId}`);
                }
                return response.text();
            })
            .then((html) => {
                modalContent.innerHTML = html;
                const bootstrapModal = new bootstrap.Modal(modal);
                bootstrapModal.show();
                setupPasswordChange();
            })
            .catch((error) => {
                console.error("Error loading modal content:", error);
                alert("An error occurred while loading the modal. Please try again.");
            });
    };

    function setupPasswordChange() {
        const togglePasswordButton = document.getElementById("togglePasswordChange");
        const passwordFields = document.querySelectorAll(".password-change");
        const newPasswordInput = document.getElementById("newPassword");
        const toggleVisibilityButton = document.getElementById("togglePasswordVisibility");
        const generatePasswordButton = document.getElementById("generatePassword");

        if (togglePasswordButton) {
            togglePasswordButton.addEventListener("click", () => {
                passwordFields.forEach((field) => field.classList.toggle("d-none"));
            });
        }

        if (toggleVisibilityButton && newPasswordInput) {
            toggleVisibilityButton.addEventListener("click", () => {
                if (newPasswordInput.type === "password") {
                    newPasswordInput.type = "text";
                    toggleVisibilityButton.textContent = "Hide";
                } else {
                    newPasswordInput.type = "password";
                    toggleVisibilityButton.textContent = "Show";
                }
            });
        }

        if (generatePasswordButton && newPasswordInput) {
            generatePasswordButton.addEventListener("click", () => {
                newPasswordInput.value = generateRandomPassword(12);
            });
        }
    }

    function generateRandomPassword(length) {
        const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        let result = "";
        for (let i = 0; i < length; i++) {
            result += characters.charAt(Math.floor(Math.random() * characters.length));
        }
        return result;
    }
});
