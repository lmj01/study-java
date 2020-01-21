package com.meijie.study.Class;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App2 {
	
	public static void main(String[] args) {
		ProcessBuilder pb = new ProcessBuilder();
		List<String> commands = new ArrayList<>();
		commands.add("D:\\portableApp\\ffmpeg-4.2.1-win64-shared\\bin\\ffmpeg.exe");
		commands.add("-help");
		for (String str : args) System.out.println(str);
		Process process;
		try {
			process = pb.command(commands).start();
			try (BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while((line = read.readLine()) != null) {
					System.out.println(line);
				}	
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
