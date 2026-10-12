package com.vehicle.vehicle_dashboard.service;

import com.vehicle.vehicle_dashboard.model.Vehicle;
import com.vehicle.vehicle_dashboard.repository.VehicleRepository;
import com.vehicle.vehicle_dashboard.util.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(String id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vehicle not found"
                        ));
    }

    public Vehicle getVehicleByNumber(
            String vehicleNumber) {

        return vehicleRepository
                .findByVehicleNumber(vehicleNumber)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vehicle not found"
                        ));
    }

    public List<Vehicle> getVehiclesByPlantCode(
            String plantCode) {

        return vehicleRepository
                .findByPlantCode(plantCode);
    }

    public List<Vehicle> searchVehicles(
            String searchTerm) {

        String trimmedSearchTerm =
                searchTerm == null ? "" : searchTerm.trim();

        if (trimmedSearchTerm.isEmpty()) {
            return getAllVehicles();
        }

        return vehicleRepository.searchByAnyField(
                trimmedSearchTerm
        );
    }

    public List<Vehicle> uploadExcel(
            MultipartFile file)
            throws IOException {

        List<Vehicle> vehicles =
                ExcelHelper.excelToVehicles(file);

        validateVehicles(vehicles);

        List<Vehicle> uniqueVehicles =
                filterDuplicateRows(vehicles);

        return vehicleRepository.saveAll(uniqueVehicles);
    }

    private List<Vehicle> filterDuplicateRows(
            List<Vehicle> vehicles) {

        List<Vehicle> deduplicated = new ArrayList<>();
        Set<String> seenRows = new HashSet<>();

        for (Vehicle vehicle : vehicles) {

            String rowSignature = buildRowSignature(vehicle);

            if (seenRows.contains(rowSignature)) {
                continue;
            }

            seenRows.add(rowSignature);

            if (vehicleRepository.findByVehicleNumber(
                    vehicle.getVehicleNumber())
                    .map(existingVehicle ->
                            isSameVehicle(existingVehicle, vehicle))
                    .orElse(false)) {
                continue;
            }

            deduplicated.add(vehicle);
        }

        return deduplicated;
    }

    private String buildRowSignature(Vehicle vehicle) {
        return String.join("|",
                normalize(vehicle.getVehicleNumber()),
                normalize(vehicle.getDriverName()),
                normalize(vehicle.getDriverPhoneNumber()),
                normalize(vehicle.getTransporterName()),
                normalize(vehicle.getTransporterEmail()),
                normalize(vehicle.getDeliveryAddress()),
                normalize(vehicle.getPlantCode()));
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean isSameVehicle(Vehicle existingVehicle,
                                 Vehicle incomingVehicle) {

        return Objects.equals(
                normalize(existingVehicle.getVehicleNumber()),
                normalize(incomingVehicle.getVehicleNumber())
        ) && Objects.equals(
                normalize(existingVehicle.getDriverName()),
                normalize(incomingVehicle.getDriverName())
        ) && Objects.equals(
                normalize(existingVehicle.getDriverPhoneNumber()),
                normalize(incomingVehicle.getDriverPhoneNumber())
        ) && Objects.equals(
                normalize(existingVehicle.getTransporterName()),
                normalize(incomingVehicle.getTransporterName())
        ) && Objects.equals(
                normalize(existingVehicle.getTransporterEmail()),
                normalize(incomingVehicle.getTransporterEmail())
        ) && Objects.equals(
                normalize(existingVehicle.getDeliveryAddress()),
                normalize(incomingVehicle.getDeliveryAddress())
        ) && Objects.equals(
                normalize(existingVehicle.getPlantCode()),
                normalize(incomingVehicle.getPlantCode())
        );
    }

    private void validateVehicles(
            List<Vehicle> vehicles) {

        for (Vehicle vehicle : vehicles) {

            if (isBlank(vehicle.getVehicleNumber())) {

                throw new IllegalArgumentException(
                        "Vehicle number cannot be empty"
                );
            }

            if (isBlank(vehicle.getDriverName())) {

                throw new IllegalArgumentException(
                        "Driver name cannot be empty for vehicle: "
                                + vehicle.getVehicleNumber()
                );
            }

            if (isBlank(vehicle.getDriverPhoneNumber())) {

                throw new IllegalArgumentException(
                        "Driver phone number cannot be empty for vehicle: "
                                + vehicle.getVehicleNumber()
                );
            }

            if (isBlank(vehicle.getTransporterName())) {

                throw new IllegalArgumentException(
                        "Transporter name cannot be empty for vehicle: "
                                + vehicle.getVehicleNumber()
                );
            }

            if (isBlank(vehicle.getTransporterEmail())) {

                throw new IllegalArgumentException(
                        "Transporter email cannot be empty for vehicle: "
                                + vehicle.getVehicleNumber()
                );
            }

            if (isBlank(vehicle.getDeliveryAddress())) {

                throw new IllegalArgumentException(
                        "Delivery address cannot be empty for vehicle: "
                                + vehicle.getVehicleNumber()
                );
            }

            if (isBlank(vehicle.getPlantCode())) {

                throw new IllegalArgumentException(
                        "Plant code cannot be empty for vehicle: "
                                + vehicle.getVehicleNumber()
                );
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public long getTotalVehicles() {
        return vehicleRepository.count();
    }

    public Vehicle updateVehicle(String id, Vehicle vehicle) {
        Vehicle existingVehicle = getVehicleById(id);
        
        if (vehicle.getVehicleNumber() != null && !vehicle.getVehicleNumber().isBlank()) {
            existingVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        }
        if (vehicle.getDriverName() != null && !vehicle.getDriverName().isBlank()) {
            existingVehicle.setDriverName(vehicle.getDriverName());
        }
        if (vehicle.getDriverPhoneNumber() != null && !vehicle.getDriverPhoneNumber().isBlank()) {
            existingVehicle.setDriverPhoneNumber(vehicle.getDriverPhoneNumber());
        }
        if (vehicle.getTransporterName() != null && !vehicle.getTransporterName().isBlank()) {
            existingVehicle.setTransporterName(vehicle.getTransporterName());
        }
        if (vehicle.getTransporterEmail() != null && !vehicle.getTransporterEmail().isBlank()) {
            existingVehicle.setTransporterEmail(vehicle.getTransporterEmail());
        }
        if (vehicle.getDeliveryAddress() != null && !vehicle.getDeliveryAddress().isBlank()) {
            existingVehicle.setDeliveryAddress(vehicle.getDeliveryAddress());
        }
        if (vehicle.getPlantCode() != null && !vehicle.getPlantCode().isBlank()) {
            existingVehicle.setPlantCode(vehicle.getPlantCode());
        }
        
        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }

}