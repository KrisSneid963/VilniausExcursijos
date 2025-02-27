import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, Link } from "react-router-dom";
import { postData } from "../../helpers/post";

const Registration = () => {
    const { register, formState: { errors }, reset, handleSubmit } = useForm();
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [isRegistered, setIsRegistered] = useState(false);
    const [bookedTour, setBookedTour] = useState(null);
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');
    
        try {
            const response = await postData(data); 
    
            if (!response.id) throw new Error("Registration failed");
    
            localStorage.setItem("userId", response.id);  
            localStorage.setItem("userEmail", response.email);
    
            reset();
            setSuccessMessage('Registration successful! Redirecting...');
            setIsRegistered(true);
    
            setTimeout(() => {
                navigate('/my-bookings'); 
            }, 2000);
        } catch (error) {
            setErrorMessage(error.response?.data?.message || 'Registration failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const bookTour = async (userId, tourId) => {
        try {
            const response = await fetch("http://localhost:8080/api/bookings", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ userId: Number(userId), tourId: Number(tourId), bookedDate: new Date().toISOString().split("T")[0] }),
            });

            if (!response.ok) throw new Error("⚠️ Booking failed");

            const bookedTourData = await response.json();
            setBookedTour(bookedTourData);
        } catch (error) {
            console.error("Booking failed:", error);
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-md">
                <h2 className="text-2xl font-bold text-center text-neutral-800">Register to Book Tours</h2>
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Name</label>
                        <input type="text" placeholder="Enter your name" {...register("name", { required: "Name is required" })} className="w-full px-3 py-2 mt-1 border rounded-md focus:outline-none focus:ring focus:ring-indigo-200" />
                        {errors.name && <p className="mt-1 text-sm text-red-600">{errors.name.message}</p>}
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Email</label>
                        <input type="email" placeholder="Enter your email" {...register("email", { required: "Email is required" })} className="w-full px-3 py-2 mt-1 border rounded-md focus:outline-none focus:ring focus:ring-indigo-200" />
                        {errors.email && <p className="mt-1 text-sm text-red-600">{errors.email.message}</p>}
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Password</label>
                        <input type="password" placeholder="Enter your password" {...register("password", { required: "Password is required" })} className="w-full px-3 py-2 mt-1 border rounded-md focus:outline-none focus:ring focus:ring-indigo-200" />
                        {errors.password && <p className="mt-1 text-sm text-red-600">{errors.password.message}</p>}
                    </div>
                    <button type="submit" className="w-full px-4 py-2 font-medium text-white bg-green-500 hover:bg-green-700 focus:outline-none focus:ring focus:ring-green-200" disabled={loading}>
                        {loading ? "Submitting..." : "Register"}
                    </button>
                    {errorMessage && <p className="mt-2 text-sm text-red-600">{errorMessage}</p>}
                    {successMessage && <p className="mt-2 text-sm text-green-600 text-center">{successMessage}</p>}
                </form>
                <div className="mt-4 text-center">
                    <Link to="/" className="text-red-500 hover:underline">
                        Back to Home Page
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Registration;
