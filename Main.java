package numbers;

import java.util.*;


public class Main {
    private static final ArrayList<String> allTypes = new ArrayList<>(Arrays.asList("even", "odd", "buzz",
            "duck", "palindromic", "gapful", "spy", "square", "sunny", "jumping", "happy", "sad","-even", "-odd", "-buzz",
            "-duck", "-palindromic", "-gapful", "-spy", "-square", "-sunny", "-jumping", "-happy", "-sad"));

    private static final ArrayList<ArrayList<String>> mutuallyExclusiveProperties = new ArrayList<>(){{
        add(new ArrayList<>(Arrays.asList("even", "odd")));
        add(new ArrayList<>(Arrays.asList("duck", "spy")));
        add(new ArrayList<>(Arrays.asList("sunny", "square")));
        add(new ArrayList<>(Arrays.asList("sad", "happy")));
        add(new ArrayList<>(Arrays.asList("-even", "-odd")));
        add(new ArrayList<>(Arrays.asList("-duck", "-spy")));
        add(new ArrayList<>(Arrays.asList("-sad", "-happy")));
    }};

    static {
        allTypes.forEach(type -> {
            mutuallyExclusiveProperties.add(new ArrayList<>(Arrays.asList(type, "-" + type)));
        });
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printWelcomeMessage();
        printInstructions();

        while (true) {
            System.out.print("Enter a request: ");
            String inputData = readData(scanner);
            System.out.println();

            if (inputData.isEmpty() || inputData.isBlank()) {
                printInstructions();
                continue;
            }

            if ("0".equals(inputData)) {
                break;
            }

            chooseMethodForProcessingNumbers(inputData);
        }

        System.out.println("Goodbye!");
    }

    private static String readData(Scanner scanner) {
        return scanner.nextLine();
    }

    private static boolean isNatural(long number) {
        return number >= 0;
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println();
    }

    private static void printInstructions() {
        System.out.println("Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
        System.out.println();
    }

    private static void processEnteredNumbers(String enteredNumber) {
        long value = checkValidityOfNumber(enteredNumber);
        if (value != -1) {
            Number numberEntity = new Number(value);
            System.out.println(numberEntity.getTextInPropertiesFormat());
            System.out.println();
        }
    }

    private static void processEnteredNumbers(String enteredNumber, String listLength) {
        long value = checkValidityOfNumber(enteredNumber);
        int length = checkValidityOfLength(listLength);

        if (value == -1 || length == -1) {
            return;
        }
        Number numberEntity = new Number(value);

        for (int i = 0; i < length; i++) {
            System.out.println(numberEntity.getTextInPlaneFormat());
            value += 1;
            numberEntity = new Number(value);
        }

    }

    private static void processEnteredNumbers(String enteredNumber, String listLength, List<String> properties) {
        long value = checkValidityOfNumber(enteredNumber);
        int length = checkValidityOfLength(listLength);

        if (value == -1 || length == -1
                || checkMutuallyProperties(properties) == null
                || checkValidityOfProperties(properties) == null) {
            return;
        }

        ArrayList<String> result = new ArrayList<>();
        int countNumbers = 0;
        Number numberEntity = new Number(value);

        while (true) {
            if (countNumbers == length) {
                break;
            }

            boolean hasSpecificProperties = true;

            for (String prop: properties) {
                if (prop.startsWith("-")) {
                    if (numberEntity.hasProperty(prop.substring(1).toLowerCase())) {
                        hasSpecificProperties = false;
                    }
                } else {
                    if (!numberEntity.hasProperty(prop.toLowerCase())) {
                        hasSpecificProperties = false;
                    }
                }
            }

            if (hasSpecificProperties) {
                result.add(numberEntity.getTextInPlaneFormat());
                countNumbers++;
            }

            value += 1;
            numberEntity.setValue(value);
        }

        result.forEach(System.out::println);
    }

    private static void chooseMethodForProcessingNumbers(String enteredNumber) {
        String[] partsOfEnteredNumber = enteredNumber.split(" ");

        if (partsOfEnteredNumber.length == 1) {
            processEnteredNumbers(partsOfEnteredNumber[0]);
        } else if (partsOfEnteredNumber.length == 2) {
            processEnteredNumbers(partsOfEnteredNumber[0], partsOfEnteredNumber[1]);
        } else if (partsOfEnteredNumber.length > 2) {
            String[] properties = Arrays.copyOfRange(partsOfEnteredNumber, 2, partsOfEnteredNumber.length);
            processEnteredNumbers(partsOfEnteredNumber[0], partsOfEnteredNumber[1], Arrays.asList(properties));
        }
    }
    
    private static long checkValidityOfNumber(String enteredNumber) {
        try {
            long value = Long.parseLong(enteredNumber);

            if (!isNatural(value)) {
                printErrorMessage("The first parameter should be a natural number or zero.");
                return -1;
            }
            return value;
        } catch (Exception e) {
            printErrorMessage("The first parameter should be a natural number or zero.");
            return -1;
        }
    }

    private static int checkValidityOfLength(String listLength) {
        int length;

        try {
            length = Integer.parseInt(listLength);
        } catch (NumberFormatException ignored) {
            printErrorMessage("The second parameter should be a natural number.");
            return -1;
        }

        if (length <= 0) {
            printErrorMessage("The second parameter should be a natural number.");
            return -1;
        }

        return length;
    }

    private static List<String> checkMutuallyProperties(List<String> properties) {
        for (ArrayList<String> list: mutuallyExclusiveProperties) {
            if (new HashSet<>(properties).containsAll(list)) {
                String message = String.format("The request contains mutually exclusive properties: %s\n" +
                        "There are no numbers with these properties.", list.stream().map(String::toUpperCase).toList());
                printErrorMessage(message);
                return null;
            }
        }
        return properties;
    }

    private static List<String> checkValidityOfProperties(List<String> properties) {
        ArrayList<String> allIncorrectProperties = new ArrayList<>();

        for (String prop: properties) {
            if (!allTypes.contains(prop.toLowerCase())) {
                allIncorrectProperties.add(prop.toUpperCase());
            }
        }

        if (allIncorrectProperties.size() == 0) {
            return properties;
        }

        String message = String.format("The %s %s %s wrong.\n" +
                "Available properties: %s",
                allIncorrectProperties.size() > 1 ? "properties" : "property",
                allIncorrectProperties.stream().map(String::toUpperCase).toList(),
                allIncorrectProperties.size() > 1 ? "are" : "is",
                allTypes.stream().map(String::toUpperCase).toList());

        printErrorMessage(message);
        return null;
    }
    private static void printErrorMessage(String message) {
        System.out.println(message);
        System.out.println();
    }
}
