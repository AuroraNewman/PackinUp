import { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, NavLink } from 'react-router-dom';
import Cropper from "react-easy-crop";
import helihike from './assets/helihike.jpeg';
import './App.css'
import NavBar from './components/NavBar';
import UserForm from './components/UserForm';
import NotFound from './components/NotFound';
import TemplateForm from './components/TemplateForm';
import TemplateCard from './components/TemplateCard';
import TemplateItemForm from './components/TemplateItemForm';
import TemplateTable from './components/TemplateTable';
import DeleteTemplate from './components/DeleteTemplate';

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


  const ImageCropper = ({ imageSrc }) => {
    const [crop, setCrop] = useState({ x: 0, y: 0 });
    const [zoom, setZoom] = useState(1);
  
    if (!imageSrc) return <p>Loading image...</p>;
  
    return (
      <div style={{ position: "relative", width: "100%", height: 725 }}>
        <Cropper
          image={imageSrc}
          crop={crop} 
          zoom={zoom}
          aspect={4 / 3}
          onCropChange={setCrop} 
          onZoomChange={setZoom} 
          onCropComplete={(croppedArea, croppedAreaPixels) => {
            console.log("Cropped area:", croppedAreaPixels);
          }}
        />
      </div>
    );
  };

  return (
    <Router>
      <div>
        <NavBar loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />
        <main>
          {/* todo: only display welcome on landing page */}
          {/* {loggedInUser !== null ? <h1>Welcome, {loggedInUser.username}</h1> : null} */}
          <Routes>
            <Route path="/" />

            {/* must be logged out */}

            <Route path="/signup" element={loggedInUser !== null ? <Navigate to="/" /> : <UserForm mode="register" setLoggedInUser={setLoggedInUser} />} />

            <Route path="/login" element={loggedInUser !== null ? <Navigate to="/" /> : <UserForm mode="login" setLoggedInUser={setLoggedInUser} />} />

            {/* must be logged in */}

            <Route path="/template" element={loggedInUser === null ? <Navigate to="/" /> : <TemplateTable loggedInUser={loggedInUser} />} />

            <Route path="/template/:templateId" element={loggedInUser === null ? <Navigate to="/" /> : <TemplateCard />} />

            <Route path="/template/create" element={loggedInUser === null ? <Navigate to="/" /> : <TemplateForm loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />} />

            <Route path="/template/edit/:templateId" element={loggedInUser === null ? <Navigate to="/" /> : <TemplateForm loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />} />

            <Route path="/template/delete/:templateId" element={loggedInUser === null ? <Navigate to="/" /> : <DeleteTemplate loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />} />

            <Route path="/templateitem/edit/:templateId" element={loggedInUser === null ? <Navigate to="/" /> : <TemplateItemForm loggedInUser={loggedInUser} />} />

            <Route path="/templateitem/create/:templateId" element={loggedInUser === null ? <Navigate to="/" /> : <TemplateItemForm loggedInUser={loggedInUser} />} />

            <Route path="/logout" element={loggedInUser === null ? <Navigate to="/" /> : <UserForm mode="logout" setLoggedInUser={setLoggedInUser} />} />

            {/* logged in state not necessary */}
            <Route path="/*" element={<NotFound />} />
          </Routes>
          {console.log("Image Source:", helihike)}
          <NavLink className='landing-page-photo' to='/template'>
            <ImageCropper imageSrc={helihike} />
          </NavLink>

        </main>
      </div>
    </Router>
  )
}

export default App