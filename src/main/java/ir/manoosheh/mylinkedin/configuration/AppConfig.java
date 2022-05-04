package ir.manoosheh.mylinkedin.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver
//                = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(5242880);
//        return multipartResolver;
//    }

//    private final long MAX_AGE_SECS = 3600;
//
//    @Value("${app.cors.allowedOrigins}")
//    private String[] allowedOrigins;
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(allowedOrigins)
//                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true)
//                        .maxAge(MAX_AGE_SECS);
//            }
//        };
//    }

}
