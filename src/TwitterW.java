import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import twitter4j.Status;
import twitter4j.TwitterException;

public class TwitterW extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -848100052123073588L;

	private final static String J_BUTTON_1_TEXT = "Update";
	private final static int MAX_STATUS_SIZE = 140;
	private final JButton jButton1 = new JButton(J_BUTTON_1_TEXT);
	private final JLabel jLabel1 = new JLabel();
	private final JTextArea jTextField1 = new JTextArea();
	private final JList<Status> jList1 = new JList<>();
	private final JScrollPane jScrollPane2 = new JScrollPane(jList1);

	private final RESTfulClient client;

	public void initTimer() {
		Timer t = new Timer("Twitter Updater`", false);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateValues();
			}
		}, 1500, 75000);
	}

	public void updateValues() {
		try {
			List<Status> statuses = client.getFriendTimeLine();

			Status[] statusesArray = new Status[statuses.size()];
			statusesArray = statuses.toArray(statusesArray);
			
			jList1.setListData(statusesArray);
			jList1.repaint();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void initPosition() {
		JPanel pane = new JPanel();
		setContentPane(pane);
		jList1.setCellRenderer(new Item(client));

		try {
			jLabel1.setIcon(new ImageIcon(client.getUserImageUrl()));
		} catch (IllegalStateException | TwitterException | IOException e) {
			e.printStackTrace();
		}

		pane.setLayout(new BorderLayout());
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints constraints =new GridBagConstraints();

		pane.add(bottom, BorderLayout.PAGE_END);

		pane.add(jScrollPane2);
		// pane.add(jScrollPane2, BorderLayout.CENTER);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;

		bottom.add(jLabel1,constraints);
		
		constraints.gridx = 1;
		constraints.weightx = 1;
		bottom.add(jTextField1,constraints);

		constraints.gridx = 2;
		constraints.weightx = 0;
		bottom.add(jButton1,constraints);
		jTextField1.setLineWrap(true);
	}
	
	private void displayIsValid(boolean isValid) {
		jButton1.setEnabled(isValid);
		jTextField1.setForeground(isValid ? Color.BLACK : Color.RED);
	}

	public void initActions() {
		jTextField1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				displayIsValid(jTextField1.getText().length() <= MAX_STATUS_SIZE);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				displayIsValid(jTextField1.getText().length() <= MAX_STATUS_SIZE);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				displayIsValid(jTextField1.getText().length() <= MAX_STATUS_SIZE);
			}
		});
		
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String status = jTextField1.getText();
					client.updateStatus(status);
					updateValues();
				} catch (TwitterException ex) {
					System.out.println("erreur " + ex.getStatusCode());
				}
			}
		});
	}

	public TwitterW(RESTfulClient client) {
		this.client = client;
		updateValues();
		initPosition();
		initActions();
		initTimer();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
	}
}
