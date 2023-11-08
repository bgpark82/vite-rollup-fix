import {useInput} from "react-admin";
import QueryEditor from "./QueryEditor";

/**
 * useInput은 외부 컴포넌트를 리액트 어드민의 Input 태그와 연결시키는 Adaptor
 *
 * useInput은 useController를 래핑한 hook
 * useController는 내부적으로 <Form> 태그 내부의 모든 <Input>의 source들이 key-value 형식으로 저장한다
 * useInput의 인풋으로 source를 등록한다
 * useInput의 반환값으로 field (value, onChange)를 가진다.
 * onChange로 form 내부의 value를 수정한다
 *
 * @see useInput https://marmelab.com/react-admin/useInput.html
 * @see useController https://react-hook-form.com/docs/usecontroller
*/
// @ts-ignore
const QueryInput = ({source, ...rest}) => {

    const {
        id,
        field,
    } = useInput({ source, ...rest })

    return (
        <label htmlFor={id} >
            <QueryEditor {...field} {...rest} />
        </label>
    );
};

export default QueryInput;
