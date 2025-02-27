import React from "react";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();
  const isLoggedIn = localStorage.getItem("token"); 

  const handleLogin = () => {
    if (isLoggedIn) {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      navigate("/"); 
    } else {
      navigate("/login"); 
    }
  };

  const handleRegister = () => {
    navigate("/register"); 
  };

  return (
    <nav className="bg-green-600 p-4 flex justify-between items-center">
      <h1 className="text-white text-lg font-bold">Vilnius Tours</h1>
      <div className="flex items-center space-x-4"> 
        {!isLoggedIn ? (
          <>
            <button
              onClick={handleLogin}
              className="bg-white text-green-600 px-4 py-2 rounded-md shadow-md hover:bg-gray-200"
            >
              Login
            </button>
            <button
              onClick={handleRegister}
              className="bg-white text-green-600 px-4 py-2 rounded-md shadow-md hover:bg-gray-200"
            >
              Register
            </button>
          </>
        ) : (
          <button
            onClick={handleLogin}
            className="bg-white text-green-600 px-4 py-2 rounded-md shadow-md hover:bg-gray-200"
          >
            Logout
          </button>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
