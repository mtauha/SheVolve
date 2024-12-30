import { useState, useEffect } from 'react';
import AdminDashboard from './admin/AdminDashboard'; // Assuming this is the correct path
import getJwtToken from '../hooks/GetJwtToken';
import { useNavigate } from 'react-router-dom'; // To handle navigation after unsuccessful fetch
import EntrepreneurDashboard from './entrepreneur/EntrepreneurDashboard';
import NGODashboard from './ngo/NgoDashboard';
import MentorDashboard from './mentor/MentorDashboard';

interface UserDao {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    userRole: string; // Assuming UserRole is a string (e.g., "ROLE_ENTREPRENEUR")
}

function Dashboard() {
    const navigate = useNavigate();
    const token = getJwtToken();
    const [userDao, setUserDao] = useState<UserDao | null>(null);

    useEffect(() => {
        const validateToken = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/user/validate`, {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (response.status === 200) {
                    const user: UserDao = await response.json(); // Parse the UserDao object   
                    console.log(user);
                    setUserDao(user);
                } else {
                    window.alert('Invalid token! Please log in again.');
                    navigate('/signin');
                }
            } catch (error) {
                console.error('Token validation failed:', error);
                window.alert('An error occurred during token validation. Please log in again.');
                navigate('/signin');
            }
        };

        if (token) {
            validateToken();
        } else {
            navigate('/signin'); // Redirect if no token is found
        }
    }, [token, navigate]); // Dependencies to re-run effect if token or navigate changes

    const getPageByRole = () => {
        if (!userDao) {
            return <div>Loading...</div>; // Show a loading state until user data is available
        }

        if (userDao.userRole === "ROLE_ADMIN") {
            return <AdminDashboard userDao={userDao} />;
        }
        if (userDao.userRole === "ROLE_ENTREPRENEUR") {
            return <EntrepreneurDashboard user={userDao} />
        }
        if (userDao.userRole === "ROLE_MENTOR") {
            return <MentorDashboard user={userDao} />
        }
        if (userDao.userRole === "ROLE_NGO") {
            return <NGODashboard user={userDao} />
        }
        // Add other roles here if needed
        return <div>Unauthorized Access</div>; // Default fallback for unsupported roles
    };

    return (
        <div>
            {getPageByRole()}
        </div>
    );
}

export default Dashboard;
