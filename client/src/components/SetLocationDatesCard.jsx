import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const SetLocationDatesCard = ({ loggedInUser, setLoggedInUser}) => {
    const navigate = useNavigate();
  const params = useParams();
  const [error, setError] = useState([]);

    const handleSubmit = (event) => {
        event.preventDefault();
        const method = "get";
        const submitUrl = "http://localhost:8080/api/packinup/weather";

        let weatherDescription = "";
        let itemsToPack = [];

        fetch(submitUrl, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                Authorization: loggedInUser.jwt,
            },
            body: JSON.stringify(templateLocation, templateStartDate, templateEndDate)
        }).then((response) => {
            if (response.status >= 200 && response.status < 300) {
                response.json().then((fetchedData) => {
                    // Map fetched data to expected structure
                    weatherDescription = fetchedData.forecast;
                    itemsToPack = fetchedData.itemsToPack;
                }
                );
            } else {
                navigate("/notFound");
            }
        })
    }
    
    return (
        <>
        <form>
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
            <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Check Weather</button>
            <button type="submit" className="btn btn-warning">Skip</button>
        </form>
        </>
    )
}

export default SetLocationDatesCard;