import {
    NumberField,
    Show,
    SimpleShowLayout,
    TextField,
    useShowController
} from "react-admin";
import QueryField from "../component/field/QueryField";
import DatabricksQueryList from "../component/databricks/DatabricksQueryList";
import {useState} from "react";
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import Button from '@mui/material/Button';


export const QueryShow = () => {

    const [show, setShow] = useState(false)

    const handleClick = () => {
        setShow(!show)
    }

    // @ts-ignore
    const {record} = useShowController()

    return (
        <Show>
            <SimpleShowLayout>
                <Button variant="contained" color="primary" onClick={handleClick} startIcon={<PlayArrowIcon/>}>쿼리 실행</Button>
                <QueryField source="query" width="100%" height="400px" label="쿼리"/>
                <TextField source="key" label="캐시 키"/>
                <TextField source="alias" label="별칭"/>
                <NumberField source="ttl" label="ttl"/>
                <TextField source="interval" label="interval"/>
                <TextField source="userId" label="등록인 아이디"/>
                <TextField source="createdDateTime" label="생성일"/>
                <TextField source="modifiedDateTime" label="수정일"/>
                <TextField source="lastCachedDateTime" emptyText="-" label="마지막 캐시일"/>
                {show && <DatabricksQueryList query={record.query}/>}
            </SimpleShowLayout>
        </Show>
    )
};
