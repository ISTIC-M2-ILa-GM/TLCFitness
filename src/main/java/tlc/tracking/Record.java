package tlc.tracking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {

    private Long id;
    private Long runId;
    private double lat;
    private double lon;
    private String user;
    private long timestamp;
}