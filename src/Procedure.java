
public class Procedure
{
    //Attributes
    private double size;
    private String name;
    private int start[] = new int[2]; //Bit where start the procedure

    public int[] getStart() {
        return start;
    }

    //Constructor
    public Procedure(String name, int size)
    {
        this.name = name;
        this.size = size;
    }

    public Procedure(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public double getSize()
    {
        return size;
    }

    public void setStart(int[] start) {
        this.start = start;
    }

}
