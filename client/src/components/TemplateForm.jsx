import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const TemplateForm = ({ loggedInUser, setLoggedInUser }) => {
  const baseUrl = "http://localhost:8080/api/packinup/template";
  const navigate = useNavigate();
  const params = useParams();

  const [error, setError] = useState([]);

  const INITIAL_TEMPLATE = {
    templateName: "",
    templateDescription: "",
    templateTripTypeId: "",
  };

  const [template, setTemplate] = useState(INITIAL_TEMPLATE);

  useEffect(() => {
    if (params.templateId) {
      const fetchUrl = `${baseUrl}/${params.templateId}`;
      fetch(fetchUrl, {
        headers: {
          Authorization: loggedInUser.jwt,
        },
      }).then((response) => {
        if (response.status >= 200 && response.status < 300) {
          response.json().then((fetchedTemplate) => {
            // Map fetched data to expected structure
            setTemplate({
              templateName: fetchedTemplate.templateName,
              templateDescription: fetchedTemplate.templateDescription,
              templateTripTypeId: fetchedTemplate.templateTripType?.tripTypeId || "",
            });
          });
        } else {
          navigate("/notFound");
        }
      });
    }
  }, [params.templateId, baseUrl, loggedInUser.jwt, navigate]);

  const handleChange = (event) => {
    setTemplate({ ...template, [event.target.name]: event.target.value });
  };

  const handleTripTypeChange = (event) => {
    setTemplate({
      ...template,
      templateTripTypeId: parseInt(event.target.value) || "",
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const method = params.templateId ? "PUT" : "POST";
    const submitUrl = params.templateId
      ? `${baseUrl}/${params.templateId}`
      : baseUrl;

    fetch(submitUrl, {
      method,
      headers: {
        "Content-Type": "application/json",
        Authorization: loggedInUser.jwt,
      },
      body: JSON.stringify(template),
    }).then((response) => {
      if (response.status >= 200 && response.status < 300) {
        navigate("/");
      } else if (response.status === 401) {
        setLoggedInUser(null);
        localStorage.clear("loggedInUser");
        alert("You have been logged out");
      } else {
        response.json().then((fetchedErrors) => setError(fetchedErrors));
      }
    });
  };

  const handleCancel = () => {
    navigate(`/template/`);
    }

  return (
    <>
      <div className="row">
        {error.length > 0 && (
          <ul id="errors">
            {error.map((err, index) => (
              <li key={index}>{err}</li>
            ))}
          </ul>
        )}

        <h2>
          {params.templateId
            ? `Editing template: ${template.templateName}`
            : "Add a new template"}
        </h2>

        <div className="col-3"></div>
        <form onSubmit={handleSubmit} className="col-6">
          <div className="form-group">
            <label htmlFor="templateName">Name:</label>
            <input
              name="templateName"
              className="form-control"
              id="templateName-input"
              type="text"
              value={template.templateName}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label htmlFor="templateDescription">Description:</label>
            <input
              name="templateDescription"
              className="form-control"
              id="templateDescription-input"
              type="text"
              value={template.templateDescription}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label htmlFor="templateTripTypeId">Trip Type:</label>
            <select
              name="templateTripTypeId"
              id="templateTripTypeId-input"
              value={template.templateTripTypeId}
              onChange={handleTripTypeChange}
              className="form-control"
            >
              <option value="" disabled>
                Select a trip type
              </option>
              <option value="1">General</option>
              <option value="2">Vacation</option>
              <option value="3">Business Trip</option>
            </select>
          </div>
          
          <div className="form-group">
            <label htmlFor="templateLocation">Location:</label>
            <input
              name="templateLocation"
              className="form-control"
              id="templateLocation-input"
              type="text"
              value={template.templateLocation}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="templateStartDate">Start Date:</label>
            <input
              name="templateStartDate"
              className="form-control"
              id="templateStartDate-input"
              type="date"
              value={template.templateStartDate}
              onChange={handleChange}
            />
            </div>
            <div className="form-group">
            <label htmlFor="templateEndDate">End Date:</label>
            <input
              name="templateEndDate"
              className="form-control"
              id="templateEndDate-input"
              type="date"
              value={template.templateEndDate}
              onChange={handleChange}            />
            </div>

          <button type="submit">
            {params.templateId ? "Save Changes" : "Add Template"}
          </button>
          <button type="submit" onClick={handleCancel}>Cancel</button>
        </form>
        <div className="col-3"></div>
      </div>
    </>
  );
};

export default TemplateForm;


// const INITIAL_TEMPLATE = {
    //     templateName: '',
    //     templateDescription: '',
    //     templateTripType: {
    //         tripTypeId: "",
    //         tripTypeName: "",
    //         tripTypeDescription: ""
    //       }
    // }
    {/* {triptypes.map((triptype) => (
                                <option key={triptype.tripTypeId} value={triptype.tripTypeId}>
                                    {triptype.tripTypeName}
                                </option>
                            ))} */}




    // const setTripTypes = ({ triptypes }) => {
    //     let optionsString = '<option value="">Select a trip type</option>'
    //     for (const triptype of triptypes) {
    //         const singleOptionString = `<triptype value="${triptype.name}">${triptype.name}</option>`
    //         optionsString += singleOptionString
    //     }
    // }
    // const handleTripTypeChange = (event) => {
    //     const selectedTripType = triptypes.find(
    //       (t) => t.id.toString() === event.target.value
    //     );
    //     setTemplate({
    //       ...template,
    //       templateTripType: selectedTripType || INITIAL_TEMPLATE.templateTripType
    //     });
    //   };