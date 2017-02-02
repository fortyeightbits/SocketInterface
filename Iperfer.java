import java.net.*;
import java.io.*;

public class Iperfer {

	public static String hostname;
	public static int serverPort, listenPort, time;

	public static void main(String[] args) {

		//argument check
		if (args[0].charAt(1) != 'c' && args[0].charAt(1) != 's')
		{
			System.out.println("Error: missing or additional arguments");
			return;
		}

		//CLIENT
		if (args[0].charAt(1) == 'c')
		{
			if (args.length != 7 || args[1].charAt(1) != 'h' || args[3].charAt(1) != 'p' || args[5].charAt(1) != 't')

			{
				System.out.println("Error: missing or additional arguments");
				return;
			}

			serverPort = Integer.parseInt(args[4]);
			if (serverPort < 1024 || serverPort > 65535)
			{
				System.out.println(" Error: port number must be in the range 1024 to 65535");
				return;
			}
		}

		//SERVER
		else
		{
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
