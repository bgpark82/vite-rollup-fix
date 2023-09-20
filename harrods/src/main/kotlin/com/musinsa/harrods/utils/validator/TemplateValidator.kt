package com.musinsa.harrods.utils.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

const val SELECT_ASTERISK_PATTERN = "^SELECT\\s+\\*.*"

class TemplateValidator : ConstraintValidator<Template, String> {

    /**
     * Template을 검증한다
     * - SELECT 절의 *을 허용하지 않는다
     */
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        value ?: return false

        return !value.matches(Regex(SELECT_ASTERISK_PATTERN, RegexOption.IGNORE_CASE))
    }
}
