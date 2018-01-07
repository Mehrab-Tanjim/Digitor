package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FeedBack extends JDialog {
	private JTextField emailadd = new JTextField(20);
	private JTextField subject = new JTextField(20);
	private JTextArea feedback = new JTextArea();
	private JPanel pane = new JPanel();
	private JButton send = new JButton("Send");
	private JButton cancel = new JButton("Cancel");
	private String[] _recipient = {"mishkat076@gmail.com","feedbackstous@gmail.com"};
	private String _sender = null;
	private String _subject = null;
	private String _body = null;

	public FeedBack(final JFrame frame){
		super(frame,"Give Us Your FeedBack",false);
		setSize(400,600);
		setLocationRelativeTo(frame);
		setLayout(new GridBagLayout());
		setResizable(false);
		GridBagConstraints gc=new GridBagConstraints();
		
		
		Dimension dim=new Dimension();
		dim.height=300;
		dim.width=220;
		pane.setPreferredSize(dim);
		pane.setLayout(new BorderLayout());
		pane.add(new JScrollPane(feedback),BorderLayout.CENTER);
		
		
		gc.gridy=0;
		gc.weightx=1;
		gc.weighty=.01;
		gc.fill=GridBagConstraints.NONE;
		
		gc.gridx=0;
		gc.anchor=GridBagConstraints.LINE_START;
		gc.insets=new Insets(0,10,0,0);
		add(new JLabel("Your Email Adress:"),gc);
		gc.gridx++;
		add(emailadd,gc);
		
		//////////////////////
		gc.gridy++;
		gc.gridx=0;
		gc.anchor=GridBagConstraints.LINE_START;
		gc.insets=new Insets(0,10,0,0);
		add(new JLabel("Subject:"),gc);
		gc.gridx++;
		add(subject,gc);
		
		////////////////////
		gc.gridy++;
		gc.gridx=0;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.insets=new Insets(0,10,0,0);
		add(new JLabel("Your FeedBack:"),gc);
		gc.gridx++;
		add(pane,gc);
		
		/////////////////
		gc.gridy++;
		gc.gridx=0;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.insets=new Insets(0,50,0,0);
		add(send,gc);
		gc.gridx++;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		gc.insets=new Insets(0,0,0,50);
		add(cancel,gc);
		
		
		send.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					_sender=emailadd.getText();
					_body=feedback.getText();
					_subject=subject.getText();	
					Thread t1=new Thread(new Runnable(){

						@Override
						public void run() {
							try{
								new Emailer(_sender,_recipient,_subject,_body);
								JOptionPane.showMessageDialog(frame, "Your message is successfully sent. "
										+ "Thank you for your support",
										"", JOptionPane.PLAIN_MESSAGE);
								
							}
							catch(Exception e){
								JOptionPane.showMessageDialog(frame, e.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
							finally{
								setVisible(false);
							}
							
							
						}
						
					});
					
					t1.start();
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		
		cancel.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try{
					setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	}

	public class Emailer {

		public Emailer(String sender, String[] receiver, String subject,
				String body) {

			final String username = "feedbackstous@gmail.com";
			final String password = "allaboutfeedback";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
//				message.setRecipients(Message.RecipientType.TO,
//						InternetAddress.parse(receiver[0]));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(receiver[1]));
				message.setSubject(subject);
				message.setText(body + "\n" + sender);

				Transport.send(message);
				System.out.println("Done");

			} 

			catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
