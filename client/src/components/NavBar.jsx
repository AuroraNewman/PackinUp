import { Link, NavLink } from 'react-router-dom';
import logo from '../assets/logo.png';


const NavBar = ({ loggedInUser, setLoggedInUser }) => {
    return (
        <>
            <nav className="navbar fixed-top navbar-expand-sm navbar-dark bg-dark">
                <NavLink className='navbar-brand' to='/'>
                    <img src={logo} alt='Packin Up Logo' width='60' />
                </NavLink>
                <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div className="navbar-nav col-12 d-flex justify-content-between">
                        {loggedInUser === null ? null :
                            <>
                                <NavLink to="/template" className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >My Templates</NavLink>
                            </>
                        }
                        <NavLink to="/template/create" className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >Create List</NavLink>
                        
                        <div className='col-4'></div>
                        <NavLink to='/template' >{loggedInUser !== null ? <p>Hi {loggedInUser.username}</p> : null}</NavLink>
                        <div className='col-4'></div>
                        {loggedInUser === null ?
                            <>
                                <NavLink to='/signup' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >Register</NavLink>
                                <NavLink to='/login' className={(arg) => { return (arg.isActive) ? 'nav-link active' : 'nav-link' }} >Log In</NavLink>
                            </>
                            :
                            <>                                
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