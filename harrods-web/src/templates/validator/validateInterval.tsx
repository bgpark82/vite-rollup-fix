import {isValidCron} from "cron-validator";

type IntervalType = {interval: string|undefined}

export const validateInterval = ({interval}: IntervalType) =>{
    if (!interval) return 'interval은 필수값입니다';
    if (!isValidCron(interval)) return '유효한 cron 식이 아닙니다';
}
