import React from 'react';
import NGONavBar from '../ngo/NgoNavBar';
import Footer from '../Footer';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../../components/ui/card";
import { Users, TrendingUp, Briefcase, BookOpen } from 'lucide-react';
import '../../styles/ngo-dashboard.css';

interface UserDao {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    userRole: string;
}

interface NGODashboardProps {
    user: UserDao;
}

const NgoDashboard: React.FC<NGODashboardProps> = ({ user }) => {
    return (
        <div className="ngo-dashboard">
            <NGONavBar />
            <main className="ngo-dashboard-content">
                <Card className="welcome-card">
                    <CardHeader>
                        <CardTitle>Welcome, {user.firstName}!</CardTitle>
                        <CardDescription>Here's an overview of your NGO's impact</CardDescription>
                    </CardHeader>
                </Card>
                <div className="dashboard-grid">
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <Users className="w-5 h-5 mr-2 inline-block" />
                                Mentor Overview
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Manage and view your mentors' activities</p>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <TrendingUp className="w-5 h-5 mr-2 inline-block" />
                                Progress Analysis
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Analyze the progress of entrepreneurs</p>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <Briefcase className="w-5 h-5 mr-2 inline-block" />
                                Resource Management
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Manage and allocate resources</p>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>
                                <BookOpen className="w-5 h-5 mr-2 inline-block" />
                                Training Programs
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <p>Organize and track training initiatives</p>
                        </CardContent>
                    </Card>
                </div>
            </main>
            <Footer />
        </div>
    );
};

export default NgoDashboard;
