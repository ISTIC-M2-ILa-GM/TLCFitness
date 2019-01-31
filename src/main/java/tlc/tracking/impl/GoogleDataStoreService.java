package tlc.tracking.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import tlc.tracking.RecordList;
import tlc.tracking.StoreService;

import java.util.List;

import static tlc.tracking.RecordMapper.toEntity;

public class GoogleDataStoreService implements StoreService<RecordList> {

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
    public List<RecordList> findBetweenRect(int a, int b, int x, int y) {
        return null;
    }

    @Override
    public void delete(RecordList o) {

    }
}
