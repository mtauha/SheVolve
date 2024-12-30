import React from "react";
import { Card, CardContent, CardFooter } from "../../components/ui/card";
import { Badge } from "../../components/ui/badge";
import { Button } from "../../components/ui/button";
import { Check, X } from 'lucide-react';
import { motion } from "framer-motion";
import getJwtToken from "../../hooks/GetJwtToken";

interface RequestData {
    requesterName: string;
    requestTime: string;
    request: string;
    requestStatus: string;
}

interface RequestCardProps {
    data: RequestData;
    onUpdate: (updatedRequest: RequestData) => void;
}

const RequestCard: React.FC<RequestCardProps> = ({ data, onUpdate }) => {
    const token = getJwtToken();
    console.log(data.requesterName);

    const handleAction = async (name: string, isApproved: boolean) => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/admin/request/ngo/${name}?approve=${isApproved}`,
                {
                    method: "POST",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            if (response.status === 200) {
                const updatedRequest = {
                    ...data,
                    requestStatus: isApproved ? "APPROVED" : "REJECTED",
                };
                onUpdate(updatedRequest);
                const action = isApproved ? "Approved" : "Rejected";
                alert(`${action} Successfully!`);
            } else {
                alert("Could not Update Status");
            }
        } catch (error) {
            console.error("Error Updating Status:", error);
            alert("An error occurred while updating the status");
        }
    };

    return (
        <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3 }}
        >
            <Card className="request-card">
                <CardContent className="grid grid-cols-6 gap-4 pt-6">
                    <div className="col-span-2">
                        <h3 className="font-semibold">{data.requesterName}</h3>
                        <p className="text-sm text-muted-foreground">
                            {new Date(data.requestTime).toLocaleDateString()}
                        </p>
                    </div>
                    <p className="col-span-4">{data.request}</p>
                </CardContent>
                <CardFooter className="justify-between">
                    <Badge
                        variant={
                            data.requestStatus === "WAITING"
                                ? "outline"
                                : data.requestStatus === "APPROVED"
                                    ? "success"
                                    : "destructive"
                        }
                    >
                        {data.requestStatus}
                    </Badge>
                    {data.requestStatus === "WAITING" && (
                        <div className="space-x-2">
                            <Button
                                size="sm"
                                variant="outline"
                                onClick={() => handleAction(data.requesterName, true)}
                            >
                                <Check className="mr-2 h-4 w-4" />
                                Approve
                            </Button>
                            <Button
                                size="sm"
                                variant="outline"
                                onClick={() => handleAction(data.requesterName, false)}
                            >
                                <X className="mr-2 h-4 w-4" />
                                Reject
                            </Button>
                        </div>
                    )}
                </CardFooter>
            </Card>
        </motion.div>
    );
};

export default RequestCard;
