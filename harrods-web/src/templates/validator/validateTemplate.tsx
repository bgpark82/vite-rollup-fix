
const EXTRACT_AS_IN_COLUMNS_PATTERN = /\bAS\s+(`?[\w.]+`?)\b/gi;
const EXTRACT_COLUMNS_BETWEEN_SELECT_AND_FROM_PATTERN = /\bSELECT\b([\s\S]*?)\bFROM\b/i;
const EXTRACT_PARAMETER_BETWEEN_PARENTHESES_PATTERN = /\{\{(.*?)\}\}/g;

export const validateTemplate = ({template, params, alias}: any) => {
    if (!template) return '템플릿은 필수값입니다'

    // 1. 파라미터 검증
    const templateParams = extractParams(template);
    const inputParams = extractParamsKey(params)

    // 템플릿에는 파라미터가 존재, 입력된 파라미터는 미존재
    if (templateParams.length > 0 && inputParams.length <= 0) {
        return `템플릿의 파라미터가 존재하지 않습니다 (${templateParams})`
    }

    // 템플릿에는 파라미터가 미존재, 입력된 파라미터는 존재
    if (templateParams.length <= 0 && inputParams.length > 0) {
        return `템플릿에 입력된 파라미터가 존재하지 않습니다 (${inputParams})`
    }

    // 템플릿의 파라미터와 입력된 파라미터가 동일하지 않은 경우
    if (!isEqual(templateParams, inputParams)) {
        return `템플릿의 파라미터와 입력된 파라미터가 일치하지 않습니다`
    }

    // 2. 별칭 검증
    const columns = extractColumns(template);
    if (!columns) return "쿼리 형식으로 작성해주세요"

    const columnAlias = extractColumnAlias(columns);
    if (!alias) return "별칭이 존재하지 않습니다"

    if (columns.includes("*")) return "SELECT절에 *는 허용하지 않습니다"

    if (columnAlias.length == 0) return "첫번째 SELECT절에 AS로 별칭을 지정해주세요"

    if (!contains(alias, columnAlias)) return "쿼리에 존재하지 않는 별칭입니다"
}

function contains(alias: any, columnAlias: any[]) {
    return alias.every((m: string) => columnAlias.includes(m));
}

function extractParamsKey(params: any) {
    return params ? Object.keys(params).map(k => k.trim()) : [];
}

function extractColumns(template: any) {
    return template.match(EXTRACT_COLUMNS_BETWEEN_SELECT_AND_FROM_PATTERN)?.[1]
}

function extractColumnAlias(columns: any) {
    return Array.from(columns.matchAll(EXTRACT_AS_IN_COLUMNS_PATTERN), (match: any) => match[1]);
}

function isEqual(templateParams: any[], inputParams: string[]) {
    templateParams.sort()
    inputParams.sort()
    return JSON.stringify(templateParams) === JSON.stringify(inputParams);
}

function extractParams(template: any) {
    return Array.from(template.matchAll(EXTRACT_PARAMETER_BETWEEN_PARENTHESES_PATTERN), (match: any) => match[1]);
}
