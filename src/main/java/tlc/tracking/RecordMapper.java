package tlc.tracking;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

public class RecordMapper {

    public static Entity toEntity(Key key, Record r) {
        return Entity.newBuilder(key)
                .set("user", r.getUser())
                .set("lat", r.getLat())
                .set("lon", r.getLon())
                .set("timestamp", r.getTimestamp())
                .set("runId", r.getRunId())
                .build();
    }
}
