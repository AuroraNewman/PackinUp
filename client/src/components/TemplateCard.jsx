import React from 'react';
import { useState, useEffect } from "react";
import { useParams, useLocation, useNavigate } from 'react-router-dom';

const TemplateCard = () => {
    const location = useLocation();
    const { template, loggedInUser } = location.state || {};
    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://localhost:8080/api/packinup/template/item/${template.templateId}`, {
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
                    <div class="row">
                        <Link to={`/templateitem/create`} className="btn btn-primary me-2 mb-2">Add Item</Link>
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
        navigate(`/templateitem/edit/${item.templateItemId}`, { state: { item, loggedInUser } });

    };


    console.log("Fetched items: ", items);
    const buttonRow = () => {
        return (
            <div className="row">
                <button className="btn btn-primary btn-sm me-2 mb-2 col-5" onClick={handleEditTemplateItemClick}>Edit</button>
                <button className="btn btn-danger btn-sm me-2 mb-2 col-5">Delete</button>
                {/* todo implement delete */}
            </div>
        )
    }

    return (
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
                <li key={item.templateItemId} className={item.item?.color ?? "default-color"}>

                    <h2>{item.item.itemId}</h2>
                    <h3>{item.item.itemName}</h3>
                    <p>Packed? {item.checked}</p>
                    <p>Quantity: {item.quantity}</p>
                    <p>Category: {item.item.categoryName}</p>
                    <div className="row">
                        <button className="btn btn-primary btn-sm me-2 mb-2 col-5" onClick={() => handleEditTemplateItemClick(item)}>Edit</button>
                        <button className="btn btn-danger btn-sm me-2 mb-2 col-5">Delete</button>
                        {/* todo implement delete */}
                    </div>
                </li>

            ))}
        </ul>
    )
}
export default TemplateCard;