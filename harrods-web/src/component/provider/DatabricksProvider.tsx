import {fetchUtils} from "react-admin";

const BASE_URL = import.meta.env.VITE_HARRODS_API_URL
const httpClient = fetchUtils.fetchJson

const DatabricksProvider =  {

    /**
     * 생성된 쿼리를 데이터브릭스에 조회
     */
    getList: (resource: any, params: any) => {
        const {pagination, sort, filter, meta} = params
        const {page, perPage} = pagination

        return httpClient(`${BASE_URL}/${resource}`, {
            method: "POST",
            body: JSON.stringify({
                query: meta.query
            }),
        }).then(({json}) => {
            const begin = (page - 1) * perPage // 페이지 시작 인덱스
            const end = begin + perPage // 페이지 끝 인덱스

            const data = json
                .map((d: any, id: number) => ({id: id + 1, ...d})) // 컬럼 아이디 추가
                .slice(begin, end) // 페이징

            return {data: data, total: json.length}
        })
    }
};

export default DatabricksProvider;
