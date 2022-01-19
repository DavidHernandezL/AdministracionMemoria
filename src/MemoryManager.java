import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryManager {

    private final int[] bitMap = new int[32];
    private final List<Process> process = new ArrayList<>();

    //Constructor
    public MemoryManager()
    {
        Arrays.fill(bitMap,0); //Inicializa el mapeo de bits con 0
    }

    public List<Process> getProcess() {
        return process;
    }

    public int[] getBitMap()
    {
        return bitMap;
    }

    /**
     *  Añade un nuevo proceso a la memoria
     * @param p1 Proceso a añadir a la memoria
     * @return 1 si tuvo exito ó -1 si no lo tuvo
     */
    public int addProcess(Process p1)
    {
        int numBlocks;
        int numBinary;
        int[] result;
        int startProcess;
        int bitAux;

        numBlocks = (int)(Math.ceil(p1.getSize() / 1024)); /* Calcula el numero de bloques que necesita */
        numBinary = generateNumBinary(numBlocks);
        result = searchPlace(numBinary,numBlocks);

        if(result[0] < 0) return -1;

        startProcess = result[0];
        bitAux = result[1];

        this.bitMap[bitAux] = this.bitMap[bitAux] | (numBinary << startProcess); //Asigna los bits necesarios en el mapa de bits
        p1.setStart(result); //Guarda en que entero se encuentra y en cual bit inicia
        this.process.add(p1);
        return 1;
    }

    /**
     * Remueve un proceso de la memoria
     * @param name Nombre del proceso que se quiere eliminar
     * @return Un entero, 1 si fue exitosa la operacion, -1 si no lo fue
     */
    public int removeProcess(String name){
        int pos = -1;
        int[] result = new int[2];
        Process processLocal = new Process(name);

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

    /**
     * Busca si existe espacio en la memoria paa un proceso
     * @param binary Numero que representa el binario de los bloques a guardar
     * @param numBlocks Numero de bloques que se van a guardar
     * @return Un arreglo de enteros donde esta guardado la posision inicial del proceso y en que entero del mapa de bits
     */
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
                    result[0] = j; //Guarda la posision inical
                    result[1] = i; //Guarda el numero de entero
                    return result;
                }
                j++;

            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Genera el numero en binario que representa el numero de bloques que se va a almacenar.
     * Ejemplo: Si se necesitan 3 bloques genera un numero binario --> 111
     * @param numBlocks Numero de bloques que se van a guardar
     * @return Un entero que representa el binario generado
     */
    private int generateNumBinary(int numBlocks) //Generates the binary number of blocks needed by the process
    {
        StringBuilder binary = new StringBuilder();

        for (int i = 0; i < numBlocks; i++)
            binary.append(1);

        return Integer.parseInt(binary.toString(),2);

    }

    /**
     * Convierte el mapa de bits en una cadena
     * @return Una cadena que representa el mapa de bits
     */
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
