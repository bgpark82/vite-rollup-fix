import {validateInterval} from "../../../templates/validator/validateInterval";

describe('validateInterval 테스트', () => {

    test('interval이 없는 경우 에러 메세지', () => {
        const values = {interval: undefined};

        const message = validateInterval(values)

        expect(message).toBe("interval은 필수값입니다")
    });

    test('interval이 유효하지 않은 cron식인 경우 에러 메세지', () => {
        const values = {interval: "abc"};

        const message = validateInterval(values)

        expect(message).toBe("유효한 cron 식이 아닙니다")
    });
});
