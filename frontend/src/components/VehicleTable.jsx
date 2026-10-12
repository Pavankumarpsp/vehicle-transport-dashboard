function VehicleTable({ vehicles, loading, onDownloadAll, onEdit, onDelete }) {

    if (loading) {
        return (
            <div className="table-message">
                Loading vehicle data...
            </div>
        );
    }

    if (vehicles.length === 0) {
        return (
            <>
                <div className="table-message">
                    No vehicle records found.
                </div>

                <div className="table-actions">
                    <button
                        className="primary-button"
                        type="button"
                        onClick={onDownloadAll}
                    >
                        Download Excel
                    </button>
                </div>
            </>
        );
    }

    return (
        <>
            <div className="table-container">

                <table>

                    <thead>

                    <tr>
                        <th>Vehicle Number</th>
                        <th>Driver Name</th>
                        <th>Driver Phone</th>
                        <th>Transporter</th>
                        <th>Transporter Email</th>
                        <th>Delivery Address</th>
                        <th>Plant Code</th>
                        <th>Actions</th>
                    </tr>

                    </thead>

                    <tbody>

                    {vehicles.map((vehicle) => (

                        <tr key={vehicle.id}>

                            <td className="vehicle-number">
                                {vehicle.vehicleNumber}
                            </td>

                            <td>
                                {vehicle.driverName}
                            </td>

                            <td>
                                {vehicle.driverPhoneNumber}
                            </td>

                            <td>
                                {vehicle.transporterName}
                            </td>

                            <td>
                                {vehicle.transporterEmail}
                            </td>

                            <td>
                                {vehicle.deliveryAddress}
                            </td>

                            <td>
                                <span className="plant-badge">
                                    {vehicle.plantCode}
                                </span>
                            </td>

                            <td className="actions-cell">
                                <button
                                    className="action-button edit-button"
                                    onClick={() => onEdit(vehicle)}
                                    title="Edit vehicle"
                                >
                                    ✏️
                                </button>
                                <button
                                    className="action-button delete-button"
                                    onClick={() => onDelete(vehicle.id)}
                                    title="Delete vehicle"
                                >
                                    🗑️
                                </button>
                            </td>

                            </tr>

                    ))}

                    </tbody>

                </table>

            </div>

            <div className="table-actions">
                <button
                    className="primary-button"
                    type="button"
                    onClick={onDownloadAll}
                >
                    Download Excel
                </button>
            </div>
        </>
    );
}

export default VehicleTable;