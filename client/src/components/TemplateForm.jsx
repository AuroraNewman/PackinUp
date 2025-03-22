import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"

const TemplateForm = ({ loggedInUser, setLoggedInUser, triptypes = [] }) => {

    const baseUrl = "http://localhost:8080/api/packinup/template";

    const navigate = useNavigate();

    const params = useParams()

    const [error, setError] = useState([]);

    const INITIAL_TEMPLATE = {
        templateName: '',
        templateDescription: '',
        templateTripType: {
            tripTypeId: "",
            tripTypeName: "",
            tripTypeDescription: ""
          }
    }

    const [template, setTemplate] = useState(INITIAL_TEMPLATE);

    const[triptype, setTripType] = useState('')

    const handleTripTypeChange = (event) => {
        const selectedTripType = triptypes.find(
          (t) => t.id.toString() === event.target.value
        );
        setTemplate({
          ...template,
          templateTripType: selectedTripType || INITIAL_TEMPLATE.templateTripType
        });
      };
      

    useEffect(() => {
        if (params.templateId) {
            const fetchUrl = `${baseUrl}/${params.templateId}`
            fetch(fetchUrl, {
                headers: {
                    Authorization: loggedInUser.jwt
                }
            })
                .then(response => {
                    if (response.status >= 200 && response.status < 300) {
                        response.json().then(template => setTemplate(template))
                    } else {
                        response.json().then((x) => {
                            debugger

                        })
                        // setErrors(["Could not find that solar panel"])
                        navigate("/notFound")
                    }
                })
        }
    }, [params.templateId, baseUrl, loggedInUser.jwt, navigate])

    const handleChange = (event) => {
        setTemplate({ ...template, [event.target.name]: event.target.value })
    };
    console.log(params)

    const handleSubmit = (event) => {
        event.preventDefault()
        const method = params.templateId ? "PUT" : "POST";
        const submitUrl = params.templateId ? `${baseUrl}/${params.templateId}`
      : baseUrl;

        fetch(submitUrl, {
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
    }

    // const setTripTypes = ({ triptypes }) => {
    //     let optionsString = '<option value="">Select a trip type</option>'
    //     for (const triptype of triptypes) {
    //         const singleOptionString = `<triptype value="${triptype.name}">${triptype.name}</option>`
    //         optionsString += singleOptionString
    //     }
    // }



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
                    
                    <div className="form-group">
                        <label htmlFor="templateTripType">Trip Type: </label>
                        <select
  id="templateTripType"
  name="templateTripType"
  value={template.templateTripType.tripTypeId || ""}
  onChange={handleTripTypeChange}
  className="form-control"
>
  <option value="">Select a trip type</option>
  {triptypes.map((triptype) => (
    <option key={triptype.id} value={triptype.id}>
      {triptype.name}
    </option>
  ))}
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