const renderChart = (ctxId, type, labels, data, label, backgroundColors) => {
    const canvas = document.getElementById(ctxId);
    if (!canvas) {
        console.error(`Canvas with ID "${ctxId}" not found.`);
        return;
    }

    const ctx = canvas.getContext('2d');
    new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                backgroundColor: backgroundColors,
                borderColor: '#fff',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            const label = tooltipItem.label || tooltipItem.raw;
                            const value = tooltipItem.raw;
                            if (label && value !== undefined) {
                                return `${label}: ${value}`;
                            }
                            return 'Data unavailable';
                        }
                    }
                },
                legend: {
                    display: true,
                    labels: {
                        boxWidth: 15,
                        padding: 10
                    }
                }
            }
        }
    });
};

const parseChartData = (elementId) => {
    try {
        const jsonData = document.getElementById(elementId).textContent;
        return JSON.parse(jsonData);
    } catch (error) {
        console.error(`Error parsing chart data for ${elementId}:`, error);
        return [];
    }
};

document.addEventListener('DOMContentLoaded', () => {
    const renderCharts = [
        {
            ctx: 'userRolesChart',
            labelsId: 'userRolesLabels',
            dataId: 'userRolesData',
            type: 'pie',
            label: 'User Roles',
            colors: ['#FFCC00', '#F39C12', '#333', '#555']
        },
        {
            ctx: 'orderStatusesChart',
            labelsId: 'orderStatusesLabels',
            dataId: 'orderStatusesData',
            type: 'bar',
            label: 'Order Statuses',
            colors: ['#FFCC00', '#F39C12', '#333', '#555']
        },
        {
            ctx: 'vehicleStatusesChart',
            labelsId: 'vehicleStatusesLabels',
            dataId: 'vehicleStatusesData',
            type: 'doughnut',
            label: 'Vehicle Statuses',
            colors: ['#FFCC00', '#F39C12', '#333', '#555']
        },
        {
            ctx: 'messageStatusesChart',
            labelsId: 'messageStatusesLabels',
            dataId: 'messageStatusesData',
            type: 'bar',
            label: 'Message Statuses',
            colors: ['#FFCC00', '#F39C12', '#333', '#555']
        },
        {
            ctx: 'incidentTypesChart',
            labelsId: 'incidentTypesLabels',
            dataId: 'incidentTypesData',
            type: 'polarArea',
            label: 'Incident Types',
            colors: ['#FFCC00', '#F39C12', '#333', '#555']
        },
        {
            ctx: 'vehicleRepairChart',
            labelsId: 'vehicleRepairLabels',
            dataId: 'vehicleRepairData',
            type: 'bar',
            label: 'Repair Costs',
            colors: ['#FF5733', '#C70039', '#900C3F']
        },
        {
            ctx: 'transportStatusChart',
            labelsId: 'transportStatusLabels',
            dataId: 'transportStatusData',
            type: 'pie',
            label: 'Transport Statuses',
            colors: ['#FFC300', '#FF5733', '#C70039']
        },
        {
            ctx: 'clientInvoiceStatusChart',
            labelsId: 'clientInvoiceStatusLabels',
            dataId: 'clientInvoiceStatusData',
            type: 'doughnut',
            label: 'Invoice Statuses',
            colors: ['#DAF7A6', '#FFC300', '#FF5733']
        },
    ];

    renderCharts.forEach(chart => {
        const labels = parseChartData(chart.labelsId);
        const data = parseChartData(chart.dataId);
        renderChart(chart.ctx, chart.type, labels, data, chart.label, chart.colors);
    });
});
