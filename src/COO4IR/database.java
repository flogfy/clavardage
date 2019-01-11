package COO4IR;

//STEP 1. Import required packages
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class database {

    // JDBC driver name and database URL
    //For embedded DB, we can use :
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
   // With conn = DriverManager.getConnection("jdbc:derby:testdb1;create=true");
    // Ne pas oublier de creer alors manuellement les tables mentionnées après au premier lancement
    //static final String DB_URL = "jdbc:mysql://localhost/clavardage?Unicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    //Daatabase connection
    static Connection conn = null;


    //Deux tables:


   // On stock tout les users a qui l'ont a parlé avec leur dernier pseudo en date
    
    /*
    
    CREATE TABLE Historique (
            IPDest VARCHAR(15),
            Message VARCHAR(500),
            Date DATETIME(),
            PRIMARY KEY (IPDest)
    );
    

    
    CREATE TABLE Authentification (
            login VARCHAR(15),
            password VARCHAR(15)
            //FOREIGN KEY (IPDest) REFERENCES Users(IPDest)
     );

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



    public database (){

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

    //Ajoute un message texte dans la DB en lien avec une IP
    public void addMessage(String contenu, InetAddress ipdistant, Timestamp time) {

       /* InetAddress iptemp = null;
        try {
            iptemp = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        addUser(iptemp, UserDatabase.getByAdr(iptemp).pseudo);
*/
        String sql = "INSERT INTO Historique (IPDest, Message,Date)\n" +
                "VALUES (?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, ipdistant.toString() );
            stm.setString(2, contenu );
            stm.setTimestamp(3,time);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


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
    //Retourne la liste des messages échangés avec une IP ( indépédamment du pseudo )
    public List<message> getMessages(String ip) {

        String sql = "SELECT * FROM Messages WHERE IPDest = ? ORDER BY Date";
        ResultSet rs=null;
        List<message> messageList = new ArrayList<message>();

        try (PreparedStatement stm = conn.prepareStatement(sql)) {

            System.out.println(ip);
            stm.setString(1, ip );
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
}//end FirstExample