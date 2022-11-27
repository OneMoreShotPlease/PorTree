// 메인 페이지

import { Link } from 'react-router-dom';

const MainPage = ({ auth }) => {
    return (
        <div>
            {/* <Link to="/">
                <h1>main</h1>
            </Link>
            <Link to="/login">
                <h1>login</h1>
            </Link>
            <Link to="/register">
                <h1>register</h1>
            </Link> */}
            {auth ? (
                <>
                    <Link to="/list">
                        <h1>list</h1>
                    </Link>
                    <Link to="/info">
                        <h1>info</h1>
                    </Link>
                    <Link to="/write">
                        <h1>write</h1>
                    </Link>
                </>
            ) : (
                <h1>로그인 해주셔야 합니다.</h1>
            )}
        </div>
    );
};

export default MainPage;
