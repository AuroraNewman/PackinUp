import { useState, useEffect } from "react";
import { Link, NavLink } from 'react-router-dom';
const ItemsList = ({ loggedInUser }) => {

    useEffect(() => {
        fetch("http://localhost:8080/api/packinup/template/item", {
            headers: {
                Authorization: loggedInUser.jwt
            }
        })
            .then(response => response.json())
            .then(fetchedIdeas => {
                setTemplates(fetchedIdeas);
                setHasFinishedFetching(true);
            })
            .catch(error => {
                console.error("Error fetching items: ", error);
                setHasFinishedFetching(true);
            });
    }, []);

    const [items, setItems] = useState([])
    const [hasFinishedFetching, setHasFinishedFetching] = useState(false)

    if (items.length === 0) {
        if (hasFinishedFetching) {
            return (
                <>
                    <div class="row">
                        <Link to={`/templateitem/create`} className="btn btn-primary me-2 mb-2">Add Template</Link>
                    </div>
                    <h1>No items found</h1>
                </>
            )
        } else {
            return (
                null
            )
        }
    }

    return (
        <>
        
        </>

    )
}

export default ItemsList;