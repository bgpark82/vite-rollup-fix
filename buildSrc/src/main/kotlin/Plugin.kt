object Plugin {
    object Spring {
        // 스프링부트
        val SPRINGBOOT = Pair("org.springframework.boot", "3.0.6")

        // 스프링부트 버전에 맞는 의존성을 가져오도록 도와주는 플러그인
        val SPRING_DEPENDENCY_MANAGEMENT =
            Pair("io.spring.dependency-management", "1.1.0")

        // Spring REST Docs AsciiDoc
        val ASCII_DOCTOR = Pair("org.asciidoctor.jvm.convert", "3.3.2")

        // Kotlin NoArgs Constructor(https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support)
        val NO_ARGUMENTS = Pair("org.jetbrains.kotlin.plugin.noarg", "1.8.21")
    }

    object KOTLIN {
        // Kotlin JVM plugin
        val JVM = Pair("jvm", "1.7.10")

        // 특정한 어노테이션이 붙은 클래스의 접근 제한자를 open 으로 모두 변경(https://kotlinlang.org/docs/all-open-plugin.html#spring-support)
        val SPRING = Pair("plugin.spring", "1.7.10")
    }
}