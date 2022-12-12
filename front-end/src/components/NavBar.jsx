import styled from 'styled-components';
import Responsive from './Responsive';
import Button from './Button';
import { Link, useNavigate } from 'react-router-dom';
import Avvvatars from 'avvvatars-react';
import { useCookies } from 'react-cookie';
import useAuth from '../hooks/useAuth';
import AuthContext from '../context/AuthProvider';
import { useContext } from 'react';
import LoginCheck from '../pages/user/LoginCheck';
const HeaderBlock = styled.div`
    position: fixed;
    display: block;
    z-index: 9999;
    width: 100%;
    background: white;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.08);
`;

const Wrapper = styled(Responsive)`
    height: 4rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .logo {
        font-size: 1.125rem;
        font-weight: 800;
        letter-spacing: 2px;
    }
    .right {
        display: flex;
        align-items: center;
    }
`;

const Spacer = styled.div`
    height: 4rem;
`;

const Nav = () => {
    const [cookies, setCookie, removeCookie] = useCookies(['id']);
    const navigate = useNavigate;
    const { setAuth } = useContext(AuthContext);
    const { auth } = useAuth();
    // const goToLogin = () => {
    //     navigate('/login');
    // };
    const authCheck = () => {
        // 페이지에 들어올때 쿠키로 사용자 체크
        const token = cookies.id; // 쿠키에서 id 를 꺼내기
    };
    const doLogout = () => {
        setAuth({});
        navigate('/');
        console.log(auth?.user?.find);
    };
    return (
        <>
            <HeaderBlock>
                <Wrapper>
                    <Link to="/">
                        <div className="logo">PORTREE</div>
                    </Link>
                    <div className="right">
                        {auth?.user ? (
                            <>
                                <Button onClick={() => doLogout()}>
                                    로그아웃
                                </Button>
                                <LoginCheck />
                                <Link to="/info">
                                    <Avvvatars value={auth?.user?.email} />
                                </Link>
                            </>
                        ) : (
                            <>
                                <Link to="/login">
                                    <Button>로그인</Button>
                                </Link>
                                <Link to="/register">
                                    <Button>회원가입</Button>
                                </Link>
                            </>
                        )}
                    </div>
                </Wrapper>
            </HeaderBlock>
            <Spacer />
        </>
    );
};

export default Nav;
