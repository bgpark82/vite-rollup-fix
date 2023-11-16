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
    return template.match(/SELECT(.*?)FROM/)[1];
}

function extractColumnAlias(columns: any) {
    return Array.from(columns.matchAll(/\bAS\s+(`?[\w.]+`?)\b/gi), (match: any) => match[1]);
}

function isEqual(templateParams: any[], inputParams: string[]) {
    templateParams.sort()
    inputParams.sort()
    return JSON.stringify(templateParams) === JSON.stringify(inputParams);
}

function extractParams(template: any) {
    return Array.from(template.matchAll(/\{\{(.*?)\}\}/g), (match: any) => match[1]);
}
