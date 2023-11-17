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

    test('상품통계 템플릿 테스트', () => {
        const values = {template: `SELECT t1.goods_no AS goods_no,
                       SUM(IF(t1.age_band = 'age_band.0', t1.cnt, 0)) AS \`age_band.0\`,
                       SUM(IF(t1.age_band = 'age_band.19', t1.cnt, 0)) AS \`age_band.19\`,
                       SUM(IF(t1.age_band = 'age_band.24', t1.cnt, 0)) AS \`age_band.24\`,
                       SUM(IF(t1.age_band = 'age_band.29', t1.cnt, 0)) AS \`age_band.29\`,
                       SUM(IF(t1.age_band = 'age_band.34', t1.cnt, 0)) AS \`age_band.34\`,
                       SUM(IF(t1.age_band = 'age_band.40', t1.cnt, 0)) AS \`age_band.40\`,
                       SUM(IF(t1.gender = 'gender.F', t1.cnt, 0)) AS \`gender.F\`,
                       SUM(IF(t1.gender = 'gender.M', t1.cnt, 0)) AS \`gender.M\`,
                       SUM(IF(t1.gender = 'gender.N', t1.cnt, 0)) AS \`gender.N\`,
                       SUM(t1.cnt) AS total,
                       SUM(t1.qty) AS quantity

                  FROM (
                SELECT o.uid,
                       o.goods_no,
                       CASE WHEN o.age <= 18 THEN 'age_band.0'
                            WHEN o.age >= 19 AND o.age < 24 THEN 'age_band.19'
                            WHEN o.age >= 24 AND o.age < 29 THEN 'age_band.24'
                            WHEN o.age >= 29 AND o.age < 34 THEN 'age_band.29'
                            WHEN o.age >= 34 AND o.age < 40 THEN 'age_band.34'
                            ELSE 'age_band.40' END AS age_band,

                       CASE WHEN o.gender = 'F' THEN 'gender.F'
                                WHEN o.gender = 'M' THEN 'gender.M'
                                ELSE 'gender.N' END AS gender,

                       SUM(IF(o.ord_state = 5, 1, -1)) AS cnt,
                       SUM(IF(o.ord_state = 5, o.qty, -1*o.qty)) AS qty

                  FROM (SELECT ow.ord_wonga_no,
                       om.uid AS uid,
                       ow.goods_no AS goods_no,
                       u.age AS age,
                       u.gender AS gender,
                       -- INT(date_format(now(), 'yyyy'))-m.birth1 AS age,
                       -- m.sex AS gender,
                       ow.ord_state AS ord_state,
                       ow.qty AS qty
                  FROM musinsa.bizest.order_opt_wonga ow JOIN musinsa.bizest.order_opt oo ON ow.ord_opt_no = oo.ord_opt_no
                                                         JOIN musinsa.bizest.order_mst om ON oo.ord_no = om.ord_no
                                                         JOIN datamart.datamart.users u ON om.uid = u.uid
                                                         -- JOIN musinsa.member.rb_s_mbrdata m ON om.uid = m.memberuid
                 WHERE ow.ord_state IN (5, 60, 61)
                   AND ow.ord_state_date >= date_format(date_add(now(), -365),'yyyyMMdd')
                   AND ow.ord_state_date < date_format(now(),'yyyyMMdd')
                   AND u.gender = {{gender}}
                   ) o
                 GROUP BY all
                 ) t1 JOIN (SELECT goods_no
                            FROM musinsa.bizest.order_opt_wonga
                         WHERE ord_state IN (5, 60, 61)
                           AND ut >= date_format(dateadd(HOUR, 9-2, now()), 'yyyy-MM-dd HH:mm:ss') -- utc 에 9시간을 더하고(KST) 2시간 이전
                           AND ut < date_format(dateadd(HOUR, 9-1, now()), 'yyyy-MM-dd HH:mm:ss')
                         GROUP BY all) t2 ON t1.goods_no = t2.goods_no
                  GROUP BY all
            `,
            params: {"gender": ["F","M"]},
            alias:["goods_no"]};

        const message = validateTemplate(values)

        expect(message).toBe(undefined)
    });
});

