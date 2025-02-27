

import { useEffect, useState } from "react";
import { fetchTours } from "../../helpers/fetchTours";
import TourCard from "./TourCard";

const TourList = () => {
    const [tours, setTours] = useState([]);
    const [loading, setLoading] = useState(true); //loading for faster 
    const [error, setError] = useState(null); 

    useEffect(() => {
        const loadTours = async () => {
            try {
                console.log("Fetching tours in useEffect...");
                const data = await fetchTours();
                console.log("Tour List Received Data:", data);

                if (Array.isArray(data)) {
                    setTours(data);
                } else {
                    console.error("Unexpected data format:", data);
                    setError("Failed to load tours.");
                }
            } catch (err) {
                console.error("Error fetching tours:", err);
                setError("Failed to load tours. Please try again later.");
            } finally {
                setLoading(false); 
            }
        };

        loadTours();
    }, []);

    return (
        <div 
          className="min-h-screen bg-cover bg-center flex flex-col items-center p-8"
          style={{ backgroundImage: "url('/assets/VilniusDownload.jpg')" }} 
        >
          <div className="bg-amber-40">
            <h1 className="text-4xl font-bold">💚</h1>
          </div>

          <div className="flex justify-center items-center flex-wrap gap-8 mt-8 w-full max-w-6xl">
            {loading ? (
              <p className="text-white text-lg">Loading tours...</p>
            ) : error ? (
              <p className="text-red-500 text-lg">{error}</p>
            ) : tours.length === 0 ? (
              <p className="text-white text-lg">No tours available.</p>
            ) : (
              tours.map((tour) => <TourCard key={tour.id} tour={tour} />)
            )}
          </div>
        </div>
      );
};

export default TourList;
