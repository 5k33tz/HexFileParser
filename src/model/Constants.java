package model;

public class Constants 
{
	// JPG file signatures
	public static final String JPGHEADER = "ffd8 ffe0";
	public static final String JPGTRAILER = "ffd9";
	
	// PDF file signatures
	public static final String PDFHEADER = "2550 4446";
	public static final String PDFTRAILER = "0a25 2545 4f46";
	
	// GIF file signatures
	public static final String GIFHEADER = "4749 4638 3761";
	public static final String GIFTRAILER = "003b";
	
	// DOCX/PPTX/XLSX file signatures
	public static final String DOCHEADER = "504b 0304 1400 0600";
	public static final String DOCTRAILER = "504b 0506";
	
	// RTF file signatures
	public static final String RTFHEADER = "7b5c 7274 6631";
	public static final String RTFTRAILER = "5c70 6172 207d 7d";
	
	// PNG file signatures
	public static final String PNGHEADER = "8950 4e47 0d0a 1a0a";
	public static final String PNGTRAILER = "4945 4e44 ae42 6082";	
}
