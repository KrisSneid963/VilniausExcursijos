import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";

const TourCard = ({ tour }) => {
    const navigate = useNavigate();

    const handleBooking = () => {
        const token = localStorage.getItem("token");

        if (!token) {
            alert("Please register to book this tour!");
            navigate("/register");
            return;
        }

        fetch("http://localhost:8080/api/bookings", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                userId: localStorage.getItem("userId"),
                tourId: tour.id,
                bookedDate: new Date().toISOString().split("T")[0], 
            }),
        })
        .then(response => response.json())
        .then(data => {
            if (!data.success) {
                throw new Error(data.message || "Failed to book tour");
            }
            alert(`Tour "${tour.title}" has been booked successfully!`);
            navigate("/my-bookings");
        })
        .catch(error => {
            console.error("Booking failed:", error);
            alert(`Booking failed: ${error.message}`);
        });
    };

    return (
        <div className="bg-white p-4 shadow-md rounded-lg">
            <img src={tour.imageUrl} alt={tour.title} className="w-full h-40 object-cover rounded-md" />
            <h2 className="text-lg font-semibold mt-2">{tour.title}</h2>
            <p className="text-gray-600">{tour.category}</p>
            <p className="text-green-600 font-bold">${tour.price}</p>

        
            {tour.availableDates && tour.availableDates.length > 0 ? (
                <div className="mt-2">
                    <p className="font-semibold">Available Dates:</p>
                    <ul className="list-disc pl-5 text-gray-600">
                        {tour.availableDates.map((date, index) => (
                            <li key={index}>{date}</li>
                        ))}
                    </ul>
                </div>
            ) : (
                <p className="text-gray-500">No dates available</p>
            )}

            <button 
                onClick={handleBooking} 
                className="bg-blue-500 text-white px-4 py-2 rounded-md mt-2 hover:bg-blue-600">
                Book Now
            </button>
        </div>
    );
};

TourCard.propTypes = {
    tour: PropTypes.shape({
        id: PropTypes.string.isRequired,
        imageUrl: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        category: PropTypes.string.isRequired,
        price: PropTypes.number.isRequired,
        availableDates: PropTypes.arrayOf(PropTypes.string), // Add availableDates prop
    }).isRequired,
};

export default TourCard;
