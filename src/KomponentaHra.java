import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class KomponentaHra extends JComponent implements MouseListener
{
    public BufferedImage pozadie;
    public BufferedImage zeton;
    private ArrayList<Karta> kartyHraca;
    private ArrayList<Karta> kartyDealera;
    private int skoreDealera;
    private int skoreHraca;
    public boolean ukrytaKarta = true;
    public static boolean bolaStavka = false;
    private int aktualnyZostatok;
    public static int aktualnaStavka;
    Zvuk zvuk = new Zvuk();

    public KomponentaHra(ArrayList<Karta> a, ArrayList<Karta> b)
    {
        kartyHraca = a;
        kartyDealera = b;
        skoreHraca = 0;
        skoreDealera = 0;
        aktualnyZostatok = 1000;
        addMouseListener(this);
    }

    public void paintComponent(Graphics graphics)
    {
        Graphics2D graphics2D = (Graphics2D) graphics;

        try
        {
            pozadie = ImageIO.read(new File("src/Resources/pozadie/Pozadie.jpg"));
            zeton = ImageIO.read(new File("src/Resources/zeton/Zeton.png"));
        }
        catch (IOException v) {}

        graphics2D.drawImage(pozadie, 0, 0, null);
        graphics2D.drawImage(zeton, 900, 300, null);
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 32));
        graphics2D.drawString("DEALER", 530, 50);
        graphics2D.drawString("HRAC", 530,400);
        graphics2D.drawString("DEALEROVE VITAZSTVA: ", 60, 100);
        graphics2D.drawString(Integer.toString(skoreDealera), 310, 150);
        graphics2D.drawString("HRACOVE VITAZSTVA: ", 60, 450);
        graphics2D.drawString(Integer.toString(skoreHraca), 310, 500);
        graphics2D.setFont(new Font("Arial", Font.ITALIC, 16));
        graphics2D.drawString("Na zaciatku kazdeho kola", 890, 260);
        graphics2D.drawString("si stavte kliknutim na zeton", 890, 280);
        graphics2D.setFont(new Font("Arial", Font.ITALIC, 24));
        graphics2D.drawString("AKTUALNY ZOSTATOK: " + aktualnyZostatok, 825, 610);

        try
        {
            for (int i = 0; i < kartyDealera.size(); i++)
            {
                if (i == 0)
                {
                    if (ukrytaKarta)
                    {
                        kartyDealera.get(i).vykresliKartu(graphics2D, true, true, i);
                    }
                    else
                    {
                        kartyDealera.get(i).vykresliKartu(graphics2D, true, false, i);
                    }
                }
                else
                {
                    kartyDealera.get(i).vykresliKartu(graphics2D, true, false, i);
                }
            }

        }

        catch (IOException v) {}

        try
        {
            for (int i = 0 ; i < kartyHraca.size(); i++)
            {
                kartyHraca.get(i).vykresliKartu(graphics2D, false, false, i);
            }

        }
        catch (IOException v) {}
    }


    public void obnov(int a, int b, int c, boolean d)
    {
        aktualnyZostatok = a;
        skoreHraca = b;
        skoreDealera = c;
        ukrytaKarta = d;
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent m)
    {
        int xSuradnicaMys = m.getX();
        int ySuradnicaMys = m.getY();

        if (xSuradnicaMys >= 900 && xSuradnicaMys <= 1060 && ySuradnicaMys >= 300 && ySuradnicaMys <= 460)
        {
            bolaStavka = true;

            String[] moznostiStavok = new String[] {"1", "5", "10", "50", "100"};

            int odpoved = JOptionPane.showOptionDialog(null, "ZVOLTE SI VYSKU STAVKY!",
                    "STAVKA", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, moznostiStavok, moznostiStavok[0]);

            if (odpoved == 0)
            {
                aktualnaStavka = 1;
                aktualnyZostatok -= 1;
            }
            else if (odpoved == 1)
            {
                aktualnaStavka = 5;
                aktualnyZostatok -= 5;
            }
            else if (odpoved == 2)
            {
                aktualnaStavka = 10;
                aktualnyZostatok -= 10;
            }
            else if (odpoved == 3)
            {
                aktualnaStavka = 50;
                aktualnyZostatok -= 50;
            }
            else if (odpoved == 4)
            {
                aktualnaStavka = 100;
                aktualnyZostatok -= 100;
            }
            else
            {
                aktualnaStavka = 1;
                aktualnyZostatok -= 1;
                System.out.println("Nezvolili ste si vysku stavky, automaticky bolo stavene 1");
            }


            BlackJackTest.hra.zacniHru();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
