import React from "react";
import Card from "../components/Card";
import NavBar from "../components/NavBar";
import '../styles/data-pages.css';

const NgoPage = () => {
    const ngoData = [
      {
        firstName: "Zara",
        lastName: "Ali",
        username: "zaraali",
        email: "zara@example.com",
        description: "Providing education resources",
        requestStatus: "Pending",
      },
    ];
  
    return (
        <>
      <NavBar />
      <div className="page-container">
        <h1 className="page-title">NGO Data</h1>
        <div className="card-container"></div>
      <div>
        {ngoData.map((data, index) => (
          <Card key={index} data={data} type="NGO" />
        ))}
      </div> </div> </> 
    );
  };
  
  export default NgoPage;