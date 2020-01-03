/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playercontroller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**asd
 *
 * @author moham
 */
public class PlayerController {
      public boolean SignUpJSON(String Name,String Email,String Password) {
	DataInputStream is;
	DataOutputStream os;
	boolean result = true;
	String noReset = "Could not reset.";
	String reset = "The server has been reset.";
		
	try {
		Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 5006);
		String string = "{ \"requestStatus\": { \"statusName\": \"SignUp\", }, \"Data\": [ { \"Name\": \""+Name+"\", \"Email\": \""+Email+"\", \"Password\": \""+Password+"\", \"Status\": \"1\", } ] }";
		is = new DataInputStream(socket.getInputStream());
		os = new DataOutputStream(socket.getOutputStream());
		PrintWriter pw = new PrintWriter(os);
		pw.println(string);
		pw.flush();
		is.close();
		os.close();
			
	} catch (IOException e) {
		result = false;
		System.out.println(noReset);
		e.printStackTrace();			
	} catch (Exception e) {
		result = false;
		System.out.println(noReset);
		e.printStackTrace();
	}
	System.out.println(reset);
	return result;
}

    public static void main(String[] args)
    {
   PlayerController x= new PlayerController(); 
    x.SignUpJSON("Mohammed Abouserei","medoaboserii@gmail.com","test123");
    }
    
}
