import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const BookingPage = () => {
    const [tours, setTours] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {
        const fetchTours = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/tours"); 
                if (!response.ok) throw new Error("Failed to fetch tours");
                const data = await response.json();
                setTours(data);
            } catch (error) {
                console.error("Error fetching tours:", error);
            }
        };
        fetchTours();
    }, []);

    const handleBooking = (tourId) => {
        const userId = localStorage.getItem("userId");
        const token = localStorage.getItem("token");

        if (!userId || !token) {
          
            localStorage.setItem("pendingBooking", JSON.stringify({ tourId }));
            alert("Please log in or register to book this tour.");
            navigate("/auth");
            return;
        }

     
        bookTour(userId, token, tourId);
    };

    const bookTour = async (userId, token, tourId) => {
        try {
            const response = await fetch("http://localhost:8080/api/bookings", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify({
                    userId: Number(userId),
                    tourId: Number(tourId),
                    bookedDate: new Date().toISOString().split("T")[0],
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "Failed to book tour");
            }

            alert("✅ Tour has been booked successfully!");
            localStorage.removeItem("pendingBooking");
            navigate("/my-bookings"); 
        } catch (error) {
            console.error("❌ Booking failed:", error);
            alert(`⚠️ Booking failed: ${error.message}`);
        }
    };

    return (
        <div className="max-w-5xl mx-auto p-6">
            <h1 className="text-3xl font-bold text-center mb-6">Available Tours</h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
                {tours.length > 0 ? (
                    tours.map((tour) => (
                        <div key={tour.id} className="bg-white p-4 shadow-md rounded-lg">
                            <img src={tour.imageUrl} alt={tour.title} className="w-full h-40 object-cover rounded-md" />
                            <h2 className="text-lg font-semibold mt-2">{tour.title}</h2>
                            <p className="text-gray-600">{tour.category}</p>
                            <p className="text-green-600 font-bold">${tour.price}</p>
                            <button 
                                onClick={() => handleBooking(tour.id)} 
                                className="bg-blue-500 text-white px-4 py-2 rounded-md mt-2 hover:bg-blue-600">
                                Book Now
                            </button>
                        </div>
                    ))
                ) : (
                    <p className="text-center text-gray-600">No tours available at the moment.</p>
                )}
            </div>
        </div>
    );
};

export default BookingPage;
