// 회원정보 보여주는 페이지
import { useContext, useState } from 'react';
import useAuth from '../../hooks/useAuth';

const UserInfoPage = () => {
    const { auth } = useAuth();

    console.log(auth);
    return (
        <div>
            userInfoPage
            {auth?.isLogin}
        </div>
    );
};

export default UserInfoPage;
