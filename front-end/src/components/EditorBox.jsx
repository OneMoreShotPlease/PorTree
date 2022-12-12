import { useRef } from 'react';

// Toast 에디터
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';

const EditorBox = () => {
    const onChange = () => {
        const data = editorRef.current.getInstance().getHTML();
        console.log(data);
    };
    const editorRef = useRef();
    return (
        <Editor
            initialValue="hello react editor world!"
            previewStyle="vertical"
            height="600px"
            initialEditType="wysiwyg"
            ref={editorRef}
            onChange={onChange}
            useCommandShortcut={false}
        />
    );
};

export default EditorBox;
