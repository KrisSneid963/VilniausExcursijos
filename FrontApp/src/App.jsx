import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import Home from "./components/main/Home";
import TourDetail from "./components/tours/TourDetail";
import Registration from "./components/auth/Registration";
import Login from "./components/auth/Login"; 
import MyBooking from "./components/tours/MyBooking"; 
import TourList from "./components/tours/TourList"; 

import NavBar from "./components/main/NavBar";
import Footer from "./components/main/Footer";
import Header from "./components/main/Header";

const App = () => {
  return (
    <BrowserRouter>
      <NavBar />
      <Header />
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/tours" element={<TourList />} />
          <Route path="/tours/:id" element={<TourDetail />} />
          <Route path="/register" element={<Registration />} />
          <Route path="/login" element={<Login />} />
          <Route path="/my-bookings" element={<MyBooking />} />
        </Routes>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
