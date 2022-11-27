// // 로그인 페이지
// // 프로젝트 나열 + 검색 페이지

import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login({ setAuth }) {
    const navigate = useNavigate();
    const [inputValue, setInputValue] = useState({
        email: '',
        password: '',
    });

    const inputChangeHandler = (e) => {
        const { name, value } = e.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

    const doLogin = async () => {
        console.log('click login');
        // try {
        //     const { data } = await axios.post(
        //         'http://localhost:8080/users',
        //         // 'http://43.201.121.70:8080/portree/api/user/login',
        //         inputValue
        //     );
        //     console.log(data);
        //     // data.find();
        //     setAuth(true);
        //     navigate('/');
        // } catch (error) {
        //     console.log(error);
        // }
        axios
            .post('http://localhost:8080/users', inputValue)
            .then((res) => {
                console.log(res.data);
                localStorage.clear();
                localStorage.setItem('id', res.data.id);
                localStorage.setItem('token', res.data.token);
                setAuth(true);
                navigate('/');
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return (
        <div>
            <input name="email" onChange={inputChangeHandler} />
            <input
                name="password"
                type="password"
                onChange={inputChangeHandler}
            />
            <button onClick={doLogin}>로그인</button>
        </div>
    );
}

export default Login;
