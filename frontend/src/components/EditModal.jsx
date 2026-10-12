import { useState, useEffect } from "react";
import "../styles/EditModal.css";

function EditModal({ vehicle, onSave, onCancel, loading }) {
    const [formData, setFormData] = useState({
        vehicleNumber: "",
        driverName: "",
        driverPhoneNumber: "",
        transporterName: "",
        transporterEmail: "",
        deliveryAddress: "",
        plantCode: ""
    });

    useEffect(() => {
        if (vehicle) {
            setFormData({
                vehicleNumber: vehicle.vehicleNumber || "",
                driverName: vehicle.driverName || "",
                driverPhoneNumber: vehicle.driverPhoneNumber || "",
                transporterName: vehicle.transporterName || "",
                transporterEmail: vehicle.transporterEmail || "",
                deliveryAddress: vehicle.deliveryAddress || "",
                plantCode: vehicle.plantCode || ""
            });
        }
    }, [vehicle]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(formData);
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Edit Vehicle Record</h2>
                    <button
                        className="close-button"
                        onClick={onCancel}
                        disabled={loading}
                    >
                        ✕
                    </button>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="vehicleNumber">Vehicle Number</label>
                        <input
                            type="text"
                            id="vehicleNumber"
                            name="vehicleNumber"
                            value={formData.vehicleNumber}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="driverName">Driver Name</label>
                        <input
                            type="text"
                            id="driverName"
                            name="driverName"
                            value={formData.driverName}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="driverPhoneNumber">Driver Phone Number</label>
                        <input
                            type="text"
                            id="driverPhoneNumber"
                            name="driverPhoneNumber"
                            value={formData.driverPhoneNumber}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="transporterName">Transporter Name</label>
                        <input
                            type="text"
                            id="transporterName"
                            name="transporterName"
                            value={formData.transporterName}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="transporterEmail">Transporter Email</label>
                        <input
                            type="email"
                            id="transporterEmail"
                            name="transporterEmail"
                            value={formData.transporterEmail}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="deliveryAddress">Delivery Address</label>
                        <textarea
                            id="deliveryAddress"
                            name="deliveryAddress"
                            value={formData.deliveryAddress}
                            onChange={handleChange}
                            required
                            rows="3"
                        ></textarea>
                    </div>

                    <div className="form-group">
                        <label htmlFor="plantCode">Plant Code</label>
                        <input
                            type="text"
                            id="plantCode"
                            name="plantCode"
                            value={formData.plantCode}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="modal-footer">
                        <button
                            type="button"
                            className="cancel-button"
                            onClick={onCancel}
                            disabled={loading}
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            className="save-button"
                            disabled={loading}
                        >
                            {loading ? "Saving..." : "Save Changes"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default EditModal;

