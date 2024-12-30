import Cookies from "js-cookie";
import SignIn from "./SignIn";

const Logout = () => {

    Cookies.set('jwt', '');

    return (
        <>
            <SignIn />
        </>
    );

}

export default Logout;