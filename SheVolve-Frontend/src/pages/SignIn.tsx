import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import '../styles/login.css';
import logo from '../../public/assets/logo.png'
import Cookies from "js-cookie";

const SignIn: React.FC = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });
    const [message, setMessage] = useState<string | null>(null);
    const [messageType, setMessageType] = useState<'success' | 'error' | null>(null);
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // Prevent form submission and page reload
        const formDataObj = new FormData();
        formDataObj.append('username', formData.username);
        formDataObj.append('password', formData.password);

        console.log(formData);
        try {
            const response = await fetch('http://localhost:8080/api/user/login', {
                method: 'POST',
                body: formDataObj,
            });

            if (response.status === 200) {
                const token = await response.text();
                Cookies.set('jwt', token, { expires: 7 }); // Set cookie with token, expires in 7 days
                setMessage('Login successful! Redirecting...'); // Set success message
                setMessageType('success');
                setTimeout(() => {
                    navigate('/dashboard'); // Pass UserDao to Dashboard
                }, 1000);
            } else {
                const errorMessage = await response.text();
                setMessage(`Login failed: ${errorMessage}`);
                setMessageType('error');
            }
        } catch (error) {
            setMessage('An error occurred. Please try again.');
            setMessageType('error');
        }
    };

    return (
        <div className="login-container">
            <div className="login-image">
                <div className="overlay"></div>
            </div>
            <div className="login-form-container">
                <div className="logo-container">
                    <img src={logo} alt="EmpowerHer Logo" className="logo" />
                </div>
                <h1 className="form-title">Welcome Back</h1>
                <p className="form-subtitle">Continue your journey of empowerment</p>
                <form onSubmit={handleSubmit} className="login-form">
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
                            id="password"
                            name="password"
                            type="password"
                            required
                            value={formData.password}
                            onChange={handleChange}
                        />
                        <label htmlFor="password">Password</label>
                    </div>
                    <button type="submit" className="submit-btn">
                        Sign In
                    </button>
                    {message && (
                        <p className={`message ${messageType}`}>
                            {message}
                        </p>
                    )}
                </form>
                <p className="switch-text">
                    Don't have an account? <Link to="/signup" className="link">Sign Up</Link>
                </p>
            </div>
        </div>
    );
};

export default SignIn;
