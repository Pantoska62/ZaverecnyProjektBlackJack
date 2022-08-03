import java.util.ArrayList;
import java.util.Collections;

public class BalikKariet
{
    private final ArrayList<Karta> karty;


    public BalikKariet()
    {
        karty = new ArrayList<>();

        for (FarbaKarty farbaKarty : FarbaKarty.values())
        {
            for (HodnotaKarty hodnotaKarty : HodnotaKarty.values())
            {
                if (hodnotaKarty.getHodnota() == 0)
                {
                    Karta karta = new Karta(farbaKarty, hodnotaKarty, 11);
                    karty.add(karta);
                }
                else if (hodnotaKarty.getHodnota() >= 10)
                {
                    Karta karta = new Karta(farbaKarty, hodnotaKarty, 10);
                    karty.add(karta);
                }
                else
                {
                    Karta karta = new Karta(farbaKarty, hodnotaKarty, hodnotaKarty.getHodnota() + 1);
                    karty.add(karta);
                }
            }

        }
    }


    public void zamiesajBalikKariet()
    {
        Collections.shuffle(karty);
    }

    public Karta getKarta(int i)
    {
        return karty.get(i);
    }


    public Karta odtstranKartu(int i)
    {
        return karty.remove(i);
    }

    @Override
    public String toString() {
        return "\n" +
                "karty=" + karty +
                '}';
    }

    public int getPocetKarietVBaliku()
    {
        return karty.size();
    }
}
