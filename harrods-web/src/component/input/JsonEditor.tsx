import React from "react";
import ReactCodeMirror from "@uiw/react-codemirror";
import {jsonLanguage} from "@codemirror/lang-json";

// @see lang-json https://www.npmjs.com/package/@codemirror/lang-json
const ParamEditor = (props: any) => {

    const handleChange = (val: string) => {
        props.onChange(JSON.parse(val))
    }

    return (
        <div>
            <ReactCodeMirror
                height={props.height}
                width={props.width}
                onChange={handleChange}
                extensions={[jsonLanguage]}
                basicSetup={{lineNumbers: false}}
                placeholder={props.placeholder}
            />
        </div>
    );
};

export default ParamEditor;
