package tlc.tracking.impl;

import com.google.cloud.datastore.*;
import tlc.tracking.Record;
import tlc.tracking.RecordList;
import tlc.tracking.RecordMapper;
import tlc.tracking.StoreService;

import java.util.ArrayList;
import java.util.List;

import static tlc.tracking.RecordMapper.toEntity;

public class GoogleDataStoreService implements StoreService {

    private Datastore DATA_STORE = DatastoreOptions.getDefaultInstance().getService();
    private KeyFactory RECORDS_KEY = DATA_STORE.newKeyFactory().setKind("content");

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
    public List<Record> findBetweenRect(int a, int b, int x, int y) {
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
    public List<Record> findByRunId(final long runId) {
        final EntityQuery query = Query.newEntityQueryBuilder()
                .setKind("Record")
                .setFilter(StructuredQuery.PropertyFilter.eq("runId", runId))
                .build();

        final QueryResults<Entity> results = DATA_STORE.run(query);
        final List<Record> records = new ArrayList<>();
        while (results.hasNext()){
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
