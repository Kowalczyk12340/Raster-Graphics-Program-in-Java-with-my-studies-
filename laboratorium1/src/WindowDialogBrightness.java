import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowDialogBrightness extends JDialog implements ActionListener
{
    JLabel text;
    JSpinner spinner;
    JButton zatwierdz, powrot;
    int k = 0;
    boolean isOK;

    public WindowDialogBrightness(JFrame owner){
        super(owner, "Ustawianie Jasności Obrazu", true);
        setSize(200, 180);
        setLayout(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = getSize().width;
        int height = getSize().height;
        int lokwidth = (dim.width - width) / 2;
        int lokheight = (dim.height - height) / 2;
        setLocation(lokwidth, lokheight);

        text = new JLabel("Jasność", JLabel.CENTER);
        text.setBounds(10, 10, 160, 30);
        add(text);

        SpinnerModel model = new SpinnerNumberModel(0, -255, 255, 1);
        spinner = new JSpinner(model);
        spinner.setLocation(45, 50);
        spinner.setSize(100, 25);

        this.add(spinner);

        zatwierdz = new JButton("OK");
        zatwierdz.setBounds(10, 85, 75, 30);
        add(zatwierdz);
        zatwierdz.addActionListener(this);

        powrot = new JButton("Powrót");
        powrot.setBounds(95, 85, 75, 30);
        add(powrot);
        powrot.addActionListener(this);
    }

    public int getValue(){
        return (int) spinner.getValue();
    }

    public boolean getOK(){
        return isOK;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if(zrodlo==powrot){
            isOK = false;
            dispose();
        }
        else if(zrodlo==zatwierdz){
            isOK = true;
            dispose();
        }
    }
}
