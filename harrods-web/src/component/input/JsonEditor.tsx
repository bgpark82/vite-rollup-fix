import React from "react";
import ReactCodeMirror from "@uiw/react-codemirror";
import {jsonLanguage} from "@codemirror/lang-json";

// @see lang-json https://www.npmjs.com/package/@codemirror/lang-json
const ParamEditor = (props: any) => {

    const handleChange = (val: string) => {
        try {
            props.onChange(JSON.parse(val));
        } catch (e) {
            // json 형식이 아닌 경우, 유효성 검사를 위해 일단 등록
            props.onChange(val);
        }
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
