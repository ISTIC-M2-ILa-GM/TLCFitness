package tlc.tracking.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import tlc.tracking.Record;
import tlc.tracking.StoreService;

import java.util.List;

public class GoogleDataStoreService implements StoreService<Record> {

    private Datastore DATA_STORE = DatastoreOptions.getDefaultInstance().getService();
    private KeyFactory RECOREDS_KEY = DATA_STORE.newKeyFactory().setKind("content");

    @Override
    public Record insert(Record o) {

        Key recKey = DATA_STORE.allocateId(RECOREDS_KEY.newKey());
        Entity record = Entity.newBuilder(recKey)
                .set("user", o.getUser())
                .set("lat", o.getLat())
                .set("lon", o.getLon())
                .set("timestamp", o.getTimestamp())
                .build();
        DATA_STORE.add(record);
        o.setId(record.getKey().getId());

        return o;
    }

    @Override
    public List<Record> findBetweenRect(int a, int b, int x, int y) {
        return null;
    }

    @Override
    public void delete(Record o) {

    }
}
