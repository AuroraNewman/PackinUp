import React, { useEffect, useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';

const TemplateItemForm = () => {
    const location = useLocation();
    const { item, loggedInUser } = location.state || {};
    const params = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState([]);
    const {templateId} = useParams();
    
    const INITIAL_ITEM = {
      templateItemItemName: "",
      templateItemTemplateId: templateId,
      templateItemQuantity: "",
      templateItemCategory: "",
      templateItemIsChecked: false,

  };
  
  
const baseUrl = `http://localhost:8080/api/packinup/templateitem/${templateId}`;

    const [templateItem, setTemplateItem] = useState(INITIAL_ITEM);
    
    // useEffect(() => {
    //     if (params.templateItemTemplateId) {
    //         const fetchUrl = `${baseUrl}/${params.templateItemTemplateId}`;
    //         fetch(fetchUrl, {
    //             headers: {
    //                 Authorization: loggedInUser.jwt,
    //             },
    //         }).then((response) => {
    //             if (response.status >= 200 && response.status < 300) {
    //                 response.json().then((fetchedTemplateItem) => {
    //                     // Map fetched data to expected structure
    //                     setTemplateItem({
    //                         templateItemItemName: fetchedTemplateItem.templateItemItemName,
    //                         templateItemQuantity: fetchedTemplateItem.templateItemQuantity,
    //                         templateItemCategory: fetchedTemplateItem.templateItemCategory,
    //                     });
    //                 });
    //             } else {
    //                 navigate("/notFound");
    //             }
    //         });
    //     }}, [params.templateItemId, baseUrl, loggedInUser.jwt, navigate]
    // );

    const handleChange = (event) => {
        setTemplateItem({ ...templateItem, [event.target.name]: event.target.value });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        // const method = params.templateItemId ? "PUT" : "POST";
        // const submitUrl = params.templateItemId
        //     ? `${baseUrl}/${params.templateItemId}`
        //     : baseUrl;
        
        fetch(baseUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: loggedInUser.jwt,
            },
            body: JSON.stringify({
              ...templateItem,
            templateId: templateId}),
        }).then((response) => {
            if (response.status >= 200 && response.status < 300) {
                navigate(`/template/${templateId}`,);
            } else {
                response.json().then((data) => {
                    setError(data.errors);
                });
            }
        });
    }

    const handleCancel = () => {
        navigate(`/template/${params.templateId}`);
    }

    return (
        <>
      <div className="row">
        {error?.length > 0 && (
          <ul id="errors">
            {error.map((err, index) => (
              <li key={index}>{err}</li>
            ))}
          </ul>
        )}

        <h2>
          Add Item
          {/* {params.templateItemId
            ? `Editing item: ${templateItem.templateItemItemName}`
            : "Add a new item"} */}
        </h2>

        <div className="col-3"></div>
        <form onSubmit={handleSubmit} className="col-6">
          <div className="form-group">
            <label htmlFor="templateItemItemName">Name:</label>
            <input
              name="templateItemItemName"
              className="form-control"
              id="templateItemItemName-input"
              type="text"
              value={templateItem.templateItemItemName}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="templateItemQuantity">Quantity:</label>
            <input
              name="templateItemQuantity"
              className="form-control"
              id="templateItemQuantity-input"
              type="number"
              value={templateItem.templateItemQuantity}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="templateItemCategory">Category:</label>
            <select
              name="templateItemCategory"
              className="form-control"
              id="templateItemCategory-input"
              type="text"
              value={templateItem.templateItemCategory}
              onChange={handleChange}
            >
            <option value="" disabled>Select a category</option>
            <option value="Weather">Weather</option>
            <option value="Clothing">Clothing</option>
            <option value="Toiletries">Toiletries</option>
            <option value="Electronics">Electronics</option>
            <option value="Miscellaneous">Miscellaneous</option>
            </select>
            </div>
          <button type="submit">
            {params.templateItemId ? "Save Changes" : "Add Item"}
          </button>
          <button type="submit" onClick={handleCancel}>Cancel</button>
        </form>
        <div className="col-3"></div>
      </div>
    </>
    );
};

export default TemplateItemForm;