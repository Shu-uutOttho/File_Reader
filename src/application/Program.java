package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> list = new ArrayList<>();

		System.out.println("Enter File Path: ");
		String sourceFileStr = sc.nextLine();

		File sourceFile = new File(sourceFileStr);
		String sourceFouldeStr = sourceFile.getParent();

		boolean success = new File(sourceFouldeStr + "\\out").mkdir();
		String targetFileStr = sourceFouldeStr + "\\out\\sumary.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

			String itemCsv = br.readLine();
			while (itemCsv != null) {

				String[] fields = itemCsv.split(",");
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);
				int quantity = Integer.parseInt(fields[2]);

				list.add(new Product(name, price, quantity));
				itemCsv = br.readLine();

			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {

				for (Product item : list) {
					bw.write(item.getName() + ", " + String.format("%.2f", item.total()));
					bw.newLine();
					System.out.println(item.toString());
				}
				
				System.out.println(targetFileStr + " CREATED!");
			} catch (IOException e) {
				System.out.println("Error writting File" + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error Reading File" + e.getMessage());
		}
		sc.close();
	}
}
