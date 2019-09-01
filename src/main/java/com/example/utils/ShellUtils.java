package com.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * java执行shell命令并获取返回结果
 * 
 * @author ben 2019年9月1日
 */
public class ShellUtils {

    public static void execute(String command) {
	Process process = null;
	List<String> processList = new ArrayList<String>();
	try {
	    process = Runtime.getRuntime().exec(command);
	    BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    while ((line = input.readLine()) != null) {
		processList.add(line);
	    }
	    input.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	for (String line : processList) {
	    System.out.println(line);
	}
    }

    public static void main(String args[]) {
	execute("ps -aux");
    }

    /**
     * 执行多个shell命令
     */
    public static void multiShell() {
	String[] command = { "ssh", "127.0.0.1", "ps", "-ef", "|", "grep", "java" };
	ProcessBuilder pb = new ProcessBuilder();
	pb.command(command);
	// very important,if error,message will output too.
	pb.redirectErrorStream(true);
	Process process;
	try {
	    process = pb.start();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    while (true) {
		String line = reader.readLine();
		if (line == null) {
		    break;
		}
		System.out.println(line);
	    }
	    process.destroy();
	    reader.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}
