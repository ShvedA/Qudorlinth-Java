package ee.srd.shveda.quadrolinth.Board.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 07.10.13
 * Time: 9:34
 */
public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(RegisterName.class);
		kryo.register(String[].class);
		kryo.register(UpdateNames.class);
		kryo.register(ChatMessage.class);
		kryo.register(Boolean.class);
	}

	static public class RegisterName {
		public String name;
	}

	static public class UpdateNames {
		public String[] names;
	}

	static public class ChatMessage {
		public String text;
	}

}
