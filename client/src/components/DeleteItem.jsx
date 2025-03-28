import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';

const DeleteItem = ({ loggedInUser }) => {
    const params = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState(null);
    const geturl = `http://localhost:8080/api/packinup/templateitem/item/${params.templateItemId}`;
    const deleteurl = `http://localhost:8080/api/packinup/templateitem/${params.templateItemId}`;
    
    useEffect(() => {
        if (!loggedInUser) {
            navigate('/login');
        }
        fetch(geturl, {
            method: 'GET',
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    console.log("hi matthew")
                    response.json().then(item => setItem(item))
                    console.log("params" + params.templateItemId);
                } else {
                    navigate("/notFound")
                }
            })
    }, []);

    if (!item) {
        return null;
    }

    const handleDelete = () => {
        fetch(deleteurl, {
            method: 'DELETE',
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => {
                if (response.ok) {
                    // navigate(`/template/${item.templateId}`);
                    navigate(-1);
                } else {
                    console.error('Error deleting item');
                }
            })
            .catch(error => console.error('Error:', error));
    }
    console.log("item: " + item);
    console.log("itemId" + item.template);
    return (
        <div className="container-box">
        
            <p>Are you sure you want to delete this item?</p>
            <p>Name: {item.outgoingItem.itemName}</p>
            <p>Quantity: {item.quantity}</p>
            <button onClick={handleDelete} className="btn btn-danger me-2 mb-2">Delete</button>
            {/* <Link to={`/template/`} className="btn btn-info me-2 mb-2">Cancel</Link> */}
            <button onClick={() => navigate(-1)} className="btn btn-info me-2 mb-2">Cancel</button>
            {/* <Link to={`/template/${item.template.templateId}`} className="btn btn-info me-2 mb-2">Cancel</Link> */}

            </div>
        );
    };
    
    export default DeleteItem;