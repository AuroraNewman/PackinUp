import React from 'react';
import { useState, useEffect } from "react";
import { useParams, useLocation, useNavigate, Link } from 'react-router-dom';

const TemplateCard = () => {
    const location = useLocation();
    const { template, loggedInUser } = location.state || {};
    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://localhost:8080/api/packinup/templateitem/${template.templateId}`, {
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => response.json())
            .then(fetchedItems => {
                setItems(fetchedItems);
                setHasFinishedFetching(true);
            })
            .catch(error => {
                console.error("Error fetching items: ", error);
                setHasFinishedFetching(true);
            }, [template, loggedInUser]);
    }, []);

    const [items, setItems] = useState([])
    const [hasFinishedFetching, setHasFinishedFetching] = useState(false)

    if (items.length === 0) {
        if (hasFinishedFetching) {
            return (
                <>
                    <div className="row">
                        <Link to={`/templateitem/create/${template.templateId}`} className="btn btn-primary me-2 mb-2" state={{ loggedInUser }}>Add Item</Link>
                    </div>
                    <h1>No items found</h1>
                </>
            )
        } else {
            return (
                null
            )
        }
    }

    const handleEditTemplateClick = () => {
        navigate(`/template/edit/${params.templateId}`),
            { state: { template, loggedInUser } };
    }
    const handleEditTemplateItemClick = (item) => {
        console.log("loggedInUser at click:", loggedInUser);
        console.log("item at click:", item);
        navigate(`/templateitem/edit/${item.templateItemId}`, { state: { item, loggedInUser } });

    };


    console.log("Fetched items: ", items);
    // let itemIndex = 0;
    
    return (
        <>
        {/* {itemIndex++} */}
        <ul className="tilesWrap ">
            <li key={template.templateId}>
                <h2>{template.templateId}</h2>
                <h3>{template.templateName}</h3>
                <p>{template.templateDescription}</p>
                <button className="btn btn-primary btn-sm me-2 mb-2 col-5">Copy</button>
                <button className="btn btn-primary btn-sm me-2 mb-2 col-5" onClick={handleEditTemplateClick}>Edit</button>
                <button className="btn btn-primary btn-sm me-2 mb-2 col-5">+Items</button>
                <button className="btn btn-danger btn-sm me-2 mb-2 col-5">Delete</button>
                {/* todo implement delete */}
            </li>
            {items.map((item) => (
                
                // todo: add category color to this card with className={(arg) => { return (template.templateItemCategory)}}
                <li key={item.templateItemId} className={item.outgoingItem?.color ?? "default-color"}>

                    {/* <h2>{itemIndex}</h2> */}
                    <h3>{item.outgoingItem.itemName}</h3>
                    <p>Packed? {item.checked}</p>
                    <p>Quantity: {item.quantity}</p>
                    <p>Category: {item.outgoingItem.categoryName}</p>
                    <div className="row">
                        <button className="btn btn-primary btn-sm me-2 mb-2 col-5" onClick={() => handleEditTemplateItemClick(item)}>Edit</button>
                        <button className="btn btn-danger btn-sm me-2 mb-2 col-5">Delete</button>
                        {/* todo implement delete */}
                    </div>
                </li>

            ))}
        </ul>
        </>
    )
}
export default TemplateCard;