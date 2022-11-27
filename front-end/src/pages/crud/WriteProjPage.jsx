// 프로젝트 작성 페이지

import { useEffect } from 'react';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const WriteProjPage = () => {
    const [title, setTitle] = useState('');
    const [body, setBody] = useState('');
    const [userId, setUserId] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        setUserId(localStorage.getItem('id'));
        console.log(userId);
    }, []);

    const handlesubmit = (e) => {
        e.preventDefault();
        const empdata = { title, body, userId };
        fetch('http://localhost:8080/projects', {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(empdata),
        })
            .then((res) => {
                alert('Saved succesfully.');
                navigate('/');
            })
            .catch((err) => {
                console.log(err.message);
            });
    };
    return (
        <div>
            <h1>WriteProjPage</h1>
            <h2>글 쓰기</h2>
            <form onSubmit={handlesubmit}>
                <label>Title</label>
                <input
                    required
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                ></input>
                <label>Body</label>
                <input
                    required
                    value={body}
                    onChange={(e) => setBody(e.target.value)}
                ></input>
                <button type="submit">Save</button>
                <Link to="/list">Back</Link>
            </form>
        </div>
    );
};

export default WriteProjPage;
