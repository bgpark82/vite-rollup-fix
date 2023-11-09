import React from 'react';
import {InputHelperText, useInput} from "react-admin";
import JsonEditor from "./JsonEditor";
import ErrorInputText from "../field/ErrorInputText";

const JsonInput = ({ source, ...rest }: any) => {

    const {
        id,
        field,
        fieldState
    } = useInput({ source, ...rest })

    return (
        <label htmlFor={id} >
            <JsonEditor {...field} {...rest} />
            {fieldState.error && <ErrorInputText message={fieldState.error.message} />}
        </label>
    );
};

export default JsonInput;
