package tlc.tracking.impl;

import tlc.tracking.Record;
import tlc.tracking.StoreService;

import java.util.List;

public class GoogleDataStoreService implements StoreService<Record> {

    @Override
    public void insert(Record o) {

    }

    @Override
    public List<Record> findBetweenRect(int a, int b, int x, int y) {
        return null;
    }

    @Override
    public void delete(Record o) {

    }
}
