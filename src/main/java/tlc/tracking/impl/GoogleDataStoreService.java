package tlc.tracking.impl;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import tlc.tracking.Record;
import tlc.tracking.RecordList;
import tlc.tracking.RecordMapper;
import tlc.tracking.StoreService;

import java.util.ArrayList;
import java.util.List;

import static com.google.cloud.datastore.Query.newEntityQueryBuilder;
import static tlc.tracking.RecordMapper.toEntity;

public class GoogleDataStoreService implements StoreService {

    private static final Datastore DATA_STORE = DatastoreOptions.getDefaultInstance().getService();
    private static final DatastoreService DATASTORE_SERVICE = DatastoreServiceFactory.getDatastoreService();
    private static final KeyFactory RECORDS_KEY = DATA_STORE.newKeyFactory().setKind("content");

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
    public List<Record> find(String user, Long id, Long lon, Long lat, Long timestampMin, Long timestampMax) {
        List<Filter> filters = new ArrayList<>();
        List<Record> records = new ArrayList<>();

        if (user != null) {
            Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
            filters.add(userFilter);
        }
        if (id != null) {
            Filter idFilter = new FilterPredicate("id", FilterOperator.EQUAL, id);
            filters.add(idFilter);
        }
        if (lon != null) {
            Filter lonFilter = new FilterPredicate("lon", FilterOperator.EQUAL, lon);
            filters.add(lonFilter);
        }
        if (lat != null) {
            Filter latFilter = new FilterPredicate("lat", FilterOperator.EQUAL, lat);
            filters.add(latFilter);
        }
        if (timestampMin != null && timestampMax != null) {
            Filter minTimeFilter = new FilterPredicate("timestamp", FilterOperator.GREATER_THAN, timestampMin);
            Filter maxTimeFilter = new FilterPredicate("timestamp", FilterOperator.LESS_THAN, timestampMax);
            filters.add(minTimeFilter);
            filters.add(maxTimeFilter);
        }

        Filter mainFilter = null;
        if (filters.size() > 1) {
            mainFilter = new CompositeFilter(Query.CompositeFilterOperator.AND, filters);
        }
        else if (filters.size() == 1){
            mainFilter = filters.get(0);
        }

        Query query = new Query("Record").setFilter(mainFilter);
        List<com.google.appengine.api.datastore.Entity> results = DATASTORE_SERVICE.prepare(query).asList(FetchOptions.Builder.withDefaults());

        for (com.google.appengine.api.datastore.Entity res : results) {
            records.add(RecordMapper.entityToRecord(res));
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
                .setFilter(StructuredQuery.PropertyFilter.eq("id", id))
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
