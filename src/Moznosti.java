import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Moznosti extends JComponent implements ActionListener
{
    private JButton tlacidloUkonci = new JButton("KONIEC");
    private JButton tlacidloPomoc = new JButton("POMOC");
    private JButton tlacidloInfo = new JButton("INFO");
    private JButton tlacidloHrat = new JButton("HRAT");

    private static BufferedImage obrazokPozadia;

    public Moznosti()
    {
        tlacidloUkonci.addActionListener(this);
        tlacidloPomoc.addActionListener(this);
        tlacidloInfo.addActionListener(this);
        tlacidloHrat.addActionListener(this);
    }

    public void paintComponent(Graphics graphics)
    {
        Graphics2D graphics2D = (Graphics2D) graphics;

        try
        {
            obrazokPozadia = ImageIO.read(new File("src/Resources/pozadie/Pozadie.jpg"));
        }
        catch (IOException v) {}

        graphics2D.drawImage(obrazokPozadia, 0,0, null);

        graphics2D.setFont(new Font("Copperplate Gothic", Font.ITALIC, 100));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("BlackJack", 350, 120);

        tlacidloUkonci.setBounds(465, 425, 200, 50);
        tlacidloPomoc.setBounds(465, 275, 200, 50);
        tlacidloInfo.setBounds(465, 350, 200, 50);
        tlacidloHrat.setBounds(465, 200, 200, 50);

        tlacidloUkonci.setFont(new Font("Tahoma", Font.BOLD, 35));
        tlacidloPomoc.setFont(new Font("Tahoma", Font.BOLD, 35));
        tlacidloInfo.setFont(new Font("Tahoma", Font.BOLD, 35));
        tlacidloHrat.setFont(new Font("Tahoma", Font.BOLD, 35));

        super.add(tlacidloUkonci);
        super.add(tlacidloPomoc);
        super.add(tlacidloInfo);
        super.add(tlacidloHrat);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton tlacidlo = (JButton) e.getSource();

        if (tlacidlo == tlacidloUkonci)
        {
            System.exit(0);
        }

        else if (tlacidlo == tlacidloHrat)
        {
            BlackJackTest.aktualnyStav = BlackJackTest.STAV.HRA;
            BlackJackTest.obrazovkaMenu.dispose();
            BlackJackTest.obnovenieHry.start();
            BlackJackTest.kontrolaHry.start();
        }

        else if (tlacidlo == tlacidloPomoc)
        {
            JOptionPane.showMessageDialog(this, "Cielom hry BlackJack je mat sucet kariet na ruke vacsi " +
                    "\nako ma vas super (Dealer) bez toho, aby ste prekrocili 21. Karty od 2 do 10 maju taku hodnotu, aka je " +
                    "\nzobrazena na lici, dolnik (J), kralovna (Q) a kral (K) maju hodnotu 10 a eso (A) ma hodnotu bud 1 alebo 11. " +
                    "\nKazde kolo su hracovi a dealerovi rozdane dve karty, pricom prvu dealerovu kartu hrac pocas hry nevidi. " +
                    "\nTlacidlom 'HIT' si potiahnete dalsiu kartu." +
                    "\nTlacidlom 'STAND' ukoncite svoj tah." +
                    "\nTlacidlom 'DOUBLE' si potiahnete prave jednu kartu a vasa aktualna stavka sa zdvojnasobi." +
                    "\nPokial prekrocite 21, prehravate a dealer automaticky vitazi. " +
                    "\nPokial dealer pocas svojho tahu prekroci 21, vyhravate. " +
                    "\nPokial mate ihned po rozdani 21, mate BlackJack a vyhravate!" +
                    "\n DOPLNUJUCE PRAVIDLA:" +
                    "\n V pripade, ze ma Dealer z prvych dvoch kariet BlackJack, vyhrava." +
                    "\n V pripade remizi, vyhravate vy.", "Pomoc", JOptionPane.INFORMATION_MESSAGE);
        }

        else if (tlacidlo == tlacidloInfo)
        {
            JOptionPane.showMessageDialog(this, "Zaverecny projekt BlackJack", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
