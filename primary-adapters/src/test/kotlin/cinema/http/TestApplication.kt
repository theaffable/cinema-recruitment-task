package cinema.http

import ddd.DomainService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

// Application and configuration classes defined below are a fix to quite peculiar problem:
// controller classes are separated from application main class,
// former are located in primary-adapters module while latter is located in application module
// This setup causes problem with spring boot context initialization when running SpringBootTests.
// Defining an application class for test purposes was the simplest solution I've found.
@SpringBootApplication
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}

@Configuration
@ComponentScan(
    basePackages = ["cinema"],
    includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [DomainService::class])]
)
class TestDomainConfiguration