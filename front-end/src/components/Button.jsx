import styled from 'styled-components';
import palette from '../libs/palette';

const StyledButton = styled.button`
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    font-weight: bold;
    padding: 0.25rem 1rem;
    color: white;
    outline: none;
    cursor: pointer;
    margin-right: 1rem;

    background: ${palette.gray[8]};
    &:hover {
        background: ${palette.gray[6]};
    }
`;

const Button = (props) => <StyledButton {...props} />;

export default Button;
//여기서 styledButton을 Button으로 컴포넌트에 넣어주는 이유는 자동으로 styled-component가 import 되게 해주기 위해서
