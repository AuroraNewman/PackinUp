import { useState, useEffect } from "react";
import TemplateTable from "./TemplateTable";

const TemplateList = ({ loggedInUser }) => {
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
        
    if(templates.length === 0) {
        if (hasFinishedFetching) {
            return (<h1>No templates found</h1>)
        } else {
            return (
                null
            )
        }
    }
  return (
    <>
        <TemplateTable templates = {templates} />
    </>
  );
  
}

export default TemplateList;