package camp.store;

import java.util.List;
import java.util.Optional;

public interface Store<T> {
    void save(T val);

    int getNextSequence();

    Optional<T> findById(String id);

    List<T> findAll();
}
