import youre_lost from '../assets/youre_lost.jpg';
const NotFound = () => {
    return <>
    <div className='container-fluid'>
        <h1>404 Not Found</h1>
        <h3>Pick another link to get back on the trail.</h3>
        <div className='navbar-brand' to='/'>
            <img src={youre_lost} alt='Dog in backpack' width='19%'/>
        </div>
        </div>
    </>
}

export default NotFound;