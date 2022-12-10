// 프로젝트 나열 + 검색 페이지
import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Button from '../../components/Button';

const PostListPage = () => {
    const [empdata, empdatachange] = useState(null);
    const navigate = useNavigate();

    const LoadDetail = (id) => {
        navigate('/post/' + id);
    };

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
                    window.location.reload();
                })
                .catch((err) => {
                    console.log(err.message);
                });
        }
    };

    useEffect(() => {
        fetch('http://localhost:8080/projects/')
            .then((res) => {
                return res.json();
            })
            .then((resp) => {
                empdatachange(resp);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);
    return (
        <>
            <Link to="/write">
                <Button>Add new</Button>
            </Link>

            <table>
                <thead>
                    <tr>
                        <td>
                            <h1>id</h1>
                        </td>
                        <td>
                            <h1>title</h1>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    {empdata &&
                        empdata.map((item) => (
                            <tr key={item.id}>
                                <td>{item.title}</td>
                                <td>
                                    <button
                                        onClick={() => {
                                            LoadEdit(item.id);
                                        }}
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => {
                                            RemoveFunction(item.id);
                                        }}
                                    >
                                        Remove
                                    </button>
                                    <button
                                        onClick={() => {
                                            LoadDetail(item.id);
                                        }}
                                    >
                                        Details
                                    </button>
                                </td>
                            </tr>
                        ))}
                </tbody>
            </table>
        </>
    );
};

export default PostListPage;
