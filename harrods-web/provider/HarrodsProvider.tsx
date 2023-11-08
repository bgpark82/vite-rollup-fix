import {DataProvider, fetchUtils} from "react-admin";

const BASE_URL = "http://localhost:8080/admin"
const httpClient = fetchUtils.fetchJson;

// @ts-ignore
// @see https://marmelab.com/react-admin/DataProviderWriting.html
export const harrodsDataProvider: DataProvider =  {
    getList: (resource: string) =>
        httpClient(`${BASE_URL}/${resource}`)
            .then(({json}) => (
                {data : json, total: json.length}
            )),
    getOne: (resource: string, params: any) =>
        httpClient(`${BASE_URL}/${resource}/${params.id}`)
            .then(({json}) => ({
                data: json
            })),
};
