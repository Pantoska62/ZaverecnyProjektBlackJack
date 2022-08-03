public enum HodnotaKarty
{
    ESO(0),
    DVOJKA(1),
    TROJKA(2),
    STVORKA(3),
    PATKA(4),
    SESTKA(5),
    SEDMICKA(6),
    OSMICKA(7),
    DEVIATKA(8),
    DESIATKA(9),
    DOLNIK(10),
    KRALOVNA(11),
    KRAL(12);


    private final int hodnotaKarty;

    HodnotaKarty(int hodnotaKarty)
    {
        this.hodnotaKarty = hodnotaKarty;
    }

    public int getHodnota()
    {
        return hodnotaKarty;
    }
}
