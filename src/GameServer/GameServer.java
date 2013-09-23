package GameServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 23.09.13
 * Time: 16:21
 */
public class GameServer {

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		Kryo kryo = server.getKryo();
		kryo.register(ConnectionRequest.class);
		kryo.register(ConnectionResponse.class);
		server.start();
		server.bind(54555, 54777);


		server.addListener(new Listener() {
			public void received(Connection connection, Object object){
				if(object instanceof ConnectionRequest) {
					ConnectionRequest request = (ConnectionRequest)object;

					System.out.println(request.text);

					ConnectionResponse response = new ConnectionResponse();
					response.text = "Thanks!";
					connection.sendTCP(response);
				}
			}
		});
	}


}
