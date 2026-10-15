import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StatementReader
{
    public static void readStatement(String fileName,
                                     ArrayList<RecordBook> books) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        String subject = br.readLine();
        int course = Integer.parseInt(br.readLine());
        int group = Integer.parseInt(br.readLine());
        int session = Integer.parseInt(br.readLine());
        String type = br.readLine();

        String line;

        while ((line = br.readLine()) != null)
        {
            if (line.trim().isEmpty())
            {
                continue;
            }

            StringTokenizer st = new StringTokenizer(line);

            String number = st.nextToken();
            String lastName = st.nextToken();
            String firstName = st.nextToken();
            String middleName = st.nextToken();

            RecordBook book = findRecordBook(books, number);

            if (book == null)
            {
                book = new RecordBook(
                        number,
                        lastName,
                        firstName,
                        middleName,
                        course,
                        group
                );

                books.add(book);
            }
            if (book.getGroup() != group)
                book.setGroup(group);
            if (book.getCourse() < course)
                book.setCourse(course);
            if (type.equalsIgnoreCase("экзамен"))
            {
                int grade = Integer.parseInt(st.nextToken());
                book.addExam(session, subject, grade);
            }
            else if (type.equalsIgnoreCase("зачет"))
            {
                String result = st.nextToken();
                boolean passed;
                if (result.equalsIgnoreCase("зачтено"))
                {
                    passed = true;
                }
                else if (result.equalsIgnoreCase("незачтено"))
                {
                    passed = false;
                }
                else
                {
                    br.close();
                    throw new IllegalArgumentException(
                            "Неправильный результат зачета в файле " + fileName
                    );
                }

                book.addCredit(session, subject, passed);
            }
            else
            {
                br.close();
                throw new IllegalArgumentException(
                        "Неизвестный тип ведомости: " + type
                );
            }
        }

        br.close();
    }

    private static RecordBook findRecordBook(
            ArrayList<RecordBook> books,
            String number)
    {
        for (RecordBook book : books)
        {
            if (book.getNumber().equals(number))
            {
                return book;
            }
        }

        return null;
    }
}