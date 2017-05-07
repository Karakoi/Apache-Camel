package component.opc;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Executors;

public class OPCWrite2 {

	static ConnectionInformation ci = new ConnectionInformation();
	static Server server;
	public static boolean temp = true;
	static Group group;

	public void init(String hostName, String userName, String userPassword, String clsid) {

		// create connection information
		ci.setHost(hostName);
		ci.setDomain("");
		ci.setUser(userName);
		ci.setPassword(userPassword);
		ci.setClsid(clsid);
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

	public synchronized void doWrite(String itemId, String number, int connTimeDelay)
			throws InterruptedException, IllegalArgumentException, UnknownHostException, NotConnectedException,
			JIException, DuplicateGroupException, AddFailedException, UnknownGroupException {
		// add sync access, poll every 500 ms
		final AccessBase access = new SyncAccess(server, 1000);
		access.addItem(itemId, new DataCallback() {
			public void changed(Item item, ItemState state) {
				// also dump value
				try {
					if (state.getValue().getType() == JIVariant.VT_UI4) {
						System.out.println(
								"<<< " + state + " /value = " + state.getValue().getObjectAsUnsigned().getValue());
					} else {
						System.out.println("<<< " + state + " /value = " + state.getValue().getObject());
					}

				} catch (JIException e) {
					e.printStackTrace();
				}
			}
		});

		// if (temp) {
		// // Add a new group
		// server.addGroup("test");
		// temp = false;
		// }
		// final Group group = server.findGroup("test");

		final Group group = server.addGroup(generateString());
		// Add a new item to the group
		final Item item = group.addItem(itemId);

		access.bind();

		final JIVariant value = new JIVariant(number);
		item.write(value);
		Thread.sleep((connTimeDelay + 1) * 1000);
		access.unbind();
	}

	public static String generateString() {
		String characters = "abcdefg";
		Random rng = new Random();
		char[] text = new char[6];
		for (int i = 0; i < 6; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

}
