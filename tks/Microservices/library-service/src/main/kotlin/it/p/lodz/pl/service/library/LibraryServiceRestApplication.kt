package it.p.lodz.pl.service.library

import org.eclipse.microprofile.auth.LoginConfig
import javax.annotation.security.DeclareRoles
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

/**
 *
 */
@ApplicationPath("/data")
@LoginConfig(authMethod = "MP-JWT")
@ApplicationScoped
@DeclareRoles(value =  [ "mysimplerole", "USER" ])
open class LibraryServiceRestApplication : Application()