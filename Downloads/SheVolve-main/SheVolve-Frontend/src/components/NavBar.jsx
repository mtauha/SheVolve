import React from "react";
import { Link } from "react-router-dom";
import '../styles/navbar.css';

function NavBar() {
  return (
      <div>
        <nav className="navbar">
          <div className="navbar-container">
            <div className="navbar-logo">
            <Link to="/lol" className="navbar-link">CompanyName</Link>
            </div>

            {/* Menu Items */}
            <ul className="navbar-menu">
              <li className="navbar-item">
                <Link to="/entrepreneur" className="navbar-link">Entrepreneur Data</Link>
              </li>
              <li className="navbar-item">
                <Link to="/mentor" className="navbar-link">Mentors Data</Link>
              </li>
              <li className="navbar-item">
                <Link to="/ngo" className="navbar-link">NGO Data</Link>
              </li>
            </ul>
          </div>
        </nav>
        </div>
  );
}

export default NavBar;