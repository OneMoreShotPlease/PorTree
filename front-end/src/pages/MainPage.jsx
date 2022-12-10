// 메인 페이지
import PostListPage from './crud/PostListPage';
// import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Responsive from '../components/Responsive';

const Wrapper = styled(Responsive)`
    height: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: space-between;
    width: 70%;
`;

const Banner = styled.div`
    height: 20rem;
    width: 100%;
    border: 2px solid black;
    border-radius: 1rem;
    margin: 2rem;
    align-items: center;
`;

const MainPage = ({ auth }) => {
    return (
        <Wrapper>
            {/* <Link to="/">
                <h1>main</h1>
            </Link>
            <Link to="/login">
                <h1>login</h1>
            </Link>
            <Link to="/register">
                <h1>register</h1>
            </Link> */}
            <Banner>banner</Banner>
            <div className="projList">
                {/* {auth ? (
                    <>
                        <PostListPage />
                    </>
                ) : (
                    <h1>로그인 해주셔야 합니다.</h1>
                )} */}
                <PostListPage />
            </div>
        </Wrapper>
    );
};

export default MainPage;
