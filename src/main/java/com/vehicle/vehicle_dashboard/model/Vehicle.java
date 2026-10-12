package com.vehicle.vehicle_dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicle_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    private String id;

    @Indexed(unique = true)
    private String vehicleNumber;

    private String driverName;

    private String driverPhoneNumber;

    private String transporterName;

    private String transporterEmail;

    private String deliveryAddress;

    private String plantCode;
}