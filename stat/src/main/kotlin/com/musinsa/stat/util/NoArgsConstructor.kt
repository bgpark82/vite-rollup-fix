package com.musinsa.stat.util

/**
 * 빈 생성자를 만들기 위한 어노테이션
 * 프라이머리 생성자가 없는 클래스의 생성자가 2개 이상일 경우, 아래 어노테이션을 추가한다.
 */
@Target(AnnotationTarget.CLASS)
annotation class NoArgsConstructor