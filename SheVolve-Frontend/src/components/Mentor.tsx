import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card";
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "../components/ui/accordion";
import { Avatar, AvatarFallback, AvatarImage } from "../components/ui/avatar";
import { Badge } from "../components/ui/badge";
import { Users, User } from 'lucide-react';
import getJwtToken from '../hooks/GetJwtToken';
import Footer from '../pages/Footer';
import NgoNavBar from '../pages/ngo/NgoNavBar';

interface Entrepreneur {
    entrepreneurId: number;
    firstName: string;
    lastName: string
    email: string;
}

interface Mentor {
    mentorId: number
    firstName: string;
    lastName: string;
    email: string;
    entrepreneurs: Entrepreneur[];
}

export default function Mentor() {
    const token = getJwtToken();
    const [mentors, setMentors] = useState<Mentor[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchMentors();
    }, []);

    const fetchMentors = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/ngo/mentors', {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            if (!response.ok) {
                throw new Error('Failed to fetch mentors');
            }
            const data = await response.json();
            console.log(data);
            setMentors(data);
            setIsLoading(false);
        } catch (err) {
            setError('Failed to load mentors. Please try again later.');
            setIsLoading(false);
        }
    };

    if (isLoading) {
        return <div className="text-center p-8">Loading mentors...</div>;
    }

    if (error) {
        return <div className="text-center p-8 text-red-500">{error}</div>;
    }

    return (
        <>
            <NgoNavBar />
            <div className="container mx-auto p-4" style={{ minHeight: '520px' }}>
                <h1 className="text-3xl font-bold text-primary-color mb-6">Mentors and Entrepreneurs</h1>
                <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
                    {mentors.map((mentor) => (
                        <Card key={mentor.mentorId} className="bg-card-bg shadow-lg hover:shadow-xl transition-shadow duration-300">
                            <CardHeader>
                                <CardTitle className="flex items-center space-x-4">
                                    <Avatar className="w-12 h-12">
                                        <AvatarImage src={`https://api.dicebear.com/6.x/initials/svg?seed=${mentor.firstName}`} />
                                        <AvatarFallback>{mentor.firstName}</AvatarFallback>
                                    </Avatar>
                                    <div>
                                        <h2 className="text-xl font-semibold">{mentor.firstName + ' ' + mentor.lastName}</h2>
                                        <p className="text-sm text-gray-500">{mentor.email}</p>
                                    </div>
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
                                <Badge className="mb-4">{"Course Expert"}</Badge>
                                <Accordion type="single" collapsible>
                                    <AccordionItem value="entrepreneurs">
                                        <AccordionTrigger>
                                            <div className="flex items-center">
                                                <Users className="w-5 h-5 mr-2" />
                                                Entrepreneurs ({mentor.entrepreneurs.length})
                                            </div>
                                        </AccordionTrigger>
                                        <AccordionContent>
                                            <ul className="space-y-2">
                                                {mentor.entrepreneurs.map((entrepreneur) => (
                                                    <li key={entrepreneur.entrepreneurId} className="flex items-center space-x-2">
                                                        <User className="w-4 h-4 text-gray-500" />
                                                        <div>
                                                            <p className="font-medium">{entrepreneur.firstName}</p>
                                                            <p className="text-sm text-gray-500">{"Enthuiast"}</p>
                                                        </div>
                                                    </li>
                                                ))}
                                            </ul>
                                        </AccordionContent>
                                    </AccordionItem>
                                </Accordion>
                            </CardContent>
                        </Card>
                    ))}
                </div>
            </div>
            <Footer />
        </>
    );
}

