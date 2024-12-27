import React from "react";
import "../styles/card.css";

const Card = ({ data, type }) => {
  return (
    <div className="card">
      <h2>{type} Details</h2>
      <p><strong>First Name:</strong> {data.firstName}</p>
      <p><strong>Last Name:</strong> {data.lastName}</p>
      <p><strong>Username:</strong> {data.username}</p>
      <p><strong>Email:</strong> {data.email}</p>
      {type === "Entrepreneur" && <p><strong>Mentor ID:</strong> {data.mentorId}</p>}
      {type === "Mentor" && <p><strong>NGO ID:</strong> {data.ngoId}</p>}
      {type === "NGO" && (
        <>
          <p><strong>Description:</strong> {data.description}</p>
          <p><strong>Request Status:</strong> {data.requestStatus}</p>
        </>
      )}
    </div>
  );
};

export default Card;
