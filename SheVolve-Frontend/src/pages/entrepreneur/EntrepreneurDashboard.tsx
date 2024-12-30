import React from 'react';
import EntrepreneurNavBar from './EntrepreneurNavBar';
import Footer from '../Footer';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../../components/ui/card";
import { Package, Users, Building2, BookOpen } from 'lucide-react';
import '../../styles/entrepreneur-dashboard.css';

interface UserDao {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    userRole: string;
}

interface EntrepreneurDashboardProps {
    user: UserDao;
}

const EntrepreneurDashboard: React.FC<EntrepreneurDashboardProps> = ({ user }) => {
    return (
        <div className="entrepreneur-dashboard">
            <EntrepreneurNavBar />
            <main className="entrepreneur-dashboard-content">
                <Card className="welcome-card">
                    <CardHeader>
                        <CardTitle>Welcome, {user.firstName}!</CardTitle>
                        <CardDescription>Here's an overview of your entrepreneurial journey</CardDescription>
                    </CardHeader>
                </Card>
                <div className="dashboard-grid">
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <Package className="w-5 h-5 mr-2 inline-block" />
                                Products
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Manage and showcase your products</p>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <Users className="w-5 h-5 mr-2 inline-block" />
                                Mentorship
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Connect with mentors and get guidance</p>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <Building2 className="w-5 h-5 mr-2 inline-block" />
                                NGO Support
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Access NGO resources and support</p>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <BookOpen className="w-5 h-5 mr-2 inline-block" />
                                Resources
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Explore learning materials and tools</p>
                        </CardContent>
                    </Card>
                </div>
            </main>
            <Footer />
        </div>
    );
};

export default EntrepreneurDashboard;
