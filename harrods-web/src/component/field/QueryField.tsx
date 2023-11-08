import {useRecordContext} from "react-admin";
import ReactCodeMirror from "@uiw/react-codemirror";
import { format } from 'sql-formatter';
import {sql} from "@codemirror/lang-sql";

/**
 * react-codemirror: 등록된 sql 조회 컴포넌트
 * lang-sql: sql 문법 하이라이팅
 * sql-formatter: 쿼리를 읽기 편하게 포메팅
 *
 * @see react-codemirror https://uiwjs.github.io/react-codemirror/
 * @see lang-sql https://github.com/codemirror/lang-sql
 * @see sql-formatter https://www.npmjs.com/package/sql-formatter
*/
const QueryField = (props: any) => {
    const record = useRecordContext(props)

    return (
        <ReactCodeMirror
            height={props.height}
            width={props.width}
            value={ format(record.query)}
            extensions={[sql()]}
            basicSetup={{lineNumbers: true}}
            readOnly
            editable={false}
        />
    );
};

export default QueryField;
