import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class GraphicsPanel extends JPanel
{
    protected BufferedImage bufferedImage;
    double mask[][] = new double[3][3];
    int minCzerwony = 255;
    int minZielony = 255;
    int minNiebieski = 255;
    int maxCzerwony = 0;
    int maxZielony = 0;
    int maxNiebieski = 0;

    public GraphicsPanel()
    {
        super();
        setLayout(new GridLayout(2,1));
        setSize(new Dimension(600,600));
        clear();
    }

    public void uploadGraphicsFile(String path)
    {
        File graphicsFile = new File(path);
        try {
            bufferedImage = ImageIO.read(graphicsFile);
            Dimension size = new Dimension(bufferedImage.getWidth(),bufferedImage.getHeight());
            setPreferredSize(size);
            setMaximumSize(size);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            repaint();
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null,"Błąd odczytu pliku: " + path);
            ex.printStackTrace();
        }
    }

    public void uploadFileMask(String path)
    {
        File maskFile = new File(path);
        int i = 0;
        int j = 0;
        try {
            Scanner scanner = new Scanner(maskFile);
            while(scanner.hasNextDouble())
            {
                mask[i][j] = scanner.nextDouble();
                if(j <= 1)
                {
                    j++;
                }
                else {
                    j = 0;
                    i++;
                }
            }
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null,"Błąd odczytu pliku" + path);
            ex.printStackTrace();
        }
    }

    public void saveGraphicsFile(String path)
    {
        File graphicsFile = new File(path);
        try {
            if(bufferedImage != null)
            {
                if(!ImageIO.write(bufferedImage,path.substring(path.lastIndexOf('.') + 1),new File(path)))
                {
                    JOptionPane.showMessageDialog(null,"Nie udało się zapisać pliku w " + path);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Brak obrazu do zapisania!");
            }
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null,"Nie udało się zapisać pliku w " + path);
        }
    }

    public void clear()
    {
        //rysowanie białego tła
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        repaint();
    }

    public void setSize(Dimension dimension)
    {
        bufferedImage = new BufferedImage((int)dimension.getWidth(),(int)dimension.getHeight(),BufferedImage.TYPE_INT_RGB);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }

    public void copy(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        int red,green,blue;
        for(int i=0;i<input.getHeight();i++)
        {
            for(int j=0;j<input.getHeight();i++)
            {
                color = new Color(input.getRGB(i,j));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                int average = (red + green + blue) / 3;
                //ustawianie koloru piksela w obiekcie BufferedImage
                bufferedImage.setRGB(i,j,new Color(average,average,average).getRGB());
            }
            repaint();
        }
    }

    /*public void filterMasking(BufferedImage input, double filtering)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        double red = 0, green = 0, blue = 0;
        Color matrix[][] = new Color[3][3];
        for(int i=1; i<input.getWidth()-1; i++)
        {
            for(int j=1; j<input.getHeight()-1; j++)
            {
                red = 0;
                green = 0;
                blue = 0;
                matrix[0][0] = new Color(input.getRGB(i-1,j-1));
                matrix[0][1] = new Color(input.getRGB(i-1,j));
                matrix[0][2] = new Color(input.getRGB(i-1,j+1));
                matrix[1][0] = new Color(input.getRGB(i,j-1));
                matrix[1][1] = new Color(input.getRGB(i,j));
                matrix[1][2] = new Color(input.getRGB(i,j+1));
                matrix[2][0] = new Color(input.getRGB(i+1,j-1));
                matrix[2][1] = new Color(input.getRGB(i+1,j));
                matrix[2][2] = new Color(input.getRGB(i+1,j+1));
                for(int k=0;k<3;k++)
                {
                    for(int l=0;l<3;l++)
                    {
                        red += mask[k][l] * matrix[k][l].getRed();
                        green += mask[k][l] * matrix[k][l].getGreen();
                        blue += mask[k][l] * matrix[k][l].getBlue();
                    }
                }
                red /= filtering;
                green /= filtering;
                blue /= filtering;
                if(red<0){
                    red = 0;
                }
                if(red>255){
                    red = 255;
                }
                if(green<0){
                    green = 0;
                }
                if(green>255){
                    green = 255;
                }
                if(blue<0){
                    blue = 0;
                }
                if(blue>255){
                    blue = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)red,(int)green,(int)blue).getRGB());
            }
        }
        repaint();
    }

    public void filterMedian(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        double red = 0, green = 0, blue = 0;
        Color matrix[][] = new Color[3][3];
        int median1[] = new int[9];
        int median2[] = new int[9];
        int median3[] = new int[9];
        for(int i=1; i<input.getWidth()-1; i++)
            for(int j=1; j<input.getHeight()-1; j++)
            {
                matrix[0][0] = new Color(input.getRGB(i-1,j-1));
                matrix[0][1] = new Color(input.getRGB(i-1,j));
                matrix[0][2] = new Color(input.getRGB(i-1,j+1));
                matrix[1][0] = new Color(input.getRGB(i,j-1));
                matrix[1][1] = new Color(input.getRGB(i,j));
                matrix[1][2] = new Color(input.getRGB(i,j+1));
                matrix[2][0] = new Color(input.getRGB(i+1,j-1));
                matrix[2][1] = new Color(input.getRGB(i+1,j));
                matrix[2][2] = new Color(input.getRGB(i+1,j+1));

                int t=0;
                for(int p=0; p<3; p++)
                {
                    for( int o=0; o<3; o++){

                        median1[t]=matrix[p][o].getRed();
                        median2[t]=matrix[p][o].getGreen();
                        median3[t]=matrix[p][o].getBlue();
                        t++;
                    }
                }
                Arrays.sort(median1);
                Arrays.sort(median2);
                Arrays.sort(median3);
                red = (median1[3]+median1[4])/2.0;
                green = (median2[3]+median2[4])/2.0;
                blue = (median3[3]+median2[4])/2.0;
                if(red<0){
                    red = 0;
                }
                if(red>255){
                    red = 255;
                }
                if(green<0){
                    green = 0;
                }
                if(green>255){
                    green = 255;
                }
                if(blue<0){
                    blue = 0;
                }
                if(blue>255){
                    blue = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)red,(int)green,(int)blue).getRGB());
            }
        repaint();
    }

    public void gradient(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        double red = 0, green = 0, blue = 0;
        Color matrix[][] = new Color[3][3];
        for(int i=1; i<input.getWidth()-1; i++)
            for(int j=1; j<input.getHeight()-1; j++)
            {
                red = 0;
                green = 0;
                blue = 0;
                Color color1 = new Color(input.getRGB(i,j));
                Color color2 = new Color(input.getRGB(i,j+1));
                Color color3 = new Color(input.getRGB(i+1,j));

                red = Math.sqrt(Math.pow(color1.getRed()-color2.getRed(),2)+Math.pow(color1.getRed()-color3.getRed(),2));
                green = Math.sqrt(Math.pow(color1.getGreen()-color2.getGreen(),2)+Math.pow(color1.getGreen()-color3.getGreen(),2));
                blue = Math.sqrt(Math.pow(color1.getBlue()-color2.getBlue(),2)+Math.pow(color1.getBlue()-color3.getBlue(),2));

                if(red<0){
                    red = 0;
                }
                if(red>255){
                    red = 255;
                }
                if(green<0){
                    green = 0;
                }
                if(green>255){
                    green = 255;
                }
                if(blue<0){
                    blue = 0;
                }
                if(blue>255){
                    blue = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)red,(int)green,(int)blue).getRGB());
            }
        repaint();
    }

    public void gradientProgress(BufferedImage input, double progress, double choose){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        double red = 0, green = 0, blue = 0;
        Color macierz[][] = new Color[3][3];
        for(int i=1; i<input.getWidth()-1; i++)
            for(int j=1; j<input.getHeight()-1; j++)
            {
                red = 0;
                green = 0;
                blue = 0;
                Color color1 = new Color(input.getRGB(i,j));
                Color color2 = new Color(input.getRGB(i,j+1));
                Color color3 = new Color(input.getRGB(i+1,j));

                red = Math.sqrt(Math.pow(color1.getRed()-color2.getRed(),2)+Math.pow(color1.getRed()-color3.getRed(),2));
                green = Math.sqrt(Math.pow(color1.getGreen()-color2.getGreen(),2)+Math.pow(color1.getGreen()-color3.getGreen(),2));
                blue = Math.sqrt(Math.pow(color1.getBlue()-color2.getBlue(),2)+Math.pow(color1.getBlue()-color3.getBlue(),2));

                if(red<0){
                    red = 0;
                }
                if(red>255){
                    red = 255;
                }
                if(green<0){
                    green = 0;
                }
                if(green>255){
                    green = 255;
                }
                if(blue<0){
                    blue = 0;
                }
                if(blue>255){
                    blue = 255;
                }
                double gray = (red + green + blue) / 3;
                if(gray < progress){
                    gray = 255;
                    bufferedImage.setRGB(i,j,new Color((int)gray,(int)gray,(int)gray).getRGB());
                }else{
                    if(choose==1){
                        gray = 0;
                        bufferedImage.setRGB(i,j,new Color((int)color1.getRed(),(int)color1.getGreen(),(int)color1.getBlue()).getRGB());
                    }else{
                        gray = 0;
                        bufferedImage.setRGB(i,j,new Color((int)gray,(int)gray,(int)gray).getRGB());
                    }
                }
            }
        repaint();
    }*/

    public void avg(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));

                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();
                int szary = (czerwony+zielony+niebieski)/3;
                bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
            }
        }
        repaint();
    }

    public void brightness(BufferedImage input, int k){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++)
        {
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();
                //alfa = pobrany.getAlpha();

                czerwony+=k;
                zielony+=k;
                niebieski+=k;
                if(czerwony>255){
                    czerwony=255;
                }
                else if(czerwony<0){
                    czerwony=0;
                }
                if(zielony>255){
                    zielony=255;
                }
                else if(zielony<0){
                    zielony=0;
                }
                if(niebieski>255){
                    niebieski=255;
                }
                else if(niebieski<0){
                    niebieski=0;
                }
                bufferedImage.setRGB(i,j, new Color(czerwony,zielony,niebieski).getRGB());
            }
        }
        repaint();
    }

    public void contrast(BufferedImage input, double k)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        double czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i, j));

                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();

                czerwony = (czerwony * k);
                zielony = (zielony * k);
                niebieski = (niebieski * k);

                if(czerwony>255){
                    czerwony = 255;
                }
                else if(czerwony<0){
                    czerwony = 0;
                }
                if(zielony>255){
                    zielony = 255;
                }
                else if(zielony<0){
                    zielony = 0;
                }
                if(niebieski>255){
                    niebieski = 255;
                }
                else if(niebieski<0){
                    niebieski = 0;
                }

                bufferedImage.setRGB(i, j, new Color((int)czerwony,(int)zielony,(int)niebieski).getRGB());
            }
        }
        repaint();
    }

    public void negation(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int red, green, blue;
        maxMin(input);

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                red = maxCzerwony - pobrany.getRed();
                green = maxZielony - pobrany.getGreen();
                blue = maxNiebieski - pobrany.getBlue();

                bufferedImage.setRGB(i,j, new Color(red, green, blue).getRGB());
            }
        }
        repaint();
    }

    public void zerujMaxMin(){
        minCzerwony = 255;
        minZielony = 255;
        minNiebieski = 255;
        maxCzerwony = 0;
        maxZielony = 0;
        maxNiebieski = 0;
    }

    public void maxMin(BufferedImage input)
    {
        zerujMaxMin();
        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();

                if(czerwony>maxCzerwony){
                    maxCzerwony = czerwony;
                }
                if(zielony>maxZielony){
                    maxZielony = zielony;
                }
                if(niebieski>maxNiebieski){
                    maxNiebieski = niebieski;
                }
                if(czerwony<minCzerwony){
                    minCzerwony = czerwony;
                }
                if(zielony<minZielony){
                    minZielony = zielony;
                }
                if(niebieski<minNiebieski){
                    minNiebieski = niebieski;
                }
            }
        }
    }

    public void oneOfThem(BufferedImage input, int kolor)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;

        if(kolor==1){
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    int szary = pobrany.getRed();
                    bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
                }
            }
        }
        else if(kolor==2)
        {
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    int szary = pobrany.getGreen();
                    bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
                }
            }
        }
        else{
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    int szary = pobrany.getBlue();
                    bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
                }
            }
        }
        repaint();
    }

    public void modelYUV(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int red, green, blue;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                red = pobrany.getRed();
                green = pobrany.getGreen();
                blue = pobrany.getBlue();
                int szary = (int)((0.299 * red) + (0.587 * green) + (0.114 * blue));
                bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
            }
        }
        repaint();
    }

    public void rangeOfBrightness(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        Color pobranyL;
        int czerwony, zielony, niebieski;
        maxMin(input);

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));

                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();

                if((maxCzerwony - minCzerwony)==0){
                    czerwony = czerwony;
                }
                else{
                    czerwony = ((czerwony - minCzerwony)*255) / (maxCzerwony - minCzerwony);
                }
                if((maxZielony - minZielony)==0){
                    zielony = zielony;
                }
                else{
                    zielony = ((zielony - minZielony)*255) / (maxZielony - minZielony);
                }
                if((maxNiebieski - minNiebieski)==0){
                    niebieski = niebieski;
                }
                else{
                    niebieski = ((niebieski - minNiebieski)*255) / (maxNiebieski - minNiebieski);
                }

                if(czerwony>255){
                    czerwony = 255;
                }
                else if(czerwony<0){
                    czerwony = 0;
                }
                if(zielony>255){
                    zielony = 255;
                }
                else if(zielony<0){
                    zielony = 0;
                }
                if(niebieski>255){
                    niebieski = 255;
                }
                else if(niebieski<0){
                    niebieski = 0;
                }
                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(bufferedImage,0,0,this);
    }
}
