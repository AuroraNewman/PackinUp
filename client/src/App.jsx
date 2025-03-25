import { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css'
import NavBar from './components/NavBar';
import UserForm from './components/UserForm';
import NotFound from './components/NotFound';
import TemplateForm from './components/TemplateForm';
import TemplateCard from './components/TemplateCard';
import TemplateItemForm from './components/TemplateItemForm';
import TemplateTable from './components/TemplateTable';

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
            {loggedInUser !== null ? <h1>Welcome, {loggedInUser.username}</h1> : null}
            <Routes>              
              <Route path="/" />
              
              {/* must be logged out */}
              
              <Route path="/signup"  element={ loggedInUser !== null ? <Navigate to="/" /> : <UserForm mode="register" setLoggedInUser={setLoggedInUser}/>} />
              
              <Route path="/login"  element={ loggedInUser !== null ? <Navigate to="/" /> : <UserForm mode="login" setLoggedInUser={setLoggedInUser}/>} />

              {/* must be logged in */}
              
              <Route path="/template"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateTable loggedInUser={loggedInUser}/>} />

              <Route path="/template/:templateId"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateCard />} />

              <Route path="/template/create"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateForm loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser}/>} />
              
              <Route path="/template/edit/:templateId"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateForm loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser}/>} />
              
              <Route path="/template/delete/:templateId"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateTable loggedInUser={loggedInUser}/>} />

              <Route path="/templateitem/edit/:templateId"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateItemForm loggedInUser={loggedInUser}/>} />

              <Route path="/templateitem/create/:templateId"  element={ loggedInUser===null ? <Navigate to="/" /> : <TemplateItemForm loggedInUser={loggedInUser}/>} />
              
              <Route path="/logout"  element={ loggedInUser===null ? <Navigate to="/" /> : <UserForm mode="logout" setLoggedInUser={setLoggedInUser}/>} />
              
              {/* logged in state not necessary */}
              <Route path="/*" element={<NotFound />} />
            </Routes>
          </main>
        </div>
      </Router>    
  )
}

export default App