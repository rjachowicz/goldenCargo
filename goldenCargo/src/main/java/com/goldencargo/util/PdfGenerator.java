package com.goldencargo.util;

import com.goldencargo.component.ReportData;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateReportPdf(ReportData report) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (byteArrayOutputStream) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            document.setMargins(20, 20, 20, 20);

            document.add(createTitleParagraph());

            document.add(createSectionHeader("Transport Data"));
            Table transportTable = createTwoColumnTable();
            addTwoColRow(transportTable, "Actual Departure", report.getActualDeparture());
            addTwoColRow(transportTable, "Actual Arrival", report.getActualArrival());
            addTwoColRow(transportTable, "Notes", report.getNotes());
            addTwoColRow(transportTable, "Created At", report.getTransportCreatedAt());
            addTwoColRow(transportTable, "Updated At", report.getTransportUpdatedAt());
            document.add(transportTable);

            document.add(createSectionHeader("Transport Order"));
            Table orderTable = createTwoColumnTable();
            addTwoColRow(orderTable, "Order Name", report.getTransportOrderName());
            addTwoColRow(orderTable, "Scheduled Departure", report.getScheduledDeparture());
            addTwoColRow(orderTable, "Scheduled Arrival", report.getScheduledArrival());
            addTwoColRow(orderTable, "Order Created At", report.getTransportOrderCreatedAt());
            addTwoColRow(orderTable, "Order Updated At", report.getTransportOrderUpdatedAt());
            document.add(orderTable);

            document.add(createSectionHeader("Driver Information"));
            Table driverTable = createTwoColumnTable();
            addTwoColRow(driverTable, "Driver Name", report.getDriverName());
            addTwoColRow(driverTable, "Phone", report.getDriverPhone());
            addTwoColRow(driverTable, "Email", report.getDriverEmail());
            addTwoColRow(driverTable, "License Number", report.getLicenseNumber());
            addTwoColRow(driverTable, "License Category", report.getLicenseCategory());
            addTwoColRow(driverTable, "Hire Date", report.getHireDate());
            addTwoColRow(driverTable, "Date of Birth", report.getDateOfBirth());
            addTwoColRow(driverTable, "Medical Certificate Expiry", report.getMedicalCertificateExpiry());
            document.add(driverTable);

            document.add(createSectionHeader("Vehicle Information"));
            Table vehicleTable = createTwoColumnTable();
            addTwoColRow(vehicleTable, "Make", report.getMake());
            addTwoColRow(vehicleTable, "Model", report.getModel());
            addTwoColRow(vehicleTable, "Registration Number", report.getRegistrationNumber());
            addTwoColRow(vehicleTable, "Capacity", safeToString(report.getCapacity()));
            addTwoColRow(vehicleTable, "Mileage", safeToString(report.getMileage()));
            addTwoColRow(vehicleTable, "Year", safeToString(report.getVehicleYear()));
            addTwoColRow(vehicleTable, "Last Service Date", report.getLastServiceDate());
            addTwoColRow(vehicleTable, "Next Service Due", report.getNextServiceDue());
            addTwoColRow(vehicleTable, "Purchase Date", report.getPurchaseDate());
            document.add(vehicleTable);

            document.add(createSectionHeader("Vehicle Type"));
            Table vehicleTypeTable = createTwoColumnTable();
            addTwoColRow(vehicleTypeTable, "Vehicle Type Name", report.getVehicleTypeName());
            addTwoColRow(vehicleTypeTable, "Maximum Load", safeToString(report.getMaximumLoad()));
            addTwoColRow(vehicleTypeTable, "Special Features", report.getSpecialFeatures());
            addTwoColRow(vehicleTypeTable, "Dimensions", report.getDimensions());
            addTwoColRow(vehicleTypeTable, "Description", report.getVehicleTypeDescription());
            document.add(vehicleTypeTable);

            document.add(createSectionHeader("Locations"));
            Table startEndTable = createThreeColumnTable(new float[]{3, 4, 4});
            addThreeColHeaderRow(startEndTable);

            addThreeColRow(startEndTable, "Name", report.getStartLocationName(), report.getEndLocationName());
            addThreeColRow(startEndTable, "Address", report.getStartAddress(), report.getEndAddress());
            addThreeColRow(startEndTable, "City", report.getStartCity(), report.getEndCity());
            addThreeColRow(startEndTable, "State", report.getStartState(), report.getEndState());
            addThreeColRow(startEndTable, "Country", report.getStartCountry(), report.getEndCountry());
            addThreeColRow(startEndTable, "Postal Code", report.getStartPostalCode(), report.getEndPostalCode());
            addThreeColRow(startEndTable, "Latitude", safeToString(report.getStartLatitude()), safeToString(report.getEndLatitude()));
            addThreeColRow(startEndTable, "Longitude", safeToString(report.getStartLongitude()), safeToString(report.getEndLongitude()));
            document.add(startEndTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }


    private static Paragraph createTitleParagraph() {
        Paragraph paragraph = new Paragraph("Transport Report");
        paragraph.setFontSize(16);
        paragraph.setBold();
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setMarginBottom(10);
        return paragraph;
    }

    private static Paragraph createSectionHeader(String text) {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setFontSize(12);
        paragraph.setBold();
        paragraph.setMarginTop(10);
        paragraph.setMarginBottom(5);
        return paragraph;
    }

    private static Table createTwoColumnTable() {
        float[] columnWidths = {3, 7};
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginBottom(10);
        return table;
    }

    private static Table createThreeColumnTable(float[] columnWidths) {
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginBottom(10);
        return table;
    }

    private static void addTwoColRow(Table table, String label, String value) {
        table.addCell(createBorderedCell(label));
        table.addCell(createBorderedCell(value));
    }

    private static void addThreeColHeaderRow(Table table) {
        Cell cell1 = createHeaderCell("Field");
        Cell cell2 = createHeaderCell("Start");
        Cell cell3 = createHeaderCell("End");

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }

    private static void addThreeColRow(Table table, String field, String start, String end) {
        table.addCell(createBorderedCell(field));
        table.addCell(createBorderedCell(start));
        table.addCell(createBorderedCell(end));
    }

    private static Cell createHeaderCell(String text) {
        Paragraph paragraph = new Paragraph(text != null ? text : "");
        paragraph.setBold();
        paragraph.setFontColor(ColorConstants.WHITE);
        Cell cell = new Cell().add(paragraph);
        cell.setBackgroundColor(ColorConstants.GRAY);
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    private static Cell createBorderedCell(String content) {
        Cell cell = new Cell().add(new Paragraph(content != null ? content : ""));
        cell.setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f));
        return cell;
    }

    private static String safeToString(Object obj) {
        return obj != null ? obj.toString() : "";
    }

}
