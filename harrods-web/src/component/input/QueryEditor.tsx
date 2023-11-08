import React from "react";
import {sql} from "@codemirror/lang-sql";
import ReactCodeMirror from "@uiw/react-codemirror";

const QueryEditor = (props: any) => {

    const handleChange = (val: string) => {
        props.onChange(val);
    }

    return (
        <ReactCodeMirror
            value={props.value}
            height={props.height}
            width={props.width}
            extensions={[sql()]}
            onChange={handleChange}/>
    );
};

export default QueryEditor;
