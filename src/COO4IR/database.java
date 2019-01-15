package COO4IR;

//STEP 1. Import required packages
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;

public class database {

    // JDBC driver name and database URL
    //For embedded DB, we can use :
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
   // With conn = DriverManager.getConnection("jdbc:derby:testdb1;create=true");
    // Ne pas oublier de creer alors manuellement les tables mentionnées après au premier lancement
    //static final String DB_URL = "jdbc:mysql://localhost/clavardage?Unicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "lucas";
    static final String PASS = "radureau";

    //Daatabase connection
    static Connection conn = null;


    //Deux tables:


   // On stock tout les users a qui l'ont a parlé avec leur dernier pseudo en date
    
    /*
    
   

    */
    /*
    Correspondance entre IP et message
    CREATE TABLE Messages (
            IPDest VARCHAR(15),
            Message VARCHAR(500),
            Date DATETIME();
            FOREIGN KEY (IPDest) REFERENCES Users(IPDest)
     );
     */



    public database () throws SQLException{

        //STEP 1: Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //STEP 2: Open a connection
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true");
            //conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
      
    }
    
    public int connexionbdd(String loginentre, String mdpentre) throws SQLException {
    	String sql="SELECT login,password FROM Authentification";
    	//String sql="SELECT login,password FROM Authentification";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

        	 ResultSet rs= stm.executeQuery();
        	
        	 //System.out.println(loginentre);
        	// System.out.println(mdpentre);
        	 rs.next();
        	 
        		 String loginretourne=rs.getString("login");
            	 String mdpretourne=rs.getString("password");
            	 rs.close();
 	       		stm.close();
            	// System.out.println(loginretourne);
            	// System.out.println(mdpretourne);
            	 
            	  //////Si pas de login dans la table alors premiere connexion on cree tout
            	    
            		 
            	 
            	 //////Sinon on va verifier si les login et mdp entr�s sont les bons
            	 
            		 if(loginentre.equals(loginretourne)&&(mdpentre.equals(mdpretourne))) {
            			 return(1);
            		 }
            		 else {
            			 return(0);
            		 }
            	 
        	 }
        	
    
        catch(SQLException e) {
        	e.printStackTrace();
        	System.out.println("BDDVIDE");
	     	sql = "CREATE TABLE Historique (IPDest VARCHAR(15), IPDest VARCHAR(15),Message VARCHAR(500),Date DATE)";
	     	
	       try (Statement stm2 = conn.createStatement()) {
	       		stm2.executeUpdate(sql);

	   		} catch (SQLException o) {
	       		o.printStackTrace();
	   		}
		        
	   	     sql = "CREATE TABLE Authentification (login VARCHAR(15),password VARCHAR(15))";
	   	  try (Statement stm2 = conn.createStatement()) {
	     		stm2.executeUpdate(sql);

	 		} catch (SQLException x) {
	     		x.printStackTrace();
	 		}
	   	     System.out.println("BDD creee plus qu'a remplir");
	   	      sql = "INSERT INTO Authentification (login,password)\n" +
	           "VALUES (?,?)";
	           try (PreparedStatement stm2 = conn.prepareStatement(sql)) {

	       		stm2.setString(1, loginentre);
	       		stm2.setString(2, mdpentre);
	       		stm2.executeUpdate();
	       		stm2.close();
	       		return(2);
        	
	           }
        }
       
        
    }

    //Ajoute un message texte dans la DB en lien avec une IP
    public void addMessage(String contenu, InetAddress ipdistant, InetAddress ipsource, Timestamp time) {

       /* InetAddress iptemp = null;
        try {
            iptemp = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        addUser(iptemp, UserDatabase.getByAdr(iptemp).pseudo);
*/
        String sql = "INSERT INTO Historique (IPDest, IPsource, Message, Date)\n" +
                "VALUES (?,?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, ipdistant.toString());
            stm.setString(2, ipsource.toString());
            stm.setString(3, contenu );
            stm.setTimestamp(4,time);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //Retourne la liste des 10 derniers messages échangés avec une IP ( indépendamment du pseudo )
    public ArrayList<message> getMessages(String ip,InetAddress ipuser) {

        String sql = "SELECT * FROM Messages WHERE IPDest = ? OR (Ipsource = ? AND IPDest=?) ORDER BY Date DESC LIMIT 10";
        ResultSet rs=null;
        ArrayList<message> messageList = new ArrayList<message>();

        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            System.out.println(ip);
            stm.setString(1, ip );
            stm.setString(2, ip );
            stm.setString(3, ipuser.toString() );
            rs = stm.executeQuery();
           
            while(rs.next()){

                //Retrieve by column name
                String message = rs.getString("Message");
                Timestamp tmstp = rs.getTimestamp("Date");

               message mes = new message(0,0,message,null) ;
               mes.setTime(tmstp);
               messageList.add(mes);
        
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return messageList;
    }
    /*
    public boolean userExist(String ip, String pseudo) {
            List<User>  users = new ArrayList<User>();
            users = getUsers();
            for(User user : users){
                if(user.adr.getHostAddress().equalsIgnoreCase(ip)) {
                    //Change le pseudo si il n'est pas le meme que dans la table user
                    if(user.pseudo.equals(pseudo) == false) {
                        System.out.println("Le pseudo a changé, " + user.pseudo + " devient " + pseudo);
                        changePseudo(user.adr.getHostAddress(),pseudo);
                    }
                    return true;
                }
            }
            System.out.println("User n'existe pas ");
            return false;
    }

  public int changePseudo(String ip, String newPseudo){
        String sql = "UPDATE users\n" +
                "SET Pseudo = ?" +
                "WHERE  IPDest = ?";

        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            System.out.println(ip);
            stm.setString(1, newPseudo );
            stm.setString(2, ip );
            stm.executeUpdate();

            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return 0;

    }

    public int addUser(InetAddress adr, String pseudo){
        if(userExist(adr.getHostAddress(),pseudo) == true) return -1;

        System.out.println("Ajout de l'utilisateur " + pseudo+":"+adr);
        String sql = "INSERT INTO Users (IPDest, Pseudo)\n" +
                "VALUES (?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, adr.getHostAddress() );
            stm.setString(2, pseudo );
            stm.executeUpdate();

            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //Retourne la liste de tout les utilisateurs a qui l'ont a parlé
    public List<User> getUsers(){
        String sql = "SELECT * FROM Users";
        Statement stm = null;
        List<User> userList = new ArrayList<User>();

        try {
            stm = conn.prepareStatement(sql);
            ResultSet rs =stm.executeQuery(sql);
            while(rs.next()){
                //Retrieve by column name
                String  ip  = rs.getString("IPDest");
                String  pseudo  = rs.getString("Pseudo");
                User user = new User(pseudo, InetAddress.getByName(ip));
                System.out.println("Recup de " + user.pseudo );
                userList.add(user);
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        return userList;

    }
*/
}//end FirstExample