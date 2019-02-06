package tlc.tracking;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import javax.annotation.Nullable;

public class RecordMapper {

    public static Entity toEntity(Key key, Record r) {
        return Entity.newBuilder(key)
                .set("id", r.getId())
                .set("user", r.getUser())
                .set("lat", r.getLat())
                .set("lon", r.getLon())
                .set("timestamp", r.getTimestamp())
                .build();
    }

    /**
     * Convert an {@link Entity} to {@link Record} or return null if e is null
     *
     * @param e {@link Entity} or null
     * @return {@link Record} or null
     */
    @Nullable
    public static Record toRecord(@Nullable final Entity e) {

        return e == null ? null : Record.builder()
                .id(e.getLong("id"))
                .user(e.getString("user"))
                .lat(e.getDouble("lat"))
                .lon(e.getDouble("lon"))
                .timestamp(e.getLong("timestamp"))
                .build();
    }

    /**
     * Convert an appengine Entity to a Record
     * @param entity appengine Entity
     * @return the generated Record or null if the Entity is null
     */
    public static Record entityToRecord(com.google.appengine.api.datastore.Entity entity) {

        return entity == null ? null : Record.builder()
                .id((Long) entity.getProperty("id"))
                .user((String) entity.getProperty("user"))
                .lat((Double) entity.getProperty("lat"))
                .lon((Double) entity.getProperty("lon"))
                .timestamp((Long) entity.getProperty("timestamp"))
                .build();
    }
}
