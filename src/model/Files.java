package model;

import java.io.IOException;
import java.util.ArrayList;
import view_and_control.GUI;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class Files implements HeaderAndTrailer
{
	public ArrayList<String> headers = new ArrayList<String>();
	public ArrayList<String> trailers = new ArrayList<String>();
	public ArrayList<String> headersHex = new ArrayList<String>();
	public ArrayList<Long> headersDecimal = new ArrayList<Long>();
	public ArrayList<String> trailersHex = new ArrayList<String>();
	public ArrayList<Long> trailersDecimal = new ArrayList<Long>();
	private static FileOutputStream output;
	private static Workbook workbook = new HSSFWorkbook();
	public String header;
	public String trailer;
	
	public Files(String header, String trailer)
	{
		this.header = header;
		this.trailer = trailer;
	}
	
	public static void printHeader(Files file)
	{
		//clears previous sheets from workbook
		if(workbook.getNumberOfSheets() > 1)
		{
			workbook.removeSheetAt(0);
			workbook.removeSheetAt(0);
		}
		Sheet sheet1 = workbook.createSheet(file.getClass().getSimpleName() + "_HEADERS");
		Row r;
		
		try
		{
			output = new FileOutputStream(GUI.fileChosen + "_" + file.getClass().getSimpleName() + ".xls");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
		//writes ArrayLists to sheet
		for (int i = 0; i < file.headers.size(); i++)
		{
			r = sheet1.createRow(i);
			r.createCell(0).setCellValue(file.headers.get(i));
			r.createCell(1).setCellValue(file.headersHex.get(i));
			r.createCell(2).setCellValue(file.headersDecimal.get(i));
		}
		try 
		{
			sheet1.autoSizeColumn(0);
			sheet1.autoSizeColumn(1);
			sheet1.autoSizeColumn(2);
			workbook.write(output);
			output.flush();
			output.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void printTrailer(Files file)
	{
			
		Sheet sheet2 = workbook.createSheet(file.getClass().getSimpleName() + "_TRAILERS");
		Row r;
		
		try
		{
			output = new FileOutputStream(GUI.fileChosen + "_" + file.getClass().getSimpleName() + ".xls");
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//writes trailer ArrayLists to sheet
		for (int i = 0; i < file.trailers.size(); i++)
		{
			r = sheet2.createRow(i);
			r.createCell(0).setCellValue(file.trailers.get(i));
			r.createCell(1).setCellValue(file.trailersHex.get(i));
			r.createCell(2).setCellValue(file.trailersDecimal.get(i));
		}
		sheet2.autoSizeColumn(0);
		sheet2.autoSizeColumn(1);
		sheet2.autoSizeColumn(2);
		try 
		{
			workbook.write(output);
			output.flush();
			output.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
		
	//Gets byte-offset value from line that contains file signature headers
	public static void getHeaderHex()
	{
		for (Files fileType : GUI.fileTypeArray)
		{
			for (int i = 0; i < fileType.headers.size(); i++)
			{
				String str = fileType.headers.get(i);
				fileType.headersHex.add(str.substring(0, str.indexOf(":")));
			}
		}
	}
	
	//Converts header byte-offset values to decimal
	public static void getHeaderDecimal()
	{
		for (Files fileType : GUI.fileTypeArray)
		{
			for (String hex : fileType.headersHex)
			{
				fileType.headersDecimal.add(Long.parseLong(hex, 16));
			}
		}
	}
	
	//Gets byte-offset value from line that contains file signature trailers
	public static void getTrailerHex()
	{
		for (Files fileType : GUI.fileTypeArray)
		{
			for (int i = 0; i < fileType.trailers.size(); i++)
			{
				String str = fileType.trailers.get(i);
				fileType.trailersHex.add(str.substring(0, str.indexOf(":"))); //JPGHeaderHex.add(str.substring(0, 7));
			}
		}
	}
	
	//Converts trailer byte-offset values to decimal
	public static void getTrailerDecimal()
	{
		for (Files fileType : GUI.fileTypeArray)
		{
			for (String hex : fileType.trailersHex)
			{
				fileType.trailersDecimal.add(Long.parseLong(hex, 16));
			}
		}
	}
	
	public String getHeader()
	{
		return this.header;
	}
	
	public String getTrailer()
	{
		return this.trailer;
	}
	
}
