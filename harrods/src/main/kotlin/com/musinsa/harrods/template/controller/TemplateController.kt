package com.musinsa.harrods.template.controller

import com.musinsa.harrods.template.dto.TemplateResponse
import com.musinsa.harrods.template.service.TemplateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateController(
    private val templateService: TemplateService
) {

    @GetMapping("/templates")
    fun findAll(): ResponseEntity<List<TemplateResponse>> {
        val templates = templateService.findAll()
        return ResponseEntity.ok(templates.map(TemplateResponse::of))
    }
}
