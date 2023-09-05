package com.musinsa.harrods.utils.validator

import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinition
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.parser.CronParser
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.lang.IllegalArgumentException

class CronValidator : ConstraintValidator<Cron, String> {

    /**
     * CRON 표현식을 검증한다
     * MWAA의 CRON 표현식은 UNIX 형식을 따른다
     *
     * @see https://github.com/jmrozanec/cron-utils
     */
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        try {
            createCronParser().parse(value)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    private fun createCronParser(): CronParser = CronParser(
        createDefinition()
    )

    private fun createDefinition(): CronDefinition? = CronDefinitionBuilder
        .instanceDefinitionFor(
            CronType.UNIX
        )
}
