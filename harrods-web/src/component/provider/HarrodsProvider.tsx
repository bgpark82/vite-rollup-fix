import {DataProvider, fetchUtils} from "react-admin";

// @see https://vitejs.dev/guide/env-and-mode.html
const BASE_URL = import.meta.env.VITE_HARRODS_API_URL
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
    create: (resource: string, params: any) =>
        httpClient(`${BASE_URL}/${resource}`, {
            method: "POST",
            body: JSON.stringify(params.data),
        }).then((res) => ({
            data: { ...params.data, id: res.json.id}
        }))
};
