import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class TwitterW extends JFrame {
	private final JButton jButton1 = new JButton("Update");
	private final JLabel jLabel1 = new JLabel();
	private final JTextField jTextField1 = new JTextField();
	private final JScrollPane jScrollPane2 = new JScrollPane();
	private final JList<Object> jList1 = new JList<>();
	private final JPanel jPanel = new JPanel();
	
	
	public TwitterW() {
		jLabel1.setSize(new Dimension(48,48));
		this.setContentPane(jPanel);
		jPanel.add(jButton1);
		jPanel.add(jLabel1);
		jPanel.add(jTextField1);
		jPanel.add(jScrollPane2);
		jPanel.add(jList1);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void update() {
		
	}
	
	public static void main(String[] args) {
		new TwitterW().setVisible(true);
	}
}
