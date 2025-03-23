// format adapted from codepen: https://codepen.io/vikassingh1111/pen/xBPmbL
import { Link, NavLink, useNavigate } from "react-router-dom";
import './TemplateTable.css';

const TemplateTable = ({ templates }) => {
    const navigate = useNavigate();
  
    function handleClick(templateId) {
      navigate(`/template/${templateId}`);
    }
  
    return (
      <ul className="tilesWrap">
        {templates.map((template) => (
          <li key={template.templateId}>
            <h2>{template.templateId}</h2>
            <h3>{template.templateName}</h3>
            <p>{template.templateDescription}</p>
            <button
              className="readMore"
              onClick={() => handleClick(template.templateId)}
            >
              Read More
            </button>
          </li>
        ))}
      </ul>
    );
  };
    
    {/*
// standard styling
        
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th className="col-2">Template Name</th>
                        <th className="col-2">Template Description</th>
                        <th className="col-2">Category</th>
                        <th className="col-4">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {templates.map(template => {
                        return (
                            <tr key={template.templateId}>
                                <td>{template.templateName}</td>
                                <td>{template.templateDescription}</td>
                                <td>{template.templateTripType.tripTypeName}</td>
                                <td>
                                    <Link to={`/template/${template.templateId}`} className="btn btn-primary me-2 mb-2">View</Link>
                                    <Link to={`/template/edit/${template.templateId}`} className="btn btn-warning">Edit</Link>
                                    <Link to={`/template/delete/${template.templateId}`} className="btn btn-danger">Delete</Link>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
            
        </>
    )
}*/}


export default TemplateTable;