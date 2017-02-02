import java.net.*;
import java.io.*;

public class Iperfer {


	public static void main(String[] args) throws UnknownHostException, IOException {

		//client/server argument check
		if (args[0].charAt(1) != 'c' && args[0].charAt(1) != 's')
		{
			System.out.println("Error: missing or additional arguments");
			return;
		}

		//CLIENT
		if (args[0].charAt(1) == 'c')
		{
			String hostname;
			int time, serverPort;
			if (args.length != 7 || args[1].charAt(1) != 'h' || args[3].charAt(1) != 'p' || args[5].charAt(1) != 't')
			{
				System.out.println("Error: missing or additional arguments");
				return;
			}

			hostname = args[2];
			time = Integer.parseInt(args[6]);
			if (time <= 0)
			{
				System.out.println(" Error: time must be more than 0");
				return;
				
			}
			serverPort = Integer.parseInt(args[4]);
			if (serverPort < 1024 || serverPort > 65535)
			{
				System.out.println(" Error: port number must be in the range 1024 to 65535");
				return;
			}
			
			try
			{
				Socket clientSocket = new Socket(hostname, serverPort);
			
			}
			
			catch(Exception e)
			{
				System.out.println("Exception thrown");
			}
			
		}

		//SERVER
		else
		{
			int listenPort;
			if (args.length != 3 || args[1].charAt(1) != 'p')
			{
				System.out.println("Error: missing or additional arguments");
			}
			
			listenPort = Integer.parseInt(args[2]);
			if (listenPort < 1024 || listenPort > 65535)
			{
				System.out.println("Error: port number must be in the range 1024 to 65535");
			}
		}


	}

}
