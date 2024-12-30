import '../../styles/user-card.css';


const ResourceCard = ({ data }) => {
    return (
        <div className="user-card">
            <p><strong>Name:</strong> {data.resourceName}</p>
            <p><strong>Mentor:</strong> {data.mentorName}</p>
            <p><strong>Mentee:</strong> {data.menteeName}</p>
            <p><strong>Description:</strong> {data.resourceDescription}</p>
            <p><strong>Type:</strong> {data.resourceType}</p>
            <p><strong>Cloud Path:</strong> {data.resourcePath}</p>
        </div>
    );
};

export default ResourceCard;