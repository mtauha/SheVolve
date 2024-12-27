import { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // To handle navigation after successful login
import '../styles/login-signup.css';
import { Link } from "react-router-dom";
import Cookies from 'js-cookie';

function SignUp() {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        username: '',
        email: '',
        password: '',
        userRole: '',
    });

    const navigate = useNavigate(); // To redirect the user after successful login

    const [message, setMessage] = useState<string | null>(null); // State for showing success/error messages
    const [messageType, setMessageType] = useState<'success' | 'error' | null>(null); // State for message type

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
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
                setMessage('Registration successful!');
                setMessageType('success');
                const token = await response.text();
                const validateResponse = await fetch(`http://localhost:8080/api/user/validate`, {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (validateResponse.status === 200) {
                    const userDao = await validateResponse.json(); // Parse the UserDao object
                    setMessage('Login successful! Redirecting...'); // Set success message
                    setMessageType('success');
                    Cookies.set('jwt', token, { expires: 7 }); // Set cookie with token, expires in 7 days

                    setTimeout(() => {
                        navigate('/dashboard', { state: { userDao } }); // Pass UserDao to Dashboard
                    }, 1000);
                } else {
                    setMessage('Invalid token! Please log in again.');
                    setMessageType('error');
                }
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
        <div className="containerrr full-page">
            <div className="left-section">
                <div className="branding">
                    <div className="logo">LOGO</div>
                    <h1>Welcome Page</h1>
                    <p>Create your account to access resources</p>
                </div>
            </div>
            <div className="right-section">
                <div className="form-container">
                    <h1>Sign Up</h1>
                    <form onSubmit={handleSubmit}>
                        <input
                            type="text"
                            name="firstName"
                            placeholder="First Name"
                            value={formData.firstName}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="text"
                            name="lastName"
                            placeholder="Last Name"
                            value={formData.lastName}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                        <select
                            name="userRole"
                            value={formData.userRole}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select Role</option>
                            <option value="ROLE_MENTOR">Mentor</option>
                            <option value="ROLE_NGO">NGO</option>
                            <option value="ROLE_ENTREPRENEUR">Entrepreneur</option>
                        </select>
                        <button className="continue-btn" type="submit">Sign Up</button>
                    </form>
                    {message && (
                        <p className={`message ${messageType}`}>
                            {message}
                        </p>
                    )}
                    <p className="switch-text">
                        Already have an account? <Link to="/signin" className="link">Login</Link>
                    </p>
                </div>
            </div>
        </div>
    );
}

export default SignUp;
