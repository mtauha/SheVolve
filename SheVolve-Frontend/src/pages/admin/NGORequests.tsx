import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import RequestCard from "../../components/card/RequestCard";
import AdminNavBar from "../../pages/admin/AdminNavBar";
import Footer from "../../pages/Footer";
import getJwtToken from "../../hooks/GetJwtToken";
import { motion } from "framer-motion";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "../../components/ui/tabs";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../../components/ui/card";
import { Inbox, CheckCircle, XCircle } from 'lucide-react';
import '../../styles/ngo-requests.css';

interface RequestData {
    requesterName: string;
    requestTime: string;
    request: string;
    requestStatus: string;
}

const NGORequests: React.FC = () => {
    const [requests, setRequests] = useState<RequestData[]>([]);
    const token = getJwtToken();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/admin/requests`, {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (response.status === 200) {
                    const data = await response.json();
                    setRequests(data);
                    // console.log(data);
                } else {
                    alert("Could not fetch requests");
                    navigate('/signin');
                }
            } catch (error) {
                console.error("Error fetching requests:", error);
                alert("An error occurred while fetching requests");
                navigate('/signin');
            }
        };

        if (token) {
            fetchRequests();
        } else {
            navigate('/signin');
        }
    }, [token, navigate]);

    const updateRequests = (updatedRequest: RequestData) => {
        setRequests((prevRequests) =>
            prevRequests.map((req) =>
                req.requesterName === updatedRequest.requesterName ? updatedRequest : req
            )
        );
    };

    const currentRequests = requests.filter(req => req.requestStatus === "WAITING");
    const approvedRequests = requests.filter(req => req.requestStatus === "APPROVED");
    const rejectedRequests = requests.filter(req => req.requestStatus === "REJECTED");

    return (
        <div className="ngo-requests-page">
            <AdminNavBar />
            <main className="ngo-requests-content">
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.5 }}
                >
                    <Card className="ngo-requests-card">
                        <CardHeader>
                            <CardTitle>NGO Requests Inbox</CardTitle>
                            <CardDescription>Manage and review NGO registration requests</CardDescription>
                        </CardHeader>
                        <CardContent>
                            <Tabs defaultValue="current" className="ngo-requests-tabs">
                                <TabsList>
                                    <TabsTrigger value="current">
                                        <Inbox className="mr-2 h-4 w-4" />
                                        Current ({currentRequests.length})
                                    </TabsTrigger>
                                    <TabsTrigger value="approved">
                                        <CheckCircle className="mr-2 h-4 w-4" />
                                        Approved ({approvedRequests.length})
                                    </TabsTrigger>
                                    <TabsTrigger value="rejected">
                                        <XCircle className="mr-2 h-4 w-4" />
                                        Rejected ({rejectedRequests.length})
                                    </TabsTrigger>
                                </TabsList>
                                <TabsContent value="current">
                                    <div className="requests-grid">
                                        {currentRequests.map((data, index) => (
                                            <RequestCard key={index} data={data} onUpdate={updateRequests} />
                                        ))}
                                    </div>
                                </TabsContent>
                                <TabsContent value="approved">
                                    <div className="requests-grid">
                                        {approvedRequests.map((data, index) => (
                                            <RequestCard key={index} data={data} onUpdate={updateRequests} />
                                        ))}
                                    </div>
                                </TabsContent>
                                <TabsContent value="rejected">
                                    <div className="requests-grid">
                                        {rejectedRequests.map((data, index) => (
                                            <RequestCard key={index} data={data} onUpdate={updateRequests} />
                                        ))}
                                    </div>
                                </TabsContent>
                            </Tabs>
                        </CardContent>
                    </Card>
                </motion.div>
            </main>
            <Footer />
        </div>
    );
};

export default NGORequests;

