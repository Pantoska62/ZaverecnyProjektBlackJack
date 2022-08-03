import javax.swing.JFrame;
import java.net.URL;

public class BlackJackTest {
    public enum STAV
    {
        HRA,
        MENU

    }

    public static JFrame obrazovkaHry = new JFrame();
    public static JFrame obrazovkaMenu = new JFrame();

    private static int dealeroveSkore = 0;
    private static int hracoveSkore = 0;
    public static int aktualnyZostatok = 1000;

    public static BlackJackHra hra = new BlackJackHra(obrazovkaHry);
    private static boolean prvaHra = true;

    


    public static STAV aktualnyStav = STAV.MENU;

    public static void main(String[] args)
    {
        if (aktualnyStav == STAV.MENU)
        {
            otvorMenu();

        }
    }

    public static void otvorMenu()
    {

        obrazovkaMenu.setTitle("BlackJack");
        obrazovkaMenu.setSize(1130, 665);
        obrazovkaMenu.setLocationRelativeTo(null);
        obrazovkaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obrazovkaMenu.setResizable(false);





        Moznosti uvod = new Moznosti();
        obrazovkaMenu.add(uvod);
        obrazovkaMenu.setVisible(true);



    }

    public static Thread obnovenieHry = new Thread(() -> {
        while (true)
        {
            hra.komponentaKasino.obnov(aktualnyZostatok, hracoveSkore, dealeroveSkore - 1, hra.ukrytaKarta);
        }
    });

    public static Thread kontrolaHry = new Thread(() -> {
        while (true)
        {
            if (prvaHra || hra.koloSkoncene)
            {

                if (hra.dealerVyhral)
                {
                    if (hra.doubleSkore)
                    {
                        dealeroveSkore++;
                        aktualnyZostatok -= KomponentaHra.aktualnaStavka*2;
                    }
                    else
                    {
                        dealeroveSkore++;
                        aktualnyZostatok -= KomponentaHra.aktualnaStavka;
                    }
                }
                else if (hra.jeRemiza)
                {
                    hracoveSkore++;
                    aktualnyZostatok += KomponentaHra.aktualnaStavka;
                }
                else
                {
                    if (hra.doubleSkore)
                    {
                        hracoveSkore++;
                        aktualnyZostatok += KomponentaHra.aktualnaStavka*2;
                    }
                    else
                    {
                        hracoveSkore++;
                        aktualnyZostatok += KomponentaHra.aktualnaStavka;
                    }
                }
                obrazovkaHry.getContentPane().removeAll();
                hra = new BlackJackHra(obrazovkaHry);
                hra.vytvorHru();

                prvaHra = false;
            }
        }
    });



}
