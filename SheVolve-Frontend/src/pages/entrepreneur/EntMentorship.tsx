import { useEffect } from "react";
import getJwtToken from "../../hooks/GetJwtToken";
import Footer from "../Footer";
import JoinNgo from "../JoinNgo";
import EntrepreneurNavBar from "./EntrepreneurNavBar";

const EntMentorship = () => {

    let hasNgo = false;

    const getAllUsers = async () => {
        const token = getJwtToken();
        if (!token) return;
        try {
            const userResponse = await fetch(`http://localhost:8080/api/entrepreneur/status/mentorship`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (userResponse.status === 200) {
                hasNgo = await userResponse.body;
            } else {
                console.error("Could not fetch status");
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        getAllUsers();
    }, []);



    return (
        <div>
            <EntrepreneurNavBar />
            {!hasNgo && <JoinNgo />}
            <Footer />
        </div>
    );

}

export default EntMentorship;