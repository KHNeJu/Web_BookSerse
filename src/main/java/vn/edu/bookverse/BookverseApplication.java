package vn.edu.bookverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@SpringBootApplication
@ServletComponentScan
public class BookverseApplication {
    @Bean
    FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter() {
        FilterRegistrationBean<ConfigurableSiteMeshFilter> registration = new FilterRegistrationBean<>(new ConfigurableSiteMeshFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(-100);
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookverseApplication.class, args);
    }
}
