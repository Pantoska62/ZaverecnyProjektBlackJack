import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class Karta implements Comparator<Karta>
{
    private FarbaKarty farbaKarty;
    private HodnotaKarty hodnotaKarty;
    private int ciselnaHodnotaKarty;
    private int suradnicaX;
    private int suradnicaY;

    public Karta()
    {
        this.farbaKarty = null;
        this.hodnotaKarty = null;
        this.ciselnaHodnotaKarty = 0;
    }

    public Karta(FarbaKarty farbaKarty, HodnotaKarty hodnotaKarty, int ciselnaHodnotaKarty)
    {
        this.farbaKarty = farbaKarty;
        this.hodnotaKarty = hodnotaKarty;
        this.ciselnaHodnotaKarty = ciselnaHodnotaKarty;
    }


    public void vykresliKartu(Graphics2D graphics2D, boolean tahDealera, boolean ukrytaKarta, int cisloKarty)
        throws IOException
    {
        BufferedImage obrazokKariet = ImageIO.read(new File("src/Resources/Karty_sprite/Sprite_Kariet.png"));
        int sirkaObrazu = 980;
        int vyskaObrazu = 420;
        BufferedImage[][] obrazkyKariet = new BufferedImage[4][13];

        BufferedImage rubKarty = ImageIO.read(new File("src/Resources/Rub_karty/Rub_karty.png"));

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                obrazkyKariet[i][j] = obrazokKariet.getSubimage(j * sirkaObrazu / 13, i * vyskaObrazu / 4, sirkaObrazu / 13, vyskaObrazu / 4);
            }
        }

        if (tahDealera)
        {
            suradnicaY = 75;
        }

        else
        {
            suradnicaY = 450;
        }

        suradnicaX = 500 + 75 * cisloKarty;

        if (ukrytaKarta)
        {
            graphics2D.drawImage(rubKarty, suradnicaX, suradnicaY, null);
        }

        else
        {
            graphics2D.drawImage(obrazkyKariet[farbaKarty.getFarba()][hodnotaKarty.getHodnota()], suradnicaX, suradnicaY, null);
        }
    }

    public FarbaKarty getFarbaKarty()
    {
        return farbaKarty;
    }

    public HodnotaKarty getHodnotaKarty()
    {
        return hodnotaKarty;
    }

    public int getCiselnaHodnotaKarty()
    {
        return ciselnaHodnotaKarty;
    }

    @Override
    public String toString() {
        return "Karta{" +
                "farbaKarty=" + farbaKarty +
                ", hodnotaKarty=" + hodnotaKarty +
                '}';
    }

    @Override
    public int compare(Karta karta1, Karta karta2)
    {
        if (karta1.getHodnotaKarty().getHodnota() == karta2.getHodnotaKarty().getHodnota())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
