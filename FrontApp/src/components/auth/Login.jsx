import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) throw new Error("Probably unauthorized/see console");

            const data = await response.json();
            localStorage.setItem("token", data.token);
            localStorage.setItem("userId", data.userId);
            navigate("/my-bookings"); // Redirect to bookings
        } catch (error) {
            alert(error.message);
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <div className="bg-white p-6 shadow-md rounded-lg w-96">
                <h2 className="text-center text-2xl font-bold">Login</h2>
                <form onSubmit={handleLogin} className="space-y-4">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        className="w-full p-2 border rounded-md"
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        className="w-full p-2 border rounded-md"
                    />
                    <button type="submit" className="w-full bg-green-500 text-white p-2 rounded-md">
                        Login
                    </button>
                </form>
                <div className="mt-4 text-center">
                    <Link to="/" className="text-red-600 hover:underline">
                        Back to Home Page
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Login;
