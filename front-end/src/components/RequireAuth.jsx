import { useLocation, Navigate, Outlet } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

const RequireAuth = () => {
    const { auth } = useAuth();
    const location = useLocation();
    console.log(auth?.user);
    console.log(auth);
    return auth?.isLogin ? (
        <Outlet />
    ) : (
        <Navigate to="login" state={{ from: location }} replace />
    );
    // 여기서 얘기하는 OUTLET은 AUTH를 필요로하는 child를 위한 것
};

export default RequireAuth;
