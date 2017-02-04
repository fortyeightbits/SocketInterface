import java.net.*;
import java.io.*;
import java.text.*;

public class Iperfer {

	static final int KILOBYTE = 1000;
	static final double NANOSEC = 1000000000.0;

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
			long totalSent = 0;
			long startTime = System.nanoTime();		
			while((System.nanoTime()/NANOSEC) - (startTime/NANOSEC) < time)
			{
				out.write(data);
				out.flush();
				totalSent = totalSent + KILOBYTE;
			}
			long endTime = System.nanoTime();
			long elapsed = endTime - startTime;
			clientSocket.close();
			double rate = ((double)totalSent*8/1000000.0)/(elapsed/NANOSEC);
			NumberFormat formatter = new DecimalFormat("#0.000");
			System.out.println("sent=" + (totalSent/KILOBYTE) + " KB rate=" + formatter.format(rate) + " Mbps");
			System.out.println("client time: " + elapsed);

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
			
			long totalReceived = 0;
			long received;
			byte[] receivedData = new byte[KILOBYTE];

			long startTime = System.nanoTime();
			while (true)
			{
				received = in.read(receivedData);
				//System.out.println(received);
				if (received == -1)
					break;
				totalReceived = totalReceived + received;
			}
			long endTime = System.nanoTime();
			long elapsed = endTime - startTime;
			double rate = ((double)totalReceived*8/1000000.0)/(elapsed/NANOSEC);
			
			NumberFormat formatter = new DecimalFormat("#0.000");
			System.out.println("received=" + (totalReceived/KILOBYTE) + " KB rate=" + formatter.format(rate) + " Mbps");
			System.out.println("server time: " + elapsed);
			serverSocket.close();
		}


	}

}
