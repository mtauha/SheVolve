import Cookies from "js-cookie";

const getJwtToken = (): string => {
    const jwtToken = Cookies.get('jwt');
    return jwtToken || '';
};

export default getJwtToken;
