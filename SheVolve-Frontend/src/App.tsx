import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignUp from './pages/SignUp';  // Import your SignUp component
import SignIn from './pages/SignIn';  // Import your SignIn component
import Dashboard from './pages/Dashboard';
// import HomePage from './HomePage'; // Import other pages if needed

function App() {
  return (
    <Router>
      <div>
        <Routes>
          {/* Define your routes here */}
          {/* <Route path="/" element={<HomePage />} /> Home page */}
          <Route path="/signup" element={<SignUp />} /> {/* Sign Up page */}
          <Route path="/signin" element={<SignIn />} /> {/* Sign In page */}
          <Route path='/dashboard' element={<Dashboard />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
