import React from "react";
import Card from "../components/Card";
import NavBar from "../components/NavBar";
import '../styles/data-pages.css';

const MentorPage = () => {
    const mentorData = [
      {
        firstName: "Sara",
        lastName: "Ahmad",
        username: "sarahmad",
        email: "sara@example.com",
        ngoId: "N202",
      },
    ];
  
    return (
        <>
      <NavBar />
      <div className="page-container">
        <h1 className="page-title">Mentor Data</h1>
        <div className="card-container">
          {mentorData.map((data, index) => (
            <Card key={index} data={data} type="Mentor" />
          ))}
        </div>
      </div>
    </>
    );
  };
  
  export default MentorPage;