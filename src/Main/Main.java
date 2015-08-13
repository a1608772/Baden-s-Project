package Main;

import java.io.IOException;

import org.mavlink.messages.pixhawk.msg_command_long;
import org.mavlink.messages.pixhawk.msg_heartbeat;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main {
	
	public static void main(String[] args) throws IOException {
			
			/*
				int sequence = 0;
				int autopilot = 8; // GCS
				int base_mode = 8; // GUIDED MODE
				long custom_mode = 0;
				int mavlink_version = 3; // V3
				int system_status = 3; // MAV STATE STANDBY
				int type = 6; // GCS
				
				msg_heartbeat hb = new msg_heartbeat(255, 0);
				hb.sequence = sequence++;
				hb.autopilot = autopilot;
				hb.base_mode = base_mode;
				hb.custom_mode = custom_mode;
				hb.mavlink_version = mavlink_version;
				hb.system_status = system_status;
				hb.type = type;
				byte[] result = hb.encode();
				// dos.put(result);
				break;
				*/
			int sequence=0;
				msg_command_long LONG = new msg_command_long(255, 0);
				LONG.sequence=sequence++;
				LONG.command = 400;
				LONG.param1 = 1;
				LONG.target_component = 0;
				LONG.target_system = 1;
				byte[] result = LONG.encode();
		
		String sending = "";
		for (byte b : result) {
			sending += (b & 0xFF) + " "; // & 0xFF makes it unsigned for display purposes only
		}
		System.out.println("Packet to send: " + sending);
		
		// Access COM port
		String[] portNames = SerialPortList.getPortNames();
		SerialPort serialPort = new SerialPort(portNames[0]);
		
		System.out.println("Accessing: " + portNames[0]);
		
		// Send packet buffer
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_57600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					
			System.out.println("Attempting to send");
			
			serialPort.writeBytes(result);
			// serialPort.closePort();
			
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
		
	}
}
