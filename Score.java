

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class Score {
	
	private File file;
	private String fileName;
	private List<Person> entries;
	
	public Score() {
		fileName = "scores.txt";
		entries = new ArrayList<Person>();
		file = new File(fileName);
		readData();
	}
	
	public void fillData() {
		if (entries.size() < 5) {
		//fills with empty data first
			while(entries.size() < 5) {
				entries.add(new Person());
			}
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < 5; i++) {
				Person p = entries.get(i);
				String entryName = p.getName();
				String result = p.getResult() + "";
				bw.write(entryName + ":" + result);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to write highscore", "Error", JOptionPane.ERROR_MESSAGE);
		}
		readData();
	}
	
	public void readData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			for (int i = 0; i < 5; i++) {
				String entry = br.readLine();
				if (entry == null) fillData();
				else {
					int x = entry.indexOf(":");
					String name = entry.substring(0, x);
					String score = entry.substring(x + 1);
					int temp = Integer.parseInt(score);
					entries.add(new Person(name, temp));
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to write highscore", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void writeData(String name, int score) {
		score = score + 1;
		entries.add(new Person(name, score));
		Collections.sort(entries);
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < 5; i++) {
				Person p = entries.get(i);
				String entryName = p.getName();
				String result = p.getResult() + "";
				bw.write(entryName + ":" + result);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to write highscore", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<Person> getEntries() {
		return entries;
	}

}
