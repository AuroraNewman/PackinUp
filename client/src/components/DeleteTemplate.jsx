import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';

const DeleteTemplate = ({ loggedInUser, setLoggedInUser}) => {
    const params = useParams();
    const navigate = useNavigate();
    // const loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"));
    const [template, setTemplate] = useState({});


    useEffect(() => {
        if (!loggedInUser) {
            navigate('/login');
        }
        fetch(`http://localhost:8080/api/packinup/template/${params.templateId}`, {
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(template => setTemplate(template))
                } else {
                    navigate("/notFound")
                }
            })
    }, [loggedInUser, navigate]);

    const handleDelete = () => {
        fetch(`http://localhost:8080/api/packinup/template/${params.templateId}`, {
            method: 'DELETE',
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    
                    navigate('/template');
                } else {
                    console.error('Error deleting template');
                }
            })
            .catch(error => console.error('Error:', error));
    }
    return (
        <>
        <p>Are you sure you want to delete this template?</p>
        <p>Name: {template.name}</p>
        <p>Description: {template.description}</p>
        {/* <p>TripType: {template.templateTripType.tripTypeName}</p> */}
        <button onClick={handleDelete} className="btn btn-danger me-2 mb-2">Delete</button> 
        <Link to="/list" className="btn btn-info me-2 mb-2">Cancel</Link>
        </>
    )
}

export default DeleteTemplate;