package edu.p.lodz.pl.pas.mvc;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;


@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/auth/login.xhtml",
                errorPage = "/auth/login-error.xhtml"
        )
)
@ApplicationScoped
public class AppConfig {
}
