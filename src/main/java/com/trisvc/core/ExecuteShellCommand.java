package com.trisvc.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteShellCommand {

	public ExecuteShellCommand() {
		// TODO Auto-generated constructor stub
	}

	
	public static String execute(String[] command) {
		System.out.println(command);

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			System.out.println(command);
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}	
	

}
