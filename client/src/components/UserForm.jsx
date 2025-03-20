import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const UserForm = ({ mode, setLoggedInUser }) => {

    let url = "http://localhost:8080/api/packinup/user";

    const navigate = useNavigate();

    const [error, setError] = useState('');

    const [user, setUser] = useState({
        username: '',
        password: '',
        confirmPassword: '',
        email: ''
    })

    const handleChange = (event) => {
        setUser({ ...user, [event.target.name]: event.target.value })
    }
    const handleSubmit = (event) => {
        event.preventDefault();
        if (mode === 'register') {
            if (user.password !== user.confirmPassword) {
                setError('Passwords do not match');
                return;
            }
            if (user.password.length < 8) {
                setError('Password must be at least 8 characters');
                return;
            }
            if (!/[A-Z]/.test(user.password)) {
                setError('Password must contain at least one uppercase letter');
                return;
            }
            if (!/[a-z]/.test(user.password)) {
                setError('Password must contain at least one lowercase letter');
                return;
            }
            if (!/[0-9]/.test(user.password)) {
                setError('Password must contain at least one number');
                return;
            }
            if (!/[!@#$%^&*]/.test(user.password)) {
                setError('Password must contain at least one special character');
                return;
            }
        }

        { mode === 'login' ? url = url + "/login" : null }
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                ...(mode === 'register' ? { username: user.username } : {}),
                email: user.email,
                password: user.password
            })
            // NB for future self: if this had been the same as made above, could use {user} instead of all this

        })
            .then(response => {
                if (response.status >= 200 && response.status < 300) {
                    response.json().then(fetchedUser => setLoggedInUser(fetchedUser));
                    // setLoggedInUser(user);
                    navigate('/');
                } else {
                    response.json().then(fetchedErrors => setError(fetchedErrors));
                }
            })
        // navigate('/');
        setError('');
    }

    return (
        <>
            <div className="row">
                <div className="flex d-flex">
                    <div className="col-3"></div>
                    <div className="col-6">
                        <h4>{mode === 'login' ? 'Log in' : 'Register'}</h4>
                        {error && <div className="alert alert-danger" role="alert">{error}</div>}
                        <label htmlFor="user-form">{mode === 'login' ? 'Log in' : 'Register'}</label>
                        <form onSubmit={handleSubmit}>
                            {mode === 'register' ?
                                <div className="form-group">
                                    <label htmlFor="username">Username</label>
                                    <input name="username" type="text" className="form-control" id="username" placeholder="Enter username" value={user.username} onChange={handleChange} />
                                </div>
                                : null}
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input name="email" type="email" className="form-control" id="email" placeholder="Enter email" value={user.email} onChange={handleChange} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input name="password" type="password" className="form-control" id="password" placeholder="Enter password" value={user.password} onChange={handleChange} />
                            </div>
                            {mode === 'register' ?
                                <div className="form-group">
                                    <label htmlFor="confirm-password">Confirm Password</label>
                                    <input name="confirmPassword" type="password" className="form-control" id="confirm-password" placeholder="Confirm password" value={user.confirmPassword} onChange={handleChange} />
                                </div>
                                : null}
                            <button type="submit" className="btn btn-primary">Submit</button>

                        </form>
                    </div>
                    <div className="col-3"></div>
                </div>
            </div>
        </>
    )
}
export default UserForm;