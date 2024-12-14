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
                setupPasswordChange(modalContent);
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

    function setupPasswordChange(modalContent) {
        const togglePasswordButton = modalContent.querySelector("#togglePasswordChange");
        const passwordFields = modalContent.querySelectorAll(".password-change");
        const newPasswordInput = modalContent.querySelector("#newPassword");
        const toggleVisibilityButton = modalContent.querySelector("#togglePasswordVisibility");
        const generatePasswordButton = modalContent.querySelector("#generatePassword");
        if (togglePasswordButton) {
            togglePasswordButton.addEventListener("click", () => {
                passwordFields.forEach(field => field.classList.toggle("d-none"));
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
