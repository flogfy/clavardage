package COO4IR;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.sql.Timestamp;
public class message implements Serializable {
	//Serializable permet d'autoriser l'envoi de cet objet via socket tcp
	private static final long serialVersionUID = -5399605122490343339L;
	private int type;
	private int id;
	private String date;
	private String contenu;
	private Image image;
	private Timestamp time;
	public message(int type, int id,String contenu,Image image) {
		super();
		this.contenu=contenu;
		this.type=type;
		this.image=image;
		this.time=new Timestamp(System.currentTimeMillis());
		//On considère la date au moment où le message est crée
		Date ladate=new Date();
		SimpleDateFormat formater = new SimpleDateFormat("h:mm");
	    this.date=(formater.format(ladate));
	}
	
	public String getContenu() {
		return contenu;
	}


	public Image getImage() {
		return image;
	}
	
	public int getType() {
		return type;
	}



	public int getId() {
		return id;
	}


	public String getDate() {
		return date;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp tmstp) {
		this.time=tmstp;
		
	}

	

}