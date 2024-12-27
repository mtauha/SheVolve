import { useEffect, useState } from "react"
import Cookies from "js-cookie"

const getJwtToken = () => {
    const [token, setToken] = useState<string>('');

    useEffect(() => {
        const jwtToken = Cookies.get('jwt');
        setToken(jwtToken || '');
    }, []);

    return token;
}

export default getJwtToken;