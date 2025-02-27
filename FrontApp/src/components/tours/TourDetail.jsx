import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const TourDetail = () => {
    const { id } = useParams();
    const [tour, setTour] = useState(null);

    useEffect(() => {
        const fetchTourDetail = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/tours/${id}`);
                if (!response.ok) throw new Error("Tour not found");
                const data = await response.json();
                setTour(data);
            } catch (error) {
                console.error("Error fetching tour:", error);
            }
        };
        fetchTourDetail();
    }, [id]);

    if (!tour) {
        return <p className="text-center text-gray-600">Loading tour details...</p>;
    }

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white shadow-md rounded-lg">
            <img src={tour.imageUrl} alt={tour.title} className="w-full h-60 object-cover rounded-md" />
            <h1 className="text-2xl font-bold mt-4">{tour.title}</h1>
            <p className="text-gray-700">{tour.category}</p>
            <p className="text-green-600 font-bold">${tour.price}</p>
            <p className="text-gray-600 mt-2">Duration: {tour.duration} days</p>
        </div>
    );
};

export default TourDetail;
