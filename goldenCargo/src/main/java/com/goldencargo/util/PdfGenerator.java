package com.goldencargo.util;

import com.goldencargo.model.dto.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGenerator {

    public static byte[] generateReportPdf(VehicleReportDTO vehicleReport,
                                           ClientReportDTO clientReport,
                                           List<TechnicalInspectionDTO> technicalInspections,
                                           List<ServiceScheduleDTO> serviceSchedules,
                                           List<DriverVehicleDTO> driverHistory) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        document.add(new Paragraph("Vehicle Report").setBold().setFontSize(14));
        document.add(new Paragraph("Make: " + safeText(vehicleReport.getVehicle().getMake())));
        document.add(new Paragraph("Model: " + safeText(vehicleReport.getVehicle().getModel())));
        document.add(new Paragraph("Registration Number: " + safeText(vehicleReport.getVehicle().getRegistrationNumber())));
        document.add(new Paragraph("Year: " + safeText(String.valueOf(vehicleReport.getVehicle().getYear()))));
        document.add(new Paragraph("Capacity: " + safeText(String.valueOf(vehicleReport.getVehicle().getCapacity())) + " kg"));
        document.add(new Paragraph("Mileage: " + safeText(String.valueOf(vehicleReport.getVehicle().getMileage())) + " km"));

        document.add(new Paragraph("\nRepairs:"));
        Table repairsTable = new Table(new float[]{4, 4, 4});
        repairsTable.addHeaderCell("Date");
        repairsTable.addHeaderCell("Cost");
        repairsTable.addHeaderCell("Type");
        vehicleReport.getRepairs().forEach(repair -> {
            repairsTable.addCell(safeText(repair.getServiceDate() != null ? repair.getServiceDate().toString() : "N/A"));
            repairsTable.addCell(safeText(String.valueOf(repair.getCost())));
            repairsTable.addCell(safeText(repair.getServiceType()));
        });
        document.add(repairsTable);

        document.add(new Paragraph("\nIncidents:"));
        Table incidentsTable = new Table(new float[]{4, 4, 6});
        incidentsTable.addHeaderCell("Date");
        incidentsTable.addHeaderCell("Type");
        incidentsTable.addHeaderCell("Description");
        vehicleReport.getIncidents().forEach(incident -> {
            incidentsTable.addCell(safeText(incident.getDate() != null ? incident.getDate().toString() : "N/A"));
            incidentsTable.addCell(safeText(incident.getIncidentType()));
            incidentsTable.addCell(safeText(incident.getDescription()));
        });
        document.add(incidentsTable);

        document.add(new Paragraph("\nTechnical Inspections:"));
        Table inspectionsTable = new Table(new float[]{4, 4, 4, 4});
        inspectionsTable.addHeaderCell("Inspection Date");
        inspectionsTable.addHeaderCell("Next Inspection Date");
        inspectionsTable.addHeaderCell("Inspector Name");
        inspectionsTable.addHeaderCell("Result");
        technicalInspections.forEach(inspection -> {
            inspectionsTable.addCell(safeText(inspection.getInspectionDate().toString()));
            inspectionsTable.addCell(safeText(inspection.getNextInspectionDate() != null ? inspection.getNextInspectionDate().toString() : "N/A"));
            inspectionsTable.addCell(safeText(inspection.getInspectorName()));
            inspectionsTable.addCell(safeText(inspection.getResult()));
        });
        document.add(inspectionsTable);

        document.add(new Paragraph("\nService Schedules:"));
        Table schedulesTable = new Table(new float[]{4, 4, 4});
        schedulesTable.addHeaderCell("Scheduled Date");
        schedulesTable.addHeaderCell("Service Type");
        schedulesTable.addHeaderCell("Status");
        serviceSchedules.forEach(schedule -> {
            schedulesTable.addCell(safeText(schedule.getScheduledDate().toString()));
            schedulesTable.addCell(safeText(schedule.getServiceType()));
            schedulesTable.addCell(safeText(schedule.getStatus()));
        });
        document.add(schedulesTable);

        document.add(new Paragraph("\nDriver History:"));
        Table driverTable = new Table(new float[]{4, 4, 4});
        driverTable.addHeaderCell("Driver Name");
        driverTable.addHeaderCell("Assigned Date");
        driverTable.addHeaderCell("End Date");
        driverHistory.forEach(history -> {
            driverTable.addCell(safeText(history.getFirstName() + " " + history.getLastName()));
            driverTable.addCell(safeText(history.getAssignedDate().toString()));
            driverTable.addCell(safeText(history.getEndDate() != null ? history.getEndDate().toString() : "N/A"));
        });
        document.add(driverTable);

        document.add(new Paragraph("\nClient Report").setBold().setFontSize(14));
        document.add(new Paragraph("Name: " + safeText(clientReport.getClient().getName())));
        document.add(new Paragraph("Email: " + safeText(clientReport.getClient().getEmail())));
        document.add(new Paragraph("Phone: " + safeText(clientReport.getClient().getPhone())));

        document.add(new Paragraph("\nOrders:"));
        Table ordersTable = new Table(new float[]{3, 3, 3});
        ordersTable.addHeaderCell("Order ID");
        ordersTable.addHeaderCell("Amount");
        ordersTable.addHeaderCell("Status");
        clientReport.getOrders().forEach(order -> {
            ordersTable.addCell(safeText(String.valueOf(order.getClientOrderId())));
            ordersTable.addCell(safeText(String.valueOf(order.getTotalAmount())));
            ordersTable.addCell(safeText(order.getStatus() != null ? order.getStatus().toString() : "N/A"));
        });
        document.add(ordersTable);

        document.add(new Paragraph("\nInvoices:"));
        Table invoicesTable = new Table(new float[]{4, 4, 4});
        invoicesTable.addHeaderCell("Invoice Number");
        invoicesTable.addHeaderCell("Amount");
        invoicesTable.addHeaderCell("Status");
        clientReport.getInvoices().forEach(invoice -> {
            invoicesTable.addCell(safeText(invoice.getInvoiceNumber()));
            invoicesTable.addCell(safeText(String.valueOf(invoice.getTotalAmount())));
            invoicesTable.addCell(safeText(invoice.getPaymentStatus() != null ? String.valueOf(invoice.getPaymentStatus()) : "N/A"));
        });
        document.add(invoicesTable);

        document.add(new Paragraph("\nGoods:"));
        Table goodsTable = new Table(new float[]{4, 4, 4});
        goodsTable.addHeaderCell("Name");
        goodsTable.addHeaderCell("Weight");
        goodsTable.addHeaderCell("Dimensions");
        clientReport.getGoods().forEach(goods -> {
            goodsTable.addCell(safeText(goods.getName()));
            goodsTable.addCell(safeText(String.valueOf(goods.getWeight())));
            goodsTable.addCell(safeText(goods.getDimensions() != null ? goods.getDimensions() : "N/A"));
        });
        document.add(goodsTable);

        document.close();
        return outputStream.toByteArray();
    }

    private static String safeText(String value) {
        return value != null ? value : "N/A";
    }
}
