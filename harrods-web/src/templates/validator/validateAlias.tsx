type AliasType = { alias: object|string|undefined }

export const validateAlias = ({alias}: AliasType) => {
    if (!alias) return '별칭은 필수값입니다';

    if (typeof alias === 'string') {
        try {
            JSON.parse(alias);
        } catch (e) {
            return '리스트 형식으로 입력해주세요'
        }
    }

    if (!Array.isArray(alias)) return '리스트 형식으로 입력해주세요';
}
