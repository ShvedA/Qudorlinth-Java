package ee.srd.shveda.quadrolinth.Board.Network;

import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 23.09.13
 * Time: 16:33
 */
public class GameClient {

	ChatFrame chatFrame;
	Client client;
	String name;

	public GameClient () {
		client = new Client();
		client.start();

		Network.register(client);

		client.addListener(new Listener() {
			public void connected (Connection connection) {
				Network.RegisterName registerName = new Network.RegisterName();
				registerName.name = name;
				client.sendTCP(registerName);
			}

			public void received (Connection connection, Object object) {
				if (object instanceof Network.UpdateNames) {
					Network.UpdateNames updateNames = (Network.UpdateNames)object;
					chatFrame.setNames(updateNames.names);
					return;
				}

				if (object instanceof Network.ChatMessage) {
					Network.ChatMessage chatMessage = (Network.ChatMessage)object;
					chatFrame.addMessage(chatMessage.text);
					return;
				}
			}

			public void disconnected (Connection connection) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						chatFrame.dispose();
					}
				});
			}
		});

		String input = (String) JOptionPane.showInputDialog(null, "Host:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE,
				null, null, "localhost");
		if (input == null || input.trim().length() == 0) System.exit(1);
		final String host = input.trim();

		input = (String)JOptionPane.showInputDialog(null, "Name:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE, null,
				null, "Name");
		if (input == null || input.trim().length() == 0) System.exit(1);
		name = input.trim();

		chatFrame = new ChatFrame(host);
		chatFrame.setSendListener(new Runnable() {
			public void run () {
				//can make it here.
						String input = chatFrame.getSendText();
				Network.ChatMessage chatMessage = new Network.ChatMessage();
				chatMessage.text = input;
				client.sendTCP(chatMessage);
			}
		});
		chatFrame.setCloseListener(new Runnable() {
			public void run () {
				client.stop();
			}
		});
		chatFrame.setVisible(true);

		new Thread("Connect") {
			public void run () {
				try {
					client.connect(5000, host, Network.port);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}.start();
	}

	static private class ChatFrame extends JFrame {
		CardLayout cardLayout;
		JProgressBar progressBar;
		JList messageList;
		JTextField sendText;
		JButton sendButton;
		JList nameList;

		public ChatFrame (String host) {
			super("Game Client");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setSize(640,400);
			setLocationRelativeTo(null);

			Container contentPane = getContentPane();
			cardLayout = new CardLayout();
			contentPane.setLayout(cardLayout);
			{
				JPanel panel = new JPanel(new BorderLayout());
				contentPane.add(panel, "progress");
				panel.add(new JLabel("Connecting to " + host + "..."));
				{
					panel.add(progressBar = new JProgressBar(), BorderLayout.SOUTH);
					progressBar.setIndeterminate(true);
				}
			}
			{
				JPanel panel = new JPanel(new BorderLayout());
				contentPane.add(panel, "chat");
				{
					JPanel topPanel = new JPanel(new GridLayout(1, 2));
					panel.add(topPanel);
					{
						topPanel.add(new JScrollPane(messageList = new JList()));
						messageList.setModel(new DefaultListModel());
					}
					{
						topPanel.add(new JScrollPane(nameList = new JList()));
						nameList.setModel(new DefaultListModel());
					}
					DefaultListSelectionModel disableSelections = new DefaultListSelectionModel() {
						public void setSelectionInterval (int index0, int index1) {
						}
					};
					messageList.setSelectionModel(disableSelections);
					nameList.setSelectionModel(disableSelections);
				}
				{
					JPanel bottomPanel = new JPanel(new GridBagLayout());
					panel.add(bottomPanel, BorderLayout.SOUTH);
					bottomPanel.add(sendText = new JTextField(), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					bottomPanel.add(sendButton = new JButton("Send"), new GridBagConstraints(1, 0, 1, 1, 0, 0,
							GridBagConstraints.CENTER, 0, new Insets(0, 0, 0, 0), 0, 0));
				}
			}

			sendText.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					sendButton.doClick();
				}
			});
		}

		public void setSendListener (final Runnable listener) {
			sendButton.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent evt) {
					if (getSendText().length() == 0) return;
					listener.run();
					sendText.setText("");
					sendText.requestFocus();
				}
			});
		}

		public void setCloseListener (final Runnable listener) {
			addWindowListener(new WindowAdapter() {
				public void windowClosed (WindowEvent evt) {
					listener.run();
				}

				public void windowActivated (WindowEvent evt) {
					sendText.requestFocus();
				}
			});
		}

		public String getSendText () {
			return sendText.getText().trim();
		}

		public void setNames (final String[] names) {
			// This listener is run on the client's update thread, which was started by client.start().
			// We must be careful to only interact with Swing components on the Swing event thread.
			EventQueue.invokeLater(new Runnable() {
				public void run () {
					cardLayout.show(getContentPane(), "chat");
					DefaultListModel model = (DefaultListModel)nameList.getModel();
					model.removeAllElements();
					for (String name : names)
						model.addElement(name);
				}
			});
		}

		public void addMessage (final String message) {
			EventQueue.invokeLater(new Runnable() {
				public void run () {
					DefaultListModel model = (DefaultListModel)messageList.getModel();
					model.addElement(message);
					messageList.ensureIndexIsVisible(model.size() - 1);
				}
			});
		}
	}

	public static void main(String[] args) throws IOException {

		Log.set(Log.LEVEL_DEBUG);
		new GameClient();

		/*Client client = new Client();
		Kryo kryo = client.getKryo();
		kryo.register(ConnectionRequest.class);
		kryo.register(ConnectionResponse.class);
		client.start();
		client.connect(5000, "localhost", 54555, 54777);


		ConnectionRequest request = new ConnectionRequest();
		request.text = "Here is the request!";
		client.sendTCP(request);


		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof ConnectionRequest) {
					ConnectionResponse response = (ConnectionResponse) object;
					System.out.println(response.text);
				}
			}
		});*/
	}

}
