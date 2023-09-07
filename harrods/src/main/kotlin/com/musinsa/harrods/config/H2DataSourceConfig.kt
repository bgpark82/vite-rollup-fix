package com.musinsa.harrods.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

// @Configuration
@EnableJpaRepositories(
    basePackages = ["com.musinsa.harrods"],
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager"
)
class H2DataSourceConfig {

    @Bean
    fun h2EntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em =
            LocalContainerEntityManagerFactoryBean()
        em.dataSource = h2DataSource()
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        em.setPackagesToScan("com.musinsa.harrods")
        return em
    }

    @Bean
    fun h2TransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = h2EntityManagerFactory().`object`
        return tm
    }

    @Bean
    fun h2DataSource(): DataSource {
        return this.h2DataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    fun h2DataSourceProperties(): DataSourceProperties {
        val properties = DataSourceProperties()
        properties.driverClassName = "org.h2.Driver"
        properties.url = "jdbc:h2:mem:test;MODE=MySQL"
        properties.username = "sa"
        properties.password = ""
        return properties
    }
}
