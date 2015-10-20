import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.FontUIResource;

import twitter4j.Status;

public class Item extends JPanel implements ListCellRenderer<Status> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2317092466250783119L;
	JLabel jTextPane1 = new JLabel();
	JLabel jLabel1 = new JLabel();
	JLabel jImage = new JLabel();
	private final RESTfulClient client;
	
	public Item(RESTfulClient client) {
		this.client = client;
		setLayout(new GridBagLayout());
		GridBagConstraints constraints =new GridBagConstraints();
		jLabel1.setFont(new FontUIResource(jLabel1.getFont().getFontName(), Font.BOLD, jLabel1.getFont().getSize() + 3));
		
		constraints.anchor = GridBagConstraints.WEST;

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		add(jImage, constraints);
		
		constraints.weightx = 5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		add(jTextPane1, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		add(jLabel1, constraints);
		jLabel1.setBackground(Color.WHITE);
		jTextPane1.setBackground(Color.WHITE);
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Status> list,
			Status value, int index, boolean isSelected, boolean cellHasFocus) {
		jLabel1.setText("<html><B>" + value.getUser().getScreenName() + "</B></html>");
		jTextPane1.setText(value.getText());
		jImage.setIcon(new ImageIcon(client.getImage(value.getUser().getId())));
		return this;
	}
}
