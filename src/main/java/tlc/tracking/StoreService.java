package tlc.tracking;

import java.util.List;
import java.util.stream.Stream;

public interface StoreService {

    RecordList insert(RecordList o);

    List<Record> findBetweenRect(int a, int b, int x, int y);

    /**
     * Supprime un {@link Record} en fonction de son id
     *
     * @param id, l'id du record
     */
    void delete(long id);

    /**
     * Récupère un {@link Stream} de {@link Record}
     *
     * @param runId, l'identifiant du run
     * @return un {@link Stream} de Record jamais null
     */
    List<Record> findByRunId(long runId);
}
