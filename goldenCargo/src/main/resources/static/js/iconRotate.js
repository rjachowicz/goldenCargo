document.addEventListener('DOMContentLoaded', function () {
    const collapseClients = document.getElementById('collapseClients');
    const toggleClientsIcon = document.querySelector('#toggleClients i');
    collapseClients.addEventListener('hide.bs.collapse', function () {
        toggleClientsIcon.classList.remove('fa-chevron-up');
        toggleClientsIcon.classList.add('fa-chevron-down');
    });
    collapseClients.addEventListener('show.bs.collapse', function () {
        toggleClientsIcon.classList.remove('fa-chevron-down');
        toggleClientsIcon.classList.add('fa-chevron-up');
    });

    const collapseGoods = document.getElementById('collapseGoods');
    const toggleGoodsIcon = document.querySelector('#toggleGoods i');
    collapseGoods.addEventListener('hide.bs.collapse', function () {
        toggleGoodsIcon.classList.remove('fa-chevron-up');
        toggleGoodsIcon.classList.add('fa-chevron-down');
    });
    collapseGoods.addEventListener('show.bs.collapse', function () {
        toggleGoodsIcon.classList.remove('fa-chevron-down');
        toggleGoodsIcon.classList.add('fa-chevron-up');
    });
});