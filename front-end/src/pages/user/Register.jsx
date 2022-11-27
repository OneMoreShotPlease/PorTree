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
            <form>
                <input
                    name="email"
                    type="email"
                    onChange={inputChangeHandler}
                />
                <input
                    name="password"
                    type="password"
                    onChange={inputChangeHandler}
                />
                <button onClick={doSignUp}>회원가입</button>
            </form>
        </div>
    );
}

export default Register;
