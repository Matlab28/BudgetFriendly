//package com.example.budgetfriendly.config;
//
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.Ssl;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomizationBean implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
//
//    @Override
//    public void customize(TomcatServletWebServerFactory factory) {
//        factory.setSsl(getSsl());
//        factory.setPort(8443);
//    }
//
//    private Ssl getSsl() {
//        Ssl ssl = new Ssl();
//        ssl.setKeyStore("classpath:path/to/your/keystore.p12");
//        ssl.setKeyStorePassword("your-password");
//        ssl.setKeyStoreType("PKCS12");
//        ssl.setKeyAlias("your-alias");
//        return ssl;
//    }
//}
