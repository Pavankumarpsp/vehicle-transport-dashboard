package com.vehicle.vehicle_dashboard.util;

import com.vehicle.vehicle_dashboard.model.Vehicle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    private static final String[] HEADERS = {
            "Vehicle Number",
            "Driver Name",
            "Driver Phone Number",
            "Transporter Name",
            "Transporter Email",
            "Delivery Address",
            "Plant Code"
    };

    public static List<Vehicle> excelToVehicles(MultipartFile file)
            throws IOException {

        List<Vehicle> vehicles = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            validateHeaders(sheet);

            for (int rowIndex = 1;
                 rowIndex <= sheet.getLastRowNum();
                 rowIndex++) {

                Row row = sheet.getRow(rowIndex);

                if (row == null || isEmptyRow(row)) {
                    continue;
                }

                Vehicle vehicle = new Vehicle();

                vehicle.setVehicleNumber(
                        getCellValue(row.getCell(0))
                );

                vehicle.setDriverName(
                        getCellValue(row.getCell(1))
                );

                vehicle.setDriverPhoneNumber(
                        getCellValue(row.getCell(2))
                );

                vehicle.setTransporterName(
                        getCellValue(row.getCell(3))
                );

                vehicle.setTransporterEmail(
                        getCellValue(row.getCell(4))
                );

                vehicle.setDeliveryAddress(
                        getCellValue(row.getCell(5))
                );

                vehicle.setPlantCode(
                        getCellValue(row.getCell(6))
                );

                vehicles.add(vehicle);
            }
        }

        return vehicles;
    }

    private static void validateHeaders(Sheet sheet) {

        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new IllegalArgumentException(
                    "Excel file does not contain headers"
            );
        }

        for (int i = 0; i < HEADERS.length; i++) {

            String actualHeader =
                    getCellValue(headerRow.getCell(i));

            if (!HEADERS[i].equalsIgnoreCase(actualHeader.trim())) {

                throw new IllegalArgumentException(
                        "Invalid template. Expected column: "
                                + HEADERS[i]
                );
            }
        }
    }

    private static String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }

    private static boolean isEmptyRow(Row row) {

        for (int i = 0; i < HEADERS.length; i++) {

            Cell cell = row.getCell(i);

            if (cell != null &&
                    !getCellValue(cell).isEmpty()) {

                return false;
            }
        }

        return true;
    }

    public static byte[] createTemplate()
            throws IOException {

        return createWorkbook(new ArrayList<>());
    }

    public static byte[] createWorkbook(List<Vehicle> vehicles)
            throws IOException {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Sheet sheet =
                    workbook.createSheet("Vehicles");

            Row headerRow =
                    sheet.createRow(0);

            CellStyle headerStyle =
                    workbook.createCellStyle();

            Font headerFont =
                    workbook.createFont();

            headerFont.setBold(true);

            headerStyle.setFont(headerFont);

            for (int i = 0;
                 i < HEADERS.length;
                 i++) {

                Cell cell =
                        headerRow.createCell(i);

                cell.setCellValue(HEADERS[i]);

                cell.setCellStyle(headerStyle);

                sheet.autoSizeColumn(i);
            }

            for (int rowIndex = 0;
                 rowIndex < vehicles.size();
                 rowIndex++) {

                Vehicle vehicle = vehicles.get(rowIndex);
                Row row = sheet.createRow(rowIndex + 1);

                row.createCell(0).setCellValue(
                        vehicle.getVehicleNumber() == null ? "" : vehicle.getVehicleNumber()
                );
                row.createCell(1).setCellValue(
                        vehicle.getDriverName() == null ? "" : vehicle.getDriverName()
                );
                row.createCell(2).setCellValue(
                        vehicle.getDriverPhoneNumber() == null ? "" : vehicle.getDriverPhoneNumber()
                );
                row.createCell(3).setCellValue(
                        vehicle.getTransporterName() == null ? "" : vehicle.getTransporterName()
                );
                row.createCell(4).setCellValue(
                        vehicle.getTransporterEmail() == null ? "" : vehicle.getTransporterEmail()
                );
                row.createCell(5).setCellValue(
                        vehicle.getDeliveryAddress() == null ? "" : vehicle.getDeliveryAddress()
                );
                row.createCell(6).setCellValue(
                        vehicle.getPlantCode() == null ? "" : vehicle.getPlantCode()
                );
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }
}