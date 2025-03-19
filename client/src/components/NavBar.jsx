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
                        <NavLink to='/' className={(arg) => {return (arg.isActive )? 'nav-link active' : 'nav-link'}} >My Items</NavLink>
                        <NavLink to='/' className={(arg) => {return (arg.isActive )? 'nav-link active' : 'nav-link'}} >My Templates</NavLink>
                        <NavLink to='/' className={(arg) => {return (arg.isActive )? 'nav-link active' : 'nav-link'}} >My Lists</NavLink>
                        <NavLink to='/' className={(arg) => {return (arg.isActive )? 'nav-link active' : 'nav-link'}} >Create List</NavLink>
                        <NavLink to='/signup' className={(arg) => {return (arg.isActive )? 'nav-link active' : 'nav-link'}} >Register</NavLink>
                        <NavLink to='/' className={(arg) => {return (arg.isActive )? 'nav-link active' : 'nav-link'}} >Log In</NavLink>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default NavBar