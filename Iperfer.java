import java.net.*;
import java.io.*;

public class Iperfer {

	static final int KILOBYTE = 1000;
	static final int NANOSEC = 1000000000;

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

			byte data[] = new byte[KILOBYTE];
			int totalSent = 0;
			long startTime = System.nanoTime()/NANOSEC;		
			while((System.nanoTime()/NANOSEC) - startTime < time)
			{
				out.write(data);
				totalSent++;
			}
			
			clientSocket.close();
			double rate = (totalSent*8/1000)/time;
			System.out.println("sent=" + totalSent + " KB rate=" + rate + " Mbps");

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
			
			int totalReceived = 0;
			int received;
			byte[] receivedData = new byte[KILOBYTE];

			long startTime = System.nanoTime()/NANOSEC;
			while (true)
			{
				received = in.read(receivedData);
				if (received == -1)
					break;
				totalReceived = totalReceived + (received/KILOBYTE);
			}
			long endTime = System.nanoTime()/NANOSEC;
			System.out.println("start: " + startTime);
			System.out.println("end: " + endTime);
			double rate = (totalReceived*8/1000)/(endTime - startTime);
			
			System.out.println("received=" + totalReceived + " KB rate=" + rate + " Mbps");
			serverSocket.close();
		}


	}

}
