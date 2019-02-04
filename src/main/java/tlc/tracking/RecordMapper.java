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
}
