//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.*;
import java.util.*;


class FilesFilter {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Нужно прередать хотя бы один аргумент");
            return;
        }
        String StaticMode = "";
        String Path = "";
        String Prefix = "";
        boolean AppendMode = false;
        ArrayList<String> files = new ArrayList<>();
        int integerCount = 0;
        int floatCount = 0;
        int stringCount = 0;
        long maxInt = Long.MIN_VALUE;
        long minInt = Long.MAX_VALUE;
        long sumInt = 0;
        float maxFloat = Float.MIN_VALUE;
        float minFloat = Float.MAX_VALUE;
        float sumFloat = 0;
        int strMaxSize = 0;
        int strMinSize = Integer.MAX_VALUE;

        String IntegerName = "integer.txt";
        String FloatName = "float.txt";
        String StringName = "string.txt";


        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {

                case "-o":
                    Path = args[i + 1];
                    ++i;
                    break;


                case "-s":
                    StaticMode = "-s";
                    break;

                case "-f":
                    StaticMode = "-f";
                    break;

                case "-p":
                    Prefix = args[i + 1];
                    ++i;
                    break;

                case "-a":
                    AppendMode = true;
                    break;

                default:
                    files.add(args[i]);
                    break;


            }
        }

        boolean check = false;
        for (String filename : files) {
            File file = new File(filename);

            if (file.exists() && file.length() > 0)
                check = true;


        }
        if (!check) {
            System.out.println("File is empty");
            return;

        }

        if (!Prefix.isEmpty()) {
            IntegerName = Prefix + IntegerName;
            FloatName = Prefix + FloatName;
            StringName = Prefix + StringName;
        }
        if (!Path.isEmpty()) {

            IntegerName = Path + File.separator + IntegerName;
            FloatName = Path + File.separator + FloatName;
            StringName = Path + File.separator + StringName;


        }


        for (String filename : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if (isInteger(line)) {
                        createAndWriteToFile(IntegerName, line, AppendMode);
                        integerCount++;
                        minInt = Math.min(minInt, Long.parseLong(line));
                        maxInt = Math.max(maxInt, Long.parseLong(line));
                        sumInt += Long.parseLong(line);

                    } else if (isFloat(line)) {
                        createAndWriteToFile(FloatName, line, AppendMode);
                        floatCount++;
                        minFloat = Math.min(minFloat, Float.parseFloat(line));
                        maxFloat = Math.max(maxFloat, Float.parseFloat(line));
                        sumFloat += Float.parseFloat(line);
                    } else {
                        createAndWriteToFile(StringName, line, AppendMode);
                        stringCount++;
                        strMinSize = Math.min(line.length(), strMinSize);
                        strMaxSize = Math.max(line.length(), strMaxSize);
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла " + filename);

            }

        }
        if (StaticMode.contains("-s")) {
            System.out.println("Integer: " + integerCount);
            System.out.println("Float: " + floatCount);
            System.out.println("String: " + stringCount);
            System.out.println("====================");
        } else if (StaticMode.contains("-f")) {
            System.out.println("Integer: " + integerCount);
            System.out.println("Float: " + floatCount);
            System.out.println("String: " + stringCount);
            if (integerCount != 0) {
                System.out.println("Min of integer: " + minInt);
                System.out.println("Max of integer: " + maxInt);
                System.out.println("Sum of Integer: " + sumInt);
                System.out.println("Average of Integer: " + sumInt / integerCount);
            }
            if (floatCount != 0) {
                System.out.println("Min of Float: " + minFloat);
                System.out.println("Max of Float: " + maxFloat);
                System.out.println("Sum of Float: " + sumFloat);
                System.out.println("Average of Float: " + sumFloat / floatCount);
            }
            if (stringCount != 0) {
                System.out.println("Min of String: " + strMinSize);
                System.out.println("Max of String: " + strMaxSize);
            }
        }
    }


    public static boolean isInteger(String str) {
        try {
            Long.parseLong(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private static void createAndWriteToFile(String filename, String value, boolean AppendMode) {




            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, AppendMode))) {
                writer.write(value);
                writer.newLine();

            } catch (IOException e) {
                System.out.println("Ошибка записи в файл " + filename);

            }
        }


    }


