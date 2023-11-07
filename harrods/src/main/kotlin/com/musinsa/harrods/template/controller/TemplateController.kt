package com.musinsa.harrods.template.controller

import com.musinsa.harrods.template.dto.TemplateRequest
import com.musinsa.harrods.template.dto.TemplateResponse
import com.musinsa.harrods.template.service.TemplateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/templates")
class TemplateController(
    private val templateService: TemplateService
) {

    @PostMapping
    fun create(
        @Valid @RequestBody
        request: TemplateRequest
    ): ResponseEntity<TemplateResponse> {
        val savedTemplate = templateService.create(request)
        return ResponseEntity.ok(TemplateResponse.of(savedTemplate))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<TemplateResponse>> {
        val templates = templateService.findAll()
        return ResponseEntity.ok(templates.map(TemplateResponse::of))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<TemplateResponse> {
        val template = templateService.findById(id)
        return ResponseEntity.ok(TemplateResponse.of(template))
    }
}
