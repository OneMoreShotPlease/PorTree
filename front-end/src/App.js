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
import Layout from './components/Layout';
import RequireAuth from './components/RequireAuth';
import Unauthorized from './pages/user/Unauthorized';

const Wrapper = styled(Responsive)`
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: space-between;
`;

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<Layout />}>
                    {/* public routes */}
                    <Route path="/" element={<MainPage />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/list" element={<PostListPage />} />
                    <Route path="unauthorized" element={<Unauthorized />} />

                    {/* we want to protect these routes */}
                    <Route element={<RequireAuth />}>
                        <Route path="/info" element={<UserInfoPage />} />
                        <Route path="/write" element={<WriteProjPage />} />
                        <Route path="/post/:empid" element={<PostPage />} />
                        <Route path="/edit/:empid" element={<EditPage />} />
                    </Route>
                </Route>
            </Routes>
        </div>
    );
}

export default App;
