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
                        label: (tooltipItem) => {
                            return `${tooltipItem.label}: ${tooltipItem.raw}`;
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
    try {
        const userRolesLabels = parseChartData('userRolesLabels');
        const userRolesData = parseChartData('userRolesData');
        renderChart('userRolesChart', 'pie', userRolesLabels, userRolesData, 'User Roles', ['#FFCC00', '#F39C12', '#333', '#555']);

        const orderStatusesLabels = parseChartData('orderStatusesLabels');
        const orderStatusesData = parseChartData('orderStatusesData');
        renderChart('orderStatusesChart', 'bar', orderStatusesLabels, orderStatusesData, 'Order Statuses', ['#FFCC00', '#F39C12', '#333', '#555']);

        const vehicleStatusesLabels = parseChartData('vehicleStatusesLabels');
        const vehicleStatusesData = parseChartData('vehicleStatusesData');
        renderChart('vehicleStatusesChart', 'doughnut', vehicleStatusesLabels, vehicleStatusesData, 'Vehicle Statuses', ['#FFCC00', '#F39C12', '#333', '#555']);

        const messageStatusesLabels = parseChartData('messageStatusesLabels');
        const messageStatusesData = parseChartData('messageStatusesData');
        renderChart('messageStatusesChart', 'bar', messageStatusesLabels, messageStatusesData, 'Message Statuses', ['#FFCC00', '#F39C12', '#333', '#555']);

        const incidentTypesLabels = parseChartData('incidentTypesLabels');
        const incidentTypesData = parseChartData('incidentTypesData');
        renderChart('incidentTypesChart', 'polarArea', incidentTypesLabels, incidentTypesData, 'Incident Types', ['#FFCC00', '#F39C12', '#333', '#555']);

        const vehicleRepairLabels = parseChartData('vehicleRepairLabels');
        const vehicleRepairData = parseChartData('vehicleRepairData');
        renderChart('vehicleRepairChart', 'bar', vehicleRepairLabels, vehicleRepairData, 'Repair Costs', ['#FF5733', '#C70039', '#900C3F']);

        const transportStatusLabels = parseChartData('transportStatusLabels');
        const transportStatusData = parseChartData('transportStatusData');
        renderChart('transportStatusChart', 'pie', transportStatusLabels, transportStatusData, 'Transport Statuses', ['#FFC300', '#FF5733', '#C70039']);

        const clientInvoiceStatusLabels = parseChartData('clientInvoiceStatusLabels');
        const clientInvoiceStatusData = parseChartData('clientInvoiceStatusData');
        renderChart('clientInvoiceStatusChart', 'doughnut', clientInvoiceStatusLabels, clientInvoiceStatusData, 'Invoice Statuses', ['#DAF7A6', '#FFC300', '#FF5733']);
    } catch (error) {
        console.error("Error rendering charts:", error);
    }
});
