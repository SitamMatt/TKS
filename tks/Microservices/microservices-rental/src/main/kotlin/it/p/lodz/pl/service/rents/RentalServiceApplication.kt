package it.p.lodz.pl.service.rents

import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/data")
@ApplicationScoped
open class RentalServiceApplication : Application()