document.addEventListener("DOMContentLoaded", function () {
    function generateTransportOrderName() {
        let timestamp = new Date().getTime();
        return "TO-" + timestamp;
    }

    document.getElementById("addTransportOrderModal").addEventListener("show.bs.modal", function () {
        document.getElementById("name").value = generateTransportOrderName();
    });
});
