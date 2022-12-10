// 프로젝트 상세정보 페이지

import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import moment from 'moment';
import 'moment/locale/ko';

const PostPage = () => {
    const { empid } = useParams();
    const navigate = useNavigate();

    const [empdata, empdatachange] = useState({});
    const [nowTime, setNowTime] = useState(
        moment().format('YYYY-MM-DD HH:mm:ss')
    );

    const LoadEdit = (id) => {
        navigate('/edit/' + id);
    };

    const RemoveFunction = (id) => {
        if (window.confirm('Do you want to remove')) {
            fetch('http://localhost:8080/projects/' + id, {
                method: 'DELETE',
            })
                .then((res) => {
                    alert('Removed succesfully.');
                    navigate('/list');
                })
                .catch((err) => {
                    console.log(err.message);
                });
        }
    };

    useEffect(() => {
        fetch('http://localhost:8080/projects/' + empid)
            .then((res) => {
                return res.json();
            })
            .then((resp) => {
                empdatachange(resp);
                setNowTime(empdata.nowTime);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    return (
        <>
            {empdata && (
                <div>
                    <h1>{empdata.id}</h1>
                    <h1>{empdata.userId}</h1>
                    <h2>{empdata.title}</h2>
                    <p>{empdata.body}</p>
                    <p>{nowTime}</p>
                    <button
                        onClick={() => {
                            LoadEdit(empdata.id);
                        }}
                    >
                        Edit
                    </button>
                    <button
                        onClick={() => {
                            RemoveFunction(empdata.id);
                        }}
                    >
                        Remove
                    </button>
                    <Link to="/">Back to List</Link>
                </div>
            )}
        </>
    );
};

export default PostPage;
