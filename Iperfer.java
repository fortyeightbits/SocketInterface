import java.net.*;
import java.io.*;

public class Iperfer {

	//TODO: verify rate and when timing is started
	//TODO: Catch exceptions

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
			Socket clientSocket = new Socket(hostname, serverPort);
			OutputStream out = clientSocket.getOutputStream();

			byte data[] = new byte[1000];
			long startTime = System.nanoTime();
			int sent = 0;
			while(System.nanoTime() - startTime != time)
			{
				out.write(data);
				sent = sent + 1000;
			}

			clientSocket.close();
			double rate = sent/time;
			System.out.println("sent=" + (sent/1000) + " KB rate=" + rate + " Mbps");

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

			ServerSocket serverSocket = new ServerSocket(listenPort);
			Socket clientSocket = serverSocket.accept(); 
			InputStream in = clientSocket.getInputStream();
			
			int received = 0;
			long startTime = System.nanoTime();
			while (!clientSocket.isClosed())
			{
				byte[] receivedData = new byte[1000];
				received = received + in.read(receivedData);
			}
			long endTime = System.nanoTime();
			double rate = received/(endTime - startTime);
			
			System.out.println("received=" + (received/1000) + " KB rate=" + rate + " Mbps");
			serverSocket.close();
		}


	}

}
