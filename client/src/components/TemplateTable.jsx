// format adapted from codepen: https://codepen.io/vikassingh1111/pen/xBPmbL
import { Link, NavLink, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import './TemplateTable.css';
import TemplateCard from "./TemplateCard";

const TemplateTable = ({ loggedInUser }) => {
    const navigate = useNavigate();

    useEffect(() => {
      fetch("http://localhost:8080/api/packinup/template", {
          headers: {
              Authorization: loggedInUser.jwt
          }
      })
          .then(response => response.json())
          .then(fetchedTemplates => {
              setTemplates(fetchedTemplates);
              setHasFinishedFetching(true);
          })
          .catch(error => {
              console.error("Error fetching templates: ", error);
              setHasFinishedFetching(true);
          });
  }, []);


  const [templates, setTemplates] = useState([])
  const [hasFinishedFetching, setHasFinishedFetching] = useState(false)

  if (templates.length === 0) {
      if (hasFinishedFetching) {
          return (
              <>
                  <div className="row">
                      <Link to={`/template/create`} className="btn btn-primary me-2 mb-2">Add Template</Link>
                  </div>
                  <h1>No templates found</h1>
              </>

          )
      } else {
          return (
              null
          )
      }
  }
  
    function handleClick(template, loggedInUser) {
        console.log("loggedinuser at click:", loggedInUser);
        navigate(`/template/${template.templateId}`, { state: { template, loggedInUser } });
    }
    function handleCreateClick() {
        navigate(`/template/create`);
      }
  
    return (
      <ul className="tilesWrap">
        <li key="createNewTemplate">
            <h2>0</h2>
            <h3>Create New Template</h3>
            <p>Plan your next adventure now! Don't forget to pack the essentials!</p>
            <button
              onClick={() => handleCreateClick()}
              >Create a Template</button>
        </li>
        {console.log("Fetched templates: ", templates)}
        {templates.map((template) => (
          <li key={template.templateId}>
            <h2>{template.templateId}</h2>
            <h3>{template.templateName}</h3>
            <p>{template.templateDescription}</p>
            <button
              onClick={() => handleClick(template, loggedInUser)}
            >
              View
            </button>
          </li>
        ))}
      </ul>
    );
  };


export default TemplateTable;