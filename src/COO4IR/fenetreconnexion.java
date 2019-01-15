package COO4IR;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;


public class fenetreconnexion implements ActionListener {
  
		boolean etablissementconnexion=false;
		JFrame converterFrame;
	    JPanel converterPanel;
	    JLabel login;
	    JLabel mdp;
	    JLabel pseudo;
	    JTextField tlogin;
	    JTextField tmdp;
	    JTextField tpseudo;
	    JButton seconnecter;
	    database bdd;
	
	    
	    public String getTlogin() {
			return tlogin.getText();
		}


		public String getTmdp() {
			return tmdp.getText();
		}

		

		public String getTpseudo() {
			return tpseudo.getText();
		}


		public fenetreconnexion(database bdd) {
	    
	    	this.bdd=bdd;
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
	        converterFrame = new JFrame("Connexion");
	        converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        converterFrame.setSize(new Dimension(400, 230));
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
	        
	        tlogin = new JTextField(1);
	        tmdp = new JTextField(1);
	        tpseudo = new JTextField(1);
	        login = new JLabel("Entrez votre login : ",SwingConstants.CENTER);
	        mdp = new JLabel("Entrez votre mdp : ",SwingConstants.CENTER);
	        pseudo = new JLabel("Entrez votre pseudo : ",SwingConstants.CENTER);
	       
	        seconnecter = new JButton("Seconnecter");
	        
	       
	        //Listen to events from the Convert button.
	        seconnecter.addActionListener(this);
	        //Add the widgets to the container.
	        converterPanel.add(login);
	        converterPanel.add(tlogin);
	        converterPanel.add(mdp);
	        converterPanel.add(tmdp);
	        converterPanel.add(pseudo);
	        converterPanel.add(tpseudo);
	        converterPanel.add(seconnecter);
	    }
	 /*   public void actionPerformed(KeyListener event) {
	    	event.keyPressed(
	    	
	    }*/

	    public void actionPerformed(ActionEvent event) {
	    	
	     String champlogin = tlogin.getText();
	     String champmdp = tmdp.getText();
	     String champpseudo = pseudo.getText();
	     try {
			verifierchamps(champlogin,champmdp,champpseudo,bdd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     
	       }


	private void verifierchamps(String champlogin, String champmdp, String champpseudo,database bdd) throws SQLException {
		// Verification par la BDD des identifiants
		int retour=bdd.connexionbdd(champlogin, champmdp);
	     if(retour==1) {
	    	 System.out.println("Vous etes bien identifies");
	    	 etablissementconnexion=true;
	    	 converterFrame.dispose();
	     }
	     if(retour==2) {
	    	 System.out.println("premiereconnexionreussie");
	    	 etablissementconnexion=true;
	    	 converterFrame.dispose();
	     }
	     else {
	    	 
	    	 tlogin.setText("");
	    	 tmdp.setText("");
	     }
		
	}


	public boolean isEtablissementconnexion() {
		return etablissementconnexion;
	}
	       

//Message recu
		
	
}

