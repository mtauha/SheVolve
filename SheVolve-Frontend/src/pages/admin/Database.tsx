import { useState, useEffect } from "react";
import UserCard from "../../components/card/UserCard";
import AdminNavBar from "./AdminNavBar";
import getJwtToken from "../../hooks/GetJwtToken";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "../../components/ui/tabs";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../../components/ui/card";
import { Users, UserPlus, Building2, BookOpen } from 'lucide-react';
import "../../styles/user-data.css";
import Footer from "../Footer";

const UserData = () => {
    const [entrepreneurs, setEntrepreneurs] = useState([]);
    const [mentors, setMentors] = useState([]);
    const [ngos, setNgos] = useState([]);
    const [resources, setResources] = useState([]);
    const token = getJwtToken();

    const getAllUsers = async () => {
        if (!token) return;
        try {
            const userResponse = await fetch(`http://localhost:8080/api/admin/users/get`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (userResponse.status === 200) {
                const data = await userResponse.json();
                setEntrepreneurs(data.filter((user: { userRole: string }) => user.userRole === "ROLE_ENTREPRENEUR"));
                setMentors(data.filter((user: { userRole: string }) => user.userRole === "ROLE_MENTOR"));
                setNgos(data.filter((user: { userRole: string }) => user.userRole === "ROLE_NGO"));
            } else {
                console.error("Could not fetch users");
            }

            const resourceResponse = await fetch(`http://localhost:8080/api/admin/resources/get`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (resourceResponse.status === 200) {
                const data = await resourceResponse.json();
                setResources(data);
            } else {
                console.error("Could not fetch resources");
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        getAllUsers();
    }, [token]);

    return (
        <div className="user-data-page">
            <AdminNavBar />
            <main className="user-data-content">
                <Card className="user-data-card">
                    <CardHeader>
                        <CardTitle>User Database</CardTitle>
                        <CardDescription>View and manage user data across different roles</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <Tabs defaultValue="entrepreneurs" className="user-data-tabs">
                            <TabsList>
                                <TabsTrigger value="entrepreneurs">
                                    <UserPlus className="mr-2 h-4 w-4" />
                                    Entrepreneurs
                                </TabsTrigger>
                                <TabsTrigger value="mentors">
                                    <Users className="mr-2 h-4 w-4" />
                                    Mentors
                                </TabsTrigger>
                                <TabsTrigger value="ngos">
                                    <Building2 className="mr-2 h-4 w-4" />
                                    NGOs
                                </TabsTrigger>
                                <TabsTrigger value="resources">
                                    <BookOpen className="mr-2 h-4 w-4" />
                                    Resources
                                </TabsTrigger>
                            </TabsList>
                            <TabsContent value="entrepreneurs">
                                <div className="user-grid">
                                    {entrepreneurs.map((data, index) => (
                                        <UserCard key={index} data={data} type="Entrepreneur" />
                                    ))}
                                </div>
                            </TabsContent>
                            <TabsContent value="mentors">
                                <div className="user-grid">
                                    {mentors.map((data, index) => (
                                        <UserCard key={index} data={data} type="Mentor" />
                                    ))}
                                </div>
                            </TabsContent>
                            <TabsContent value="ngos">
                                <div className="user-grid">
                                    {ngos.map((data, index) => (
                                        <UserCard key={index} data={data} type="NGO" />
                                    ))}
                                </div>
                            </TabsContent>
                            <TabsContent value="resources">
                                <div className="user-grid">
                                    {resources.map((data, index) => (
                                        <UserCard key={index} data={data} type="Resource" />
                                    ))}
                                </div>
                            </TabsContent>
                        </Tabs>
                    </CardContent>
                </Card>
            </main>
            <Footer />
        </div>
    );
};

export default UserData;
