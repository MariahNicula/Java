package Repository;

import Domain.Entity;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.ObjectNotFoundException;

import java.io.IOException;
import java.util.Collection;

public interface IRepository <T extends Entity> extends Iterable<T>{
    public void add_pacient(T entity) throws DuplicateEntityExceptions;
    public void remove(int id) throws ObjectNotFoundException, IOException;
    public T find(int id);
    public void update(T entity) throws ObjectNotFoundException;
    public Collection<T> getAll();

}
