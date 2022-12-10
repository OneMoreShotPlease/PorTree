import './App.css';
import { useEffect, useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import MainPage from './pages/MainPage';
import Login from './pages/user/Login';
import PostListPage from './pages/crud/PostListPage';
import WriteProjPage from './pages/crud/WriteProjPage';
import UserInfoPage from './pages/user/UserInfoPage';
import PostPage from './pages/crud/PostPage';
import EditPage from './pages/crud/EditPage';
import Register from './pages/user/Register';
import Nav from './components/NavBar';
import styled from 'styled-components';
import Responsive from './components/Responsive';
import AuthProvider from './context/AuthProvider';

const Wrapper = styled(Responsive)`
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: space-between;
`;

function App() {
    const [auth, setAuth] = useState(false);
    const [user, setUser] = useState();
    useEffect(() => {
        console.log('로그인 인증값', auth);
    }, [auth]);
    return (
        <div className="App">
            <Nav auth={auth} setAuth={setAuth} />
            <Wrapper>
                <Routes>
                    <Route path="/" element={<MainPage auth={auth} />} />
                    <Route
                        path="/login"
                        element={<Login setAuth={setAuth} />}
                    />
                    <Route path="/register" element={<Register />} />
                    <Route path="/list" element={<PostListPage />} />
                    <Route path="/info" element={<UserInfoPage />} />
                    <Route path="/write" element={<WriteProjPage />} />
                    <Route path="/post/:empid" element={<PostPage />} />
                    <Route path="/edit/:empid" element={<EditPage />} />
                </Routes>
            </Wrapper>
        </div>
    );
}

export default App;
