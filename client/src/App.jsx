import { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css'
import NavBar from './components/NavBar';
import UserForm from './components/UserForm';

const App = () => {

  const [loggedInUser, setLoggedInUser] = useState(null);
  const [finishedCheckingLocalStorage, setFinishedCheckingLocalStorage] = useState(false);

  useEffect(() => {
    
    if (localStorage.getItem("loggedInUser")) {
      setLoggedInUser(JSON.parse(localStorage.getItem("loggedInUser")));
    }
    setFinishedCheckingLocalStorage(true);
  }, [])

  if (!finishedCheckingLocalStorage) {
    return null;
  }
    
  return (    
      <Router>
        <div>
          <NavBar loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />
          <main>
            {/* todo: only display welcome on landing page */}
            <h1>{loggedInUser !== null ? <h1>Welcome, {loggedInUser.username}</h1> : null}</h1>
            <Routes>
              <Route path="/" />
              {/* must be logged out */}
              <Route path="/signup"  element={ loggedInUser !== null ? <Navigate to="/" /> : <UserForm mode="register" setLoggedInUser={setLoggedInUser}/>} />
              <Route path="/login"  element={ loggedInUser !== null ? <Navigate to="/" /> : <UserForm mode="login" setLoggedInUser={setLoggedInUser}/>} />
              {/* must be logged in */}
              <Route path="/logout"  element={ loggedInUser===null ? <Navigate to="/" /> : <UserForm mode="logout" setLoggedInUser={setLoggedInUser}/>} />
              {/* logged in state not necessary */}
              <Route path="/*" element={<div>invalid route try again heffalump</div>}/>
            </Routes>
          </main>
        </div>
      </Router>    
  )
}

export default App