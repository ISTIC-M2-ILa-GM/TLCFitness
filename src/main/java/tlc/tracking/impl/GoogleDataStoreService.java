package tlc.tracking.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import tlc.tracking.Record;
import tlc.tracking.RecordList;
import tlc.tracking.RecordMapper;
import tlc.tracking.StoreService;

import java.util.ArrayList;
import java.util.List;

import static tlc.tracking.RecordMapper.toEntity;

public class GoogleDataStoreService implements StoreService {

    private Datastore DATA_STORE = DatastoreOptions.getDefaultInstance().getService();
    private KeyFactory RECORDS_KEY = DATA_STORE.newKeyFactory().setKind("Record");

    @Override
    public RecordList insert(RecordList recordList) {
        recordList.forEach(r -> {
            Key recKey = DATA_STORE.allocateId(RECORDS_KEY.newKey());
            Entity entity = toEntity(recKey, r);
            DATA_STORE.add(entity);
        });
        return recordList;
    }

    @Override
    public List<Record> find(String user, Long lon, Long lat, Long timestampMin, Long timestampMax) {
        return null;
    }

    @Override
    public void delete(long id) {
        // Recréer la clé
        final Key key = longToKey(id);
        // Supprime l'enregistrement
        DATA_STORE.delete(key);
    }

    @Override
    public List<Record> findById(final long id) {
        final EntityQuery query = Query.newEntityQueryBuilder()
                .setKind("Record")
                .setFilter(StructuredQuery.PropertyFilter.eq("id", id))
                .build();

        final QueryResults<Entity> results = DATA_STORE.run(query);
        final List<Record> records = new ArrayList<>();
        while (results.hasNext()) {
            records.add(
                    RecordMapper.toRecord(results.next())
            );
        }


        return records;
    }

    private Key longToKey(long id) {
        return RECORDS_KEY.newKey(id);
    }

}
