import React from 'react';
import { Link } from 'react-router-dom';
import AdminNavBar from './AdminNavBar';
import { Card, CardContent, CardHeader, CardTitle } from "../../components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "../../components/ui/avatar";
import { BarChart, Users, Inbox, Settings } from 'lucide-react';
import '../../styles/admin-dashboard.css';

interface UserDao {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    userRole: string;
}

function AdminDashboard({ userDao }: { userDao: UserDao }) {
    return (
        <div className="admin-dashboard">
            <AdminNavBar />
            <main className="admin-main">
                <div className="admin-content">
                    <Card className="admin-welcome-card">
                        <CardHeader>
                            <CardTitle>Welcome, {userDao.firstName}!</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Manage your admin tasks and oversee user activities from this dashboard.</p>
                        </CardContent>
                    </Card>

                    <div className="admin-grid">
                        <Card className="admin-profile-card">
                            <CardHeader>
                                <CardTitle>Admin Profile</CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="admin-profile-info">
                                    <Avatar className="admin-avatar">
                                        <AvatarImage src={`https://api.dicebear.com/6.x/initials/svg?seed=${userDao.firstName} ${userDao.lastName}`} />
                                        <AvatarFallback>{userDao.firstName[0]}{userDao.lastName[0]}</AvatarFallback>
                                    </Avatar>
                                    <div>
                                        <h2>{userDao.firstName} {userDao.lastName}</h2>
                                        <p>{userDao.email}</p>
                                        <p className="admin-role">{userDao.userRole}</p>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        <Card className="admin-quick-actions">
                            <CardHeader>
                                <CardTitle>Quick Actions</CardTitle>
                            </CardHeader>
                            <CardContent>
                                <ul>
                                    <li>
                                        <Link to="/admin/data">
                                            <BarChart size={24} />
                                            <span>View Database</span>
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/admin/requests">
                                            <Inbox size={24} />
                                            <span>Check Inbox</span>
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/admin/management">
                                            <Users size={24} />
                                            <span>Manage Users</span>
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/admin/settings">
                                            <Settings size={24} />
                                            <span>Settings</span>
                                        </Link>
                                    </li>
                                </ul>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </main>
        </div>
    );
}

export default AdminDashboard;
