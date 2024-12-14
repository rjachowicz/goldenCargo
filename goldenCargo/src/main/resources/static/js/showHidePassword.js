document.addEventListener("DOMContentLoaded", function () {
    const togglePasswordVisibility = document.getElementById("togglePasswordVisibility");
    const passwordInput = document.getElementById("password");

    togglePasswordVisibility.addEventListener("click", function () {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            togglePasswordVisibility.textContent = "Hide";
        } else {
            passwordInput.type = "password";
            togglePasswordVisibility.textContent = "Show";
        }
    });
});