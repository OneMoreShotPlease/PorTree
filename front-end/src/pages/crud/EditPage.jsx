// import { useEffect } from 'react';

// const EmpEdit = () => {
//     const { empid } = useParams();

//     useEffect(() => {
//         fetch('http://localhost:8080');
//     });
// };

import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import moment from 'moment/moment';

const EditPage = () => {
    const { empid } = useParams();

    const [title, setTitle] = useState('');
    const [body, setBody] = useState('');
    const [userId, setUserId] = useState('');
    const [nowTime, setNowTime] = useState(
        moment().format('YYYY-MM-DD HH:mm:ss')
    );

    useEffect(() => {
        fetch('http://localhost:8080/projects' + empid)
            .then((res) => {
                return res.json();
            })
            .then((resp) => {
                console.log(resp);
                setTitle(resp.title);
                setBody(resp.body);
                setUserId(resp.userId);
                setNowTime(resp.nowTime);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);
    const navigate = useNavigate();

    const handlesubmit = (e) => {
        e.preventDefault();
        const empdata = { title, body, userId, nowTime };

        fetch('http://localhost:8080/projects' + empid, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringfy(empdata),
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
            <form onSubmit={handlesubmit}>
                <div>
                    <h2>Employee Edit</h2>
                    <div>
                        <label>ID</label>
                        <input value={userId}></input>
                    </div>
                    <div>
                        <label>Title</label>
                        <input
                            defaultValue={title ? title : '빈칸'}
                            disabled="disabled"
                        ></input>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default EditPage;
