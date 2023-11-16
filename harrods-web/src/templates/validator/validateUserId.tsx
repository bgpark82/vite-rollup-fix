import * as EmailValidator from "email-validator";

type UserIdType = {userId: string|undefined}

export const validateUserId = ({userId}: UserIdType) => {
    if (!userId) return '등록인 아이디는 필수값입니다'
    if (EmailValidator.validate(userId)) return '이메일 형식은 허용하지 않습니다'
}
