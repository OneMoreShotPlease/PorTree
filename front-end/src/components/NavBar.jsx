import styled from 'styled-components';
import Responsive from './Responsive';
import Button from './Button';
import { Link, useNavigate } from 'react-router-dom';

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

const Nav = ({ auth, setAuth }) => {
    const navigate = useNavigate;
    // const goToLogin = () => {
    //     navigate('/login');
    // };
    const doLogout = () => {
        // let token = localStorage.getItem('token');
        setAuth(false);
        localStorage.clear();
        navigate('/');
    };
    return (
        <>
            <HeaderBlock>
                <Wrapper>
                    <Link to="/">
                        <div className="logo">PORTREE</div>
                    </Link>
                    <div className="right">
                        {auth ? (
                            <Button onClick={() => doLogout()}>로그아웃</Button>
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
