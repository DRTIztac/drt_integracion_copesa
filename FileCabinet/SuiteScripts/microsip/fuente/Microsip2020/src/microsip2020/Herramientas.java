/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microsip2020;

/**
 *
 * @author Jorge Villalobos
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

import org.xml.sax.InputSource;

public class Herramientas {

    public Herramientas() {
        
    }
    
     public static String covTF8(String txt) throws UnsupportedEncodingException{
       return  new String(txt.getBytes("ISO-8859-1"),"UTF-8");
    }
     
      public static String covISO(String txt) throws UnsupportedEncodingException{
       return  new String(txt.getBytes("UTF-8"),"ISO-8859-1");  
    }

     public static java.awt.Point puntoCentroPag(javax.swing.JFrame frame) {
        java.awt.Point point = new java.awt.Point();
        int yy = frame.getHeight();
        int xx = frame.getWidth();
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        java.awt.Dimension dim = toolkit.getScreenSize();
        int x = (int) ((float) dim.getWidth() / 2) - (int) ((float) xx / 2);
        int y = (int) ((float) dim.getHeight() / 2) - (int) ((float) yy / 2);
        point.setLocation(x, y);
        return point;
    }
    
    public static void appenTextFile(java.io.File arc, byte[] txt) {
        try {
            Herramientas.creaCarpetas(arc.getPath());
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(arc, true)));
            out.println(new String(txt));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String remp(String txt) {
        return txt.replaceAll("\r", "\n");
    }

    public static java.util.ArrayList<String> leeArchivoLineautf(java.io.File fileName) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        java.util.ArrayList<String> arrLinea = new java.util.ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf8"));//
        String line = "";
        while ((line = br.readLine()) != null) {
            arrLinea.add(line);
        }
        br.close();
        return arrLinea;
    }

    public static int grabaArchivo(File source, File target) {
        try {
            Herramientas.creaCarpetas(target.getPath());
            FileUtils.copyFile(source, target);
            return 1;
        } catch (IOException ex) {
            //    Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static String fechUltima(String mes, String año) {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        int mest = Integer.parseInt(mes) - 1;
        cal.set(java.util.Calendar.MONTH, mest);
        cal.set(java.util.Calendar.YEAR, Integer.parseInt(año));
        cal.add(java.util.Calendar.MONTH, 1);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        return cal.get(java.util.Calendar.DAY_OF_MONTH) + "/" + (cal.get(java.util.Calendar.MONTH) + 1) + "/" + cal.get(java.util.Calendar.YEAR);
    }

    public static String rellenaIzq(String txt, int car) {
        String reg = "";
        if (txt.length() > car) {
            return txt.substring(0, car);
        }

        for (int i = txt.length(); i < car; i++) {
            reg += " ";
        }
        return txt + reg;
    }

    public static String rellena(String txt, int car) {
        String reg = "";
        if (txt.length() > car) {
            return txt.substring(0, car);
        }

        for (int i = txt.length(); i < car; i++) {
            reg += " ";
        }
        return reg + txt;
    }

    public static String getCountry() {
        java.util.Properties pro = System.getProperties();
        return pro.getProperty("user.country");
    }

    public static void grabaTxt(java.io.File file, String txt) throws IOException {
        String newLine = System.getProperty("line.separator");
        if (file.exists() == false) {
            creaCarpetas(file.getPath());
        } else {
            file.delete();
        }

        Writer output;
        output = new BufferedWriter(new FileWriter(file.getPath(), true));  //clears file every time
        output.append(txt);
        output.close();

    }

    public static void appendFile(java.io.File file, String txt) throws IOException {
        String newLine = System.getProperty("line.separator");
        if (file.exists() == false) {
            creaCarpetas(file.getPath());
        }

        Writer output = null;
        output = new BufferedWriter(new FileWriter(file.getPath(), true));  //clears file every time
        output.append(txt + newLine);
        output.close();
    }

    public static void appendFile(java.io.File file, String txt, String txt2) throws IOException {
        String newLine = System.getProperty("line.separator");
        if (file.exists() == false) {
            creaCarpetas(file.getPath());
        }

        Writer output;
        output = new BufferedWriter(new FileWriter(file.getPath(), true));  //clears file every time
        output.append("---------------------------------" + newLine);
        output.append(txt + newLine);
        output.append(txt2 + newLine);
        output.append("---------------------------------" + newLine);
        output.close();
    }

    public static String cFraccion2(String nn) {
        String res = "0.00";
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "MX"));
            symbols.setDecimalSeparator('.');
            symbols.setGroupingSeparator(',');
            DecimalFormat df1 = new DecimalFormat("###,###,###,##0.##", symbols);
            df1.setMaximumFractionDigits(2);
            df1.setMinimumFractionDigits(2);
            df1.setRoundingMode(RoundingMode.HALF_UP);
            res = df1.format(Double.parseDouble(nn));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return res;
    }

    public static String cFraccion(double nn) {
        String res = "0.00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "MX"));
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        DecimalFormat df1 = new DecimalFormat("###,###,###,##0.##", symbols);
        res = df1.format(nn);
        return res;
    }

    public static String cMony(BigDecimal nn) {
        if (nn == null) {
            return "-E-";
        }

        String res = "0.00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "MX"));
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        DecimalFormat df1 = new DecimalFormat("###,###,###,##0.##", symbols);
        res = df1.format(nn);
        return res;
    }

    public static String getMac() {
        InetAddress ip = null;
        StringBuilder sb = null;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return sb.toString();
    }

    public static String quitaHtml(String txt) {
        txt = txt.replaceAll("<html>", "");
        txt = txt.replaceAll("</html>", "");
        return txt.trim();
    }

    public static String quitarDoblesEspacios(String res) {
        res = res.replaceAll("[\\s]{1,}", " ");
        return res;
    }

    public static String dosD(int nu) {
        String txt = "";
        if (nu < 10) {
            txt = "0" + nu;
        } else {
            txt = "" + nu;
        }
        return txt;
    }

    public String lecturaAcrivo(String rut) {
        StringBuffer sb = new StringBuffer();
        java.io.InputStream is = getClass().getResourceAsStream("reportes/" + rut);
        try {
            java.io.InputStreamReader isr = new java.io.InputStreamReader(is);
            java.io.BufferedReader br = new java.io.BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
            br.close();
            isr.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            mensageError("problemas de lectura");
        }
        return sb.toString();
    }

    public static String arrImplode(java.util.ArrayList<String> arr, String sep) {
        String res = "";
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                res = res + arr.get(i) + sep;
                //   System.out.println(res);
            }
        } else {
            return "";
        }
        int tam = res.length();
        if (tam > 0) {
            res = res.substring(0, tam - 1);
        }
        return res;
    }

    public static void saveJPG(java.awt.Image img, String ruta) throws FileNotFoundException, FileNotFoundException, IOException {
        creaCarpetas(ruta);
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, null, null);
        ImageIO.write(bi, "jpg", new java.io.File(ruta));
        bi = null;
    }

    public static void grabaByte(byte[] buffer, String nombre) {
        if (buffer != null) {
            try {
                RandomAccessFile f = new RandomAccessFile(nombre, "rw");
                f.write(buffer);
                f.close();
            } catch (Exception ex) {
                mensageGrafico("Error  en  grabar");
            }
        }
    }

    public static java.awt.Image traduceImageBytesBI(byte[] datos) {
        java.awt.image.BufferedImage bi = null;
        try {
            InputStream in = new ByteArrayInputStream(datos);
            bi = ImageIO.read(in);
            return bi;
        } catch (IOException ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bi;
    }

    public static java.awt.image.BufferedImage traduceImageBytesBI2(byte[] datos) {
        if (datos == null) {
            return null;
        }
        java.awt.image.BufferedImage bi = null;
        try {
            InputStream in = new ByteArrayInputStream(datos);
            bi = ImageIO.read(in);
            return bi;
        } catch (IOException ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bi;
    }

    public static byte[] traduce_to_byte(java.awt.image.BufferedImage ima) {
        if (ima == null) {
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = null;
        try {
            ImageIO.write(ima, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (Exception ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imageInByte;
    }

    public static byte[] leeByte(String fileName) {
        byte[] byt = null;
        try {
            RandomAccessFile f = new RandomAccessFile(fileName, "r");
            byt = new byte[(int) f.length()];
            f.read(byt);
            f.close();
        } catch (Exception ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byt;
    }

    public static void muestraCarpeta(java.io.File carp) {
        java.io.File fileopen = null;
        if (carp.isFile()) {
            String ruta = carp.getPath();
            int ro = ruta.lastIndexOf("\\");
            try {
                ruta = ruta.substring(0, ro);
                System.out.println(ruta);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            fileopen = new java.io.File(ruta);
        } else {
            fileopen = carp;
        }

        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("C:\\Windows\\explorer.exe " + fileopen.getPath());

        } catch (java.io.IOException ex) {
        }
    }

    public static String convsegaMS(int num) {
        String res = "00:00";
        if (num < 59) {
            return "00:" + doscifras(num);
        } else {
            int minu = (int) (num / 60);
            int desc = minu * 60;
            int nuv = num - desc;
            return doscifras(minu) + ":" + doscifras(nuv);
        }
    }

    public static void grabaError(String msg) {
        try {
            String archivo = "C:\\bioscan\\errores\\";
            java.io.File file = new java.io.File(archivo);
            if (file.exists() == false) {
                file.mkdir();
            }
            Date date = new Date();
            String linea = archivo + "error_" + date.getTime() + ".txt";
            System.out.println("Error  almacenado  en " + linea);
            java.io.File fileRecibe = new java.io.File(linea);
            java.io.FileWriter fstream = new java.io.FileWriter(linea);
            java.io.BufferedWriter out = new java.io.BufferedWriter(fstream);
            out.write(msg);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String doscifras(int num) {
        String res = "00";
        if (num < 10) {
            res = "0" + num;
        } else {
            res = num + "";
        }
        return res;
    }

    public static String numAlaterio() {
        String res = "";
        double x = Math.random();
        System.out.println("---" + x);
        res = x + "";
        res = res.substring(res.length() - 4, res.length());
        if (res.length() == 3) {
            res = res + "0";
        }
        return res;
    }

    public static String verfi(String dato) {
        String res = "";
        if (dato == null) {
            res = "";
        }
        return dato;
    }

    public static long tmNow() {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        return cal.getTimeInMillis() / 1000;
    }

    public static long tmNow2() {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        return cal.getTimeInMillis() / 1000;
    }

    public static long tiempocero(long tm) {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        cal.setTimeInMillis(tm * 1000);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        return cal.getTimeInMillis() / 1000;
    }

    public static String tmTraducionAAAAmmdd(long tm) {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        cal.setTimeInMillis(tm * 1000);
        cal.add(java.util.Calendar.HOUR, 1);
        return cal.get(java.util.Calendar.YEAR) + "-" + Herramientas.llenaDecimal(cal.get(java.util.Calendar.MONTH) + 1) + "-" + Herramientas.llenaDecimal(cal.get(java.util.Calendar.DAY_OF_MONTH));
    }

    public static String tmTraducionddmmAAAA(long tm) {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        cal.setTimeInMillis(tm * 1000);
        cal.add(java.util.Calendar.HOUR, 1);
        return dnum(cal.get(java.util.Calendar.DAY_OF_MONTH))
                + "-" + dnum(cal.get(java.util.Calendar.MONTH) + 1)
                + "-" + cal.get(java.util.Calendar.YEAR);
    }

    public static String tmTraducionddmmAA(long tm) {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(Herramientas.getTimezoneMexico());
        cal.setTimeInMillis(tm * 1000);
        cal.add(java.util.Calendar.HOUR, 1);

        String rees = cal.get(java.util.Calendar.YEAR) + "";

        rees = rees.substring(rees.length() - 2, rees.length());

        return dnum(cal.get(java.util.Calendar.DAY_OF_MONTH))
                + "-" + dnum(cal.get(java.util.Calendar.MONTH) + 1)
                + "-" + rees;
    }

    public static java.awt.Dimension getDimencionPantalla() {
        java.awt.Dimension dim = null;
        java.awt.Toolkit tool = java.awt.Toolkit.getDefaultToolkit();
        dim = tool.getScreenSize();
        dim.setSize(dim.getWidth(), dim.getHeight() - 30);
        return dim;
    }

    public static String rellenaCerrosIzq(String txt, int numChar, char car) {
        StringBuffer buf = new StringBuffer(numChar);
        char[] cart = txt.toCharArray();

        if (cart.length <= numChar) {
            int i = 0;
            for (i = 0; i < (numChar - cart.length); i++) {
                buf.insert(i, car);
            }
            int k = 0;
            for (int j = i; j < numChar; j++) {
                buf.insert(j, cart[k]);
                k++;
            }
        } else {
            return "error";
        }
        return buf.toString();
    }

    public static String conFechaFactura(String res) {
        // 2011-01-02T03:55:17;
        String rest = "error";
        String[] arr = res.split("-");
        if (arr.length > 0) {
            int mes = 0;
            try {
                mes = Integer.parseInt(arr[1]);
                mes = mes - 1;
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            int dia = 0;
            try {
                String diat = arr[2];
                int ro = diat.lastIndexOf("T");
                if (ro > 0) {
                    diat = diat.substring(0, ro);
                }
                dia = Integer.parseInt(diat);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            rest = dia + " de " + tmesC(mes) + " de " + arr[0];
        }
        return rest;
    }

    public static org.w3c.dom.Document cargaDocumendesdeString(String xml) {
        javax.xml.parsers.DocumentBuilder docBuilder = null;
        javax.xml.parsers.DocumentBuilderFactory docBuilderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document docum = null;
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            docum = docBuilder.parse(new InputSource(new java.io.StringReader(xml)));
        } catch (Exception e) {
            //       System.out.println("Mala configuracio  : " + e.getMessage());
            //        System.out.println("["+xml+"]");
            //      Herramientas.mensageGrafico(xml);
            return null;
        }
        return docum;
    }

    public static String hoyMx() {
        String res = "";
        java.util.Calendar calen = new java.util.GregorianCalendar();
        calen.setTimeZone(Herramientas.getTimezoneMexico());
        calen.set(java.util.Calendar.MINUTE, 0);
        calen.set(java.util.Calendar.SECOND, 0);
        calen.set(java.util.Calendar.HOUR, 0);
        int mes = calen.get(java.util.Calendar.MONTH) + 1;
        int ano = calen.get(java.util.Calendar.YEAR);
        res = calen.get(java.util.Calendar.DATE) + "-" + mes + "-" + ano;
        return res;
    }

    public static long tmdiasHoyAtras(int dias) {
        long tm = Herramientas.hoytm();
        long tmfin = 0;
        java.util.Calendar calen = new java.util.GregorianCalendar();
        calen.setTimeZone(Herramientas.getTimezoneMexico());
        calen.setTimeInMillis(tm * 1000);
        calen.set(java.util.Calendar.MINUTE, 0);
        calen.set(java.util.Calendar.SECOND, 0);
        calen.set(java.util.Calendar.HOUR, 0);
        dias = -1 * dias;
        calen.add(java.util.Calendar.DAY_OF_MONTH, dias);
        tmfin = calen.getTimeInMillis() / 1000;
        return tmfin;
    }

    public static String tmcal(java.util.Calendar calen) {
        String timpo = calen.get(java.util.Calendar.SECOND) + "";
        return timpo;
    }

    public static java.util.TimeZone getTimezoneMexico() {
        java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone("America/Tijuana");
        return timeZone;
    }

    public static String seXml(String nodo, String val) {
        String res = "";
        if (val.length() > 0) {
            res = " " + nodo + "=\"" + val + "\"";
        }
        return res;
    }

    public static String toUTF8(String text) {
        String str = "";
        try {
            // System.err.println(text);
            text = text.replaceAll("\"", "'");
            str = new String(text.trim().getBytes(), "UTF-8");

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
            str = "Error";
        }
        return str;
    }

    public static String conPz(float val, String pp) {
        String linPz = "";
        if (pp.equals("pz")) {
            int ii = (int) val;
            linPz = ii + "";
        } else {
            linPz = Herramientas.convierteMonedaSinComa(val);
        }
        return linPz;
    }

    public static String ver(String val) {
        if (val.length() > 0) {
            return val + "|";
        }
        return "";
    }

    public static String dnum(int num) {
        String res = "" + num;
        if (res.length() == 1) {
            return "0" + res;
        }
        return res;
    }

    public static String getZonaTiempoWindows() {
        java.util.Calendar now = java.util.Calendar.getInstance();
        java.util.TimeZone timeZone = now.getTimeZone();
        float num = now.get(java.util.Calendar.ZONE_OFFSET) / (1000 * 3600);
        int hor = (int) num;
        return "[" + timeZone.getID() + " " + hor + "]";
    }

    public static float redondeo(float numero) {
        return round(numero, 2);
    }

    public static String abreVentanaGraficaArchivo(java.io.File origen) throws Exception {
        String res = "";
        javax.swing.JFileChooser fc = null;
        fc = new javax.swing.JFileChooser(origen);
        int resval = fc.showOpenDialog(null);
        if (resval == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File arch = fc.getSelectedFile();
            res = leeArchivo(arch);
        }
        return res;
    }

    public class Filtro implements java.io.FileFilter {

        String terminacion = "";

        public Filtro(String terminacion) {
            setTerminacion(terminacion);
        }

        public String getTerminacion() {
            return terminacion;
        }

        public void setTerminacion(String terminacion) {
            this.terminacion = terminacion;
        }

        public boolean accept(File pathname) {
            return terminacion.endsWith(getTerminacion());
        }
    }

    public static String leeArchivo(java.io.File fileName)
            throws Exception {
        String Result = null;
        try {
            RandomAccessFile f = new RandomAccessFile(fileName, "r");
            byte b[] = new byte[(int) f.length()];
            f.read(b);
            Result = new String(b);
            f.close();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage() + "\n\n" + fileName);
        }
        return Result;
    }

    public static java.util.ArrayList<String> leeArchivoLinea(java.io.File fileName) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        java.util.ArrayList<String> arrLinea = new java.util.ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "iso-8859-1"));
        String line = "";
        while ((line = br.readLine()) != null) {
            arrLinea.add(line);
        }
        br.close();
        return arrLinea;
    }

    public static byte[] getFullBinary(String fileName)
            throws Exception {
        byte buffer[] = (byte[]) null;
        try {
            java.io.File oFile = new java.io.File(fileName);
            buffer = new byte[(int) oFile.length()];
            FileInputStream iStream = new FileInputStream(oFile);
            iStream.read(buffer, 0, (int) oFile.length());
            iStream.close();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage() + "\n\n" + fileName);
        }
        return buffer;
    }

    /*  
    public static int grabaArchivo(File source, File target)
            throws Exception {
        Herramientas.creaCarpetas(target.getPath());
        FileUtils.copyFile(source, target);
        return 1;
    }
     */
    public static int BinaryCompare(String filePivote, String fileToCompare)
            throws Exception {
        int Result = 0;
        byte bufPivote[] = (byte[]) null;
        byte bufFounded[] = (byte[]) null;
        try {
            bufPivote = getFullBinary(filePivote);
            bufFounded = getFullBinary(fileToCompare);
            boolean Iguales = (bufPivote.length == bufFounded.length);
            if (Iguales) {
                Result = 0;
                for (int i = 0; i < bufFounded.length; i++) {
                    Iguales = bufPivote[i] == bufFounded[i];
                    if (Iguales) {
                        continue;
                    }
                    Result = -1;
                    break;
                }

            } else {
                Result = bufPivote.length - bufFounded.length;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Result;
    }

    public static String decodificaLinea(String entrada) {
        String res = entrada;
        res = res.replaceAll("" + (char) 0, "<NUL>");
        res = res.replaceAll("" + (char) 1, "<SOH>");
        res = res.replaceAll("" + (char) 2, "<STX>");
        res = res.replaceAll("" + (char) 3, "<ETX>");
        res = res.replaceAll("" + (char) 4, "<EOT>");
        res = res.replaceAll("" + (char) 5, "<ENQ>");
        res = res.replaceAll("" + (char) 6, "<ACK>");
        res = res.replaceAll("" + (char) 7, "<BEL>");
        res = res.replaceAll("" + (char) 8, "<BS>");
        res = res.replaceAll("" + (char) 9, "<TAB>");
        res = res.replaceAll("" + (char) 10, "<LF>");
        res = res.replaceAll("" + (char) 11, "<VT>");
        res = res.replaceAll("" + (char) 12, "<FF>");
        res = res.replaceAll("" + (char) 13, "<CR>");
        res = res.replaceAll("" + (char) 14, "<SO>");
        res = res.replaceAll("" + (char) 15, "<SI>");
        res = res.replaceAll("" + (char) 16, "<DLE>");
        res = res.replaceAll("" + (char) 17, "<DC1>");
        res = res.replaceAll("" + (char) 18, "<DC2>");
        res = res.replaceAll("" + (char) 19, "<DC3>");
        res = res.replaceAll("" + (char) 20, "<DC4>");
        res = res.replaceAll("" + (char) 21, "<NAK>");
        res = res.replaceAll("" + (char) 22, "<SYN>");
        res = res.replaceAll("" + (char) 23, "<ETB>");
        res = res.replaceAll("" + (char) 24, "<CAN>");
        res = res.replaceAll("" + (char) 25, "<EM>");
        res = res.replaceAll("" + (char) 26, "<SUB>");
        res = res.replaceAll("" + (char) 27, "<ESC>");
        res = res.replaceAll("" + (char) 28, "<FS>");
        res = res.replaceAll("" + (char) 29, "<GS>");
        res = res.replaceAll("" + (char) 30, "<RS>");
        res = res.replaceAll("" + (char) 31, "<US>");
        res = res.replaceAll("" + (char) 10, "<LF>");
        return res;
    }

    public static String getPassword() {
        String res = "";
        double num = Math.random();
        res = num + "";
        try {
            res = res.substring(res.length() - 4, res.length());
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        String pas = res.trim();
        if (pas.length() < 4) {
            pas = getPassword();
        }
        return pas;
    }

    public static long tmLinux(String fecha) {
        long tm = 0;
        int dia = 0;
        int mes = 0;
        int ano = 0;
        String[] arr = fecha.split("-");
        java.util.Calendar cal = null;
        try {
            if (arr.length > 1) {
                ano = Integer.parseInt(arr[0]);
                mes = Integer.parseInt(arr[1]) - 1;
                dia = Integer.parseInt(arr[2]);
                java.util.TimeZone timezone = Herramientas.getTimezoneMexico();
                cal = new java.util.GregorianCalendar(ano, mes, dia, 0, 0, 0);
                cal.setTimeZone(timezone);
                tm = cal.getTimeInMillis() / 1000;
            }
        } catch (Exception e) {
            return hoytm();
        }
        return tm;
    }

    public static String hoynormal() {
        String hora = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = Herramientas.getTimezoneMexico();
        cal.setTimeZone(timezone);
        hora = dnum(cal.get(Calendar.DAY_OF_MONTH)) + "-" + dnum(cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

        return hora;
    }

    public static String hoyLinux() {
        String hora = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = Herramientas.getTimezoneMexico();
        cal.setTimeZone(timezone);
        hora = cal.get(Calendar.YEAR) + "-" + dnum(cal.get(Calendar.MONTH) + 1) + "-" + dnum(cal.get(Calendar.DAY_OF_MONTH));
        return hora;
    }

    public static String hoyLinuxTextHMS(java.util.Calendar cal) {
        String hora = "";
        hora = cal.get(Calendar.DAY_OF_MONTH)
                + "-" + tmes(cal.get(Calendar.MONTH))
                + "-" + cal.get(Calendar.YEAR);

        return hora;
    }

    public static String hoyLinuxTextIngHMS(java.util.Calendar cal) {
        String hora = "";
        hora = tmesI(cal.get(Calendar.MONTH))
                + "-" + cal.get(Calendar.DAY_OF_MONTH)
                + "-" + cal.get(Calendar.YEAR);

        return hora;
    }

    public static String hora(java.util.Calendar cal) {
        String hora = "";
        hora = dnum(cal.get(Calendar.HOUR_OF_DAY))
                + "" + dnum(cal.get(Calendar.MINUTE))
                + "" + dnum(cal.get(Calendar.SECOND));

        return hora;
    }

    public static String hoyddmmyy(java.util.Calendar cal) {
        String hora = "";
        hora = dnum(cal.get(Calendar.DAY_OF_MONTH))
                + dnum(cal.get(Calendar.MONTH) + 1)
                + (cal.get(Calendar.YEAR) + "").substring(2, 4);

        return hora;
    }

    public static String hoyLinuxHMS(java.util.Calendar cal) {
        String hora = "";
        hora = dnum(cal.get(Calendar.DAY_OF_MONTH))
                + "-" + dnum(cal.get(Calendar.MONTH) + 1)
                + "-" + cal.get(Calendar.YEAR) + " "
                + dnum(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                + dnum(cal.get(Calendar.MINUTE)) + ":"
                + dnum(cal.get(Calendar.SECOND));
        return hora;
    }

    public static String hoyLinuxHMSAM(java.util.Calendar cal) {
        String hora = "";
        String[] amTX = new String[]{"AM", "PM"};
        try {
            hora = dnum(cal.get(Calendar.DAY_OF_MONTH))
                    + "-" + dnum(cal.get(Calendar.MONTH) + 1)
                    + "-" + cal.get(Calendar.YEAR) + " "
                    + dnum(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                    + dnum(cal.get(Calendar.MINUTE)) + ":"
                    + dnum(cal.get(Calendar.SECOND)) + " " + amTX[Calendar.AM];
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return hora;
    }

    public static String numeroKilosSinde(String peso) {
        String relleno = "";
        peso = peso.replaceAll("\\.", "");
        for (int i = peso.length(); i < 5; i++) {
            relleno += "0";
        }
        return relleno + peso;
    }

    public static String pesosinpunto(String peso) {
        peso = peso.replaceAll("\\.", "");
        return peso;
    }

    public static String pesoAlibras(String kilo) {
        try {
            float lb = 0;
            lb = Float.parseFloat(kilo) * 2.20462F;
            return Herramientas.convierteMoneda((lb + "").toString().replaceAll(",", "."));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return 0 + "";
    }

    public static String llenaSinDec(String peso, int num) {
        peso = peso.replaceAll("\\.", "");
        peso = peso.replaceAll("\\,", "");
        String cero = "";
        try{
            for (int i = peso.length(); i < num; i++) {
                cero += "0";
            }
        }catch(Exception ee){
            ee.printStackTrace();
        }
        return cero + peso;
    }

    public static void main(String[] arr) {
        try {

            System.out.println(hoyddmmyy(new java.util.GregorianCalendar()));
            System.out.println(cFraccion2("10"));
            
            
        } catch (Exception ex) {
            Logger.getLogger(Herramientas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String hoyLinuxSinEspacion() {
        String hora = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = Herramientas.getTimezoneMexico();
        cal.setTimeZone(timezone);
        hora = dnum(cal.get(Calendar.DAY_OF_MONTH)) + dnum(cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.YEAR);
        return hora;
    }

    public static String calculoEdadLinux(int ano) {
        String res = "0000-00-00";
        if (ano > 0) {
            java.util.Calendar cal = new java.util.GregorianCalendar();
            java.util.TimeZone timezone = getTimezoneMexico();
            cal.setTimeZone(timezone);
            int anow = ano * -1;
            cal.add(java.util.Calendar.YEAR, anow);
            cal.add(java.util.Calendar.MONTH, 1);
            res = cal.get(Calendar.YEAR) + "-" + dnum(cal.get(Calendar.MONTH) + 1) + "-" + dnum(cal.get(Calendar.DAY_OF_MONTH));
        }
        return res;
    }

    public static int calculodeEdad(String fechaLinux) {
        //    System.out.println("fecha calculo " + fechaLinux);
        /*
         * Este programa calcula la edad *
         */
        int dia = 0;
        int mes = 0;
        int ano = 0;
        int anoo = 0;
        try {
            String[] arr = fechaLinux.split("-");
            if (arr.length == 0) {
                return 0;
            }
            mes = Integer.parseInt(arr[1]) - 1;
            ano = Integer.parseInt(arr[0]);
            dia = Integer.parseInt(arr[2]);
            java.util.Calendar today = new java.util.GregorianCalendar();
            java.util.TimeZone timezone = java.util.TimeZone.getTimeZone("GMT-6");
            today.setTimeZone(timezone);

            java.util.Calendar persona = new java.util.GregorianCalendar();
            persona.setTimeZone(timezone);

            persona.set(java.util.Calendar.MONTH, mes);
            persona.set(java.util.Calendar.DAY_OF_MONTH, dia);
            persona.set(java.util.Calendar.YEAR, ano);

            //    System.out.println("Obtiene" + persona.get(java.util.Calendar.DAY_OF_MONTH) + " "
            //            + persona.get(java.util.Calendar.MONTH) + " " + persona.get(java.util.Calendar.YEAR));
            anoo = today.get(java.util.Calendar.YEAR) - persona.get(java.util.Calendar.YEAR);
            if (persona.get(java.util.Calendar.MONTH) == today.get(java.util.Calendar.MONTH)) {
                if (today.get(java.util.Calendar.DAY_OF_MONTH) <= persona.get(java.util.Calendar.DAY_OF_MONTH)) {
                    anoo = anoo - 1;
                    return anoo;
                }
            } else {
                if (persona.get(java.util.Calendar.MONTH) > today.get(java.util.Calendar.MONTH)) {
                    anoo = anoo - 1;
                    return anoo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anoo;
    }

    public static int creaCarpetas(String ruta) {
        int res = 0;
        String dir = "";
        try {
            int ro = ruta.lastIndexOf("\\");
            if (ro > 0) {
                dir = ruta.substring(0, ro);
            }
            if (dir.length() > 0) {
                java.io.File file = new java.io.File(dir);
                if (file.exists() == false) {
                    file.mkdirs();
                }
                res = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static java.awt.image.BufferedImage getBufferedImage(java.awt.Image ima) {
        int width = ima.getWidth(null);
        int height = ima.getHeight(null);
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(ima, 0, 0, null);
        return bi;
    }

    public static java.util.Properties propiedadesBasicas(String xml) throws Exception {
        java.util.Properties prop = new java.util.Properties();
        java.io.FileInputStream fis = null;
        fis = new java.io.FileInputStream(xml);
        prop.loadFromXML(fis);
        fis.close();
        return prop;
    }

    public static java.awt.Image getImageLocal(java.io.File archivo) {
        java.awt.image.BufferedImage img = null;
        try {
            img = javax.imageio.ImageIO.read(archivo);
        } catch (java.io.IOException e) {
        }
        return (java.awt.Image) img;
    }

    public static byte[] traduce_to_byte(java.awt.Image ima) {
        byte[] byt = new byte[255];
        /*
         java.awt.image.BufferedImage imab = null;
         imab = getBufferedImage(ima);
        
         java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
         com.sun.image.codec.jpeg.JPEGImageEncoder encoder =
         com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(os);
         try {
         encoder.encode(imab);
         byt = os.toByteArray();
         } catch (java.io.IOException f) {
         System.out.println("NO hay imagem");
         }
         */
        return byt;
    }

    private javax.swing.ImageIcon getIco(String ruta) {
        javax.swing.ImageIcon icon = null;
        String rut = "icon/" + ruta;
        try {
            icon = new javax.swing.ImageIcon(getClass().getResource(rut));
        } catch (Exception e) {
            System.out.println(rut + "\n" + "No se  cargo la icon en la ruta " + e.getMessage());
        }
        return icon;
    }

    /*
    public void copyFileJar(String ultxt, File dest) throws IOException {
        //"/absolute/path/of/source/in/jar/file"
        URL inputUrl = getClass().getResource(ultxt);
        FileUtils.copyURLToFile(inputUrl, dest);
    }
     */
    public static java.util.Calendar hoy() {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        return cal;
    }

    public static java.util.Calendar now() {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        return cal;
    }

    public static long hoytm() {
        long tm = 0;
        java.util.Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = getTimezoneMexico();
        cal.setTimeZone(timezone);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        return cal.getTimeInMillis() / 1000;
    }

    public static String horaLarga() {
        String hora = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = getTimezoneMexico();
        cal.setTimeZone(timezone);
        hora = cal.get(Calendar.DAY_OF_MONTH) + " - " + tmes(cal.get(Calendar.MONTH)) + " -" + cal.get(Calendar.YEAR) + "  " + cal.get(Calendar.HOUR_OF_DAY) + ":" + llenaDecimal(cal.get(Calendar.MINUTE));
        return hora;
    }

    public static javax.swing.ImageIcon getIcono(String ruta) {
        javax.swing.ImageIcon icono = null;
        try {
            icono = new javax.swing.ImageIcon(getImage(ruta));
        } catch (Exception e) {
            System.out.println("Error  en la Icono  " + ruta);
        }
        return icono;
    }

    public static java.awt.Image getImage(String ruta) {
        java.awt.Image ima = null;
        try {
            Herramientas her = new Herramientas();
            ima = her.getIco(ruta).getImage();
        } catch (Exception e) {
            System.out.println("No se  cargo la imagem en la ruta");
            System.out.println(ruta);
        }
        return ima;
    }

    public static java.awt.Image getImage(String ruta, int x, int y) {
        java.awt.Image ima = null;
        try {
            Herramientas her = new Herramientas();
            ima = her.getIco(ruta).getImage();
            ima = ima.getScaledInstance(x, y, java.awt.Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println("No se  cargo la imagem en la ruta");
            System.out.println(ruta);
        }
        return ima;
    }

 

    public static java.awt.Point puntoCentroPag(int xx, int yy) {
        java.awt.Point point = new java.awt.Point();
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        java.awt.Dimension dim = toolkit.getScreenSize();
        int x = (int) ((float) dim.getWidth() / 2) - (int) ((float) xx / 2);
        int y = (int) ((float) dim.getHeight() / 2) - (int) ((float) yy / 2);
        point.setLocation(x, y);
        return point;
    }

    public static java.awt.Point puntoCentroPag(java.awt.Dimension dimP) {
        java.awt.Point point = new java.awt.Point();
        double yy = dimP.getHeight();
        double xx = dimP.getWidth();
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        java.awt.Dimension dim = toolkit.getScreenSize();
        int x = (int) ((float) dim.getWidth() / 2) - (int) ((float) xx / 2);
        int y = (int) ((float) dim.getHeight() / 2) - (int) ((float) yy / 2);
        point.setLocation(x, y);
        return point;
    }

    public static void imprimePfd(java.io.File pdf) {
        System.out.println(pdf.getPath());
        String ima = pdf.getAbsolutePath();
        Process p;
        try {
            p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ima);
            p.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static javax.swing.ImageIcon getIcono(String ruta, int x, int y) {
        java.awt.Image ima = null;
        try {
            Herramientas her = new Herramientas();
            ima = her.getIco(ruta).getImage();
        } catch (Exception e) {
            System.out.println("No se  cargo la imagem en la ruta");
            System.out.println(ruta);
        }
        javax.swing.ImageIcon icon2 = null;
        try {
            icon2 = new javax.swing.ImageIcon(ima.getScaledInstance(x, y, java.awt.Image.SCALE_DEFAULT));
        } catch (Exception e) {
            System.out.println("Error");
        }
        return icon2;
    }

    public static javax.swing.border.TitledBorder getBorder(String title) {
        javax.swing.border.TitledBorder border
                = javax.swing.BorderFactory.createTitledBorder(title);
        return border;
    }

    public static String fechaCompletaHoy() {
        String line = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        long tm = cal.getTimeInMillis() / 1000;
        line = convierteFecha(tm);
        return line;
    }

    public static String fechaCompletaHoyLarga() {
        String line = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());

        line = "" + cal.get(Calendar.DAY_OF_MONTH) + "/" + dnum(cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + dnum(cal.get(Calendar.MINUTE));

        return line;
    }

    public static java.util.Calendar fechaLinux(String date) {
        java.util.Calendar cal = null;
        String line = "";
        String[] aee = date.split("-");
        int dia = 0;
        int mes = 0;
        int ano = 0;
        try {
            dia = Integer.parseInt(aee[2]);
            mes = Integer.parseInt(aee[1]) - 1;
            ano = Integer.parseInt(aee[0]);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (ano > 1000) {
            cal = new java.util.GregorianCalendar(ano, mes, dia, 0, 0, 0);
            cal.setTimeZone(getTimezoneMexico());
        } else {
            cal = Herramientas.hoy();
        }
        return cal;
    }

    public static String mmReglog(int mm) {
        String tf = "00:00";
        float t = (float) mm;
        float min = t / 60;
        float seg = t - (min * 60) / 60;
        return min + ":" + seg;
    }

    public static String convieMH(java.util.Calendar cal) {
        String res = "";
        res = cal.get(Calendar.HOUR_OF_DAY) + ":" + llenaDecimal(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convierteFecha(java.util.Calendar cal) {
        String res = "";
        res = cal.get(Calendar.DAY_OF_MONTH) + "-" + tmes(cal.get(Calendar.MONTH)) + "-" + cal.get(Calendar.YEAR);
        return res;
    }

    public static String convierteFechaMysql(java.util.Calendar cal) {
        String res = "";
        res = cal.get(Calendar.YEAR)
                + "-"
                + dnum(cal.get(Calendar.MONTH) + 1)
                + "-"
                + dnum(cal.get(Calendar.DAY_OF_MONTH))
                + " "
                + dnum(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                + dnum(cal.get(Calendar.MINUTE)) + ":"
                + dnum(cal.get(Calendar.SECOND));
        return res;
    }

    public static String convierteFechaLin(java.util.Calendar cal) {
        String res = "";
        res = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.DAY_OF_MONTH) + 1 + "-" + Calendar.DAY_OF_WEEK_IN_MONTH;
        return res;
    }

    public static void mensageGrafico(String msg) {
        javax.swing.JOptionPane.showMessageDialog(null, "<html>" + msg + "</html>");
    }

    public static String convierteFecha(long tm) {
        String res = "";
        Calendar cal = new java.util.GregorianCalendar(getTimezoneMexico());
        cal.setTimeInMillis(tm);
        res = cal.get(Calendar.DAY_OF_MONTH) + "-" + tmes(cal.get(Calendar.MONTH)) + "-" + (cal.get(Calendar.YEAR) + "").substring(2, 4);
        return res;
    }

    public static String arrInplode(java.util.ArrayList<String> arr) {
        String res = "";
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                res = res + arr.get(i) + ",";
            }
        } else {
            return "";
        }
        int tam = res.length();
        if (tam > 0) {
            res = res.substring(0, tam - 1);
        }
        return res;
    }

    public static String convierteFechaHHmm(long tm) {
        String res = "";
        tm = tm * 1000;
        Calendar cal = new java.util.GregorianCalendar(getTimezoneMexico());
        cal.setTimeInMillis(tm);
        res = dnum(cal.get(Calendar.HOUR_OF_DAY)) + ":" + dnum(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convierteFecha2(long tm) {
        String res = "";
        tm = tm * 1000;
        java.util.TimeZone timezone = getTimezoneMexico();
        int cat = timezone.getDSTSavings();
        Calendar cal = new java.util.GregorianCalendar(timezone);
        cal.setTimeInMillis(tm);

        res = cal.get(Calendar.DAY_OF_MONTH) + "-" + tmes(cal.get(Calendar.MONTH)) + "-" + (cal.get(Calendar.YEAR) + "").substring(2, 4) + " " + dnum(cal.get(Calendar.HOUR_OF_DAY)) + ":" + dnum(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convierteFechaDMA(long tm) {
        String res = "";

        java.util.TimeZone timezone = getTimezoneMexico();

        Calendar cal = new java.util.GregorianCalendar(timezone);
        cal.setTimeInMillis(tm);
        res = cal.get(Calendar.DAY_OF_MONTH) + "-" + tmes(cal.get(Calendar.MONTH)) + "-" + (cal.get(Calendar.YEAR) + "");
        return res;
    }

    public static String conviertHM(long tm) {
        String res = "";
        tm = tm * 1000;
        java.util.TimeZone timezone = getTimezoneMexico();
        Calendar cal = new java.util.GregorianCalendar(timezone);
        cal.setTimeInMillis(tm);
        res = cal.get(Calendar.HOUR_OF_DAY) + ":" + dnum(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convAgenda(long tm) {
        String res = "";
        tm = tm * 1000;
        Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        cal.setTimeInMillis(tm);
        res = cal.get(Calendar.DAY_OF_MONTH) + " - " + tmes(cal.get(Calendar.MONTH)) + " -" + (cal.get(Calendar.YEAR) + "").substring(2, 4) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + llenaDecimal(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convierteFechaDDMMYYHm(long tm) {
        String res = "";
        if (tm == 0) {
            return "---";
        }
        tm = tm * 1000;
        Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        cal.setTimeInMillis(tm);
        res = dnum(cal.get(Calendar.DAY_OF_MONTH))
                + "-" + dnum(cal.get(Calendar.MONTH) + 1)
                + "-" + dnum(cal.get(Calendar.YEAR))
                + " " + dnum(cal.get(Calendar.HOUR_OF_DAY))
                + ":" + dnum(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String cFecDdMmYyNetsuite(Calendar cal) {
        String res = dnum(cal.get(Calendar.DAY_OF_MONTH))
                + "/" + dnum(cal.get(Calendar.MONTH) + 1)
                + "/" + dnum(cal.get(Calendar.YEAR));
        return res;
    }

    public static String fechaDeCalulosEtiqHM(Calendar cal) {
        String res = dnum(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                + dnum(cal.get(Calendar.MINUTE)) + ":" + dnum(cal.get(Calendar.SECOND));
        return res;
    }

    public static String fechaDeCalulosEtiq(Calendar cal) {
        String res = dnum(cal.get(Calendar.DAY_OF_MONTH)) + "-"
                + dnum(cal.get(Calendar.MONTH) + 1) + "-" + dnum(cal.get(Calendar.YEAR));
        return res;
    }

    public static String fechaDeCalulos(Calendar cal) {
        String res = dnum(cal.get(Calendar.YEAR))
                + dnum(cal.get(Calendar.MONTH) + 1)
                + dnum(cal.get(Calendar.DAY_OF_MONTH));
        return res;
    }

    public static Integer conInt(String lin, int maxc) {
        int res = 0;
        try {
            if (lin.length() > maxc) {
                lin = lin.substring(0, maxc);
            }
            res = Integer.parseInt(lin);
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return res;
    }

    public static java.util.Calendar getTmNetsuite(String time) {
        time = time.replaceAll("-", "/");
        int dia = 0;
        int mes = 0;
        int ano = 0;
        java.util.GregorianCalendar go = new java.util.GregorianCalendar();
        java.util.Calendar cal = go;
        cal.setTimeZone(getTimezoneMexico());
        cal.set(java.util.Calendar.MILLISECOND, 0);

        String[] arEspacios = time.split(" ");
        String[] arTime = arEspacios[0].split("\\/");
        if (arTime != null) {
            if (arTime.length > 2) {
                dia = conInt(arTime[0], 2);
                mes = conInt(arTime[1], 2);
                ano = conInt(arTime[2], 4);
                cal.set(java.util.Calendar.DAY_OF_MONTH, dia);
                cal.set(java.util.Calendar.MONTH, mes - 1);
                cal.set(java.util.Calendar.YEAR, ano);
            }
        }
        try {
            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.set(java.util.Calendar.SECOND, 0);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        System.out.println(dia + "-" + mes + "-" + ano + " " + cal.getTimeInMillis());
        return cal;
    }

    public static long getTmNetsuiteTM(String time) {
        time = time.replaceAll("-", "/");

        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        String[] arEspacios = time.split(" ");
        String[] arTime = arEspacios[0].split("\\/");
        if (arTime != null) {
            if (arTime.length > 2) {
                int dia = conInt(arTime[0], 2);
                int mes = conInt(arTime[1], 2);
                int ano = conInt(arTime[2], 4);
                cal.set(java.util.Calendar.DAY_OF_MONTH, dia);
                cal.set(java.util.Calendar.MONTH, mes - 1);
                cal.set(java.util.Calendar.YEAR, ano);
            }
        }
        if (arEspacios.length > 1) {
            try {
                String[] arTimeMin = arEspacios[1].split(":");
                int horas = conInt(arTimeMin[0], 2);
                int min = conInt(arTimeMin[1], 2);
                cal.set(java.util.Calendar.HOUR_OF_DAY, horas);
                cal.set(java.util.Calendar.MINUTE, min);
                cal.set(java.util.Calendar.SECOND, 0);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return cal.getTimeInMillis() / 1000;
    }

    public static String convierteFechaDDMMYYHm(Calendar cal) {
        String res = dnum(cal.get(Calendar.DAY_OF_MONTH))
                + "-" + dnum(cal.get(Calendar.MONTH) + 1)
                + "-" + dnum(cal.get(Calendar.YEAR))
                + " " + dnum(cal.get(Calendar.HOUR_OF_DAY))
                + ":" + dnum(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convierteFechaDDMMYYHmSS(Calendar cal) {
        String res = dnum(cal.get(Calendar.DAY_OF_MONTH))
                + "-" + dnum(cal.get(Calendar.MONTH) + 1)
                + "-" + dnum(cal.get(Calendar.YEAR))
                + " " + dnum(cal.get(Calendar.HOUR_OF_DAY))
                + ":" + dnum(cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND);
        return res;
    }

    public static String convierteFechaCompleta(long tm) {
        String res = "";
        if (tm == 0) {
            return "---";
        }
        tm = tm * 1000;
        Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = getTimezoneMexico();
        cal.setTimeZone(timezone);
        cal.setTimeInMillis(tm);
        res = cal.get(Calendar.DAY_OF_MONTH) + "-" + tmes(cal.get(Calendar.MONTH)) + "-" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + llenaDecimal(cal.get(Calendar.MINUTE));
        return res;
    }

    public static String convierteFechaDMY(long tm) {
        String res = "";
        if (tm == 0) {
            return "---";
        }
        tm = tm * 1000;
        Calendar cal = new java.util.GregorianCalendar();
        java.util.TimeZone timezone = getTimezoneMexico();
        cal.setTimeZone(timezone);
        cal.setTimeInMillis(tm);
        res = dnum(cal.get(Calendar.DAY_OF_MONTH)) + "-" + dnum(cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        return res;
    }

    public static String formatNetSuiteDosDecima(String num) {
        double mon = 0;
        try {
            mon = Double.parseDouble(num);
        } catch (Exception e) {
        }
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setCurrencySymbol("");
        String strange = "##########0.##";
        DecimalFormat currencyFormat = new DecimalFormat(strange, unusualSymbols);
        return currencyFormat.format(mon);
    }

    public static String formatNetSuiteDosDecimaArchivo(java.io.File num) {
        double mon = 0;
        try {
            mon = (double) (Math.ceil(num.length() / 1000));
        } catch (Exception e) {
        }
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setCurrencySymbol("");
        String strange = "##,###,###,##0";
        DecimalFormat currencyFormat = new DecimalFormat(strange, unusualSymbols);
        return currencyFormat.format(mon);
    }

    public static double formatNetSuiteDosDecimaDoble(double num) {

        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setCurrencySymbol("");

        String strange = "##########0.00";
        DecimalFormat currencyFormat = new DecimalFormat(strange, unusualSymbols);
        return Double.parseDouble(currencyFormat.format(num));
    }

    public static String formatNetSuiteCuatro(String num) {
        double mon = 0;
        try {
            mon = Double.parseDouble(num);
        } catch (Exception e) {
        }
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setCurrencySymbol("");

        String strange = "##########0.0000";
        DecimalFormat currencyFormat = new DecimalFormat(strange, unusualSymbols);
        return currencyFormat.format(mon);
    }

    public static String conFcNetsuiteEtiq(Calendar cal) {
        String res = "";
        if (cal == null) {
            return "-";
        }
        res = dnum(cal.get(Calendar.DAY_OF_MONTH)) + dnum(cal.get(Calendar.MONTH) + 1)
                + cal.get(Calendar.YEAR) + ","
                + dnum(cal.get(Calendar.HOUR_OF_DAY))
                + dnum(cal.get(Calendar.MINUTE))
                + dnum(cal.get(Calendar.SECOND));
        return res;
    }

    public static String conFcNetsuite(Calendar cal) {
        String res = "";
        if (cal == null) {
            return "-";
        }
        res = dnum(cal.get(Calendar.DAY_OF_MONTH)) + "-" + dnum(cal.get(Calendar.MONTH) + 1)
                + "-" + cal.get(Calendar.YEAR);
        return res;
    }

    public static java.util.Calendar tmCalCero(long tm) {
        java.util.Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(java.util.TimeZone.getTimeZone("GMT-6"));
        cal.setTimeInMillis(tm * 1000);
        //int dia2 = cal.get(Calendar.DAY_OF_MONTH);
        //int mes2 = cal.get(Calendar.MONTH);
        //int ano2 = cal.get(Calendar.YEAR);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        return cal;
    }

    public static String convierteFechaCompletaFacturas(long tm) {
        String res = "";
        tm = tm * 1000;
        Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(getTimezoneMexico());
        cal.setTimeInMillis(tm);
        res = cal.get(Calendar.DAY_OF_MONTH) + " DE " + tmesC(cal.get(Calendar.MONTH)) + " DE " + cal.get(Calendar.YEAR);
        return res.toUpperCase();
    }

    public static String convierteFechaHora(long tm) {
        String res = "";
        if (tm == 0) {
            return "-";
        }
        tm = tm * 1000;
        try {
            Calendar cal = new java.util.GregorianCalendar();
            cal.setTimeZone(getTimezoneMexico());
            cal.setTimeInMillis(tm);
            //if (timezone.getDSTSavings() > 0) {
            //       cal.add(-1,Calendar.HOUR );
            //}
            res = cal.get(Calendar.HOUR_OF_DAY) + ":" + dnum(cal.get(Calendar.MINUTE));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return res;
    }

    public static float round(float Rval, int Rpl) {
        float p = (float) Math.pow(10, Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float) tmp / p;
    }

    public static void grabaArchivoByte(byte[] buffer, String nomeArchivo) {
        try {
            creaCarpetas(nomeArchivo);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(nomeArchivo);
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static java.awt.Image imagenLogoBioscan() {
        java.awt.Color bioColor = new java.awt.Color(0, 50, 160, 90);
        java.awt.image.BufferedImage buffImg = new java.awt.image.BufferedImage(30, 30,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = buffImg.createGraphics();
        g2d.setColor(bioColor);
        g2d.fillRect(0, 0, 30, 30);
        java.awt.Color verde = new java.awt.Color(144, 238, 144);
        int px = 0;
        int py = 0;
        g2d.setColor(verde);
        for (int i = 0; i < 6; i++) {
            px = 6 * i;
            g2d.fillRoundRect(px, 4, 5, 3, 2, 2);
            g2d.fillOval(px + 2, 8, 5, 5);
        }
        g2d.setColor(java.awt.Color.white);
        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.ITALIC, 6);
        g2d.setFont(font);
        g2d.drawString("BIOSCAN", 2, 20);
        return (java.awt.Image) buffImg;
    }

    public static String tmesC(int mes) {
        String resul = "Error";
        switch (mes) {
            case 0:
                resul = "Enero";
                break;
            case 1:
                resul = "Febrero";
                break;
            case 2:
                resul = "Marzo";
                break;
            case 3:
                resul = "Abril";
                break;
            case 4:
                resul = "Mayo";
                break;
            case 5:
                resul = "Junio";
                break;
            case 6:
                resul = "Julio";
                break;
            case 7:
                resul = "Agosto";
                break;
            case 8:
                resul = "Septiembre";
                break;
            case 9:
                resul = "Octubre";
                break;
            case 10:
                resul = "Noviembre";
                break;
            case 11:
                resul = "Diciembre";
                break;
        }
        return resul.toUpperCase();
    }

    public static String tmes(int mes) {
        String resul = "Error";
        switch (mes) {
            case 0:
                resul = "Ene";
                break;
            case 1:
                resul = "Feb";
                break;
            case 2:
                resul = "Mar";
                break;
            case 3:
                resul = "Abr";
                break;
            case 4:
                resul = "May";
                break;
            case 5:
                resul = "Jun";
                break;
            case 6:
                resul = "Jul";
                break;
            case 7:
                resul = "Ago";
                break;
            case 8:
                resul = "Sep";
                break;
            case 9:
                resul = "Oct";
                break;
            case 10:
                resul = "Nov";
                break;
            case 11:
                resul = "Dic";
                break;
        }
        return resul.toUpperCase();
    }

    public static String tmesI(int mes) {
        String resul = "Error";
        switch (mes) {
            case 0:
                resul = "Jan";
                break;
            case 1:
                resul = "Feb";
                break;
            case 2:
                resul = "Mar";
                break;
            case 3:
                resul = "Apr";
                break;
            case 4:
                resul = "May";
                break;
            case 5:
                resul = "Jun";
                break;
            case 6:
                resul = "Jul";
                break;
            case 7:
                resul = "Ago";
                break;
            case 8:
                resul = "Sep";
                break;
            case 9:
                resul = "Oct";
                break;
            case 10:
                resul = "Nov";
                break;
            case 11:
                resul = "Dec";
                break;
        }
        return resul.toUpperCase();
    }

    public static String llenaDecimal(float num) {
        String dat = "";
        if (num < 9) {
            dat = "0" + num;
        } else {
            dat = "" + num;
        }
        return dat + "";
    }

    public static String llenaDecimal(double can) {
        float num = (float) can;
        return llenaDecimal(num);
    }

    public static String llenaDecimal(int can) {
        float num = (float) can;
        return llenaDecimal(num);
    }

    public static String HoraMinuto(java.util.Calendar cal) {
        String res = "";
        int min = cal.get(java.util.Calendar.MINUTE);
        String mint = "";
        if (min < 9) {
            mint = "0" + min;
        } else {
            mint = min + "";
        }
        res = cal.get(java.util.Calendar.HOUR_OF_DAY) + ":"
                + mint;
        return res;
    }

    public static String traduceMinutosSeg(int seg) {
        String res = "";
        int minu = (int) (seg / 60);
        int sed = seg - (minu * 60);
        res = minu + ":" + dnum(sed);
        return res;
    }

    public static String traduceMn(float monto) {
        return traduceMn(monto + "");
    }

    public static String traduceMn(String monto) {
        String resul = "";
        if (monto.length() == 0) {
            return "";
        }
        double total = 0;
        try {
            total = Double.parseDouble(monto);
        } catch (Exception e) {
            return "Error";
        }

        String t7 = "";
        String t6 = "";
        String t5 = "";
        String t4 = "";
        String t3 = "";
        String t2 = "";
        String t1 = "";
        String mil = "";
        String y1 = "";
        String y2 = "";
        int p7, p6, p5, p4, p3, p2, p1 = 0;
        String centavos = "00";
        p7 = (int) (total / 1000000);
        total = total - (p7 * 1000000);
        p6 = (int) (total / 100000);
        total = total - (p6 * 100000);
        p5 = (int) (total / 10000);
        total = total - (p5 * 10000);
        p4 = (int) (total / 1000);
        total = total - (p4 * 1000);
        p3 = (int) (total / 100);
        total = total - (p3 * 100);
        p2 = (int) (total / 10);
        total = total - (p2 * 10);
        p1 = (int) (total / 1);
        centavos = llenaDecimal((total - p1 * 1) * 100);
        switch (p7) {
            case 9:
                t7 = "NUEVE MILLONES ";
                break;
            case 8:
                t7 = "OCHO MILLONES ";
                break;
            case 7:
                t7 = "SIETE MILLONES ";
                break;
            case 6:
                t7 = "SEIS MILLONES ";
                break;
            case 5:
                t7 = "CINCO MILLONES ";
                break;
            case 4:
                t7 = "CUATRO MILLONES ";
                break;
            case 3:
                t7 = "TRES MILLONES ";
                break;
            case 2:
                t7 = "DOS MILLONES ";
                break;
            case 1:
                t7 = "UN MILLON ";
                break;
        }
        // miles
        switch (p6) {
            case 9:
                t6 = "NOVECIENTOS ";
                break;
            case 8:
                t6 = "OCHOCIENTOS ";
                break;
            case 7:
                t6 = "SETECIENTOS ";
                break;
            case 6:
                t6 = "SEISCIENTOS ";
                break;
            case 5:
                t6 = "QUINIENTOS ";
                break;
            case 4:
                t6 = "CUATROCIENTOS ";
                break;
            case 3:
                t6 = "TRESCIENTOS ";
                break;
            case 2:
                t6 = "DOSCIENTOS ";
                break;
            case 1:
                t6 = "CIENTO ";
                break;
        }
//  NOVECIENTOS
        switch (p5) {
            case 9:
                t5 = "NOVENTA ";
                break;
            case 8:
                t5 = "OCHENTA ";
                break;
            case 7:
                t5 = "SETENTA ";
                break;
            case 6:
                t5 = "SESENTA ";
                break;
            case 5:
                t5 = "CINCUENTA ";
                break;
            case 4:
                t5 = "CUARENTA ";
                break;
            case 3:
                t5 = "TREINTA ";
                break;
            case 2:
                t5 = "VEINTE ";
                break;
            case 1:
                t5 = "DIEZ ";
                break;
        }

// DECENAS
        switch (p4) {
            case 9:
                t4 = "NUEVE ";
                break;
            case 8:
                t4 = "OCHO ";
                break;
            case 7:
                t4 = "SIETE ";
                break;
            case 6:
                t4 = "SEIS ";
                break;
            case 5:
                t4 = "CINCO ";
                break;
            case 4:
                t4 = "CUATRO ";
                break;
            case 3:
                t4 = "TRES ";
                break;
            case 2:
                t4 = "DOS ";
                break;
            case 1:
                t4 = "UN ";
                break;
        }

        if (p4 >= 1 && p5 >= 1) {
            y2 = " Y ";
        }

        if (p4 >= 1 || p5 >= 1 || p6 >= 1) {
            mil = " MIL ";
        }
        if (p4 == 0 && p5 == 0 && p6 == 1) {
            mil = "CIEN MIL ";
            t6 = "";
            t5 = "";
            t4 = "";
        }

// -----------------------------------------------------
        switch (p3) {
            case 9:
                t3 = "NOVECIENTOS ";
                break;
            case 8:
                t3 = "OCHOCIENTOS ";
                break;
            case 7:
                t3 = "SETECIENTOS ";
                break;
            case 6:
                t3 = "SEISCIENTOS ";
                break;
            case 5:
                t3 = "QUINIENTOS ";
                break;
            case 4:
                t3 = "CUATROCIENTOS ";
                break;
            case 3:
                t3 = "TRESCIENTOS ";
                break;
            case 2:
                t3 = "DOSCIENTOS ";
                break;
            case 1:
                t3 = "CIENTO ";
                break;
        }

//  NOVECIENTOS
        switch (p2) {
            case 9:
                t2 = "NOVENTA ";
                break;
            case 8:
                t2 = "OCHENTA ";
                break;
            case 7:
                t2 = "SETENTA ";
                break;
            case 6:
                t2 = "SESENTA ";
                break;
            case 5:
                t2 = "CINCUENTA ";
                break;
            case 4:
                t2 = "CUARENTA ";
                break;
            case 3:
                t2 = "TREINTA ";
                break;
            case 2:
                t2 = "VEINTE ";
                break;
            case 1:
                t2 = "DIEZ ";
                break;
        }

        if (p2 == 1 && p1 == 0) {
            t2 = "DIEZ ";
        }

// DECENAS
        switch (p1) {
            case 9:
                t1 = "NUEVE ";
                break;
            case 8:
                t1 = "OCHO ";
                break;
            case 7:
                t1 = "SIETE ";
                break;
            case 6:
                t1 = "SEIS ";
                break;
            case 5:
                t1 = "CINCO ";
                break;
            case 4:
                t1 = "CUATRO ";
                break;
            case 3:
                t1 = "TRES ";
                break;
            case 2:
                t1 = "DOS ";
                break;
            case 1:
                t1 = "UN";
                break;
        }

        if (p1 >= 1 && p2 >= 1) {
            y1 = " Y ";
        }
        if (p1 == 1 && p2 == 1) {
            t1 = "ONCE ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 2 && p2 == 1) {
            t1 = "DOCE ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 3 && p2 == 1) {
            t1 = "TRECE ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 4 && p2 == 1) {
            t1 = "CATORCE ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 5 && p2 == 1) {
            t1 = "QUINCE ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 6 && p2 == 1) {
            t1 = "DIECISEIS ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 7 && p2 == 1) {
            t1 = "DIECISIETE ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 8 && p2 == 1) {
            t1 = "DIECIOCHO ";
            t2 = "";
            y1 = "";
        }
        if (p1 == 9 && p2 == 1) {
            t2 = "";
            t1 = "";
            y1 = "DIECINUEVE ";
        }
//-----------------------------------------
        if (p4 == 9 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "DIECINUEVE ";
        }
        if (p4 == 1 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "ONCE ";
        }
        if (p4 == 2 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "DOCE ";
        }
        if (p4 == 3 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "TRECE ";
        }
        if (p4 == 4 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "CATORCE ";
        }
        if (p4 == 5 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "QUINCE ";
        }
        if (p4 == 6 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "DIECISEIS ";
        }
        if (p4 == 7 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "DIECISIETE ";
        }
        if (p4 == 8 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "DIECIOCHO ";
        }
        if (p4 == 9 && p5 == 1) {
            t4 = "";
            t5 = "";
            y2 = "DIECINUEVE ";
        }
//------------------------------------------
        if (p2 == 0 && p3 == 1 && p1 == 0) {
            t1 = "CIEN";
            t2 = "";
            t3 = "";
        }
//------------------------------------------------------
        if (p1 == 5 && p2 == 1) {
            t2 = "";
            t1 = "";
            y1 = "QUINCE ";
        }
        if (p4 == 5 && p5 == 1) {
            t5 = "";
            t4 = "QUINCE ";
            y2 = "";
        }
//traducido= "(".ltrim(t7.t6.t5.y2.t4.mil.t3.t2.y1.t1." PESOS ".centavos."/100 M.N.)");
        resul = "(" + (rev(t7) + rev(t6) + rev(t5) + rev(y2) + rev(t4) + mil + rev(t3) + rev(t2) + rev(y1) + rev(t1)).trim() + " PESOS " + centavos + "/100 M.N.)";
        resul = resul.replaceAll("  ", " ");
        System.out.println(resul + "-->" + monto);
        return resul;
    }

    private static String rev(String val) {
        String res = "";
        if (val.trim().length() > 0) {
            res = val.trim() + " ";
        }
        return res;
    }

    public static void mensageError(String msg) {
        javax.swing.JOptionPane.showMessageDialog(null, "<html><b>Error</b><br>" + msg + "</html>");
    }

    public static int mensajeOpcion(String aviso) {
        int respuesta = 0;
        respuesta = javax.swing.JOptionPane.showConfirmDialog(null, aviso, "Pregunta", javax.swing.JOptionPane.YES_NO_OPTION);
        return respuesta;
    }

    public static String mensajeEntrada(String aviso) {
        String respuesta = "";
        respuesta = javax.swing.JOptionPane.showInputDialog(null, aviso);
        return respuesta;
    }

    public static String convierteMoneda(String dato) {
        String res = "-------";
        try {
            res = convierteMoneda(Float.parseFloat(dato));

        } catch (Exception e) {
            System.out.println("Error  en el paso de datos");
        }
        return res;
    }

    public static String convierteMoneda(float mon) {
        String res = "";
        java.text.NumberFormat formatter = new java.text.DecimalFormat("###,###,###,###.00");
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        res = formatter.format(mon);
        return res;
    }

    public static String convierteMonedaSinComa(float mon) {
        String res = "";
        java.text.NumberFormat formatter = new java.text.DecimalFormat("############.00");
        res = formatter.format(mon);
        return res;
    }

    public static float quitacomasN(float mon) {
        float res = 0;
        String linea = mon + "";
        linea = linea.replaceAll(",", "");
        try {
            res = Float.parseFloat(linea);
        } catch (Exception ee) {
            mensageError("Error en la converion de Numerica");
        }
        return res;
    }

    public static String quitacomas(float mon) {
        String res = "";
        java.text.NumberFormat formatter = new java.text.DecimalFormat("#########.00");
        res = formatter.format(mon);
        return res;
    }

    public static String quitacomas(String mon) {
        String res = "";
        res = mon.replaceAll(",", "");
        return res;
    }

    public static String corte(String linea, int max) {
        String res = "";
        if (linea.length() > max) {
            res = linea.substring(0, max) + "_".trim();
        } else {
            res = linea;
        }
        return res;
    }

    public static String lineaTexto(String valor, int px, int py, int fuente, int sep) {
        px = px + sep;
        String Rest = "A" + px + "," + py + ",0," + fuente + ",1,1,N,\"" + valor + "\"";
        return Rest;
    }

    public static String lineaBarra(String valor, int px, int py, int sep) {
        px = px + sep;
        System.out.println(px);
        int alturadeBarra = 30;
        //String Rest = "B" + px + "," + py + ",0,3,2,7," + alturadeBarra + ",N,\"" + valor + "\"";
        String Rest = "B" + px + "," + py + ",0,1,2,7," + alturadeBarra + ",N,\"" + valor + "\"";
        return Rest;
    }

    public static void manda(String maquina, int puerto, java.util.Vector vec) {
        java.net.Socket socket = null;
        java.io.PrintWriter out = null;
        try {
            socket = new java.net.Socket(maquina, 84);
            out = new java.io.PrintWriter(socket.getOutputStream());
        } catch (Exception ee) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error  en Impresora");
        }

        try {
            String esp = "@";
            out.print("N" + esp);
            for (int i = 0; i < vec.size(); i++) {
                out.print((String) vec.elementAt(i) + esp);
            }
            out.print("P1" + esp);
            out.flush();
            out.close();
            socket.close();
        } catch (java.net.UnknownHostException ex) {
            mensageError("NO encuentro la impresora");
        } catch (java.io.IOException ex) {
            mensageError("NO hay transmision");
        }
    }

    public static String cortecom(String linea, int ini) {
        String res = "";
        if (linea.length() > ini) {
            res = linea.substring(ini, linea.length());
        }
        return res;
    }
}
