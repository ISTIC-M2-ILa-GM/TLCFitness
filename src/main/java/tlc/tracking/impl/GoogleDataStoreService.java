package tlc.tracking.impl;

import static tlc.tracking.RecordMapper.toEntity;

import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;

import tlc.tracking.Record;
import tlc.tracking.RecordList;
import tlc.tracking.StoreService;

public class GoogleDataStoreService implements StoreService {

    private Datastore DATA_STORE = DatastoreOptions.getDefaultInstance().getService();
    private KeyFactory RECORDS_KEY = DATA_STORE.newKeyFactory().setKind("content");

    @Override
    public RecordList insert(RecordList recordList) {
        recordList.forEach(r -> {
            Key recKey = DATA_STORE.allocateId(RECORDS_KEY.newKey());
            Entity entity = toEntity(recKey, r);
            DATA_STORE.add(entity);
            r.setId(entity.getKey().getId());
        });
        return recordList;
    }

    @Override
    public List<Record> findBetweenRect(int a, int b, int x, int y) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Record> findByRunId(long id) {
        return null;
    }

}
