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

    return (
        <>
        
        </>

    )
}

export default ItemsList;