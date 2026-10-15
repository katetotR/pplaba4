import java.util.ArrayList;

public class RecordBook
{
    private String number;
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private int group;
    private ArrayList<Result> results = new ArrayList<Result>();
    private class Result
    {
        int session;
        String subject;
        boolean exam;
        int grade;
        boolean passed;

        Result(int session, String subject, boolean exam, int grade, boolean passed)
        {
            if (session < 1 || session > 9)
                throw new IllegalArgumentException("Номер сессии должен быть от 1 до 9");

            if (subject.trim().isEmpty())
                throw new IllegalArgumentException("Не указано название предмета");

            this.session = session;
            this.subject = subject;
            this.exam = exam;
            this.grade = grade;
            this.passed = passed;
        }

        String toReportLine()
        {
            String line = + session + " сессия, " + subject + " ";
            if (exam)
            {
                line += grade;
            }
            else if (passed)
            {
                line += "зачтено";
            }
            else
            {
                line += " не зачтено";
            }
            return line;
        }
    }

    public RecordBook(String number, String lastName, String firstName,
                      String middleName, int course, int group)
    {
        this.number = number;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
    }

    public String getNumber()
    {
        return number;
    }
    public int getCourse()
    {
        return course;
    }
    public int getGroup()
    {
        return group;
    }
    public void setCourse(int course_)
    {
        course = course_;
    }
    public void setGroup(int group_)
    {
        group = group_;
    }


    public String studentInfo()
    {
        return lastName + " " + firstName + " " + middleName + ", " + course + " курс, " + group + " группа";
    }

    public void addExam(int session, String subject, int grade)
    {
        if (grade < 1 || grade > 10)
        {
            throw new IllegalArgumentException("Оценка экзамена должна быть от 1 до 10");
        }
        addResult(new Result(session, subject, true, grade, false));
    }

    public void addCredit(int session, String subject, boolean passed)
    {
        addResult(new Result(session, subject, false, 0, passed));
    }

    private void addResult(Result result)
    {
        results.add(result);
    }

    private boolean meetsRequirements(int minimumGrade)
    {
        if (results.isEmpty())
            return false;

        for (Result result : results)
        {
            if (result.exam)
            {
                if (result.grade < minimumGrade)
                {
                    return false;
                }
            } else if (!result.passed)
            {
                return false;
            }
        }
        return true;
    }

    public boolean isSuccessful()
    {
        return meetsRequirements(4);
    }

    public boolean isExcellent()
    {
        return meetsRequirements(9);
    }
    public double getAverageGrade()
    {
        int sum = 0;
        int count = 0;

        for (Result result : results)
        {
            if (result.exam)
            {
                sum += result.grade;
                count++;
            }
        }

        if (count == 0)
        {
            return -1;
        }

        return (double) sum / count;
    }

    public double getAverageGradeForSession(int session)
    {
        int sum = 0;
        int count = 0;

        for (Result result : results)
        {
            if (result.exam && result.session == session)
            {
                sum += result.grade;
                count++;
            }
        }

        if (count == 0)
        {
            return -1;
        }

        return (double) sum / count;
    }

    private boolean hasSession(int session)
    {
        for (Result result : results)
        {
            if (result.session == session)
            {
                return true;
            }
        }

        return false;
    }

    public String getAverageGradeInfo()
    {
        double average = getAverageGrade();

        if (average == -1)
        {
            return "нет экзаменов\n";
        }

        return "средний балл: " + average + "\n";
    }

    public String getSessionAverageInfo()
    {
        if (!isSuccessful())
        {
            return "";
        }

        StringBuffer text = new StringBuffer();

        text.append(studentInfo());

        for (int session = 1; session <= 9; session++)
        {
            if (hasSession(session))
            {
                double average = getAverageGradeForSession(session);
                text.append("\n");
                text.append(session);
                text.append(" сессия: ");

                if (average == -1)
                {
                    text.append("нет экзаменов");
                }
                else
                {
                    text.append(average);
                }
            }
        }

        text.append("\n");
        return text.toString();
    }

    public String getExcellentInfo()
    {
        if (!isExcellent())
        {
            return "";
        }

        StringBuffer text = new StringBuffer();
        text.append(studentInfo());
        text.append("\n");
        for (int session = 1; session <= 9; session++)
        {
            for (Result result : results)
            {
                if (result.session == session)
                {
                    text.append(result.toReportLine());
                    text.append('\n');
                }
            }
        }

        return text.toString();
    }

    public static ArrayList<RecordBook> findExcellentStudents(
            ArrayList<RecordBook> books)
    {
        ArrayList<RecordBook> excellentStudents =
                new ArrayList<RecordBook>();

        for (RecordBook book : books)
        {
            if (book.isExcellent())
            {
                excellentStudents.add(book);
            }
        }

        return excellentStudents;
    }

    public static ArrayList<RecordBook> findSuccessfulStudents(
            ArrayList<RecordBook> books)
    {
        ArrayList<RecordBook> successfulStudents =
                new ArrayList<RecordBook>();

        for (RecordBook book : books)
        {
            if (book.isSuccessful())
            {
                successfulStudents.add(book);
            }
        }

        return successfulStudents;
    }

}
