
public class Process
{
    //Attributes
    private double size;
    private final String name;
    private int[] start = new int[2];

    //Constructors
    public Process(String name, int size)
    {
        this.name = name;
        this.size = size;
    }

    public Process(String name)
    {
        this.name = name;
    }

    //Getters
    public int[] getStart() {
        return start;
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
