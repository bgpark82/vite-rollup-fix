import React from 'react';
import {InputHelperText} from "react-admin";

const ErrorInputText = ({message}: any) => {
    return (
        <div style={{color: '#d32f2f', fontWeight: 400, fontSize: '0.75rem'}}>
            <InputHelperText touched error={message}/>
        </div>
    );
};

export default ErrorInputText;
