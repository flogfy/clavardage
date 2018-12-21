package COO4IR;

import java.net.InetAddress;
import java.util.EventListener;

public interface NewMessageListener extends EventListener {
	void aMessageHasBeenReceived(InetAddress address);
}
