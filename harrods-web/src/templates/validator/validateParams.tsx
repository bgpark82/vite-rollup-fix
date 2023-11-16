
type ParamsType = {params: any|string|undefined}
export const validateParams = ({params}: ParamsType) => {
    if (typeof params === 'string') {
        try {
            JSON.parse(params);
        } catch (e) {
            return 'JSON 형식으로 입력해주세요'
        }
    }
    if (Array.isArray(params)) return '리스트 형식은 허용하지 않습니다';
}
