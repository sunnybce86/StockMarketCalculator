package com.sapient.transaction_equity_calculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class App {
	@SuppressWarnings("null")
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		List<Transactions> transaction = ConvertExcelToJava();

		intradayReport(transaction);
		normalReport(transaction);

	}

	public static void intradayReport(List<Transactions> transaction) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfPTable table = new PdfPTable(new float[] { 2, 2, 2, 2, 2 });

		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("Client Id");
		table.addCell("Transaction Type");
		table.addCell("Transaction Date");
		table.addCell("PriorityFlag");
		table.addCell("Processing Fee");
		table.setHeaderRows(1);
		PdfPCell[] cells = table.getRow(0).getCells();
		for (int j = 0; j < cells.length; j++) {
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}

		Pattern pattern = Pattern.compile(",");

		Map<List<String>, List<Transactions>> grouped = transaction.stream().collect(Collectors.groupingBy(x -> {
			return new ArrayList<String>(Arrays.asList(x.getClientId(), x.getSecurityId(), x.getTransactionDate()));
		}));

		Iterator<Map.Entry<List<String>, List<Transactions>>> itr = grouped.entrySet().iterator();
		System.out.println("              1. Intraday Transactions Reporty:         ");
		System.out.println(
				"Client Id |" + "Transaction Type |" + "Transaction Date | " + "PriorityFlag |" + " Processing Fee");
		System.out.println("---------------------------------------------------------------------");

		while (itr.hasNext()) {
			Map.Entry<List<String>, List<Transactions>> entry = itr.next();

			if (entry.getValue().iterator().next().getTransactionType().contentEquals("BUY")
					|| entry.getValue().iterator().next().getTransactionType().contentEquals("SELL")) {

				table.addCell(entry.getValue().iterator().next().getClientId());
				table.addCell(entry.getValue().iterator().next().getTransactionType());
				table.addCell(entry.getValue().iterator().next().getTransactionDate());
				table.addCell(entry.getValue().iterator().next().getPriorityFlag());
				table.addCell("500 $");

				System.out.print(entry.getValue().iterator().next().getClientId() + "            |");
				System.out.print(entry.getValue().iterator().next().getTransactionType() + "            |");
				System.out.print(entry.getValue().iterator().next().getTransactionDate() + "            |");
				System.out.print(entry.getValue().iterator().next().getPriorityFlag() + "           |");
				System.out.print("10 $" + "   |");

			}

			System.out.println(" ");
		}
		PdfWriter.getInstance(document, new FileOutputStream(
				"D:\\\\Sunny\\\\Radian_Eclipse_Workspace\\\\transaction-equity-calculator\\\\src\\\\main\\\\java\\\\com\\\\sapient\\\\transaction_equity_calculator\\\\IntradayTransactionData.pdf"));
		document.open();
		document.add(table);
		document.close();
	}

	public static void normalReport(List<Transactions> transaction) throws FileNotFoundException, DocumentException {

		Document document = new Document();
		PdfPTable table = new PdfPTable(new float[] { 2, 2, 2, 2, 2 });

		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("Client Id");
		table.addCell("Transaction Type");
		table.addCell("Transaction Date");
		table.addCell("PriorityFlag");
		table.addCell("Processing Fee");
		table.setHeaderRows(1);
		PdfPCell[] cells = table.getRow(0).getCells();
		for (int j = 0; j < cells.length; j++) {
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}

		System.out.println("              2. Normal Transactions Reporty:         ");
		System.out.println(
				"Client Id |" + "Transaction Type |" + "Transaction Date | " + "PriorityFlag |" + " Processing Fee");
		System.out.println("--------------------------------------------------------------------------");
		for (int i = 0; i < transaction.size(); i++) {
			// System.out.println(transaction.get(i).getPriorityFlag());
			if ("Y".equals(transaction.get(i).getPriorityFlag())) {
				table.addCell(transaction.get(i).getClientId());
				table.addCell(transaction.get(i).getTransactionType());
				table.addCell(transaction.get(i).getTransactionDate());
				table.addCell(transaction.get(i).getPriorityFlag());
				table.addCell("500 $");

				System.out.print(transaction.get(i).getClientId() + "            |");
				System.out.print(transaction.get(i).getTransactionType() + "            |");
				System.out.print(transaction.get(i).getTransactionDate() + "            |");
				System.out.print(transaction.get(i).getPriorityFlag() + "               |");
				System.out.print("500 $" + "            ");

			}
			System.out.println(" ");

		}
		PdfWriter.getInstance(document, new FileOutputStream(
				"D:\\\\Sunny\\\\Radian_Eclipse_Workspace\\\\transaction-equity-calculator\\\\src\\\\main\\\\java\\\\com\\\\sapient\\\\transaction_equity_calculator\\\\NormalTransactionData.pdf"));
		document.open();

		System.out.println("Done");

		System.out.println("-------Sell------Withdraw------with Normal Priority------ -----------------");

		PdfPTable table1 = new PdfPTable(new float[] { 2, 2, 2, 2, 2 });
		table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell("Client Id");
		table1.addCell("Transaction Type");
		table1.addCell("Transaction Date");
		table1.addCell("PriorityFlag");
		table1.addCell("Processing Fee");
		table.setHeaderRows(1);
		PdfPCell[] cells1 = table.getRow(0).getCells();
		for (int j = 0; j < cells1.length; j++) {
			cells1[j].setBackgroundColor(BaseColor.GRAY);
		}

		for (int i = 0; i < transaction.size(); i++) {

			if ("N".equals(transaction.get(i).getPriorityFlag())
					|| "Sell".equals(transaction.get(i).getTransactionType())
					|| " ".equals(transaction.get(i).getTransactionType())) {

				table1.addCell(transaction.get(i).getClientId());
				table1.addCell(transaction.get(i).getTransactionType());
				table1.addCell(transaction.get(i).getTransactionDate());
				table1.addCell(transaction.get(i).getPriorityFlag());
				table1.addCell("100 $");

				System.out.print(transaction.get(i).getClientId() + "            |");
				System.out.print(transaction.get(i).getTransactionType() + "            |");
				System.out.print(transaction.get(i).getTransactionDate() + "            |");
				System.out.print(transaction.get(i).getPriorityFlag() + "               |");
				System.out.print("100 $" + "            ");
			}
			System.out.println(" ");
		}

		System.out.println("----------------Buy----Deposite--with Normal Priority-------------------------");
		PdfPTable table2 = new PdfPTable(new float[] { 2, 2, 2, 2, 2 });
		table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell("Client Id");
		table2.addCell("Transaction Type");
		table2.addCell("Transaction Date");
		table2.addCell("PriorityFlag");
		table2.addCell("Processing Fee");
		table.setHeaderRows(1);
		PdfPCell[] cells2 = table.getRow(0).getCells();
		for (int j = 0; j < cells2.length; j++) {
			cells2[j].setBackgroundColor(BaseColor.GRAY);
		}

		for (int i = 0; i < transaction.size(); i++) {

			if ("N".equals(transaction.get(i).getPriorityFlag())
					|| "Buy".equals(transaction.get(i).getTransactionType())
					|| "Deposit".equals(transaction.get(i).getTransactionType())) {

				table2.addCell(transaction.get(i).getClientId());
				table2.addCell(transaction.get(i).getTransactionType());
				table2.addCell(transaction.get(i).getTransactionDate());
				table2.addCell(transaction.get(i).getPriorityFlag());
				table2.addCell("100 $");

				System.out.print(transaction.get(i).getClientId() + "            |");
				System.out.print(transaction.get(i).getTransactionType() + "            |");
				System.out.print(transaction.get(i).getTransactionDate() + "            |");
				System.out.print(transaction.get(i).getPriorityFlag() + "               |");
				System.out.print("50 $" + "            ");
			}
			System.out.println(" ");
		}

		document.add(table);
		document.add(table1);
		document.add(table2);
		document.close();
	}

	public static List ConvertExcelToJava() {
		List trxList = new ArrayList();
		try {
			String excelPath = "D:\\Sunny\\Radian_Eclipse_Workspace\\transaction-equity-calculator\\src\\main\\java\\com\\sapient\\transaction_equity_calculator\\Sample Data.csv";

			FileInputStream fileInputStream = new FileInputStream(new File(excelPath));

			// Create Workbook instance holding .xls file
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

			// Get the first worksheet
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows
			Iterator rowIterator = sheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();

			while (rowIterator.hasNext()) {
				// Get Each Row
				Row row = (Row) rowIterator.next();

				// Leaving the first row alone as it is header
				if (row.getRowNum() == 0)
					continue;

				// Iterating through Each column of Each Row
				Iterator cellIterator = row.cellIterator();

				Transactions trx = new Transactions();
				while (cellIterator.hasNext()) {
					Cell currentCell = (Cell) cellIterator.next();

					int columnIndex = currentCell.getColumnIndex();

					switch (columnIndex + 1) {
					case 1:
						trx.setExternalTransactionId(currentCell.getStringCellValue());
						break;
					case 2:
						trx.setClientId(currentCell.getStringCellValue());
						break;
					case 3:
						trx.setSecurityId(currentCell.getStringCellValue());
						break;
					case 4:
						trx.setTransactionType(currentCell.getStringCellValue());
						break;
					case 5:
						String cellStringValue = dataFormatter.formatCellValue(row.getCell(4));
						trx.setTransactionDate(cellStringValue);
						break;
					case 6:
						trx.setMarketValue(currentCell.getNumericCellValue());
						break;
					case 7:
						trx.setPriorityFlag(currentCell.getStringCellValue());
						break;
					}
				}
				trxList.add(trx);
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return trxList;
	}
}
