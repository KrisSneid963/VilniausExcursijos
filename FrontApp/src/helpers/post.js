import axios from "axios";

const url = "http://localhost:8080/api/register";

export const postData = async (data) => {
  try {
    console.log("Sending data to server:", data); 
    const response = await axios.post(url, data, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log("Server response:", response.data); //server resp if smthn wrong
    return response.data;
  } catch (error) {
    console.error("Error response:", error.response); 
    if (error.response) {
      console.error("Error status:", error.response.status); 
      console.error("Error data:", error.response.data); 
    } else {
      console.error("Error message:", error.message); 
    }
    if (error.response && error.response.data && error.response.data.errors) {
     
      throw new Error(error.response.data.errors.map(err => err.message).join(", "));
    } else {
      throw new Error("An error occurred while registering");
    }
  }
};