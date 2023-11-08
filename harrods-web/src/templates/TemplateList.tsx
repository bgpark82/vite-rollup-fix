import {Datagrid, DateField, List, TextField} from "react-admin";

export const TemplateList = () => (
    <List title="템플릿">
        <Datagrid rowClick="show">
            <TextField source="id" label="아이디"/>
            <TextField source="name" label="템플릿 이름"/>
            <TextField source="userId" label="등록인 아이디"/>
            <TextField source="createDateTime" label="생성일"/>
            <TextField source="modifiedDateTime" label="수정일"/>
        </Datagrid>
    </List>
);
