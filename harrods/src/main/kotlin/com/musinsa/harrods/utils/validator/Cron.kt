package com.musinsa.harrods.utils.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ CronValidator::class])
annotation class Cron(
    val message: String = "{jakarta.validation.constraints.Pattern.message}",

    val groups: Array<KClass<*>> = [],

    val payload: Array<KClass<out Payload>> = []
)
