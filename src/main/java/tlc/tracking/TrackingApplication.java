package tlc.tracking;

import org.restlet.Restlet;
import org.restlet.Application;
import org.restlet.routing.Router;

public class TrackingApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/api/run", RunResource.class);
		router.attach("/api/run/{list}", RunResource.class);
		return router;
	}
}
