import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignUp from './pages/SignUp';  // Import your SignUp component
import SignIn from './pages/SignIn';  // Import your SignIn component
import Dashboard from './pages/Dashboard';
import Database from './pages/admin/Database';
import NGORequests from './pages/admin/NGORequests';
import Logout from './pages/Logout';
import ProductList from './pages/entrepreneur/ProductList';
import UserDetailsPage from './pages/UserDetailsPage';
import Mentor from './components/Mentor';
import EntResources from './pages/entrepreneur/EntResources';
import EntMentorship from './pages/entrepreneur/EntMentorship';
import EntNgo from './pages/entrepreneur/EntNgo';
import { AddProductForm } from './pages/entrepreneur/AddProductForm';

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
          <Route path='/admin/data' element={<Database />} />
          <Route path='/admin/requests' element={<NGORequests />} />
          <Route path='/logout' element={<Logout />} />
          <Route path='/entrepreneur/products' element={<ProductList />} />
          <Route path='/entrepreneur/resources' element={<EntResources />} />
          <Route path='/entrepreneur/mentorship' element={<EntMentorship />} />
          <Route path='/entrepreneur/add-product' element={<AddProductForm />} />
          <Route path='/entrepreneur/ngo' element={<EntNgo />} />
          <Route path='/account' element={<UserDetailsPage />} />
          <Route path='/ngo/mentors' element={<Mentor />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
