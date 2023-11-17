import {validateInterval} from "./validateInterval";
import {validateUserId} from "./validateUserId";
import {validateAlias} from "./validateAlias";
import {validateParams} from "./validateParams";
import {validateTemplate} from "./validateTemplate";

// 파라미터가 추가되면 검증하고자 하는 필드와 함수를 추가
const paramsFuncMap: any = {
    'interval': validateInterval,
    'userId': validateUserId,
    'alias': validateAlias,
    'params': validateParams,
    'template': validateTemplate
}

export class TemplateCreateValidator {

    /** form의 모든 입력값 */
    values

    /** input field별 validation 함수 매핑 */
    paramFuncMap

    constructor(values: any) {
        this.values = values;
        this.paramFuncMap = paramsFuncMap;
    }

    /**
     * form에 저장된 각 필드의 유효성을 검증하고
     * errors 객체에 각 필드당 에러 메세지를 담아 반환
     *
     * ex) errors = { interval: "에러 메세지", userId: "에러 메세지" }
     */
    validate() {
        const errors:any = {}

        for(const key in this.paramFuncMap) {
            const fun = this.paramFuncMap[key]
            const message = fun(this.values)
            if (message) errors[key] = message
        }
        return errors
    }
}
