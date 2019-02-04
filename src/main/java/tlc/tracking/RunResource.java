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
import java.util.Objects;

public class RunResource extends ServerResource {

    final StoreService service = new GoogleDataStoreService();

    @Post("json")
    public void bulkAdd(RecordList toAdd) {
        service.insert(toAdd);
    }

    @Get("json")
    public RecordList search() {
        // Read and print URL parameters
        Form form = getRequest().getResourceRef().getQueryAsForm();
        for (Parameter parameter : form) {
            System.out.print("parameter " + parameter.getName());
            System.out.println(" -> " + parameter.getValue());
        }

        // Build a dummy result
        RecordList res = new RecordList();
        //        res.add(new Record(5, 43.8, 12.6, "lea", 154789));
        //        res.add(new Record(5, 43.8, 12.6, "john", 154789));

        //@FIXME You must query Google Datastore to retrieve the records instead of sending dummy results
        //@FIXME Don't forget to apply potential filters got from the URL parameters

        return res;
    }

    @Delete("json")
    public void bulkDelete() {
        final String[] run_ids = getRequest().getAttributes().get("list").toString().split(",");

        Arrays.stream(run_ids)
                .map(it -> {
                    try {
                        return Long.valueOf(it);
                    } catch (Exception e) {
                        // TODO Si un id n'est pas bon on fais quoi ?
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(this.service::findByRunId)
                .flatMap(Collection::stream)
                .map(Record::getId)
                .forEach(this.service::delete);

    }

}