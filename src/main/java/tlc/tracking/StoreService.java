package tlc.tracking;

import java.util.List;
import java.util.stream.Stream;

public interface StoreService {

    RecordList insert(RecordList o);

    RecordList find(String user, Long id, Double lon, Double lat, Long timestampMin, Long timestampMax);

    /**
     * Supprime un {@link Record} en fonction de son id
     *
     * @param id, l'id du record
     */
    void delete(long id);

    /**
     * Récupère un {@link Stream} de {@link Record}
     *
     * @param id, l'identifiant du run
     * @return un {@link Stream} de Record jamais null
     */
    List<Record> findById(long id);
}
