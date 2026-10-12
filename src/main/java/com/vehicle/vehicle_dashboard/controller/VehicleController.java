package com.vehicle.vehicle_dashboard.controller;

import com.vehicle.vehicle_dashboard.model.Vehicle;
import com.vehicle.vehicle_dashboard.service.VehicleService;
import com.vehicle.vehicle_dashboard.util.ExcelHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(
            VehicleService vehicleService) {

        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {

        return ResponseEntity.ok(
                vehicleService.getAllVehicles()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                vehicleService.getVehicleById(id)
        );
    }

    @GetMapping("/number/{vehicleNumber}")
    public ResponseEntity<Vehicle> getVehicleByNumber(
            @PathVariable String vehicleNumber) {

        return ResponseEntity.ok(
                vehicleService.getVehicleByNumber(
                        vehicleNumber
                )
        );
    }

    @GetMapping("/plant/{plantCode}")
    public ResponseEntity<List<Vehicle>>
    getVehiclesByPlantCode(
            @PathVariable String plantCode) {

        return ResponseEntity.ok(
                vehicleService.getVehiclesByPlantCode(
                        plantCode
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>>
    searchVehicles(
            @RequestParam String query) {

        return ResponseEntity.ok(
                vehicleService.searchVehicles(
                        query
                )
        );
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalVehicles() {

        return ResponseEntity.ok(
                vehicleService.getTotalVehicles()
        );
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Vehicle>>
    uploadExcel(
            @RequestParam("file")
            MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {

            throw new IllegalArgumentException(
                    "Please upload an Excel file"
            );
        }

        String fileName =
                file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase()
                        .endsWith(".xlsx")) {

            throw new IllegalArgumentException(
                    "Only .xlsx Excel files are allowed"
            );
        }

        List<Vehicle> vehicles =
                vehicleService.uploadExcel(file);

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/template")
    public ResponseEntity<byte[]>
    downloadTemplate()
            throws IOException {

        byte[] template =
                ExcelHelper.createTemplate();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=vehicle_template.xlsx"
                )
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .body(template);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]>
    exportVehiclesToExcel()
            throws IOException {

        List<Vehicle> vehicles =
                vehicleService.getAllVehicles();

        byte[] workbook =
                ExcelHelper.createWorkbook(vehicles);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=vehicle_records.xlsx"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(workbook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable String id,
            @RequestBody Vehicle vehicle) {

        return ResponseEntity.ok(
                vehicleService.updateVehicle(id, vehicle)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable String id) {

        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}