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
                <TextField source="id"/>
                <TextField source="name"/>
                <TextField source="userId"/>
                <TextField source="createDateTime"/>
                <TextField source="modifiedDateTime"/>
                <ArrayField source="queries">
                    <Datagrid rowClick="show">
                        <QueryField source="query" height="300px" width="600px"/>
                        <TextField source="key"/>
                        <TextField source="alias"/>
                        <NumberField source="ttl"/>
                        <TextField source="interval"/>
                        <TextField source="userId"/>
                        <TextField source="lastCachedDateTime" emptyText="-"/>
                    </Datagrid>
                </ArrayField>
            </SimpleShowLayout>
        </Show>
    )};
