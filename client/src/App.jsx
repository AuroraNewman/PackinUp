import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import './App.css'
import NavBar from './components/NavBar';
import RegisterForm from './components/RegisterForm';

const App = () => {

  // const navigate = useNavigate();
    
  return (    
      <Router>
        <div>
          <NavBar />
          <main>
            <Routes>
              <Route path="/" />
              <Route path="/signup"  element={<RegisterForm />} />
              <Route path="/*" element={<div>invalid route try again heffalump</div>}/>
            </Routes>
          </main>
        </div>
      </Router>    
  )
}

export default App