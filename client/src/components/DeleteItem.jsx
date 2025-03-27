import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';

const DeleteItem = ({ loggedInUser }) => {
    const params = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState({});
    const url = `http://localhost:8080/api/packinup/templateitem/${params.templateItemId}`;

    useEffect(() => {
        if (!loggedInUser) {
            navigate('/login');
        }
        fetch(url, {
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(item => setItem(item))
                } else {
                    navigate("/notFound")
                }
            })
    }, [loggedInUser, navigate]);

    const handleDelete = () => {
        fetch(url, {
            method: 'DELETE',
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    // navigate(`/template/${item.templateId}`);
                    navigate(`/template/`);
                } else {
                    console.error('Error deleting item');
                }
            })
            .catch(error => console.error('Error:', error));
    }
    return (
        <>
            <p>Are you sure you want to delete this item?</p>
            <p>Name: {item.name}</p>
            <p>Description: {item.description}</p>
            <button onClick={handleDelete} className="btn btn-danger me-2 mb-2">Delete</button>
            <Link to={`/template/${item.templateId}`} className="btn btn-info me-2 mb-2">Cancel</Link>

            </>
        );
    };
    
    export default DeleteItem;