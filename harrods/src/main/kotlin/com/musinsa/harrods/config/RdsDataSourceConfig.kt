package com.musinsa.harrods.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

const val BASE_PACKAGE_TO_SCAN_ENTITY = "com.musinsa.harrods"
const val HIBERNATE_DDL_AUTO = "hibernate.hbm2ddl.auto"

@Configuration
@EnableJpaRepositories(
    basePackages = [BASE_PACKAGE_TO_SCAN_ENTITY],
    entityManagerFactoryRef = "rdsEntityManagerFactory",
    transactionManagerRef = "rdsTransactionManager"
)
class RdsDataSourceConfig(
    val hibernateProperties: HibernateProperties,
    val jpaProperties: JpaProperties
) {

    /**
     * RDS 데이터베이스 접속 정보
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun rdsDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }


    /**
     * DataSource 생성
     * @Primary: 기본 DataSource로 지정 (데이터브릭스 DataSource는 명시적으로 사용)
     */
    @Bean
    @Primary
    fun rdsDataSource(): DataSource {
        return rdsDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    /**
     * EntityManagerFactory 생성
     * @see https://stackoverflow.com/questions/65087283/get-appropriate-properties-for-entity-manager
     */
    @Bean
    fun rdsEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = rdsDataSource()
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        em.setPackagesToScan(BASE_PACKAGE_TO_SCAN_ENTITY)
        em.setJpaPropertyMap(createPropertyMap())
        return em
    }

    fun createPropertyMap(): Map<String, Any> {
        return jpaProperties.properties + mapOf(HIBERNATE_DDL_AUTO to hibernateProperties.ddlAuto)
    }

    /**
     * TransactionManager 생성
     */
    @Bean
    fun rdsTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = rdsEntityManagerFactory().`object`
        return tm
    }
}
