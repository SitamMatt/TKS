package ports.primary.combined

import ports.primary.ResourceManageCommandPort
import ports.primary.ResourceQueryPort

interface IResourceService : ResourceQueryPort, ResourceManageCommandPort