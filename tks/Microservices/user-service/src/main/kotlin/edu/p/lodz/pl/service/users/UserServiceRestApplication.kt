package edu.p.lodz.pl.service.users

import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

/**
 *
 */
@ApplicationPath("/data")
@ApplicationScoped
class UserServiceRestApplication : Application()