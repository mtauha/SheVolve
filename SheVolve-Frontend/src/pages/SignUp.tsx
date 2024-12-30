import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import Cookies from 'js-cookie';
import '../styles/signup.css';
import logo from '../../public/assets/logo.png'

const SignUp: React.FC = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        username: '',
        email: '',
        password: '',
        userRole: '',
    });
    const [message, setMessage] = useState<string | null>(null);
    const [messageType, setMessageType] = useState<'success' | 'error' | null>(null);
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/user/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (response.status === 201) {
                const token = await response.text();
                Cookies.set('jwt', token, { expires: 7 }); // Set cookie with token, expires in 7 days
                setMessage('Registration successful! Redirecting...'); // Set success message
                setMessageType('success');
                setTimeout(() => {
                    navigate('/dashboard'); // Pass UserDao to Dashboard
                }, 1000);
            } else {
                const errorMessage = await response.text();
                setMessage(`Registration failed: ${errorMessage}`);
                setMessageType('error');
            }
        } catch (error) {
            setMessage('An error occurred. Please try again.');
            setMessageType('error');
        }
    };

    return (
        <div className="signup-container">
            <div className="signup-image">
                <div className="overlay"></div>
            </div>
            <div className="signup-form-container">
                <div className="logo-container">
                    <img src={logo} alt="EmpowerHer Logo" className="logo" />
                </div>
                <h1 className="form-title">Join Our Community</h1>
                <p className="form-subtitle">Start your journey of empowerment today</p>
                <form onSubmit={handleSubmit} className="signup-form">
                    <div className="input-group">
                        <input
                            id="firstName"
                            name="firstName"
                            type="text"
                            required
                            value={formData.firstName}
                            onChange={handleChange}
                        />
                        <label htmlFor="firstName">First Name</label>
                    </div>
                    <div className="input-group">
                        <input
                            id="lastName"
                            name="lastName"
                            type="text"
                            required
                            value={formData.lastName}
                            onChange={handleChange}
                        />
                        <label htmlFor="lastName">Last Name</label>
                    </div>
                    <div className="input-group">
                        <input
                            id="username"
                            name="username"
                            type="text"
                            required
                            value={formData.username}
                            onChange={handleChange}
                        />
                        <label htmlFor="username">Username</label>
                    </div>
                    <div className="input-group">
                        <input
                            id="email"
                            name="email"
                            type="email"
                            required
                            value={formData.email}
                            onChange={handleChange}
                        />
                        <label htmlFor="email">Email</label>
                    </div>
                    <div className="input-group">
                        <input
                            id="password"
                            name="password"
                            type="password"
                            required
                            value={formData.password}
                            onChange={handleChange}
                        />
                        <label htmlFor="password">Password</label>
                    </div>
                    <div className="input-group">
                        <select
                            id="userRole"
                            name="userRole"
                            required
                            value={formData.userRole}
                            onChange={handleChange}
                        >
                            <option value="">Select Role</option>
                            <option value="ROLE_MENTOR">Mentor</option>
                            <option value="ROLE_NGO">NGO</option>
                            <option value="ROLE_ENTREPRENEUR">Entrepreneur</option>
                        </select>
                        <label htmlFor="userRole" className="select-label">User Role</label>
                    </div>
                    <button type="submit" className="submit-btn">
                        Sign Up
                    </button>
                    {message && (
                        <p className={`message ${messageType}`}>
                            {message}
                        </p>
                    )}
                </form>
                <p className="switch-text">
                    Already have an account? <Link to="/signin" className="link">Sign In</Link>
                </p>
            </div>
        </div>
    );
};

export default SignUp;

