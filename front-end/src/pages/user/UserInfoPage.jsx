// 회원정보 보여주는 페이지
import { useState } from 'react';

const UserInfoPage = () => {
    const [userId, setUserId] = useState('');
    setUserId(localStorage.getItem('id'));
    console.log(userId);
    return (
        <div>
            userInfoPage
            <h1>{userId}</h1>
        </div>
    );
};

export default UserInfoPage;
