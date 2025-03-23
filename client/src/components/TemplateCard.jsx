import React from 'react';
import { useParams, useLocation } from 'react-router-dom';

const TemplateCard = () => {
    const location = useLocation();
    const { template, loggedInUser } = location.state || {};
    const params = useParams();

    const templateItems = Array.from({ length: 20 }, (_, i) => ({
        templateItemId: i + 1,
        templateItemName: `Item ${i + 1}`,
        templateItemQuantity: Math.floor(Math.random() * 10) + 1,
        templateItemCategory: `Category ${((i % 4) + 1)}`
    }));

    return (
        <ul className="tilesWrap ">
            <li key={template.templateId}>
                <h2>{template.templateId}</h2>
                <h3>{template.templateName}</h3>
                <p>{template.templateDescription}</p>
            </li>            
            {templateItems.map((item) => (
                <li key={item.templateItemId} className="templateItemCard">
                    <h2>{item.templateItemId}</h2>
                    <h3>{item.templateItemName}</h3>
                    <p>Quantity: {item.templateItemQuantity}</p>
                    <p>Category: {item.templateItemCategory}</p>
                </li>
            
            ))}
        </ul>
    )
}
export default TemplateCard;