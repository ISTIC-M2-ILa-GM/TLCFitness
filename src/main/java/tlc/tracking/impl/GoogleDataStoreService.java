package tlc.tracking.impl;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import tlc.tracking.Record;
import tlc.tracking.RecordList;
import tlc.tracking.RecordMapper;
import tlc.tracking.StoreService;

import java.util.ArrayList;
import java.util.List;

import static com.google.cloud.datastore.Query.newEntityQueryBuilder;
import static com.google.cloud.datastore.StructuredQuery.Filter;
import static com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import static com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import static tlc.tracking.RecordMapper.toEntity;

public class GoogleDataStoreService implements StoreService {

    private static final Datastore DATA_STORE = DatastoreOptions.getDefaultInstance().getService();
    private static final KeyFactory RECORDS_KEY = DATA_STORE.newKeyFactory().setKind("Record");

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
    public RecordList find(String user, Long id, Double lon, Double lat, Long timestampMin, Long timestampMax) {
        List<Filter> filters = new ArrayList<>();
        RecordList records = new RecordList();

        if (user != null) {
            Filter userFilter = PropertyFilter.eq("user", user);
            filters.add(userFilter);
        }
        if (id != null) {
            PropertyFilter idFilter = PropertyFilter.eq("id", id);
            filters.add(idFilter);
        }
        if (lon != null) {
            PropertyFilter lonFilter = PropertyFilter.eq("lon", lon);
            filters.add(lonFilter);
        }
        if (lat != null) {
            PropertyFilter latFilter = PropertyFilter.eq("lat", lat);
            filters.add(latFilter);
        }
        if (timestampMin != null && timestampMax != null) {
            PropertyFilter minTimeFilter = PropertyFilter.ge("timestamp", timestampMin);
            PropertyFilter maxTimeFilter = PropertyFilter.le("timestamp", timestampMax);
            filters.add(minTimeFilter);
            filters.add(maxTimeFilter);
        }

        Filter mainFilter = null;
        for (Filter f : filters) {
            if (mainFilter == null) {
                mainFilter = f;
            }
            else {
                mainFilter = CompositeFilter.and(mainFilter, f);
            }
        }

        Query query = Query
                .newEntityQueryBuilder()
                .setKind("Record")
                .setFilter(mainFilter)
                .build();

        QueryResults<Entity> results = DATA_STORE.run(query);

        while (results.hasNext()) {
            records.add(RecordMapper.toRecord(results.next()));
        }

        return records;
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
        final EntityQuery query = newEntityQueryBuilder()
                .setKind("Record")
                .setFilter(PropertyFilter.eq("id", id))
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
