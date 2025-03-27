import React, { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const DeleteTemplate = ({ loggedInUser, setLoggedInUser}) => {
    const params = useParams();
    const navigate = useNavigate();
    const loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"));


    useEffect(() => {
        if (!loggedInUser) {
            navigate('/login');
        }
    }, [loggedInUser, navigate]);

    const handleDelete = () => {
        fetch(`http://localhost:8080/api/packinup/template/${template.templateId}`, {
            method: 'DELETE',
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    onDelete(template.templateId);
                    navigate('/template');
                } else {
                    console.error('Error deleting template');
                }
            })
            .catch(error => console.error('Error:', error));
    }

    export default function DeleteTemplate;