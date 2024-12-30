import { Card } from '../../src/components/ui/card'
import { UserDao } from '../../public/types/User'

interface UserDetailsProps {
    user: UserDao
}

export function UserDetails({ user }: UserDetailsProps) {
    return (
        <Card className="bg-card-bg p-6 shadow-lg rounded-lg">
            <div className="space-y-4">
                <DetailItem label="First Name" value={user.firstName} />
                <DetailItem label="Last Name" value={user.lastName} />
                <DetailItem label="Username" value={user.username} />
                <DetailItem label="Email" value={user.email} />
                <DetailItem label="User Role" value={user.userRole} />
            </div>
        </Card>
    )
}

function DetailItem({ label, value }: { label: string; value: string }) {
    return (
        <div>
            <dt className="text-sm font-medium text-gray-500">{label}</dt>
            <dd className="mt-1 text-lg text-text-color">{value}</dd>
        </div>
    )
}