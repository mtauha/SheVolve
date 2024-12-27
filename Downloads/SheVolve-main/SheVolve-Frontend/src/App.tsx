import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignUp from './pages/SignUp';  // Import your SignUp component
import SignIn from './pages/SignIn';  // Import your SignIn component
import Dashboard from './pages/AdminDashboard';
import 'bootstrap/dist/css/bootstrap.min.css';
import EntrepreneurPage from "./pages/EntrepreneurPage";
import MentorPage from "./pages/MentorPage";
import NgoPage from "./pages/NgoPage";
import Dashbo from './pages/Dashbo'

// import HomePage from './HomePage'; // Import other pages if needed

function App() {
  return (
    <Router>
        <Routes>
          {/* Define your routes here */}
          {/* <Route path="/" element={<HomePage />} /> Home page */}
          <Route path="/signup" element={<SignUp />} /> {/* Sign Up page */}
          <Route path="/signin" element={<SignIn />} /> {/* Sign In page */}
          <Route path='/dashboard' element={<Dashboard />} />
          <Route path="/entrepreneur" element={<EntrepreneurPage />} />
          <Route path="/mentor" element={<MentorPage />} />
          <Route path="/ngo" element={<NgoPage />} />
          <Route path="/lol" element={<Dashbo />} />
        </Routes>
    </Router>
  );
}

export default App;
