// // 프로젝트 작성 페이지

// import { useState } from 'react';
// import { Link, useNavigate } from 'react-router-dom';

// const UserCreate = () => {
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');

//     const navigate = useNavigate();

//     const handlesubmit = (e) => {
//         e.preventDefault();
//         const empdata = { email, password };

//         fetch('http://localhost:8080/users', {
//             method: 'POST',
//             headers: { 'content-type': 'application/json' },
//             body: JSON.stringify(empdata),
//         })
//             .then((res) => {
//                 alert('Saved succesfully.');
//                 navigate('/');
//             })
//             .catch((err) => {
//                 console.log(err.message);
//             });
//     };
//     return (
//         <div>
//             <h2>회원가입</h2>
//             <form onSubmit={handlesubmit}>
//                 <label>EMAIL</label>
//                 <input
//                     required
//                     type="email"
//                     value={email}
//                     onChange={(e) => setEmail(e.target.value)}
//                 ></input>
//                 <label>PW</label>
//                 <input
//                     required
//                     type="password"
//                     value={password}
//                     onChange={(e) => setPassword(e.target.value)}
//                 ></input>
//                 <button type="submit">Save</button>
//                 <Link to="/list">Back</Link>
//             </form>
//         </div>
//     );
// };

// export default UserCreate;
import React, { useState } from 'react';
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

function Register() {
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
    const doSignUp = async () => {
        try {
            const { data } = await axios.post(
                'http://localhost:8080/users',
                inputValue
            );
            console.log(data);
            navigate('/login');
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div>
            <SignUpForm>
                <form>
                    <h1>회원가입</h1>
                    <h3>Email Id</h3>
                    <input
                        name="email"
                        type="email"
                        placeholder="이메일을 입력해주세요."
                        onChange={inputChangeHandler}
                        onBlur={checkEmail}
                    />
                    <h3>Password</h3>
                    <input
                        name="password"
                        type="password"
                        placeholder="비밀번호를 입력해주세요."
                        onChange={inputChangeHandler}
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
                </form>
                <button onClick={doSignUp}>회원가입</button>
            </SignUpForm>
        </div>
    );
}

export default Register;
