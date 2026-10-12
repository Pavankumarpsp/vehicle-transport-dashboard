package com.vehicle.vehicle_dashboard.repository;

import com.vehicle.vehicle_dashboard.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByPlantCode(String plantCode);

    @Query("{ $or: [ { 'vehicleNumber': { $regex: ?0, $options: 'i' } }, { 'driverName': { $regex: ?0, $options: 'i' } }, { 'driverPhoneNumber': { $regex: ?0, $options: 'i' } }, { 'transporterName': { $regex: ?0, $options: 'i' } }, { 'transporterEmail': { $regex: ?0, $options: 'i' } }, { 'deliveryAddress': { $regex: ?0, $options: 'i' } }, { 'plantCode': { $regex: ?0, $options: 'i' } } ] }")
    List<Vehicle> searchByAnyField(String keyword);
}