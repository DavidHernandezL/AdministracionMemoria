import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryManager {

    private final int[] bitMap = new int[32];
    private final List<Procedure> process = new ArrayList<>();

    //Constructor
    public MemoryManager()
    {
        Arrays.fill(bitMap,0); //Initialize el bitMap with zeros
    }

    public List<Procedure> getProcess() {
        return process;
    }

    public int addProcess(Procedure p1)
    {
        int numBlocks;
        int numBinary;
        int result[];
        int startProcess;
        int bitAux;

        numBlocks = (int)(Math.ceil(p1.getSize() / 1024)); //Calculate the number of blocks that the process needs

        numBinary = generateNumBinary(numBlocks);

        result = searchPlace(numBinary,numBlocks);
        if(result[0] < 0) return -1;

        startProcess = result[0];
        bitAux = result[1];
        this.bitMap[bitAux] = this.bitMap[bitAux] | (numBinary << startProcess);
        p1.setStart(result);
        this.process.add(p1);
        return 1;
    }

    public int removeProcess(String name){
        int pos = -1;
        int result[] = new int[2];
        Procedure processLocal = new Procedure(name);

        for (int i = 0; i < process.size(); i++)
        {
            if(process.get(i).getName().compareTo(name) == 0)
            {
                result = process.get(i).getStart();
                processLocal = process.get(i);
                pos = 1;
            }
        }
        if(pos < 0) return -1;
        int binary = generateNumBinary((int)(Math.ceil(processLocal.getSize() / 1024)));
        binary = binary << result[0];
        this.bitMap[result[1]] = this.bitMap[result[1]] ^ binary;
        return pos;
    }

    public int[] getBitMap()
    {
        return bitMap;
    }

    private int[] searchPlace(int binary, int numBlocks)
    {
        int j = 0;
        int binaryAux;
        int result[] = new int[2];
        for (int i = 0; i < this.bitMap.length; i++)
        {
            binaryAux = binary;
            while (j < (32 - numBlocks))
            {

                System.out.println("compare");
                System.out.println(this.bitMap[i] & (binaryAux << j));
                if((this.bitMap[i] & (binaryAux << j)) == 0)
                {
                    result[0] = j; //Save the position start in the bit
                    result[1] = i; //Save the number of int
                    return result;
                }
                j++;

            }
        }
        result[0] = -1;
        return result;
    }

    private int generateNumBinary(int numBlocks) //Generates the binary number of blocks needed by the process
    {
        StringBuilder binary = new StringBuilder();

        for (int i = 0; i < numBlocks; i++)
            binary.append(1);

        return Integer.parseInt(binary.toString(),2);

    }

    public String bitMapToString() {
        String formatString = "";
        int i = 1;
        for(int bit : bitMap)
        {
            formatString += bit + " | ";
            if((i % 8) == 0)
            {
                formatString += "\n";
            }
            i++;
        }
        return formatString;
    }
}
