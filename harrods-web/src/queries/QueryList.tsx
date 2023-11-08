import {Datagrid, List, NumberField, TextField} from "react-admin";

export const QueryList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="id" label="아이디" />
            <TextField source="key" label="캐시 키" />
            <TextField source="alias" label="별칭" />
            <NumberField source="ttl" label="ttl" />
            <TextField source="interval" label="interval" />
            <TextField source="userId" label="등록자 아이디" />
            <TextField source="lastCachedDateTime" label="마지막 캐시일" emptyText="-" />
            <TextField source="createdDateTime" label="생성일" />
            <TextField source="modifiedDateTime" label="수정일" />
        </Datagrid>
    </List>
);
