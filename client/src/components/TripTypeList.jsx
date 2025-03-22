import { useState, useEffect } from "react";
import TemplateForm from "./TemplateForm"; // assuming this import is present

const TripTypeList = ({ loggedInUser, setLoggedInUser }) => {
    console.log("TripTypeList component");
    const [triptypes, setTripTypes] = useState([]);
    const [hasFinishedFetching, setHasFinishedFetching] = useState(false);

    useEffect(() => {
        // if (!loggedInUser || !loggedInUser.jwt) return;

        console.log("Fetching triptypes");
        fetch("http://localhost:8080/api/packinup/triptype", {
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
        .then(response => response.json())
        .then(fetchedTripTypes => {
            setTripTypes(fetchedTripTypes);
            setHasFinishedFetching(true);
        })
        .catch(error => {
            console.error("Error fetching triptypes: ", error);
            setHasFinishedFetching(true);
        });
    }, [loggedInUser]);

    if (triptypes.length === 0) {
        if (hasFinishedFetching) {
            return <h1>No triptypes found</h1>;
        } else {
            return null; // still loading
        }
    }

    return (
        <TemplateForm
            triptypes={triptypes}
            loggedInUser={loggedInUser}
            setLoggedInUser={setLoggedInUser}
        />
    );
};

export default TripTypeList;
