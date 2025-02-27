import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";

const MyBooking = () => {
    const [booking, setBooking] = useState(null);
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const location = useLocation();

    const userEmail = sessionStorage.getItem("userEmail");

    const tourId = new URLSearchParams(location.search).get("tourId");

    useEffect(() => {
        if (!userEmail) {
           
            navigate("/login");
            return;
        }

        if (!tourId) {
            setError("No tour selected.");
            return;
        }

      
        fetch(`http://localhost:8080/api/bookings/tour/${tourId}`)
            .then((response) => response.json())
            .then((data) => {
                if (data.length > 0) {
                    setBooking(data[0]);  //booking for the tour
                } else {
                    setError("No booking found for this tour.");
                }
            })
            .catch((error) => {
                setError("Error fetching booking data.");
                console.error(error);
            });
    }, [tourId, userEmail, navigate]);

    return (
        <div className="p-6">
            <h2 className="text-2xl font-bold mb-4">My Booking</h2>
            
            {error && <p className="text-red-500">{error}</p>} 
            
            
            {booking && (
                <div className="border p-4 mb-2 rounded-md">
                    <h3 className="font-semibold">{booking.tour.title}</h3>
                    <p><strong>Booked on:</strong> {booking.bookedDate}</p>
                    <p><strong>Confirmed:</strong> {booking.confirmed ? "Yes" : "No"}</p>
                </div>
            )}
        </div>
    );
};

export default MyBooking;
