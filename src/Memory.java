import java.util.List;
import java.util.Scanner;

public class Memory {
    public static void main(String[] args) {

        Scanner entry = new Scanner(System.in);
        int option,sizeProcedure;
        String nameProcedure;
        Process p;
        MemoryManager manager = new MemoryManager();
        int[] mapBit = manager.getBitMap();

        do {
            System.out.println("1.Enter procedure");
            System.out.println("2.Delete Procedure");
            System.out.println("3.Exit");
            System.out.print("Choose one option: ");
            option = entry.nextInt();

            switch (option)
            {

                case 1:
                    try
                    {
                        System.out.println("Enter Procedure");
                        System.out.print("Enter the name of procedure: ");
                        nameProcedure = entry.next();
                        System.out.print("Enter size of procedure (Bytes): ");
                        sizeProcedure = entry.nextInt();

                        p = new Process(nameProcedure, sizeProcedure);

                        if(manager.addProcess(p) < 0)
                        {
                            System.out.println("There are not memory available");
                            break;
                        }


                        List<Process> lista = manager.getProcess();
                        System.out.println(manager.bitMapToString());

                        System.out.println("Process names in memory");
                        for(int j = 0;j < lista.size() ;j++)
                        {
                            System.out.println(String.format("%x. %s",j+1,lista.get(j).getName()));
                        }
                        System.out.println();
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                        option = 3;
                    }
                    break;
                case 2:
                    System.out.println("Delete Procedure");
                    System.out.print("Enter the name to process: ");
                    String name = entry.next();
                    System.out.println(manager.removeProcess(name));
                    int i = 1;
                    for (int bit: mapBit)
                    {
                        System.out.print(bit);
                        if((i % 8) == 0) System.out.println();
                        i++;
                    }
                    break;
                case 3:
                    System.out.println(manager.bitMapToString());
                    System.out.println("Thanks for using");
                    break;
                default:
                    System.out.println("Incorrect Option");
            }
        }while(option != 3);
    }
}
