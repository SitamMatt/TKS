package ports.primary.combined

import ports.primary.UserQueryPort
import ports.primary.UserRegisterCommandPort

interface IUserService : UserQueryPort, UserRegisterCommandPort