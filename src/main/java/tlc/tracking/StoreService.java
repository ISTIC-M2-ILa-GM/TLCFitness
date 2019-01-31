package tlc.tracking;

import java.util.List;

public interface StoreService<T> {

    void insert(T o);

    List<T> findBetweenRect(int a, int b, int x, int y);

    void delete(T o);
}
