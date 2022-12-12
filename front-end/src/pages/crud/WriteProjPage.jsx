// 프로젝트 작성 페이지

import { useEffect } from 'react';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import moment from 'moment';
import 'moment/locale/ko';
import EditorBox from '../../components/EditorBox';

const WriteProjPage = () => {
    const [title, setTitle] = useState('');
    const [body, setBody] = useState('');
    const [userId, setUserId] = useState('');
    const [nowTime, setNowTime] = useState(
        moment().format('YYYY-MM-DD HH:mm:ss')
    );
    const navigate = useNavigate();

    useEffect(() => {
        setUserId(localStorage.getItem('id'));
        console.log(userId);
    }, []);

    const handlesubmit = (e) => {
        e.preventDefault();
        const temp = moment().format('YYYY-MM-DD HH:mm:ss');
        setNowTime(temp);
        console.log(temp);
        const empdata = { title, body, userId, nowTime };
        fetch('http://localhost:8080/projects', {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(empdata),
        })
            .then((res) => {
                alert('Saved succesfully.');
                console.log(nowTime);
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
                <EditorBox />
                <button type="submit">Save</button>
            </form>
            <Link to="/list">Back</Link>
        </div>
    );
};

export default WriteProjPage;
