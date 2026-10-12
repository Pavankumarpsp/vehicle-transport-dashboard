const API_URL = "/api/vehicles";

export async function getVehicles() {
    const response = await fetch(API_URL);

    if (!response.ok) {
        throw new Error("Failed to fetch vehicles");
    }

    return response.json();
}

export async function getVehicleCount() {
    const response = await fetch(`${API_URL}/count`);

    if (!response.ok) {
        throw new Error("Failed to fetch vehicle count");
    }

    return response.json();
}

export async function searchVehicles(query) {
    const response = await fetch(
        `${API_URL}/search?query=${encodeURIComponent(query)}`
    );

    if (!response.ok) {
        throw new Error("Failed to search vehicles");
    }

    return response.json();
}

export async function getVehiclesByPlant(plantCode) {
    const response = await fetch(
        `${API_URL}/plant/${encodeURIComponent(plantCode)}`
    );

    if (!response.ok) {
        throw new Error("Failed to fetch vehicles by plant");
    }

    return response.json();
}

export async function uploadExcel(file) {
    const formData = new FormData();

    formData.append("file", file);

    const response = await fetch(`${API_URL}/upload`, {
        method: "POST",
        body: formData
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Failed to upload Excel file");
    }

    return response.json();
}

export async function downloadTemplate() {
    const response = await fetch(`${API_URL}/template`);

    if (!response.ok) {
        throw new Error("Failed to download template");
    }

    const blob = await response.blob();

    const url = window.URL.createObjectURL(blob);

    const link = document.createElement("a");

    link.href = url;
    link.download = "vehicle_template.xlsx";

    document.body.appendChild(link);

    link.click();

    link.remove();

    window.URL.revokeObjectURL(url);
}

export async function downloadAllVehiclesExcel() {
    const response = await fetch(`${API_URL}/export`);

    if (!response.ok) {
        throw new Error("Failed to download vehicle records");
    }

    const blob = await response.blob();

    const url = window.URL.createObjectURL(blob);

    const link = document.createElement("a");

    link.href = url;
    link.download = "vehicle_records.xlsx";

    document.body.appendChild(link);

    link.click();

    link.remove();

    window.URL.revokeObjectURL(url);
}

export async function updateVehicle(id, updatedData) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedData)
    });

    if (!response.ok) {
        throw new Error("Failed to update vehicle");
    }

    return response.json();
}

export async function deleteVehicle(id) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    if (!response.ok) {
        throw new Error("Failed to delete vehicle");
    }
}
