import React from 'react';
import {useInput} from "react-admin";
import JsonEditor from "./JsonEditor";

const JsonInput = ({ source, ...rest }: any) => {

    const {
        id,
        field,
    } = useInput({ source, ...rest })

    return (
        <label htmlFor={id} >
            <JsonEditor {...field} {...rest} />
        </label>
    );
};

export default JsonInput;
