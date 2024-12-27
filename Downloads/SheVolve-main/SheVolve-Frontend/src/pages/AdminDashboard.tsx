import { useLocation } from 'react-router-dom';
import getJwtToken from '../hooks/GetJwtToken';
import { useState } from 'react';


 
interface UserDao {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    userRole: string;  // Assuming UserRole is a string (e.g., "ROLE_ENTREPRENEUR")
}

function Dashboard() {
    const location = useLocation();
    const userDao = location.state?.userDao as UserDao;  // Extract UserDao from location state
    const token = getJwtToken();

    if (!userDao) {
        return <div>No user data found. Please log in again.</div>;
    }

    const getAllUsers = async () => {
        console.log('Validating');
        console.log(token);
        const response = await fetch(`http://localhost:8080/api/user/validate`, {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        console.log(response.status);
        if (response.status === 200) {
            const body: UserDao = await response.json();
            if (body.userRole === "ROLE_ADMIN")
                setAdmin('Admin')
        }
    }

    const [admin, setAdmin] = useState<string>('Not Admin');

    return ( 
        <>
        <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h1>Welcome, {userDao.firstName}!</h1>
            <ul style={{ listStyle: 'none', padding: 0 }}>
                <li><strong>First Name:</strong> {userDao.firstName}</li>
                <li><strong>Last Name:</strong> {userDao.lastName}</li>
                <li><strong>Username:</strong> {userDao.username}</li>
                <li><strong>Email:</strong> {userDao.email}</li>
                <li><strong>Password:</strong> {userDao.password}</li>
                <li><strong>Role:</strong> {userDao.userRole}</li>
                <li>{admin}</li>
            </ul>
            <button onClick={getAllUsers}>all users</button>
        </div>
        </>
    );
}

export default Dashboard;
