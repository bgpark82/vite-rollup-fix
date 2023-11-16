import {validateParams} from "../../../templates/validator/validateParams";

describe('validateParams 테스트', () => {

    test('params이 문자인 경우 에러 메세지', () => {
        const values = {params: "test"};

        const message = validateParams(values)

        expect(message).toBe("JSON 형식으로 입력해주세요")
    });

    test('params가 리스트인 경우 에러 메세지', () => {
        const values = {params: ["test"]};

        const message = validateParams(values)

        expect(message).toBe("리스트 형식은 허용하지 않습니다")
    });
});
