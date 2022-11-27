// 프로젝트 상세정보 페이지

import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';

const PostPage = () => {
    const { empid } = useParams();

    const [empdata, empdatachange] = useState({});

    useEffect(() => {
        fetch('http://localhost:8080/projects/' + empid)
            .then((res) => {
                return res.json();
            })
            .then((resp) => {
                empdatachange(resp);
            })
            .catch((err) => {
                console.log(err.message);
            });
    });

    return (
        <>
            {empdata && (
                <div>
                    <h1>{empdata.id}</h1>
                    <h1>{empdata.userId}</h1>
                    <h2>{empdata.title}</h2>
                    <p>{empdata.body}</p>
                    <Link to="/">Back to List</Link>
                </div>
            )}
        </>
    );
};

export default PostPage;
