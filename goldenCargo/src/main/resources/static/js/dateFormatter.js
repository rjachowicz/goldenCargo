document.addEventListener("DOMContentLoaded", function () {
    const repairDateInput = document.getElementById("repairDate");

    if (repairDateInput && repairDateInput.value) {
        const date = new Date(repairDateInput.value);
        repairDateInput.value = date.toISOString().split("T")[0];
    }
});
