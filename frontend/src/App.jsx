import { useEffect, useState } from "react";

import Header from "./components/Header";
import StatsCard from "./components/StatsCard";
import UploadSection from "./components/UploadSection";
import SearchFilter from "./components/SearchFilter";
import VehicleTable from "./components/VehicleTable";
import EditModal from "./components/EditModal";

import {
    getVehicles,
    getVehicleCount,
    searchVehicles,
    downloadAllVehiclesExcel,
    updateVehicle,
    deleteVehicle
} from "./services/vehicleService";

import "./App.css";

function App() {

    const [vehicles, setVehicles] = useState([]);

    const [totalVehicles, setTotalVehicles] = useState(0);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [searchText, setSearchText] = useState("");

    const [editingVehicle, setEditingVehicle] = useState(null);

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);

    const [isSubmitting, setIsSubmitting] = useState(false);


    async function loadVehicles() {

        try {

            setLoading(true);
            setError("");

            const data = await getVehicles();

            setVehicles(data);

        } catch (error) {

            setError(error.message);

        } finally {

            setLoading(false);
        }
    }

    async function loadCount() {

        try {

            const count = await getVehicleCount();

            setTotalVehicles(count);

        } catch (error) {

            console.error(error);
        }
    }

    async function handleSearch() {

        if (!searchText.trim()) {

            loadVehicles();

            return;
        }

        try {

            setLoading(true);
            setError("");

            const data =
                await searchVehicles(searchText.trim());

            setVehicles(data);

        } catch (error) {

            setError(error.message);

        } finally {

            setLoading(false);
        }
    }

    function handleClear() {

        setSearchText("");

        loadVehicles();
    }

    async function handleUploadSuccess() {

        await loadVehicles();

        await loadCount();
    }

    async function handleDownloadAll() {

        try {

            await downloadAllVehiclesExcel();

        } catch (error) {

            setError(error.message);
        }
    }

    function handleEdit(vehicle) {
        setEditingVehicle(vehicle);
        setIsEditModalOpen(true);
    }

    async function handleSaveEdit(updatedData) {
        if (!editingVehicle) return;

        try {
            setIsSubmitting(true);
            setError("");

            await updateVehicle(editingVehicle.id, updatedData);

            setIsEditModalOpen(false);
            setEditingVehicle(null);

            await loadVehicles();
            await loadCount();
        } catch (error) {
            setError(error.message);
        } finally {
            setIsSubmitting(false);
        }
    }

    async function handleDelete(vehicleId) {
        if (!window.confirm("Are you sure you want to delete this vehicle record?")) {
            return;
        }

        try {
            setError("");

            await deleteVehicle(vehicleId);

            await loadVehicles();
            await loadCount();
        } catch (error) {
            setError(error.message);
        }
    }

    function handleCloseEditModal() {
        setIsEditModalOpen(false);
        setEditingVehicle(null);
    }


    useEffect(() => {

        loadVehicles();
        loadCount();

    }, []);

    return (

        <div className="app">

            <Header />

            <main className="dashboard">

                <StatsCard
                    count={totalVehicles}
                />

                <UploadSection
                    onUploadSuccess={
                        handleUploadSuccess
                    }
                />

                <section className="records-section">

                    <div className="section-header">

                        <div>
                            <h2>Vehicle Records</h2>

                            <p>
                                View and search uploaded vehicle data
                            </p>
                        </div>

                        <span className="record-count">
                            {vehicles.length} records
                        </span>

                    </div>

                    <SearchFilter
                        searchText={searchText}
                        setSearchText={setSearchText}
                        onSearch={handleSearch}
                        onClear={handleClear}
                    />

                    {error && (
                        <div className="error-message">
                            ✕ {error}
                        </div>
                    )}

                    <VehicleTable
                        vehicles={vehicles}
                        loading={loading}
                        onDownloadAll={handleDownloadAll}
                        onEdit={handleEdit}
                        onDelete={handleDelete}
                    />

                </section>

            </main>

            {isEditModalOpen && (
                <EditModal
                    vehicle={editingVehicle}
                    onSave={handleSaveEdit}
                    onCancel={handleCloseEditModal}
                    loading={isSubmitting}
                />
            )}

        </div>
    );
}

export default App;