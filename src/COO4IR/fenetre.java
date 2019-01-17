package COO4IR;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;


public class fenetre implements WindowListener,ActionListener,KeyListener {
	    JFrame converterFrame;
	    JPanel converterPanel;
	    JPanel converterPanel2;
	    JLabel discussion;
	    JTextField message;
	    JButton envoyer;
	    private InetAddress source;
	    

		private InetAddress destination;
	    private Socket socketsource ;
	    private String pseudodestinataire;
	    private utilisateur user;
		int TEXTE=0;
		int DOCUMENT=1;
		int IMAGE=2;
		int PSEUDO=3;
		int CONNEXION=4;
		int DECONNEXION=5;
		static int id=0;
	    
	    public fenetre(utilisateur user,InetAddress source,InetAddress destination,Socket socketsource,String pseudodestinataire2) {
	    	this.user=user;
	    	this.source=source;
	    	this.destination=destination;
	    	this.socketsource=socketsource;
	    	this.pseudodestinataire=pseudodestinataire2;
	    	
	        String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	        try {
	        	UIManager.setLookAndFeel(lookAndFeel);
	        }
	        catch(ClassNotFoundException e){
	        	 System.err.println("Couldn't find class for specified look and feel:"
	                        + lookAndFeel);
	        } catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //Create and set up the window.
	        converterFrame = new JFrame(this.pseudodestinataire);
	        converterFrame.setSize(new Dimension(400, 700));
	        converterFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        converterFrame.addWindowListener(this);
	        converterFrame.addKeyListener(this);
	      //  converterFrame.setBackground(Color.BLACK);

	        //Create and set up the panel.
	        converterPanel = new JPanel();
	      //  converterPanel.setBackground(Color.BLACK);
	        converterPanel2 = new JPanel();
	      //  converterPanel2.setBackground(Color.BLACK);
	        converterPanel.setBorder(BorderFactory.createEmptyBorder(
	                30, //top
	                30, //left
	                10, //bottom
	                30) //right
	                );

	        converterPanel.setLayout(new BoxLayout(converterPanel,BoxLayout.PAGE_AXIS));
	    
	        converterPanel2.setLayout(new BoxLayout(converterPanel2,BoxLayout.LINE_AXIS));
	        //Add the widgets.
	        addWidgets();

	        //Set the default button.
	       // converterFrame.getRootPane().setDefaultButton(envoyer);

	        //Add the panel to the window.
	        JScrollPane scroll=new JScrollPane(converterPanel);
	        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        scroll.setBounds(30, 30, 330, 600);
	        JPanel contentPane = new JPanel(null);
	        contentPane.setPreferredSize(new Dimension(330, 600));
	        contentPane.add(scroll);
	        converterFrame.getContentPane().add(contentPane, BorderLayout.NORTH);
	        converterFrame.getContentPane().add(converterPanel2, BorderLayout.SOUTH);
	       
	        
	        
	        ArrayList<message> listemessage = user.getBdd().getMessages(this.destination.toString(),user.getAdresseip());
	        int i=0;
	        while(i<listemessage.size()) {
	        	
	        	affichermessageancien(listemessage.get(i));
	        	i++;
	        }
	        //Display the window.
	       // converterFrame.pack();
	        converterFrame.setVisible(true);
	    }

	    /**
	     * Create and add the widgets.
	     */
	    private void addWidgets() {
	        //Create widgets.
	        
	        message = new JTextField(2);
	        message.setText("");
	        envoyer = new JButton("Envoyer");
	        
	       
	        //Listen to events from the Convert button.
	        envoyer.addActionListener(this);
	        //Add the widgets to the container.
	        converterPanel2.add(message);
	        converterPanel2.add(envoyer);
	    }
	 /*   public void actionPerformed(KeyListener event) {
	    	event.keyPressed(
	    	
	    }*/

	    public void actionPerformed(ActionEvent event) {
	    	
	    	envoyer();
	       
	       
	      
	    }
	    public void windowClosing(WindowEvent event) {
	    	try {
				socketsource.close();
		        converterFrame.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

//Message recu
		public void affichermessage(message message) {
			
			   String messageaffiche= message.getContenu();
		    	   String date=message.getDate();
			       discussion=new JLabel("<html><font Color=black>"+ date +"</fond><font Color=red>"+ messageaffiche  +"</font></html>",SwingConstants.CENTER);
			       discussion.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			       converterPanel.add(discussion);
			       converterFrame.setVisible(true);
		       
		}
		
		public void affichermessageancien(message message) {
			
			   String messageaffiche= message.getContenu();
			   String date=message.getDate();
			   if(message.getType()==0) {
				   
				   discussion=new JLabel("<html><font Color=black>"+ date +"</fond><font Color=blue> "+ messageaffiche  +"</font></html>",SwingConstants.CENTER);
			   }
			   else {
				   
				   discussion=new JLabel("<html><font Color=black>"+ date +"</fond><font Color=red> "+ messageaffiche  +"</font></html>",SwingConstants.CENTER);
			      
			   }
			   discussion.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		       converterPanel.add(discussion);
		       converterFrame.setVisible(true);   
		       
		}
		private void envoyer() {
			// TODO Auto-generated method stub
			String messageaffiche= message.getText();
		       message.setText(null);
		       message messageenvoye=new message(TEXTE,id,messageaffiche,null);
		       id++; 
		       int retour=user.envoyermessage(messageenvoye,source,destination,socketsource);
		       if (retour==1) {
		    	   Date date=new Date();
			       SimpleDateFormat formater = new SimpleDateFormat("h:mm");
			       String datee=(formater.format(date));
			       discussion=new JLabel("<html><font Color=black>"+ datee +"</font><font Color=blue>"+ messageaffiche +"</font></html>",SwingConstants.CENTER);
			       discussion.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			       converterPanel.add(discussion);
			       converterFrame.setVisible(true);
		       }
		       else {
		    	   Date date=new Date();
			       SimpleDateFormat formater = new SimpleDateFormat("h:mm");
			       String datee=(formater.format(date));
		    	   discussion=new JLabel("<html><font Color=orange>"+ datee +"</font><font Color=red>"+ " Votre ami n'est plus là ... "+"</font></html>",SwingConstants.CENTER);
			       discussion.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			       converterPanel.add(discussion);
			       converterFrame.setVisible(true);
			       
		       }
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void setPseudodestinataire(String pseudodestinataire2) {
			this.pseudodestinataire = pseudodestinataire2;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				envoyer();
			}
		}

		

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		
}

