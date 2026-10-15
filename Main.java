import java.io.*;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        ArrayList<RecordBook> books = new ArrayList<RecordBook>();

        try
        {
            StatementReader.readStatement("math.txt", books);
            StatementReader.readStatement("programming.txt", books);
            StatementReader.readStatement("physics.txt", books);
            StatementReader.readStatement("physicalEducation.txt", books);

            BufferedWriter excellentWriter = new BufferedWriter(
                    new FileWriter("excellent.txt")
            );

            ArrayList<RecordBook> excellentStudents =
                    RecordBook.findExcellentStudents(books);

            for (RecordBook book : excellentStudents)
            {
                excellentWriter.write(book.getExcellentInfo() + '\n');
            }

            excellentWriter.close();
            BufferedWriter successfulWriter = new BufferedWriter(
                    new FileWriter("successful.txt")
            );

            ArrayList<RecordBook> successfulStudents =
                    RecordBook.findSuccessfulStudents(books);

            for (RecordBook book : successfulStudents)
            {
                successfulWriter.write(book.getSessionAverageInfo()+ '\n');
            }

            successfulWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Ошибка чтения файла");
        }
        catch (NumberFormatException e)
        {
            System.out.println("В ведомости неправильно записано число");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}