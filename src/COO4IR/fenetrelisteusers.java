package COO4IR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class fenetrelisteusers implements ActionListener{

	boolean etablissementconnexion=false;
	JFrame converterFrame;
    JPanel converterPanel;
    JLabel login;
    ArrayList<JButton> listeboutons;
    ArrayList<String> pseudoconnectes;
    ArrayList<InetAddress>adressesconnectes;
    private utilisateur user;
    
   
	public fenetrelisteusers(utilisateur user,ArrayList<String> pseudoconnectes,ArrayList<InetAddress>adressesconnectes)
	{
		this.pseudoconnectes=pseudoconnectes;
		this.adressesconnectes=adressesconnectes;
    	this.user=user;
    	
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
        converterFrame = new JFrame("Utilisateurs disponibles");
        converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        converterFrame.setSize(new Dimension(400, 700));
        converterFrame.setBackground(Color.WHITE);

        //Create and set up the panel.
        converterPanel = new JPanel();
        converterPanel.setBackground(Color.WHITE);
        
        converterPanel.setBorder(BorderFactory.createEmptyBorder(
                30, //top
                30, //left
                10, //bottom
                30) //right
                );

        converterPanel.setLayout(new BoxLayout(converterPanel,BoxLayout.PAGE_AXIS));
     
        //Add the widgets.
        addWidgets();

        //Set the default button.
       // converterFrame.getRootPane().setDefaultButton(envoyer);

        //Add the panel to the window.
        converterFrame.getContentPane().add(converterPanel, BorderLayout.NORTH);
        //Display the window.
       // converterFrame.pack();
        converterFrame.setVisible(true);
    }

    /**
     * Create and add the widgets.
     */
    private void addWidgets() {
        //Create widgets.
        System.out.println(pseudoconnectes.size());
        for(int i=0;i<pseudoconnectes.size();i++) {
    

        	if(!(pseudoconnectes.get(i).equals(user.getPseudo()))) {
        		
        	
        	JButton bouton = new JButton(pseudoconnectes.get(i));
        //Listen to events from the Convert button.
        	bouton.addActionListener(this);
        //Add the widgets to the container.
       
        	converterPanel.add(bouton);
        }
       }	
    }


    public void actionPerformed(ActionEvent event) 
    {
    	//Ouvrir une conversation c'est a dire ouvrir une fenetre de discussion avec les infos necessaire en parametre
    	JButton bout=(JButton) event.getSource();
    	
    	for(int i=0;i<pseudoconnectes.size();i++) {
    		if(bout.getText()==pseudoconnectes.get(i)) {
    			Socket socketsource=new Socket();
    			InetAddress adressedestination;
				try {
					adressedestination = adressesconnectes.get(i);
					socketsource.connect(new InetSocketAddress(adressedestination,8200));
					Thread t = new MyThread(socketsource,user,pseudoconnectes.get(i));
	    			t.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    			
    		}
    	}
    }
    
    
}
