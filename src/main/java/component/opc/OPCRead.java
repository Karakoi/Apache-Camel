package component.opc;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class OPCRead {

	static ConnectionInformation ci = new ConnectionInformation();
	static Server server;
	// static String itemId;
	static List<Object> data = new ArrayList<Object>();

	public void init(String hostName, String userName, String userPassword, String clsid) {

		// create connection information
		ci.setHost(hostName);
		ci.setDomain("");
		ci.setUser(userName);
		ci.setPassword(userPassword);
		ci.setClsid(clsid);
		// create a new server
		server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
	}

	public void connect() throws IllegalArgumentException, UnknownHostException, AlreadyConnectedException {
		try {
			// connect to server
			server.connect();

		} catch (final JIException e) {
			System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
		}
	}

	public List<Object> doRead(String itemId, int connTimeDelay) throws IllegalArgumentException, UnknownHostException,
			NotConnectedException, JIException, DuplicateGroupException, AddFailedException, InterruptedException {

		// add sync access, poll every 1000 ms
		final AccessBase access = new SyncAccess(server, 1000);
		access.addItem(itemId, new DataCallback() {
			public void changed(Item item, ItemState state) {
				System.out.println("||||||||||||||||" + state.getValue() + "||||||||||||||||");
				data.add(state.getValue());
			}
		});
		// start reading
		access.bind();
		// wait a little bit
		Thread.sleep((connTimeDelay + 1) * 1000);
		// stop reading
		access.unbind();
		return data;
	}

}
