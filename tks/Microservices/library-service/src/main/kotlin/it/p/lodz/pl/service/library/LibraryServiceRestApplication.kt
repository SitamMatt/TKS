package it.p.lodz.pl.service.library

import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

/**
 *
 */
@ApplicationPath("/data")
@ApplicationScoped
class LibraryServiceRestApplication : Application()