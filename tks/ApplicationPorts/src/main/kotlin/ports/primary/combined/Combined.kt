package ports.primary.combined

import ports.primary.*

interface IRentService : RentQueryPort, ResourceRentCommandPort

interface IResourceService : ResourceQueryPort, ResourceManageCommandPort

interface IUserService : UserQueryPort, UserRegisterCommandPort