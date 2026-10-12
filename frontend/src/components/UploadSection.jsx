import { useRef, useState } from "react";
import {
    uploadExcel,
    downloadTemplate
} from "../services/vehicleService";

function UploadSection({ onUploadSuccess }) {

    const fileInputRef = useRef(null);

    const [selectedFile, setSelectedFile] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    async function handleUpload(file) {

        if (!file) {
            setError("Please select an Excel file first.");
            return;
        }

        try {

            setUploading(true);
            setError("");
            setMessage("");

            const result = await uploadExcel(file);

            setMessage(
                `${result.length} vehicle records uploaded successfully.`
            );

            setSelectedFile(null);

            if (fileInputRef.current) {
                fileInputRef.current.value = "";
            }

            onUploadSuccess();

        } catch (error) {

            setError(error.message);

        } finally {

            setUploading(false);
        }
    }

    async function handleFileChange(event) {

        const file = event.target.files[0];

        setMessage("");
        setError("");

        if (!file) {
            setSelectedFile(null);
            return;
        }

        if (!file.name.toLowerCase().endsWith(".xlsx")) {
            setSelectedFile(null);
            setError("Please select an .xlsx Excel file.");
            if (fileInputRef.current) {
                fileInputRef.current.value = "";
            }
            return;
        }

        setSelectedFile(file);
        await handleUpload(file);
    }

    async function handleDownload() {

        try {

            setError("");
            setMessage("");

            await downloadTemplate();

            setMessage("Template downloaded successfully.");

        } catch (error) {

            setError(error.message);
        }
    }

    return (
        <div className="upload-section">

            <div className="upload-title">
                <div>
                    <h2>Vehicle Data</h2>
                    <p>
                        Download the template, fill in the vehicle
                        information and upload it.
                    </p>
                </div>
            </div>

            <div className="upload-actions">

                <button
                    className="secondary-button"
                    onClick={handleDownload}
                >
                    ↓ Download Template
                </button>

                <label className="file-button">
                    {uploading ? "Uploading..." : "Upload File"}

                    <input
                        ref={fileInputRef}
                        type="file"
                        accept=".xlsx"
                        onChange={handleFileChange}
                    />
                </label>

            </div>

            {selectedFile && (
                <div className="selected-file">
                    Selected file:
                    <strong>{selectedFile.name}</strong>
                </div>
            )}

            {message && (
                <div className="success-message">
                    ✓ {message}
                </div>
            )}

            {error && (
                <div className="error-message">
                    ✕ {error}
                </div>
            )}

        </div>
    );
}

export default UploadSection;