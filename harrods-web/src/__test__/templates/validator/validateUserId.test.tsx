import {validateUserId} from "../../../templates/validator/validateUserId";

describe('validateUserId 테스트', () => {

    test('userId가 없는 경우 에러 메세지', () => {
        const values = {userId: undefined};

        const message = validateUserId(values)

        expect(message).toBe("등록인 아이디는 필수값입니다")
    });

    test('interval이 유효하지 않은 cron식인 경우 에러 메세지', () => {
        const values = {userId: "peter.park@musinsa.com"};

        const message = validateUserId(values)

        expect(message).toBe("이메일 형식은 허용하지 않습니다")
    });
});
