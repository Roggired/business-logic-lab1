package ru.yofik.kickstoper.api.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(provideJaasAuthenticationProvider());
        http.authorizeRequests()
                .antMatchers("/api/v1/**")
                .authenticated()
                .and()
                .httpBasic();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/v2/auth");
    }

    public DefaultJaasAuthenticationProvider provideJaasAuthenticationProvider() {
        DefaultJaasAuthenticationProvider provider = new DefaultJaasAuthenticationProvider();

        Map<String, AppConfigurationEntry[]> configurationEntryMap = new HashMap<>();
        AppConfigurationEntry configurationEntry = new AppConfigurationEntry(
                KickstoperJaasLoginModule.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                Collections.emptyMap()
        );
        configurationEntryMap.put("SPRINGSECURITY", new AppConfigurationEntry[] {configurationEntry});
        InMemoryConfiguration configuration = new InMemoryConfiguration(configurationEntryMap);

        provider.setConfiguration(configuration);
        provider.setAuthorityGranters(new AuthorityGranter[] { new KickstoperJaasAuthorityGranter() });

        try {
            provider.afterPropertiesSet();
        } catch (Exception e) {
            log.fatal("Unexpected exception during building Default Jaas Authentication Provider", e);
            throw new RuntimeException(e);
        }

        return provider;
    }
}
