package ports.primary.combined

import ports.primary.RentQueryPort
import ports.primary.ResourceRentCommandPort

interface IRentService : RentQueryPort, ResourceRentCommandPort