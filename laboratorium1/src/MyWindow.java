import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame implements ActionListener
{
    GraphicsPanel left = new GraphicsPanel();
    GraphicsPanel right = new GraphicsPanel();
    String pathToFile;
    String pathToFileTxt;
    MenuWindow menu = new MenuWindow();
    JTextField txtValue = new JTextField(30);
    JTextField normalization = new JTextField(3);
    JTextField prog = new JTextField(3);
    double choose = 1;
    WindowDialogBrightness dialogBrightness;
    WindowDialogContrast dialogContrast;
    //Konstruktor
    public MyWindow() throws HeadlessException
    {
        super("Marcin Kowalczyk - Grafika Komputerowa Laboratorium 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(2,2));
        setJMenuBar(menu);
        add(left,BorderLayout.LINE_START);
        add(right,BorderLayout.LINE_END);
        JPanel down = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        txtValue.setEnabled(false);
        down.add(new JLabel("    "));
        normalization.setText("1");
        down.add(new JLabel("    "));
        down.add(new JLabel("Próg Gradientu: "));
        prog.setText("1");
        down.add(prog);
        add(down,BorderLayout.PAGE_END);
        setListenerEvents();
        matchToContent();
        setVisible(true);
    }

    private void setListenerEvents()
    {
        menu.openFile.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        menu.openFile.addActionListener(this);
        menu.saveFile.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menu.saveFile.addActionListener(this);
        menu.finish.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        menu.finish.addActionListener(this);
        menu.leftClear.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        menu.leftClear.addActionListener(this);
        menu.leftCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        menu.leftCopy.addActionListener(this);

        /*menu.leftMask.addActionListener(this);
        menu.leftMedian.addActionListener(this);
        menu.leftGradient.addActionListener(this);
        menu.leftGradientProgress.addActionListener(this);*/
        menu.negation.addActionListener(this);
        menu.YUW.addActionListener(this);
        menu.Red.addActionListener(this);
        menu.Green.addActionListener(this);
        menu.Blue.addActionListener(this);
        menu.average.addActionListener(this);
        menu.brightness.addActionListener(this);
        menu.contrast.addActionListener(this);
        menu.rangeBrightness.addActionListener(this);
        txtValue.addActionListener(this);

        menu.rightClear.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        menu.rightClear.addActionListener(this);
        menu.authorDescription.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
        menu.authorDescription.addActionListener(this);
        menu.programDescription.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        menu.programDescription.addActionListener(this);
    }

    //Obsługa Zdarzeń
    @Override
    public void actionPerformed(ActionEvent ev)
    {
        String label = ev.getActionCommand();
        Object source = ev.getSource();
        if(label.equals("Otwórz Plik"))
        {
            openFile();
        }
        else if(label.equals("Zapisz Plik"))
        {
            saveFile();
        }
        else if(label.equals("Zakończ"))
        {
            int answer = JOptionPane.showConfirmDialog(null, "Czy napewno chcesz wyjść z programu?", "Pytanie", JOptionPane.YES_NO_CANCEL_OPTION);//ta metoda zwraca int
            if(answer == JOptionPane.YES_OPTION)
                dispose();
            else if(answer == JOptionPane.NO_OPTION)
                JOptionPane.showMessageDialog(null, "Nie opuszczasz programu! Tak trzymać! :)");
            else if(answer == JOptionPane.CLOSED_OPTION)
                JOptionPane.showConfirmDialog(null, "Opuszczasz program :(", "Tytuł", JOptionPane.WARNING_MESSAGE);
        }
        else if(label.equals("Wyczyść Lewy"))
        {
            left.clear();
        }
        else if(label.equals("Kopiuj"))
        {
            int width = right.bufferedImage.getWidth();
            int height = right.bufferedImage.getHeight();
            right.copy(left.bufferedImage);
            if(width != right.bufferedImage.getWidth() || height != right.bufferedImage.getHeight())
            {
                matchToContent();
            }
        }
        else if(label.equals("Wyczyść Prawy"))
        {
            right.clear();
        }
        /*else if(label.equals("Maska"))
        {
            int width = right.bufferedImage.getWidth();
            int height = right.bufferedImage.getHeight();
            right.filterMasking(left.bufferedImage,Double.parseDouble(normalization.getText()));
            if(width != right.bufferedImage.getWidth() || height != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("Medianowy"))
        {
            int width = right.bufferedImage.getWidth();
            int height = right.bufferedImage.getHeight();
            right.filterMedian(left.bufferedImage);
            if(width != right.bufferedImage.getWidth() || height != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("Gradient"))
        {
            int width = right.bufferedImage.getWidth();
            int height = right.bufferedImage.getHeight();
            //skopiuj prawy panel na bazie lewego
            right.gradient(left.bufferedImage);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(width != right.bufferedImage.getWidth() || height != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("Gradient z progiem"))
        {
            int width = right.bufferedImage.getWidth();
            int height = right.bufferedImage.getHeight();
            right.gradientProgress(left.bufferedImage, Double.parseDouble(prog.getText()),choose);
            if(width != right.bufferedImage.getWidth() || height != right.bufferedImage.getHeight())
                matchToContent();
        }*/
        else if(label.equals("Model YUW"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.modelYUV(left.bufferedImage);
            matchSize(right, w, h);
        }
        else if(label.equals("Negacja"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.negation(left.bufferedImage);
            matchSize(right, w, h);
        }
        else if(label.equals("R"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.oneOfThem(left.bufferedImage, 1);
            matchSize(right, w, h);
        }
        else if(label.equals("G"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.oneOfThem(left.bufferedImage, 2);
            matchSize(right, w, h);
        }
        else if(label.equals("B"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.oneOfThem(left.bufferedImage, 3);
            matchSize(right, w, h);
        }
        else if(label.equals("Srednia"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.avg(left.bufferedImage);
            matchSize(right, w, h);
        }
        else if(label.equals("Jasność"))
        {
            if(dialogBrightness == null){
                dialogBrightness = new WindowDialogBrightness(this);
            }

            dialogBrightness.setVisible(true);
            dialogBrightness.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if(dialogBrightness.getOK()){
                int w = right.bufferedImage.getWidth();
                int h = right.bufferedImage.getHeight();
                right.brightness(left.bufferedImage, dialogBrightness.getValue());
                matchSize(right, w, h);
            }
        }
        else if(label.equals("Kontrast"))
        {
            if(dialogContrast == null)
            {
                dialogContrast = new WindowDialogContrast(this);
            }
            dialogContrast.setVisible(true);
            dialogContrast.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if(dialogContrast.getOK())
            {
                int w = right.bufferedImage.getWidth();
                int h = right.bufferedImage.getHeight();
                right.contrast(left.bufferedImage, dialogContrast.getValue());

                matchSize(right, w, h);
            }
        }
        else if(label.equals("Zakres Jasności"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.rangeOfBrightness(left.bufferedImage);
            matchSize(right,w,h);
        }
        else if(label.equals("Wyczyść prawy"))
        {
            right.clear();
        }
        else if(label.equals("O Programie"))
        {
            JOptionPane.showMessageDialog(this, "Program okienkowy demonstruje wykorzystanie biblioteki \n javax.swing i java.awt do stworzenia prostego panelu do wstawiania zdjęć z nadawaniem im filtrów", "Opis Aplikacji", JOptionPane.INFORMATION_MESSAGE );
        }

        else if(label.equals("O Autorze"))
        {
            JOptionPane.showMessageDialog(this, "Autor : Marcin Kowalczyk - więcej informacji na stronie internetowej gingercoder.com", "Opis Autora", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    private void openFile()
    {
        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & BMP & PNG Images","jpg","bmp","png");
        open.setFileFilter(filter);
        int score = open.showOpenDialog(this);
        if(score == JFileChooser.APPROVE_OPTION)
        {
            pathToFile = open.getSelectedFile().getPath();
            int width = left.bufferedImage.getWidth();
            int height = left.bufferedImage.getHeight();
            left.uploadGraphicsFile(pathToFile);
            if(width != left.bufferedImage.getWidth() || height != left.bufferedImage.getHeight())
                matchToContent();
        }
    }

    private void saveFile()
    {
        JFileChooser save;
        if(pathToFile != null)
        {
            save = new JFileChooser(pathToFile);
        }
        else {
            save = new JFileChooser();
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & BMP & PNG Images","jpg","bmp","png");
        save.setFileFilter(filter);
        int score = save.showSaveDialog(this);
        if(score == JFileChooser.APPROVE_OPTION)
        {
            pathToFile = save.getSelectedFile().getPath();
            right.saveGraphicsFile(pathToFile);
        }
    }

    public void matchSize(GraphicsPanel input, int w, int h){
        if(w != input.bufferedImage.getWidth() || h != input.bufferedImage.getHeight()){
            if(input.bufferedImage.getWidth()>=600 || input.bufferedImage.getHeight()>=600){
                input.setPreferredSize(new Dimension(600, 600));
                input.setMaximumSize(new Dimension(600, 600));
            }
            matchToContent();
        }
    }

    private void matchToContent()
    {
        pack();
        setLocationRelativeTo(null);
    }

    private boolean openFileTxt(){
        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
        open.setFileFilter(filter);
        int score = open.showOpenDialog(this);
        boolean check = false;
        if (score == JFileChooser.APPROVE_OPTION)
        {
            pathToFileTxt = open.getSelectedFile().getPath();
            check = true;
        }
        return check;
    }
}
