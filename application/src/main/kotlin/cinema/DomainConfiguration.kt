package cinema

import cinema.rating.RatingConstraints
import ddd.DomainService
import java.math.BigDecimal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
// we look for components with our custom annotation defined in domain module
@ComponentScan(
    basePackages = ["cinema"],
    includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [DomainService::class])]
)
class DomainConfiguration {
    @Bean
    fun configureRatingConstraints() = RatingConstraints(minIncl = BigDecimal.ONE, maxIncl = BigDecimal("5.0"))
}