package com.teachingaura.spring.configuration

import com.teachingaura.contexts.UserContext
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Tags


@Configuration
class MetricsConfig {

    @Bean
    fun webMvcTagsContributor(): WebMvcTagsContributor? {
        return object : WebMvcTagsContributor {
            override fun getTags(request: HttpServletRequest?, response: HttpServletResponse?,
                handler: Any?,
                exception: Throwable?
            ): Iterable<Tag> {
                var tags: Tags = Tags.empty()
                val uid = UserContext.getUid() ?: ""
                tags = tags.and(Tag.of("uid", uid))
                return tags
            }

            override fun getLongRequestTags(request: HttpServletRequest, handler: Any): Iterable<Tag> {
                return listOf()
            }
        }
    }
}