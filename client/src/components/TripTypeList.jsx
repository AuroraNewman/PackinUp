import { useState, useEffect } from "react";
const TripTypeList = ({ loggedInUser, setLoggedInUser }) => {
    useEffect(() => {
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
    }, []);
    

    const [triptypes, setTripTypes] = useState([])
    const [hasFinishedFetching, setHasFinishedFetching] = useState(false)
        
    if(triptypes.length === 0) {
        if (hasFinishedFetching) {
            return (<h1>No triptypes found</h1>)
        } else {
            return (
                null
            )
        }
    }
    return (
        <TemplateForm 
          triptypes={triptypes} 
          loggedInUser={loggedInUser} 
          setLoggedInUser={setLoggedInUser} 
        />
    )
}
    export default TripTypeList;