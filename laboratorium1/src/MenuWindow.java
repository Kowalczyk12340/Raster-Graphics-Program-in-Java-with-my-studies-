import javax.swing.*;

public class MenuWindow extends JMenuBar
{
    JMenu file = new JMenu("Plik");
    JMenuItem openFile = new JMenuItem("Otwórz Plik");
    JMenuItem saveFile = new JMenuItem("Zapisz Plik");
    JMenuItem finish = new JMenuItem("Zakończ");
    JMenuItem leftClear = new JMenuItem("Wyczyść Lewy");
    JMenuItem leftCopy = new JMenuItem("Kopiuj Lewy");

    JMenuItem rightClear = new JMenuItem("Wyczyść Prawy");

    JMenu RGB = new JMenu("Szaroodcieniowe Warianty");

    JMenu changedImage = new JMenu("Przekształcenia");
    /*JMenuItem leftMask = new JMenuItem("Maska");
    JMenuItem leftMedian = new JMenuItem("Medianowy");
    JMenuItem leftGradient = new JMenuItem("Gradient");
    JMenuItem leftGradientProgress = new JMenuItem("Gradient z progiem");*/
    JMenuItem negation = new JMenuItem("Negacja");
    JMenuItem YUW = new JMenuItem("Model YUW");
    JMenuItem Red = new JMenuItem("R");
    JMenuItem Green = new JMenuItem("G");
    JMenuItem Blue = new JMenuItem("B");
    JMenuItem average = new JMenuItem("Srednia");
    JMenuItem brightness = new JMenuItem("Jasność");
    JMenuItem contrast = new JMenuItem("Kontrast");
    JMenuItem rangeBrightness = new JMenuItem("Zakres Jasności");

    JMenu description = new JMenu("Opis");
    JMenuItem authorDescription = new JMenuItem("O Autorze");
    JMenuItem programDescription = new JMenuItem("O Programie");

    public MenuWindow()
    {
        file.add(openFile);
        file.add(saveFile);
        file.add(leftClear);
        file.add(leftCopy);
        file.add(rightClear);
        file.add(new JSeparator());
        file.add(finish);
        add(file);

        RGB.add(Red);
        RGB.add(Green);
        RGB.add(Blue);
        RGB.add(average);
        RGB.add(YUW);
        add(RGB);

        //modyfikacje
        /*changedImage.add(leftMask);
        changedImage.add(leftMedian);
        changedImage.add(leftGradient);
        changedImage.add(leftGradientProgress);*/
        changedImage.add(negation);
        changedImage.add(brightness);
        changedImage.add(contrast);
        changedImage.add(rangeBrightness);
        add(changedImage);

        description.add(authorDescription);
        description.add(programDescription);
        add(description);
    }
}
