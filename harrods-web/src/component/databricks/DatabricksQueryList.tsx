import React from 'react';
import {
    Datagrid,
    ListContextProvider,
    Pagination,
    TextField,
    useListController
} from "react-admin";


/**
 * 생성된 쿼리로 데이터브릭스에서 조회한 결과를 테이블로 보여주는 컴포넌트
 *
 * @param query 생성된 쿼리
 * @see https://marmelab.com/react-admin/ListTutorial.html
 */
const DatabricksQueryList = ({query}: any) => {

    // @ts-ignore
    const context = useListController({resource: "databricks/queries", queryOptions: {meta: {query: query}}})

    return (
        <ListContextProvider value={context}>
            <Datagrid>
                { context.data && Object.keys(context.data[0]).map(row => <TextField source={row}/>)}
            </Datagrid>
            <Pagination/>
        </ListContextProvider>
    );
};

export default DatabricksQueryList;
