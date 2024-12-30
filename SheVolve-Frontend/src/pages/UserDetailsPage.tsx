import React, { useEffect, useState } from 'react';
import { UserDetails } from '../../src/components/UserDetails';
import getJwtToken from '../hooks/GetJwtToken';
import { UserDao } from '../../public/types/User';
import { useNavigate } from 'react-router-dom';
import EntrepreneurNavBar from './entrepreneur/EntrepreneurNavBar';
import Footer from './Footer';

const UserDetailsPage: React.FC = () => {
    const token = getJwtToken();
    const [userDao, setUserDao] = useState<UserDao | null>(null); // Initialize with null
    const navigate = useNavigate();

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
                    alert('Invalid token! Please log in again.');
                    navigate('/signin');
                }
            } catch (error) {
                console.error('Token validation failed:', error);
                alert('An error occurred during token validation. Please log in again.');
                navigate('/signin');
            }
        };

        if (token) {
            validateToken();
        } else {
            navigate('/signin'); // Redirect if no token is found
        }
    }, [token, navigate]);

    if (!userDao) {
        // Show a loading indicator while fetching data
        return (
            <div className="min-h-screen flex items-center justify-center bg-background-color">
                <p>Loading...</p>
            </div>
        );
    }

    return (
        <>
            <EntrepreneurNavBar />
            <div className="min-h-screen bg-background-color py-12 px-4 sm:px-6 lg:px-8">
                <div className="max-w-md mx-auto">
                    <h1 className="text-3xl font-bold text-primary-color text-center mb-8">User Details</h1>
                    <UserDetails user={userDao} />
                </div>
            </div>
            <Footer />
        </>
    );
};

export default UserDetailsPage;
