import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BlackJackHra
{
    public boolean ukrytaKarta;
    public boolean dealerVyhral;
    public volatile boolean koloSkoncene;
    public boolean doubleSkore = false;

    public boolean jeRemiza = false;


    ArrayList<Karta> kartyHraca;
    ArrayList<Karta> kartyDealera;
    JFrame frame;
    BalikKariet balikKariet;
    KomponentaHra komponentaKasino;
    KomponentaHra komponentaKarty;

    JButton tlacidloHit;
    JButton tlacidloStand;
    JButton tlacidloDouble;
    JButton tlacidloUkoncit;
    Zvuk zvuk = new Zvuk();



    public BlackJackHra(JFrame frameBlackJackHra)
    {
        balikKariet = new BalikKariet();
        balikKariet.zamiesajBalikKariet();
        kartyHraca = new ArrayList<>();
        kartyDealera = new ArrayList<>();

        komponentaKasino = new KomponentaHra(kartyHraca, kartyDealera);
        frame = frameBlackJackHra;
        ukrytaKarta = true;
        koloSkoncene = false;
        dealerVyhral = true;

        // Hra hudbu ale problem je ze sa spusti po kazden novej hre
        // Mozno to vyriesilo ked som dal zastav() do vytvorHru()
        hrajZvuk(0);

    }

    public void vytvorHru()
    {


        zastavZvuk();
        frame.setTitle("BlackJack");
        frame.setSize(1200, 680);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        tlacidloUkoncit = new JButton("SKONCIT HRU");
        tlacidloUkoncit.setFont(new Font("Impact", Font.PLAIN, 18));
        tlacidloUkoncit.setBounds(975, 25, 150, 30);

        tlacidloStand = new JButton("STAND");
        tlacidloStand.setFont(new Font("Impact", Font.PLAIN, 18));
        tlacidloStand.setBounds(540, 570, 110, 60);

        tlacidloHit = new JButton("HIT");
        tlacidloHit.setFont(new Font("Impact", Font.PLAIN, 18));
        tlacidloHit.setBounds(400, 570, 110, 60);

        tlacidloDouble = new JButton("DOUBLE");
        tlacidloDouble.setFont(new Font("Impact", Font.PLAIN, 18));
        tlacidloDouble.setBounds(680, 570, 110, 60);

        frame.add(tlacidloUkoncit);
        frame.add(tlacidloStand);
        frame.add(tlacidloHit);
        frame.add(tlacidloDouble);

        tlacidloUkoncit.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Vase konecne skore je: " + BlackJackTest.aktualnyZostatok + "$.");
            System.exit(0);
        });

        komponentaKasino = new KomponentaHra(kartyHraca, kartyDealera);
        komponentaKasino.setBounds(0, 0, 1000, 630);
        frame.add(komponentaKasino);
        frame.setVisible(true);




    }

    public void zacniHru()
    {


        for (int i = 0; i < 2; i++)
        {
            kartyDealera.add(balikKariet.getKarta(i));
        }
        for (int i = 2; i < 4; i++)
        {
            kartyHraca.add(balikKariet.getKarta(i));
        }
        for (int i = 0; i < 4; i++)
        {
            balikKariet.odtstranKartu(0);
        }

        komponentaKarty = new KomponentaHra(kartyHraca, kartyDealera);
        komponentaKarty.setBounds(0, 0, 1200, 680);
        frame.add(komponentaKarty);
        frame.setVisible(true);

        skontrolujKartyNaRuke(kartyHraca);
        skontrolujKartyNaRuke(kartyDealera);

        tlacidloHit.addActionListener(e -> {
            pridaj(kartyHraca);
            skontrolujKartyNaRuke(kartyHraca);
            if (getSucetKarietNaRuke(kartyHraca) < 17 && getSucetKarietNaRuke(kartyDealera) < 17)
            {
                skontrolujKartyNaRuke(kartyDealera);             }
        });

        // TODO zmen double pretoze to ukazuje nespravne veci
        tlacidloDouble.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pridaj(kartyHraca);
                skontrolujKartyNaRuke(kartyHraca);
                doubleSkore = true;
                if (getSucetKarietNaRuke(kartyHraca) < 17 && getSucetKarietNaRuke(kartyDealera) < 17)
                {
                    skontrolujKartyNaRuke(kartyDealera);
                }
                while (getSucetKarietNaRuke(kartyDealera) < 17)
                {
                    pridaj(kartyDealera);
                    skontrolujKartyNaRuke(kartyDealera);
                }
                if ((getSucetKarietNaRuke(kartyDealera) < 21) && getSucetKarietNaRuke(kartyHraca) < 21)
                {
                    if (getSucetKarietNaRuke(kartyHraca) > getSucetKarietNaRuke(kartyDealera))
                    {

                        hrajZE(1);
                        ukrytaKarta = false;
                        dealerVyhral = false;
                        JOptionPane.showMessageDialog(frame, "VITAZOM JE HRAC!");
                        cakaj();
                        koloSkoncene = true;

                    }
                    else
                    {
                        hrajZE(2);
                        ukrytaKarta = false;
                        JOptionPane.showMessageDialog(frame, "VITAZOM JE DEALER!");
                        cakaj();
                        koloSkoncene = true;

                    }
                }

            }
        });

        tlacidloStand.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                while (getSucetKarietNaRuke(kartyDealera) < 17)
                {
                    pridaj(kartyDealera);
                    skontrolujKartyNaRuke(kartyDealera);
                }
                if ((getSucetKarietNaRuke(kartyDealera) < 21) && getSucetKarietNaRuke(kartyHraca) < 21)
                {
                    if (getSucetKarietNaRuke(kartyHraca) > getSucetKarietNaRuke(kartyDealera))
                    {
                        hrajZE(1);
                        //zastavZvuk();
                        ukrytaKarta = false;
                        dealerVyhral = false;
                        JOptionPane.showMessageDialog(frame, "VITAZOM JE HRAC!");
                        cakaj();
                        koloSkoncene = true;
                    }
                    else if (getSucetKarietNaRuke(kartyHraca) == getSucetKarietNaRuke(kartyDealera))
                    {
                        hrajZvuk(1);
                        zastavZvuk();
                        ukrytaKarta = false;
                        JOptionPane.showMessageDialog(frame, "REMIZA!");
                        cakaj();
                        koloSkoncene = true;
                        jeRemiza = true;
                        dealerVyhral = false;
                    }
                    else
                    {
                        hrajZE(2);

                        ukrytaKarta = false;
                        JOptionPane.showMessageDialog(frame, "VITAZOM JE DEALER!");
                        cakaj();
                        koloSkoncene = true;
                    }
                }
            }
        });
    }

    public void skontrolujKartyNaRuke(ArrayList<Karta> kartyNaRuke)
    {
        if (kartyNaRuke.equals(kartyHraca))
        {
            if (getSucetKarietNaRuke(kartyNaRuke) == 21)
            {
                hrajZE(1);
                //zastavZvuk();
                ukrytaKarta = false;
                dealerVyhral = false;
                JOptionPane.showMessageDialog(frame, "BLACKJACK!\n VITAZOM JE HRAC!");
                cakaj();
                koloSkoncene = true;
            }
            else if (getSucetKarietNaRuke(kartyNaRuke) > 21)
            {
                hrajZE(2);

                ukrytaKarta = false;
                JOptionPane.showMessageDialog(frame, "PREKROCILI STE 21!\n VITAZOM JE DEALER!");
                cakaj();
                koloSkoncene = true;
            }
        }
        else
        {
            if (getSucetKarietNaRuke(kartyNaRuke) == 21)
            {
                hrajZE(2);

                ukrytaKarta = false;
                JOptionPane.showMessageDialog(frame, "DEALER MA BLACKJACK!\n VITAZOM JE DEALER!");
                cakaj();
                koloSkoncene = true;
            }
            else if (getSucetKarietNaRuke(kartyNaRuke) > 21)
            {
                hrajZE(1);
                //zastavZvuk();
                ukrytaKarta = false;
                dealerVyhral = false;
                JOptionPane.showMessageDialog(frame, "DEALER PREKROCIL 21!\n VITAZOM JE HRAC!");
                cakaj();
                koloSkoncene= true;
            }
        }
    }

    public void pridaj(ArrayList<Karta> kartyNaRuke)
    {
        kartyNaRuke.add(balikKariet.getKarta(0));
        balikKariet.odtstranKartu(0);
        ukrytaKarta = true;
    }

    public boolean jeEsoNaRuke(ArrayList<Karta> kartyNaRuke)
    {
        for (int i = 0; i < kartyNaRuke.size(); i++)
        {
            if (kartyNaRuke.get(i).getCiselnaHodnotaKarty() == 11)
            {
                return true;
            }
        }
        return false;
    }

    public int zistiPocetEsNaRuke(ArrayList<Karta> kartyNaRuke)
    {
        int pocetEsNaRuke = 0;
        for (int i = 0; i < kartyNaRuke.size(); i++)
        {
            if (kartyNaRuke.get(i).getCiselnaHodnotaKarty() == 11)
            {
                pocetEsNaRuke++;
            }
        }
        return pocetEsNaRuke;
    }

    public int getSucetSVysokymEsom(ArrayList<Karta> kartyNaRuke)
    {
        int sucetKarietNaRuke = 0;
        for (int i = 0; i < kartyNaRuke.size(); i++)
        {
            sucetKarietNaRuke += kartyNaRuke.get(i).getCiselnaHodnotaKarty();
        }
        return sucetKarietNaRuke;
    }

    public int getSucetKarietNaRuke(ArrayList<Karta> kartyNaRuke)
    {
        if (jeEsoNaRuke(kartyNaRuke))
        {
            if (getSucetSVysokymEsom(kartyNaRuke) <= 21)
            {
                return getSucetSVysokymEsom(kartyNaRuke);
            }
            else
            {
                for (int i = 0; i < zistiPocetEsNaRuke(kartyNaRuke); i++)
                {
                    int sucetKarietNaRuke = getSucetSVysokymEsom(kartyNaRuke) - (i+1) * 10;
                    if (sucetKarietNaRuke <= 21)
                    {
                        return sucetKarietNaRuke;
                    }
                }
            }
        }
        else
        {
            int sucetKarietNaRuke = 0;
            for (int i = 0; i < kartyNaRuke.size(); i++)
            {
                sucetKarietNaRuke += kartyNaRuke.get(i).getCiselnaHodnotaKarty();
            }
            return sucetKarietNaRuke;
        }
        return 22;
    }

    public static void cakaj()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException v) {}
    }

    /**
     * Hra hudnu v loope
     * @param i
     */
    public void hrajZvuk(int i){
        zvuk.nastavSubor(i);
        zvuk.loop();

    }
    public void zastavZvuk(){
        zvuk.zastav();
    }

    /**
     * Zvukovy efekt
     * @param i index zvuku
     */
    public void hrajZE(int i){
        zvuk.nastavSubor(i);
        zvuk.hraj();
    }
}
