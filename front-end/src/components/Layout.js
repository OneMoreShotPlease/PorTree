import { Outlet } from 'react-router-dom';
import Nav from './NavBar';

const Layout = () => {
    return (
        <div>
            <Nav />
            <main className="App">
                <Outlet />
            </main>
        </div>
    );
};

export default Layout;
