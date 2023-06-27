package com.teachingaura.spring.configuration

import org.hibernate.boot.model.naming.ImplicitNamingStrategy
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
class HibernateConfig {
    @Bean
    fun physical(): PhysicalNamingStrategy {
        return PhysicalNamingStrategyStandardImpl()
    }

    @Bean
    fun implicit(): ImplicitNamingStrategy {
        return ImplicitNamingStrategyLegacyJpaImpl()
    }

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return JpaTransactionManager()
    }
}