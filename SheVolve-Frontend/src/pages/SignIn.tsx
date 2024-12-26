import { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // To handle navigation after successful login
import '../styles/login-signup.css'; // Reusing the same styles for consistency
import Cookies from 'js-cookie';
import { Link } from "react-router-dom";

function SignIn() {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    const [message, setMessage] = useState<string | null>(null); // State for showing success/error messages
    const [messageType, setMessageType] = useState<'success' | 'error' | null>(null); // To distinguish between success and error messages

    const navigate = useNavigate(); // To redirect the user after successful login

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
                // Validate token by hitting the API
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
                setMessage(`Login failed: ${errorMessage}`);
                setMessageType('error');
            }
        } catch (error) {
            setMessage('An error occurred. Please try again.');
            setMessageType('error');
        }
    };

    return (
        <div className="container full-page">
            <div className="left-section">
                <div className="branding">
                    <div className="logo">LOGO</div>
                    <h1>Welcome Page</h1>
                    <p>Login to continue access</p>
                </div>
            </div>
            <div className="right-section">
                <div className="form-container">
                    <h1>Login</h1>
                    <form onSubmit={handleSubmit}>
                        <input
                            id="username"
                            name="username"
                            type="text"
                            placeholder="Username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                        />
                        <input
                            id="password"
                            name="password"
                            type="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                        <button className="continue-btn" type="submit">Continue</button>
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
        </div>
    );
}

export default SignIn;
