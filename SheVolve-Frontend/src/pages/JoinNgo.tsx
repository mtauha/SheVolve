'use client';

import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../components/ui/select";
import { Button } from "../components/ui/button";
import { useToast } from "../hooks/use-toast";
import { Loader2 } from 'lucide-react';
import getJwtToken from '../hooks/GetJwtToken';
import { useNavigate } from 'react-router-dom';
import '../styles/join-ngo.css';

export default function JoinNgo() {
    const [ngos, setNgos] = useState<string[]>([]); // Updated to reflect the API response type
    const [selectedNgo, setSelectedNgo] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isSending, setIsSending] = useState(false);
    const { toast } = useToast();
    const navigate = useNavigate();

    useEffect(() => {
        fetchNgos();
    }, []);

    const fetchNgos = async () => {
        try {
            const token = getJwtToken(); // Ensure getJwtToken() is correctly implemented
            const response = await fetch('http://localhost:8080/api/entrepreneur/ngos', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch NGOs');
            }

            const data: string[] = await response.json(); // Assuming the API returns a list of strings
            setNgos(data);
        } catch (error) {
            console.error('Error fetching NGOs:', error);
            toast({
                title: "Error",
                description: "Failed to load NGOs. Please try again later.",
                variant: "destructive",
            });
        } finally {
            setIsLoading(false);
        }
    };

    const handleSendRequest = async () => {
        if (!selectedNgo) {
            toast({
                title: "Error",
                description: "Please select an NGO before sending a request.",
                variant: "destructive",
            });
            return;
        }

        setIsSending(true);
        try {
            const token = getJwtToken(); // Ensure getJwtToken() is correctly implemented
            const response = await fetch(`http://localhost:8080/api/entrepreneur/request/join/${selectedNgo}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify({ ngoName: selectedNgo }), // Updated to send the NGO name
            });

            if (!response.ok) {
                throw new Error('Failed to send join request');
            }

            toast({
                title: "Success",
                description: "Your request to join the NGO has been sent successfully.",
            });
            navigate('/dashboard'); // Redirect to the dashboard after sending the request
        } catch (error) {
            console.error('Error sending join request:', error);
            toast({
                title: "Error",
                description: "Failed to send join request. Please try again later.",
                variant: "destructive",
            });
        } finally {
            setIsSending(false);
        }
    };

    return (
        <div className="container">
            <Card className="card">
                <CardHeader>
                    <CardTitle className="title">Join an NGO</CardTitle>
                </CardHeader>
                <CardContent>
                    {isLoading ? (
                        <div className="loading">
                            <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                            Loading NGOs...
                        </div>
                    ) : (
                        <>
                            <Select onValueChange={(value) => setSelectedNgo(value)}>
                                <SelectTrigger className="select">
                                    <SelectValue placeholder="Select an NGO" />
                                </SelectTrigger>
                                <SelectContent>
                                    {ngos.map((ngo) => (
                                        <SelectItem key={ngo} value={ngo}>
                                            {ngo}
                                        </SelectItem>
                                    ))}
                                </SelectContent>
                            </Select>
                            <Button
                                className="button"
                                onClick={handleSendRequest}
                                disabled={!selectedNgo || isSending}
                            >
                                {isSending ? (
                                    <>
                                        <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                                        Sending Request...
                                    </>
                                ) : (
                                    'Send Join Request'
                                )}
                            </Button>
                        </>
                    )}
                </CardContent>
            </Card>
        </div>
    );
}
