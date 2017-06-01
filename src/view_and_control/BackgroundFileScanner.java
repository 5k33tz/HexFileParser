package view_and_control;

import java.beans.PropertyChangeEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import model.Files;
import view_and_control.GUI;


public class BackgroundFileScanner extends SwingWorker<Long, Object> 
{
	private final JLabel numberOfLinesLabel, linesScannedLabel, numberOfJPGLabel, numberOfPDFLabel, numberOfGIFLabel, numberOfDOCLabel, numberOfRTFLabel, numberOfPNGLabel;
	private final JProgressBar progressBar;
	private Long numberOfLinesScanned;
	
	public BackgroundFileScanner(JProgressBar progressBar, JLabel resultLabel, JLabel linesScannedLabel, JLabel numberOfJPG, JLabel numberOfPDF, JLabel numberOfGIF, JLabel numberOfDOC, JLabel numberOfRTF, JLabel numberOfPNG)
	{
		this.numberOfLinesLabel = resultLabel;
		this.progressBar = progressBar;
		this.linesScannedLabel = linesScannedLabel;
		this.numberOfJPGLabel = numberOfJPG;
		this.numberOfPDFLabel = numberOfPDF;
		this.numberOfGIFLabel = numberOfGIF;
		this.numberOfDOCLabel = numberOfDOC;
		this.numberOfRTFLabel = numberOfRTF;
		this.numberOfPNGLabel = numberOfPNG;
	}
		
	public Long getNumberOfLines()
	{
		return this.numberOfLinesScanned;
	}
	
	@Override
	protected Long doInBackground() throws Exception 
	{
		clearData();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);
		
		final Long numberOfLinesInDocument = (long) countLines("" + GUI.fileChosen); //finds number of lines from hex dump
		progressBar.setIndeterminate(false);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		linesScannedLabel.setVisible(true);
		numberOfLinesLabel.setVisible(true);
		numberOfLinesScanned = (long) 0;
		Scanner scanner = new Scanner(new File("" + GUI.fileChosen));;
		
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			for (Files fileType : GUI.fileTypeArray)
			{
				//adds line to specific file type headers to ArrayList if the header exists and isn't in the byte-offset
				if(line.contains(fileType.getHeader()) && !(line.substring(0, line.indexOf(":")).contains(fileType.getHeader()))) 
				{
					fileType.headers.add(line);
				}
				//adds line to specific file type trailers to ArrayList if the trailer exists and isn't in the byte-offset
				if(line.contains(fileType.getTrailer()) && !(line.substring(0, line.indexOf(":")).contains(fileType.getTrailer()))) 
				{
					fileType.trailers.add(line); //
				}
			}

			//updates JLabels on GUI
			numberOfLinesScanned++;
			String formattedLong = NumberFormat.getInstance().format(numberOfLinesScanned);
			numberOfLinesLabel.setText(formattedLong.toString());
			numberOfJPGLabel.setText("" + GUI.jpg.headers.size());
			numberOfPDFLabel.setText("" + GUI.pdf.headers.size());
			numberOfGIFLabel.setText("" + GUI.gif.headers.size());
			numberOfDOCLabel.setText("" + GUI.doc.headers.size());
			numberOfRTFLabel.setText("" + GUI.rtf.headers.size());
			numberOfPNGLabel.setText("" + GUI.png.headers.size());
			
			//gets percent complete for progress bar
			double progressPercent = (double) numberOfLinesScanned/numberOfLinesInDocument;
			String tempNumber = String.valueOf(progressPercent);
			tempNumber = tempNumber.substring(tempNumber.indexOf("."));
			double tempNumber2 = (Double.parseDouble(tempNumber)*100);
			int realProgressPercent = (int) tempNumber2;
			
			progressBar.setValue(realProgressPercent);			
		}
		scanner.close();
		return numberOfLinesScanned;
	}
	
	protected void done()
	{
		progressBar.setIndeterminate(false);
		progressBar.setVisible(false);
		JOptionPane.showMessageDialog(null, "Scanning is Complete", "Scan Complete", JOptionPane.INFORMATION_MESSAGE); 
	}
	
	public static int countLines(String filename) throws IOException //http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
	{
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try 
	    {
	    	byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) 
	        {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) 
	            {
	                if (c[i] == '\n') 
	                {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } 
	    finally 
	    {
	        is.close();
	    }
	}
	
	//clears JLabels from GUI
	private void clearData()
	{
		numberOfLinesScanned = (long) 0;
				
		numberOfJPGLabel.setText("" + GUI.jpg.headers.size());
		numberOfPDFLabel.setText("" + GUI.pdf.headers.size());
		numberOfGIFLabel.setText("" + GUI.gif.headers.size());
		numberOfDOCLabel.setText("" + GUI.doc.headers.size());
		numberOfRTFLabel.setText("" + GUI.rtf.headers.size());
		numberOfPNGLabel.setText("" + GUI.png.headers.size());
	}
	public void propertyChange(PropertyChangeEvent evt)
	{
		
	}
}