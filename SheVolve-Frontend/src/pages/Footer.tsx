import React from 'react';
import { Link } from 'react-router-dom';
import { Input } from "../components/ui/input";
import { Button } from "../components/ui/button";
import { Facebook, Twitter, Instagram, Linkedin } from 'lucide-react';
import '../styles/footer.css';
import logo from '../../public/assets/logo.png';

const Footer: React.FC = () => {
    return (
        <footer className="site-footer">
            <div className="footer-content">
                <div className="footer-section">
                    <img src={logo} alt="SheVolve Logo" className="footer-logo" />
                    <p>Empowering women through entrepreneurship, mentorship, and community support.</p>
                </div>
                <div className="footer-section">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/about">About Us</Link></li>
                        <li><Link to="/programs">Programs</Link></li>
                        <li><Link to="/resources">Resources</Link></li>
                        <li><Link to="/contact">Contact</Link></li>
                    </ul>
                </div>
                <div className="footer-section">
                    <h3>Connect With Us</h3>
                    <div className="social-icons">
                        <a href="https://facebook.com" target="_blank" rel="noopener noreferrer"><Facebook size={24} /></a>
                        <a href="https://twitter.com" target="_blank" rel="noopener noreferrer"><Twitter size={24} /></a>
                        <a href="https://instagram.com" target="_blank" rel="noopener noreferrer"><Instagram size={24} /></a>
                        <a href="https://linkedin.com" target="_blank" rel="noopener noreferrer"><Linkedin size={24} /></a>
                    </div>
                </div>
                <div className="footer-section">
                    <h3>Newsletter</h3>
                    <p>Stay updated with our latest news and events.</p>
                    <form className="newsletter-form">
                        <Input type="email" placeholder="Enter your email" />
                        <Button type="submit">Subscribe</Button>
                    </form>
                </div>
            </div>
            <div className="footer-bottom">
                <p>&copy; 2023 SheVolve. All rights reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;

