import {NumberField, Show, SimpleShowLayout, TextField} from "react-admin";
import QueryField from "../templates/QueryField";

export const QueryShow = () => {
    return (
        <Show>
            <SimpleShowLayout>
                <QueryField source="query" width="100%" height="400px"/>
                <TextField source="key"/>
                <NumberField source="ttl"/>
                <TextField source="interval"/>
                <TextField source="userId"/>
                <TextField source="createdDateTime"/>
                <TextField source="modifiedDateTime"/>
                <TextField source="lastCachedDateTime" emptyText="-"/>
                <TextField source="alias"/>
            </SimpleShowLayout>
        </Show>
    )
};
