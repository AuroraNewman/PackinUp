import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"

const TemplateForm = ({ loggedInUser, setLoggedInUser }) => {

    let url = "http://localhost:8080/api/packinup/user";

    const navigate = useNavigate();

    const params = useParams()

    const [error, setError] = useState([]);

    const [template, setTemplate] = useState({ INITIAL_TEMPLATE })

    const INITIAL_TEMPLATE = {
        templateName: '',
        templateDescription: '',
        templateCategoryId: ''
    }

    const handleChange = (event) => {
        setTemplate({ ...template, [event.target.name]: event.target.value })
    };

    useEffect(() => {
        if (params.templateId) {
            fetch(`http://localhost:8080/api/packinup/template/${params.templateId}`)
                .then(response => {
                    if (response.status >= 200 && response.status < 300) {
                        return response.json()
                    } else {
                        throw new Error("Template not found");
                    }
                })
                .then(fetchedTemplate => setTemplate(fetchedTemplate))
                .catch(() => navigate("/notFound"));
        }
    }, [params.templateId, navigate]);

    const handleSubmit = (event) => {
        event.preventDefault()
        let method = "POST"
        if (params.templateId !== undefined) {
            method = "PUT"
            url += `/${params.templateId}`
        }

        fetch(url, {
            method,
            headers: {
                "Content-Type": "application/json",
                Authorization: loggedInUser.jwt
            },
            body: JSON.stringify(template)
        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    navigate("/") //todo implement this
                } else if (response.status === 401) {
                    setLoggedInUser(null)
                    localStorage.clear("loggedInUser")
                    alert("You have been logged out")
                } else {
                    response.json().then(fetchedErrors => setError(fetchedErrors))
                }
            })
            .catch(err => console.error("Submit failed", err));
    }



    return (
        <>
            <div className="row">
                {error.length > 0 && <ul id="errors">
                    {error.map(error => <li key={error}>{error}</li>)}
                </ul>}

                <h2>{params.templateId ? `Editing template ${template.templateName}` : "Add a new template"}</h2>

                <div className="col-3"></div>
                <form onSubmit={handleSubmit} className="col-6">
                    <div className="form-group">
                        <label htmlFor="templateName">Name: </label>
                        <input name="templateName" className="form-control" id="templateName-input" type="text" value={template.templateName} onChange={handleChange} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="templateDescription">Description: </label>
                        <input name="templateDescription" className="form-control" id="templateDescription-input" type="text" value={template.templateDescription} onChange={handleChange} />
                    </div>
                    {/* //todo implement getting the trip types */}
                    <div className="form-group">
                        <label htmlFor="tripType">Trip Type: </label>
                        <select name="templateTripType" className="form-control" id="tripType-input" value={template.material} onChange={handleChange}>
                            <option value="">Pick a material...</option>
                            <option value="POLY_SI">Multicrystalline Silicon</option>
                            <option value="MONO_SI">Monocrystalline Silicon</option>
                            <option value="A_SI">Amorphous Silicon</option>
                            <option value="CD_TE">Cadmium Telluride</option>
                            <option value="CIGS">Copper Indium Gallium Selenide</option>
                        </select>
                    </div>

                    <button type="submit">{params.templateId ? "Edit!" : "Add!"}</button>
                </form>
                <div className="col-3"></div>
            </div>
        </>
    )
}
export default TemplateForm;