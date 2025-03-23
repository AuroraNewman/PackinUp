import React from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';

const TemplateCard = () => {
    const location = useLocation();
    const { template, loggedInUser } = location.state || {};
    const params = useParams();
    const navigate = useNavigate();

    const templateItems = Array.from({ length: 20 }, (_, i) => ({
        templateItemId: i + 1,
        templateItemName: `Item ${i + 1}`,
        templateItemQuantity: Math.floor(Math.random() * 10) + 1,
        templateItemIsChecked: false,
        templateItemCategory: `Category ${((i % 4) + 1)}`
    }));

    const handleEditTemplateClick = () => {
        navigate(`/template/edit/${params.templateId}`),
            { state: { template, loggedInUser } };
    }
    const handleEditTemplateItemClick = (templateItem) => {
        console.log("loggedInUser at click:", loggedInUser);
        navigate(`/templateitem/edit/${templateItem.templateItemId}`, { state: { templateItem, loggedInUser } });
        
    };


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
                <button className="btn btn-primary btn-sm me-2 mb-2 col-12">Copy</button>
                <button className="btn btn-primary btn-sm me-2 mb-2 col-5" onClick={handleEditTemplateClick}>Edit</button>
                <button className="btn btn-danger btn-sm me-2 mb-2 col-5">Delete</button>
                {/* todo implement delete */}
            </li>
            {templateItems.map((item) => (
                // todo: add category color to this card with className={(arg) => { return (template.templateItemCategory)}}
                <li key={item.templateItemId} className="templateItemCard" >
                    <h2>{item.templateItemId}</h2>
                    <h3>{item.templateItemName}</h3>
                    <p>Packed? {item.templateItemIsChecked}</p>
                    <p>Quantity: {item.templateItemQuantity}</p>
                    <p>Category: {item.templateItemCategory}</p>
                    <div className="row">
                        <button className="btn btn-primary btn-sm me-2 mb-2 col-5" onClick={() =>handleEditTemplateItemClick(item)}>Edit</button>
                        <button className="btn btn-danger btn-sm me-2 mb-2 col-5">Delete</button>
                        {/* todo implement delete */}
                    </div>
                </li>

            ))}
        </ul>
    )
}
export default TemplateCard;