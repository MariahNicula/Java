package Repository;

import Domain.Entity;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.ObjectNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Repository<T extends Entity> implements IRepository<T>
{
    ArrayList<T> entities = new ArrayList<T>();

    @Override
    public void add_pacient(T entity) throws DuplicateEntityExceptions {
        if(find(entity.getId()) != null)
        {
            throw new DuplicateEntityExceptions("ID Duplicat!");
        }
      entities.add(entity);
    }

    @Override
    public void remove(int id) throws ObjectNotFoundException, IOException {
        if(find(id) == null)
        {
            throw new ObjectNotFoundException("Obiectul nu exista. ");
        }
        entities.removeIf(entity -> entity.getId() == id);
    }

    @Override
    public T find(int id) {
        for(T entity: entities)
        {
            if(entity.getId() == id)
            {
                return entity;
            }
        }
        return null;
    }


    @Override
    public void update(T entity) throws ObjectNotFoundException {
        if(find(entity.getId()) == null)
        {
            throw new ObjectNotFoundException("Obiectul nu exista. ");
        }
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId())
                entities.set(i, entity);
        }
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<T>(entities);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(entities).iterator();
    }
}











/*
import Domain.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 */
/*
public class Repository<T> implements Iterable<T> {
    private List<T> entityList = new ArrayList<>();
    public void addEntity(T entity) {
        entityList.add(entity);
    }
    public T findEntityById(int id) {
        for (T entity : entityList) {
            if (entity instanceof Entity) {
                Entity e = (Entity) entity;
                if (e.getId() == id) {
                    return entity;
                }
            }
        }
        return null;
    }
    public void deleteEntity(T entity) {
        entityList.remove(entity);
    }

    @Override
    public Iterator<T> iterator() {
        return entityList.iterator();
    }
}
 */