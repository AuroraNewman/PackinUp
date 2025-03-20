import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import './App.css'
import NavBar from './components/NavBar';

import UserForm from './components/UserForm';
UserForm

const App = () => {

  const [loggedInUser, setLoggedInUser] = useState(null);

  // const navigate = useNavigate();
    
  return (    
      <Router>
        <div>
          <NavBar loggedInUser={loggedInUser}/>
          <main>
            {/* todo: only display welcome on landing page */}
            <h1>{loggedInUser !== null ? <h1>Welcome, {loggedInUser.username}</h1> : null}</h1>
            {console.log(loggedInUser)}
            <Routes>
              <Route path="/" />
              <Route path="/signup"  element={<UserForm mode="register" setLoggedInUser={setLoggedInUser}/>} />
              <Route path="/login"  element={<UserForm mode="login" setLoggedInUser={setLoggedInUser}/>} />
              <Route path="/logout"  element={<UserForm mode="logout" setLoggedInUser={setLoggedInUser}/>} />
              <Route path="/*" element={<div>invalid route try again heffalump</div>}/>
            </Routes>
          </main>
        </div>
      </Router>    
  )
}

export default App