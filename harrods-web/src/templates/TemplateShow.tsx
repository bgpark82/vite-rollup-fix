import {
    ArrayField,
    Datagrid,
    NumberField,
    Show,
    SimpleShowLayout,
    TextField
} from "react-admin";
import QueryField from "./QueryField";

export const TemplateShow = () => {
    return (
        <Show>
            <SimpleShowLayout>
                <TextField source="id" label="아이디"/>
                <TextField source="name" label="템플릿 이름"/>
                <TextField source="userId" label="등록인 아이디"/>
                <TextField source="createDateTime" label="생성일"/>
                <TextField source="modifiedDateTime" label="수정일"/>
                <ArrayField source="queries">
                    <Datagrid rowClick="show">
                        <QueryField source="query" label="쿼리" height="300px" width="600px"/>
                        <TextField source="key" label="캐시 키"/>
                        <TextField source="alias" label="별칭"/>
                        <NumberField source="ttl" label="ttl"/>
                        <TextField source="interval" label="interval"/>
                        <TextField source="userId" label="등록인 아이디"/>
                        <TextField source="lastCachedDateTime" emptyText="-" label="마지막 캐시일"/>
                    </Datagrid>
                </ArrayField>
            </SimpleShowLayout>
        </Show>
    )};
