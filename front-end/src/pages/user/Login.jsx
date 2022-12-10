// // 로그인 페이지
// // 프로젝트 나열 + 검색 페이지

import React, { useState, useEffec, useContext } from 'react';
import AuthContext from '../../context/AuthProvider';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { useCookies } from 'react-cookie';

const LogInForm = styled.div`
    border: 1px solid black;
    margin: 2rem;
    min-width: 80%;
    width: 50%;
    height: 80%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: left;
    h1 {
        margin: 0.5rem;
        border-bottom: 1px solid black;
    }
    h3 {
        margin: 0.5rem;
    }
    input {
        border-radius: 5px;
        width: 90%;
        height: 2rem;
        margin-bottom: 0.5rem;
        margin-left: 0.5rem;
    }

    button {
        border-radius: 5px;
        background: transparent;
        cursor: pointer;
        transition: all 0.3s ease;
        position: relative;
        display: inline-block;
        width: 90%;
        margin: 0.5rem;
        height: 2rem;
        transition: all 0.3s ease;
        overflow: hidden;
        :after {
            position: absolute;
            content: ' ';
            top: 0;
            left: 0;
            z-index: -1;
            width: 100%;
            height: 100%;
            transition: all 0.3s ease;
            -webkit-transform: scale(0.1);
            transform: scale(0.1);
        }
        :hover {
            color: #fff;
        }
        :hover:after {
            background: #000;
            -webkit-transform: scale(1);
            transform: scale(1);
`;

const Login = () => {
    const { setAuth } = useContext(AuthContext);
    const [cookies, setCookie] = useCookies(['id']);
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState('');

    //비밀번호 유효성 검사
    const checkPassword = (e) => {
        //  8 ~ 10자 영문, 숫자 조합
        var regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,10}$/;
        // 형식에 맞는 경우 true 리턴
        console.log('비밀번호 유효성 검사 :: ', regExp.test(e.target.value));
    };

    // 이메일 유효성 검사
    const checkEmail = (e) => {
        var regExp =
            /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        // 형식에 맞는 경우 true 리턴
        console.log('이메일 유효성 검사 :: ', regExp.test(e.target.value));
    };

    const onSubmitAccount = async (e) => {
        e.preventDefault();

        console.log(email, password);
        // console.log('click login');
        // let body = {
        //     email,
        //     password,
        // };
        // console.log(body);
        // axios
        //     .post('http://43.201.121.70:8080/portree/api/user/login', body)
        //     .then((res) => {
        //         let status = res.status;
        //         if (status === 200) {
        //             console.log('로그인');
        //             setAuth(true);
        //             console.log(res);
        //             console.log(res.headers);
        //             // cookie.set(res.headers['set-cookie']);
        //             // console.log(res.h);
        //             console.log(res.headers['set-cookie']);
        //             setCookie('id', res.headers['token']);
        //             navigate('/');
        //         } else if (status === 401) {
        //             console.log('unauthorized');
        //         } else if (status === 403) {
        //             console.log('Forbidden');
        //         } else if (status === 404) {
        //             console.log('Not Found');
        //         } else {
        //             console.log('???');
        //         }
        //     });
        try {
            const resp = await axios.post(
                'http://43.201.121.70:8080/portree/api/user/login',
                JSON.stringify({ email, password }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    // withCredentials: true,
                }
            );
            console.log(JSON.stringify(resp?.data));
            const accessToken = resp?.data?.accessToken;
            const roles = resp?.data;
            setAuth({ email, password, roles, accessToken });
        } catch (err) {
            let status = err.response?.Reactstatus;
            if (status === 200) {
                console.log('로그인');
            } else if (status === 401) {
                console.log('unauthorized');
            } else if (status === 403) {
                console.log('Forbidden');
            } else if (status === 404) {
                console.log('Not Found');
            } else {
                console.log('Login Failed');
            }
        }
    };

    return (
        <div>
            <LogInForm>
                <form onSubmit={onSubmitAccount}>
                    <h1>로그인</h1>
                    <h3>Email Id</h3>
                    <input
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        onBlur={checkEmail}
                    />
                    <h3>Password</h3>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        onBlur={checkPassword}
                    />
                    <button type="submit" disabled={loading}>
                        로그인
                    </button>
                    {/* <button type="submit" disabled={loading}>로그인</button> */}
                </form>
            </LogInForm>
        </div>
    );
};

export default Login;
