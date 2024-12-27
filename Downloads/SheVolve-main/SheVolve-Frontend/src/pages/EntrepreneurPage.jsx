import React from "react";
import Card from "../components/Card";
import NavBar from "../components/NavBar";
import '../styles/data-pages.css'; 

const EntrepreneurPage = () => {
  const entrepreneurData = [
    {
      firstName: "Ali",
      lastName: "Khan",
      username: "alikhan",
      email: "ali@example.com",
      mentorId: "M101",
    },
    
  ];

  return (
    <>
      <NavBar />
      <div className="page-container">
        <h1 className="page-title">Entrepreneur Data</h1>
        <div className="card-container">
          {entrepreneurData.map((data, index) => (
            <Card key={index} data={data} type="Entrepreneur" />
          ))}
        </div>
      </div>
    </>
  );
};

export default EntrepreneurPage;
