package edu.p.lodz.pl.pas.mvc.repositories.interfaces;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.IncompatibleTypeExeption;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface IResourcesRepository extends IRepositoryBase<Resource> {
    boolean delete(UUID id) throws ObjectNotFoundException;
    ArrayList<Resource> getSelectedResources(int pagesCount, int pageNumber);
}
