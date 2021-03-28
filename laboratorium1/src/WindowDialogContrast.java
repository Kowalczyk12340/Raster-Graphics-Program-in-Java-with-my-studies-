import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowDialogContrast extends JDialog implements ActionListener
{
    JLabel text;
    JSpinner spinner;
    JButton zatwierdz, powrot;
    int k = 0;
    boolean isOK;

    public WindowDialogContrast(JFrame owner){
        super(owner, "Ustawianie Kontrastu Obrazu", true);
        setSize(200, 180);
        setLayout(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int szer = getSize().width;
        int wys = getSize().height;
        int loks = (dim.width - szer) / 2;
        int lokw = (dim.height - wys) / 2;
        setLocation(loks, lokw);

        text = new JLabel("Kontrast", JLabel.CENTER);
        text.setBounds(10, 10, 160, 30);
        add(text);

        SpinnerModel model = new SpinnerNumberModel(0, 0, 5, 0.01);
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

    public double getValue(){
        return (double) spinner.getValue();
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
