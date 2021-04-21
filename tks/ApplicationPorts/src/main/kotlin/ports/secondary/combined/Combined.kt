package ports.secondary.combined

import ports.secondary.*

interface IRentRepositoryAdapter : RentPersistencePort, RentSearchPort

interface IResourceRepositoryAdapter : ResourcePersistencePort, ResourceSearchPort

interface IUserRepositoryAdapter : UserPersistencePort, UserSearchPort