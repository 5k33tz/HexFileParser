package view_and_control;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JComboBox;

import model.Files;
import model.DOC;
import model.GIF;
import model.JPG;
import model.PDF;
import model.PNG;
import model.RTF;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUI extends JFrame 
{
	private JPanel contentPane;
	public static File fileChosen;
	FileWriter writer;
	static long numberOfLines;
	public static ArrayList<Files> fileTypeArray = new ArrayList<Files>();
	public static Files jpg, pdf, gif, doc, rtf, png, fileTypeChosen;
	private BackgroundFileScanner task;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setTitle("Hex Analyzer");
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 271, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoadHexdump = new JMenuItem("Load Hex Dump");
		mnFile.add(mntmLoadHexdump);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JProgressBar progressBar = new JProgressBar();
		
		final JLabel lblLinesScanned = new JLabel("Lines Scanned:");
		final JLabel lblNumberOfLines = new JLabel("");
		final JLabel lblPossibleFiles = new JLabel("Possible Files:");
		
		final JLabel lblNumberOfJPG = new JLabel("");
		final JLabel lblJpeg = new JLabel("JPEG");
		
		final JLabel lblNumberOfPDF = new JLabel("");
		final JLabel lblPdf = new JLabel("PDF");
		
		final JLabel lblNumberOfGIF = new JLabel("");
		final JLabel lblGif = new JLabel("GIF");
		
		final JLabel lblNumberOfDOC = new JLabel("");
		final JLabel lblDoc = new JLabel("DOC");
		
		final JLabel lblNumberOfRTF = new JLabel("");
		final JLabel lblRtf = new JLabel("RTF");
		
		final JLabel lblNumberOfPNG = new JLabel("");
		final JLabel lblPng = new JLabel("PNG");
		
		final JButton btnStopAnalyzing = new JButton("Stop Analyzing");
		
		JButton btnWriteContentsTo = new JButton("Write Contents to Disk");
		
		
		JLabel lblChooseFileType = new JLabel("Choose file type to write:");
		
		String[] comboBoxArray = {"", "JPEG","PDF","GIF", "DOC", "RTF", "PNG"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox fileTypeComboBox = new JComboBox(comboBoxArray);
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblPossibleFiles)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblPdf)
												.addComponent(lblJpeg)
												.addComponent(lblGif)
												.addComponent(lblDoc)
												.addComponent(lblPng)
												.addComponent(lblRtf))))
									.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblLinesScanned)
									.addPreferredGap(ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
									.addComponent(lblNumberOfLines)
									.addGap(20))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(34)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNumberOfPDF)
								.addComponent(lblNumberOfJPG)
								.addComponent(lblNumberOfGIF)
								.addComponent(lblNumberOfDOC)
								.addComponent(lblNumberOfRTF)
								.addComponent(lblNumberOfPNG)
								.addComponent(fileTypeComboBox, 0, 154, Short.MAX_VALUE)
								.addComponent(btnStopAnalyzing, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addComponent(lblChooseFileType))
								.addComponent(btnWriteContentsTo, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
							.addGap(57)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLinesScanned)
						.addComponent(lblNumberOfLines))
					.addGap(26)
					.addComponent(lblPossibleFiles)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfJPG)
						.addComponent(lblJpeg))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfPDF)
						.addComponent(lblPdf))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfGIF)
						.addComponent(lblGif))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfDOC)
						.addComponent(lblDoc))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfRTF)
						.addComponent(lblRtf))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfPNG)
						.addComponent(lblPng))
					.addGap(30)
					.addComponent(btnStopAnalyzing, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblChooseFileType)
					.addGap(11)
					.addComponent(fileTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnWriteContentsTo, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);

		progressBar.setVisible(false);

		
		//exit button from File menu
		mntmExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);
			}
		});
		
		//load button from File menu
		mntmLoadHexdump.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				//clears ArrayLists from each file type
				for (Files fileType : fileTypeArray)
				{
					fileType.headers.clear();
					fileType.headersHex.clear();
					fileType.headersDecimal.clear();
					fileType.trailers.clear();
					fileType.trailersHex.clear();
					fileType.trailersDecimal.clear();
				}
				
				jpg = new JPG();
				pdf = new PDF();
				gif = new GIF();
				doc = new DOC();
				rtf = new RTF();
				png = new PNG();
				
				fileTypeArray.add(jpg);
				fileTypeArray.add(pdf);
				fileTypeArray.add(gif);
				fileTypeArray.add(doc);
				fileTypeArray.add(rtf);
				fileTypeArray.add(png);
				
				numberOfLines = 0;
				
				//chooses the file you want to analyze, and starts SwingWorker
				JFileChooser fc = new JFileChooser();
				System.out.println("Choosing file to analyze");
				fc.showOpenDialog(fc);
				fileChosen = fc.getSelectedFile();
				System.out.println("The file you chose is " + fileChosen);
				task = new BackgroundFileScanner(progressBar, lblNumberOfLines, lblLinesScanned, lblNumberOfJPG, lblNumberOfPDF, lblNumberOfGIF, lblNumberOfDOC, lblNumberOfRTF, lblNumberOfPNG);
				task.execute();				
			}
		});
		
		//Stop Analyzing Button
		btnStopAnalyzing.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				task.cancel(true);
			}
		});
		
		//Write Contents to Disk button
		btnWriteContentsTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (!(fileTypeComboBox.getSelectedIndex() > 0))
				{
					JOptionPane.showMessageDialog(null, "Must choose file type to write");
					return;
				}
				
				//chooses file to save workbook to
				JFileChooser fc = new JFileChooser();
				System.out.println("Choosing file to save");
				fc.showSaveDialog(fc);
				fileChosen = fc.getSelectedFile();
				System.out.println("The file you are saving is " + fileChosen);
				Files.getHeaderHex();
				Files.getHeaderDecimal();
				Files.getTrailerHex();
				Files.getTrailerDecimal();
					
				//combo box selections
				if (fileTypeComboBox.getSelectedIndex() == 1 && jpg.headersHex.size() > 0)//writing JPEG
					fileTypeChosen = fileTypeArray.get(0);
				if (fileTypeComboBox.getSelectedIndex() == 2 && pdf.headersHex.size() > 0)//writing PDF
					fileTypeChosen = fileTypeArray.get(1);
				if (fileTypeComboBox.getSelectedIndex() == 3 && gif.headersHex.size() > 0)//writing GIf
					fileTypeChosen = fileTypeArray.get(2);
				if (fileTypeComboBox.getSelectedIndex() == 4 && doc.headersHex.size() > 0)//writing DOC
					fileTypeChosen = fileTypeArray.get(3);
				if (fileTypeComboBox.getSelectedIndex() == 5 && rtf.headersHex.size() > 0)//writing RTF
					fileTypeChosen = fileTypeArray.get(4);
				if (fileTypeComboBox.getSelectedIndex() == 6 && png.headersHex.size() > 0)//writing PNG
					fileTypeChosen = fileTypeArray.get(5);
				
				Files.printHeader(fileTypeChosen);
				Files.printTrailer(fileTypeChosen);

				//displays which directory file is being saved in
				System.out.println("Files are in: " + fileChosen.getParentFile());
				JOptionPane.showMessageDialog(null, "Files are in: " + fileChosen.getParentFile());
				fileTypeChosen = null;
				fileTypeComboBox.setSelectedIndex(0);
			}
		});
	}
}
