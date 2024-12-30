import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "../../components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "../../components/ui/avatar";
import { Badge } from "../../components/ui/badge";
import "../../styles/user-card.css";

interface UserCardProps {
    data: {
        firstName: string;
        lastName: string;
        username: string;
        email: string;
        mentorId?: string;
        ngoId?: string;
        description?: string;
        requestStatus?: string;
        title?: string;
        link?: string;
    };
    type: "Entrepreneur" | "Mentor" | "NGO" | "Resource";
}

const UserCard: React.FC<UserCardProps> = ({ data, type }) => {
    return (
        <Card className="user-card">
            <CardHeader className="user-card-header">
                <Avatar className="user-avatar">
                    <AvatarImage src={`https://api.dicebear.com/6.x/initials/svg?seed=${data.firstName} ${data.lastName}`} />
                    <AvatarFallback>{data.firstName?.[0]}{data.lastName?.[0]}</AvatarFallback>
                </Avatar>
                <div>
                    <CardTitle>{data.firstName} {data.lastName}</CardTitle>
                    <Badge variant="secondary">{type}</Badge>
                </div>
            </CardHeader>
            <CardContent>
                <p><strong>Username:</strong> {data.username}</p>
                <p><strong>Email:</strong> {data.email}</p>
                {type === "Entrepreneur" && <p><strong>Mentor ID:</strong> {data.mentorId || 'N/A'}</p>}
                {type === "Mentor" && <p><strong>NGO ID:</strong> {data.ngoId || 'N/A'}</p>}
                {type === "NGO" && (
                    <>
                        <p><strong>Description:</strong> {data.description}</p>
                        <p><strong>Request Status:</strong> {data.requestStatus}</p>
                    </>
                )}
                {type === "Resource" && (
                    <>
                        <p><strong>Title:</strong> {data.title}</p>
                        <p><strong>Description:</strong> {data.description}</p>
                        <p><strong>Link:</strong> <a href={data.link} target="_blank" rel="noopener noreferrer">{data.link}</a></p>
                    </>
                )}
            </CardContent>
        </Card>
    );
};

export default UserCard;
