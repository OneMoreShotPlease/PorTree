// export default UserCreate;
import React, { useState, useRef, useLocation } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const SignUpForm = styled.div`
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
        border: 1px solid black;
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

const Register = () => {
    const navigate = useNavigate();
    // const location = useLocation();
    // const from = location.state?.from?.pathname || '/';
    const [inputValue, setInputValue] = useState({
        email: '',
        password: '',
    });
    const [errMsg, setErrMsg] = useState('');

    const errRef = useRef();

    const inputChangeHandler = (e) => {
        const { name, value } = e.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

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
    const handleSubmit = async (e) => {
        try {
            const { data } = await axios.post(
                'http://43.201.121.70:8080/portree/api/user/signup',
                inputValue
            );
            console.log(data);
            navigate('/login');
        } catch (error) {
            let status = error.response?.status;
            console.log(status);
            if (status === 401) {
                // console.log('unauthorized');
                setErrMsg('unauthorized');
            } else if (status === 403) {
                // console.log('Forbidden');
                setErrMsg('Forbidden');
            } else if (status === 404) {
                // console.log('Not Found');
                setErrMsg('Not Found');
            } else {
                // console.log('Login Failed');
                setErrMsg('Login Failed');
            }
            errRef.current.focus();
        }
    };

    return (
        <div>
            <SignUpForm>
                <p
                    ref={errRef}
                    className={errMsg ? 'errmsg' : 'offscreen'}
                    aria-live="assertive"
                >
                    {errMsg}
                </p>
                <form onSubmit={handleSubmit}>
                    <h1>회원가입</h1>
                    <h3>Email Id</h3>
                    <input
                        name="email"
                        type="email"
                        placeholder="이메일을 입력해주세요."
                        onChange={inputChangeHandler}
                        required
                        autoComplete="off"
                        onBlur={checkEmail}
                    />
                    <h3>Password</h3>
                    <input
                        name="password"
                        type="password"
                        placeholder="비밀번호를 입력해주세요."
                        onChange={inputChangeHandler}
                        autoComplete="off"
                        required
                        onBlur={checkPassword}
                    />
                    <h3>Pw confirm</h3>
                    <input
                        name="confirmPassword"
                        type="password"
                        placeholder="비밀번호 확인용"
                        onChange={inputChangeHandler}
                        onBlur={checkPassword}
                    />

                    <button type="submit">회원가입</button>
                </form>
            </SignUpForm>
        </div>
    );
};

export default Register;
