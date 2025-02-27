import React from "react";
import TourList from "../tours/TourList"; 

const Home = () => {
  return (
    <div className="bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-lg">
        <h1 className="text-3xl font-bold text-center text-green-600">
          Welcome to Vilnius Tours
        </h1>
        <p className="text-gray-700 text-center mt-2">
          Discover the beauty of Vilnius through guided tours and unique
          experiences.
        </p>
      </div>


      <div className="mt-8">
        <TourList />
      </div>
    </div>
  );
};

export default Home;
