import {validateAlias} from "../../../templates/validator/validateAlias";

describe('validateAlias 테스트', () => {

    test('alias가 없는 경우 에러 메세지', () => {
        const values = {alias: undefined};

        const message = validateAlias(values)

        expect(message).toBe("별칭은 필수값입니다")
    });

    test('alias가 문자인 경우 에러 메세지', () => {
        const values = {alias: "abc"};

        const message = validateAlias(values)

        expect(message).toBe("리스트 형식으로 입력해주세요")
    });

    test('alias가 JSON인 경우 에러 메세지', () => {
        const values = {alias: {"alias":"별칭"}};

        const message = validateAlias(values)

        expect(message).toBe("리스트 형식으로 입력해주세요")
    });
});
