import { Link, NavLink } from 'react-router-dom';
import logo from '../assets/logo.png';


const NavBar = ({ loggedInUser, setLoggedInUser }) => {
    return (
        <>

            <nav className="navbar fixed-top navbar-expand-sm navbar-light">
                <NavLink className='navbar-brand' to='/'>
                    <img src={logo} alt='Packin Up Logo' width='60' />
                </NavLink>
                <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div className="navbar-nav">
                        {loggedInUser === null ? null :
                            <>
                                <NavLink to='/' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >My Items</NavLink>
                                <NavLink to='/' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >My Templates</NavLink>
                                <NavLink to='/' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >My Lists</NavLink>
                            </>
                        }
                        <NavLink to='/' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >Create List</NavLink>
                        {loggedInUser === null ?
                            <>
                                <NavLink to='/signup' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >Register</NavLink>
                                <NavLink to='/login' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >Log In</NavLink>
                            </>
                            :
                            <>
                                <ul>
                                    <li className="nav-item">
                                        <button className={(arg) => (arg.isActive) ? 'nav-link active' : 'nav-link'} onClick={() => {
                                            setLoggedInUser(null)
                                            localStorage.clear('loggedInUser')
                                        }}>Log Out</button>
                                    </li>
                                </ul>

                                <NavLink to='/logout' className={(arg) => (arg.isActive) ? 'nav-link active' : 'nav-link'}
                                    onClick={() => {
                                        setLoggedInUser(null)
                                        localStorage.clear('loggedInUser')
                                        navigate('/register');
                                    }} >Log Out</NavLink>
                            </>
                        }
                    </div>
                </div>
            </nav>
        </>
    )
}

export default NavBar