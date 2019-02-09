package tlc.tracking;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import tlc.tracking.impl.GoogleDataStoreService;

import java.util.Arrays;
import java.util.Collection;

public class RunResource extends ServerResource {

    final StoreService service = new GoogleDataStoreService();

    @Post("json")
    public void bulkAdd(RecordList toAdd) {
        service.insert(toAdd);
    }

    @Get("json")
    public RecordList search() {
        String user = null;
        Long id = null;
        Double lon = null;
        Double lat = null;
        Long timestampMin = null;
        Long timestampMax = null;


        // Read URL parameters
        Form form = getRequest().getResourceRef().getQueryAsForm();
        for (Parameter parameter : form) {
            switch (parameter.getName()) {
                case "user":
                    user = parameter.getValue();
                    break;
                case "loc":
                    String location = parameter.getValue();
                    if (location != null) {
                        lon = Double.valueOf(location.split(",")[0]);
                        lat = Double.valueOf(location.split(",")[1]);
                    }
                    break;
                case "timestamp":
                    String timestamp = parameter.getValue();
                    if (timestamp != null) {
                        timestampMin = Long.valueOf(timestamp.split(",")[0]);
                        timestampMax = Long.valueOf(timestamp.split(",")[1]);
                    }
                    break;
                case "id":
                    id = Long.valueOf(parameter.getValue());
                    break;
            }
        }

        return service.find(user, id, lon, lat, timestampMin, timestampMax);
    }

    @Delete("json")
    public void bulkDelete() {
        final String[] run_ids = getRequest().getAttributes().get("list").toString().split(",");

        Arrays.stream(run_ids)
                .map(Long::valueOf)
                .map(this.service::findById)
                .flatMap(Collection::stream)
                .map(Record::getId)
                .forEach(this.service::delete);

    }

}