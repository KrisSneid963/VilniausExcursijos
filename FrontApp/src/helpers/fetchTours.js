const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api"; 

export async function fetchTours() {
    try {
        console.log("Fetching tours from:", `${API_URL}/tours`); 
        const response = await fetch(`${API_URL}/tours`);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log("Fetched Tours:", data); 
        return data;
    } catch (error) {
        console.error("Error fetching tours:", error);
        return [];
    }
}

