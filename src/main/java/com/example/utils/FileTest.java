package com.example.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class FileTest {

    private static int rows = 100000000;
    
    private static int totalLines = 0;

    public static void main(String[] args) throws Exception {
	// writeFile();
	File file = new File("d:/test/fjh.txt");
	long t1 = System.currentTimeMillis();
	readFile2();
	// 1g内存：4.9G time :176s,218s,179s cpu :  6%-->37%   5.25G 223s 223s 242s c                                                                                                                                                                                                                                                                   pu 12% ---> 31% readfile2
	//                                                          240s 244s 223s  cpu 12% ---> 30% readfile
	//getTotalLines(file);
	//totalLines = getFileLines(file);
	//readAppointedLineNumber(file,rows-100);
	//skipAndRead(file.getAbsolutePath());
	//readFile();
	//writeFile();
	long t2 = System.currentTimeMillis();
	System.out.println("耗时" + (t2 - t1) / 1000 + "秒");
    }

    public static void writeFile() throws Exception {
	File file = new File("d:/test/fjh.txt");
	//LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	FileOutputStream fos = new FileOutputStream(file,true);//append=true
	BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
	int i = 0;
	while (i < rows) {
	    bw.append("1234!^世纪东方!^admin!^001!^hello world!!^");
	    bw.append("\n");
	    i++;
	}
	System.out.println("over....");
	bw.flush();
	bw.close();
    }

    public static void readFile() throws Exception {
	File file = new File("d:/test/fjh.txt");
	// FileInputStream fis = new FileInputStream(file);
	// BufferedInputStream bis = new BufferedInputStream(fis);
	LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	// BufferedReader br = new BufferedReader(new InputStreamReader(bis, "utf-8"),10*1024*1024);
	 //BufferedReader br = new BufferedReader(new FileReader(file),10*1024*1024);
	String cnt = "";
	BigDecimal bd = new BigDecimal(0);
	int i = 0;
//	while ((cnt=br.readLine())!=null) {
//	    //cnt = it.next();
//	    String num = cnt.split("!\\^")[0];
//	    bd = bd.add(new BigDecimal(num));
//	}
	while (it.hasNext()) {
	    cnt = it.next();
	    String num = cnt.split("!\\^")[0];
	    bd = bd.add(new BigDecimal(num));
	}
	// br.close();
	// fis.close();
	System.out.println(bd);
    }

    /**
     * BufferedReader
     * 
     * @throws Exception
     */
    public static void readFile2() throws Exception {
	File file = new File("d:/test/fjh.txt");
	FileInputStream fis = new FileInputStream(file);
	BufferedInputStream bis = new BufferedInputStream(fis);
	// BufferedReader br = new BufferedReader(new InputStreamReader(bis,
	// "utf-8"),10*1024*1024);
	BufferedReader br = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"));
	String cnt = "";
	BigDecimal bd = new BigDecimal(0);
	int i = 0;
	while (br.ready()) {
	    cnt = br.readLine();
	    String num = cnt.split("!\\^")[0];
	    bd = bd.add(new BigDecimal(num));
	}
	br.close();
	fis.close();
	System.out.println(bd);
    }

    // 文件内容的总行数。
    public static int getTotalLines(File file) throws IOException {
	FileReader in = new FileReader(file);
	LineNumberReader reader = new LineNumberReader(in);
	String s = reader.readLine();
	int lines = 0;
	while (s != null) {
	    lines++;
	    s = reader.readLine();
	}
	reader.close();
	in.close();
	System.out.println(lines);
	return lines;
    }

    // 快速获取文件内容的总行数。
    public static int getFileLines(File file) throws IOException {
	long fileLength = file.length();
	LineNumberReader rf = null;
	int lines = 0;
	try {
	    rf = new LineNumberReader(new FileReader(file));
	    if (rf != null) {
		rf.skip(fileLength);
		lines = rf.getLineNumber();
		rf.close();
	    }
	} catch (IOException e) {
	    if (rf != null) {
		rf.close();
	    }
	}
	System.out.println(lines);
	return lines;
    }
    
 // 读取文件指定行。   
    static void readAppointedLineNumber(File sourceFile, int lineNumber)  
            throws IOException {  
        FileReader in = new FileReader(sourceFile);  
        LineNumberReader reader = new LineNumberReader(in);  
        String s = reader.readLine();  
        if (lineNumber < 0 || lineNumber > rows) {  
            System.out.println("不在文件的行数范围之内。");  
        }  
        {  
            while (s != null) {  
        	int num= reader.getLineNumber();
        	if(num>lineNumber){
        	    System.out.println("当前行号为:"  
                            + num);  
                    System.out.println(s);  
                    s = reader.readLine();  
        	}
            }  
        }  
        reader.close();
        in.close();  
    }  
    
    /**
     * 读取指定行数以后的内容
     * @param file
     * @throws IOException
     */
    public static void skipAndRead(String file) throws IOException{
	String line32="";
	try (Stream<String> lines = Files.lines(Paths.get(file))) {
	    //lines.
	    //line32 = lines.skip(99990000).findFirst().get();
	    Iterator iterator = lines.skip(99000000).iterator();
	    while(iterator.hasNext()){
		System.out.println(iterator.next());
	    }
	}
	System.out.println(line32);
    }
}
