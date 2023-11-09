import {NumberField, Show, SimpleShowLayout, TextField} from "react-admin";
import QueryField from "../component/field/QueryField";

export const QueryShow = () => {
    return (
        <Show>
            <SimpleShowLayout>
                <QueryField source="query" width="100%" height="400px" label="쿼리"/>
                <TextField source="key" label="캐시 키"/>
                <TextField source="alias" label="별칭"/>
                <NumberField source="ttl" label="ttl"/>
                <TextField source="interval" label="interval"/>
                <TextField source="userId" label="등록인 아이디"/>
                <TextField source="createdDateTime" label="생성일"/>
                <TextField source="modifiedDateTime" label="수정일"/>
                <TextField source="lastCachedDateTime" emptyText="-" label="마지막 캐시일"/>
            </SimpleShowLayout>
        </Show>
    )
};
