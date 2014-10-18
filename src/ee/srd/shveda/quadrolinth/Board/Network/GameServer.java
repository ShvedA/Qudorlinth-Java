package ee.srd.shveda.quadrolinth.Board.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 23.09.13
 * Time: 16:21
 */
public class GameServer {
	Server server;
	private int nrOfPlayers;
	private boolean started = false;
    private boolean stopped = false;


	public GameServer() throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new GameConnection();
			}
		};
		Network.register(server);

		server.addListener(new Listener() {
			public void received(Connection c, Object object) {
				GameConnection connection = (GameConnection) c;
				if (object instanceof Network.RegisterName) {
					if (started) return;
					if (connection.name != null) return;
					String name = ((Network.RegisterName) object).name;
					if (name == null) return;
					name = name.trim();
					if (name.length() == 0) return;
					connection.name = name;

					Network.ChatMessage chatMessage = new Network.ChatMessage();
					chatMessage.text = name + " connected.";
					server.sendToAllExceptTCP(connection.getID(), chatMessage);

					updateNames();
					return;
				}
				if (object instanceof Network.ChatMessage) {
					// Ignore the object if a client tries to chat before registering a name.
					if (connection.name == null) return;
					Network.ChatMessage chatMessage = (Network.ChatMessage) object;
					// Ignore the object if the chat message is invalid.
					String message = chatMessage.text;
					if (message == null) return;
					message = message.trim();
					if (message.length() == 0) return;
					if (message.equals("start")) {
						if (started) {
							chatMessage.text = "Game has already started";
							server.sendToTCP(connection.getID(), chatMessage);
							return;
						}
						started = true;
						chatMessage.text = "New game has started";
						server.sendToAllTCP(chatMessage);
					} else {
						// Prepend the connection's name and send to everyone.
						chatMessage.text = connection.name + ": " + message;
						server.sendToAllTCP(chatMessage);
					}
					return;
				}
				if (object instanceof Boolean) {
					Network.ChatMessage chatMessage = (Network.ChatMessage) object;
					if (started) {
						chatMessage.text = "Game has already started";
						server.sendToTCP(connection.getID(), chatMessage);
						return;
					}
					started = true;
					chatMessage.text = "New game has started";
					server.sendToAllTCP(chatMessage);
					return;
				}
			}

			public void disconnected(Connection c) {
				GameConnection connection = (GameConnection) c;
				if (connection.name != null) {
					Network.ChatMessage chatMessage = new Network.ChatMessage();
					chatMessage.text = connection.name + " disconnected.";
                    pause(c);
					server.sendToAllTCP(chatMessage);
					updateNames();
				}
			}

            public void pause(Connection c) {
                GameConnection connection = (GameConnection) c;
                Network.ChatMessage chatMessage = new Network.ChatMessage();
                chatMessage.text = "Game has been paused due to disconection.";
                server.sendToAllTCP(chatMessage);
                stopped = true;
            }
		});
		server.bind(Network.port);
		server.start();

		// Open a window to provide an easy way to stop the server.
		JFrame frame = new JFrame("Game Server");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent evt) {
				server.stop();
			}
		});
		frame.getContentPane().add(new JLabel("Close to stop the game server."));
		frame.setSize(320, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void updateNames() {
		// Collect the names for each connection.
		Connection[] connections = server.getConnections();
		ArrayList names = new ArrayList(connections.length);
		for (int i = connections.length - 1; i >= 0; i--) {
			GameConnection connection = (GameConnection) connections[i];
			names.add(connection.name);
		}
		// Send the names to everyone.
		Network.UpdateNames updateNames = new Network.UpdateNames();
		updateNames.names = (String[]) names.toArray(new String[names.size()]);
		server.sendToAllTCP(updateNames);
	}

	// This holds per connection state.
	static class GameConnection extends Connection {
		public String name;
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameServer();

		/*Server server = new Server();
		Kryo kryo = server.getKryo();
		kryo.register(ConnectionRequest.class);
		kryo.register(ConnectionResponse.class);
		server.start();
		server.bind(54555, 54777);


		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof ConnectionRequest) {
					ConnectionRequest request = (ConnectionRequest) object;

					System.out.println(request.text);

					ConnectionResponse response = new ConnectionResponse();
					response.text = "Thanks!";
					connection.sendTCP(response);
				}
			}
		});*/
	}


}
