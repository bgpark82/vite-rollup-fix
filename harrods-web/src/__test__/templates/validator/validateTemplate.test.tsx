import {validateTemplate} from "../../../templates/validator/validateTemplate";

describe('validateTemplate 테스트', () => {

    describe('template 검증', () => {

        test('템플릿이 없는 경우 에러 메세지', () => {
            const values = {template: undefined,};

            const message = validateTemplate(values)

            expect(message).toBe("템플릿은 필수값입니다")
        });

        test('쿼리 형식이 아닌 템플릿의 경우 에러 메세지', () => {
            const values = {template: "abc"};

            const message = validateTemplate(values)

            expect(message).toBe("쿼리 형식으로 작성해주세요")
        });
    });

    describe('template과 params 검증', () => {

        test('템플릿에는 파라미터가 있지만 입력된 파라미터가 없는 경우 에러 메세지', () => {
            const 파라미터가_있는_템플릿 = "SELECT name as name FROM user WHERE username = {{username}} AND age = {{age}}";
            const 입력되지_않은_파라미터 = undefined;
            const values = {
                template: 파라미터가_있는_템플릿,
                params: 입력되지_않은_파라미터,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("템플릿의 파라미터가 존재하지 않습니다 (username,age)")
        });

        test('템플릿에는 파라미터가 없지만 입력된 파라미터는 있는 경우 에러 메세지', () => {
            const 파라미터가_없는_템플릿 = "SELECT name as name FROM user";
            const 입력된_파라미터 = {"username":"peter", "age":30};
            const values = {
                template: 파라미터가_없는_템플릿,
                params: 입력된_파라미터,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("템플릿에 입력된 파라미터가 존재하지 않습니다 (username,age)")
        });

        test('템플릿에는 파라미터와 입력된 파라미터가 일치하지 않는 경우 에러 메세지', () => {
            const 파라미터가_있는_템플릿 = "SELECT name as name FROM user WHERE mobile = {{mobile}}";
            const 입력된_파라미터 = {"username":"peter", "age":30};
            const values = {
                template: 파라미터가_있는_템플릿,
                params: 입력된_파라미터,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("템플릿의 파라미터와 입력된 파라미터가 일치하지 않습니다")
        });

        test('입력된 파라미터의 타입이 문자열인 경우 에러 메세지', () => {
            const 문자타입_입력된_파라미터 = "params";
            const values = {
                template: "SELECT name as name FROM user WHERE mobile = {{mobile}}",
                params: 문자타입_입력된_파라미터,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("템플릿의 파라미터와 입력된 파라미터가 일치하지 않습니다")
        });

        test('입력된 파라미터의 타입이 리스트인 경우 에러 메세지', () => {
            const 리스트_형식_파라미터 = ["param"];
            const values = {
                template: "SELECT name as name FROM user WHERE mobile = {{mobile}}",
                params: 리스트_형식_파라미터,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("템플릿의 파라미터와 입력된 파라미터가 일치하지 않습니다")
        });
    });

    describe('template과 alias 검증', () => {

        test('SELECT절에 *가 존재하는 경우 에러 메세지', () => {
            const 별표가_있는_템플릿 = "SELECT * FROM user";
            const values = {
                template: 별표가_있는_템플릿,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("SELECT절에 *는 허용하지 않습니다")
        });

        test('SELECT절에 별칭이 존재하지 않는 경우 에러 메세지', () => {
            const 별칭이_없는_템플릿 = "SELECT name FROM user";
            const values = {
                template: 별칭이_없는_템플릿,
                alias: ["name"]
            };

            const message = validateTemplate(values)

            expect(message).toBe("첫번째 SELECT절에 AS로 별칭을 지정해주세요")
        });

        test('SELECT절에 별칭이 존재하지 않는 경우 에러 메세지', () => {
            const 별칭이_있는_템플릿 = "SELECT username AS name, age as age FROM user";
            const 템플릿에는_없는_별칭 = ["mobile"];
            const values = {
                template: 별칭이_있는_템플릿,
                alias: 템플릿에는_없는_별칭
            };

            const message = validateTemplate(values)

            expect(message).toBe("쿼리에 존재하지 않는 별칭입니다")
        });
    });
});
