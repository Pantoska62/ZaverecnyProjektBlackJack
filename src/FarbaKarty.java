public enum FarbaKarty
{
    KRIZ(0),
    KARO(1),
    SRDCE(2),
    PIKA(3);


    private final int farbaKarty;

    FarbaKarty(int farbaKarty)
    {
        this.farbaKarty = farbaKarty;
    }

    public int getFarba()
    {
        return farbaKarty;
    }
}
