package numbers;

import java.util.ArrayList;
import java.util.List;

public class Number {
    private long value;
    private boolean even;
    private boolean odd;
    private boolean buzz;
    private boolean duck;
    private boolean palindromic;
    private boolean gapful;
    private boolean spy;

    private boolean sunny;

    private boolean square;

    private boolean jumping;

    private boolean happy;

    private boolean sad;
    public Number(long value) {
        this.value = value;
        setEven();
        setBuzz();
        setDuck();
        setOdd();
        setPalindromic();
        setGapful();
        setSpy();
        setSunny();
        setSquare();
        setJumping();
        setHappy();
        setSad();
    }

    private void setHappy() {
        this.happy = isHappy(this.value);
    }

    private void setSad() {
        this.sad = !isHappy(this.value);
    }

    private boolean isHappy(long value) {
        if (value == 1) {
            return true;
        }

        ArrayList<Long> encounteredNumbers = new ArrayList<>();

        while (value != 1) {
            encounteredNumbers.add(value);

            long sumOfAllNumbers = 0;
            String[] digits = String.valueOf(value).split("");

            for (int i = 0; i < digits.length; i++) {
                long digit = Long.parseLong(digits[i]);
                sumOfAllNumbers += Math.pow(digit, 2);

            }

            if (encounteredNumbers.contains(sumOfAllNumbers)) {
                return false;
            }
            value = sumOfAllNumbers;
        }
        return true;
    }

    public void setValue(long value) {
        this.value = value;
        setEven();
        setBuzz();
        setDuck();
        setOdd();
        setPalindromic();
        setGapful();
        setSpy();
        setSunny();
        setSquare();
        setJumping();
        setHappy();
        setSad();
    }

    private void setJumping() {
        String stringValue = String.valueOf(this.value);

        if (stringValue.length() == 1) {
            this.jumping = true;
            return;
        }

        this.jumping = isJumping(this.value);
    }

    private boolean isJumping(long value) {
        String[] digits = String.valueOf(value).split("");

        if (digits.length == 2) {
            int firstDigit = Integer.parseInt(digits[0]);
            int secondDigit = Integer.parseInt(digits[1]);
            return Math.abs(firstDigit - secondDigit) == 1 || Math.abs(secondDigit - firstDigit) == 1;
        }

        boolean result = true;

        for (int i = 1; i < digits.length - 1; i++) {
            int prevDigit = Integer.parseInt(digits[i - 1]);
            int currDigit = Integer.parseInt(digits[i]);
            int nextDigit = Integer.parseInt(digits[i + 1]);

            boolean isFirstPairJumping = Math.abs(prevDigit - currDigit) == 1 || Math.abs(currDigit - prevDigit) == 1;
            boolean isSecondPairJumping = Math.abs(nextDigit - currDigit) == 1 || Math.abs(currDigit - nextDigit) == 1;

            if (!(isFirstPairJumping && isSecondPairJumping)) {
                result = false;
            }
        }

        return result;
    }


    private void setSunny() {
        long value = this.value + 1;
        this.sunny = isPerfectSquareNumber(value);
    }

    private void setSquare() {
        this.square = isPerfectSquareNumber(this.value);
    }

    private boolean isPerfectSquareNumber(long value) {
        double square = Math.sqrt(value);
        return square % 1 == 0;
    }

    private void setGapful() {
        String stringValue = String.valueOf(this.value);

        if (stringValue.length() < 3) {
            this.gapful = false;
            return;
        }

        String firstDigit = stringValue.split("")[0];
        String lastDigit = stringValue.split("")[stringValue.length() -1];
        long remainder = this.value % Long.parseLong(firstDigit + lastDigit);
        this.gapful = remainder == 0;
    }

    private void setEven() {
        this.even = this.value % 2 == 0;
    }


    private void setOdd() {
        this.odd = this.value % 2 != 0;
    }

    private void setBuzz() {
        String stringValue = String.valueOf(this.value);
        long remainder = this.value % 7;
        this.buzz = remainder == 0 || stringValue.charAt(stringValue.length() - 1) == '7';
    }
    private void setDuck() {
        String[] digits = String.valueOf(this.value).split("");

        if (digits.length == 1 && "0".equals(digits[0])) {
            this.duck = false;
            return;
        }

        for (int i = 1; i < digits.length; i++) {
            if ("0".equals(digits[i])) {
                this.duck = true;
                return;
            }
        }

        this.duck = false;
    }

    private void setPalindromic() {
        StringBuilder builder = new StringBuilder(String.valueOf(this.value));
        this.palindromic = builder.toString().equals(builder.reverse().toString());
    }

    private void setSpy() {
        String[] numbers = String.valueOf(this.value).split("");
        long sumOfAllNumbers = 0;
        long productOfAllNumbers = 1;

        for (int i = 0; i < numbers.length; i++) {
            sumOfAllNumbers += Integer.parseInt(numbers[i]);
            productOfAllNumbers *= Integer.parseInt(numbers[i]);
        }

        this.spy = sumOfAllNumbers == productOfAllNumbers;
    }

    boolean hasProperty(String prop) {
        return switch (prop) {
            case "buzz" -> this.buzz;
            case "odd" -> this.odd;
            case "palindromic" -> this.palindromic;
            case "even" -> this.even;
            case "spy" -> this.spy;
            case "duck" -> this.duck;
            case "gapful" -> this.gapful;
            case "sunny" -> this.sunny;
            case "square" -> this.square;
            case "jumping" -> this.jumping;
            case "happy" -> this.happy;
            case "sad" -> this.sad;
            default -> false;
        };
    }
    private String getTextRepresentationOfValue(String stringValue) {

        if (stringValue.length() <= 3) {
            return stringValue;
        }

        StringBuilder representation = new StringBuilder();
        int remainder = stringValue.length() % 3;

        if (remainder != 0) {
            representation.append(stringValue, 0, remainder);
            representation.append(",");
        }

        for (int i = remainder; i < stringValue.length(); i++) {

            if (i + 3 > stringValue.length()) {
                representation.append(stringValue, i, stringValue.length());
                break;
            }

            if (i + 3 == stringValue.length()) {
                representation.append(stringValue, i, stringValue.length());
                break;
            }

            int start = i;
            int end = i + 3;

            representation.append(stringValue, start, end);
            representation.append(",");
            i = end - 1;
        }

        return representation.toString();
    }

    String getTextInPropertiesFormat() {
        return String.format("Properties of %s\n\t\teven: " +
                        "%b\n\t\t odd: %b\n\t\tbuzz: %b\n\t\tduck: %b" +
                        "\n palindromic: %b \n\t  gapful: %b" + "\n\t\t spy: %b"
                        + "\n\t   sunny: %b\n\t  square: %b\n\t jumping: %b" +
                        "\n\t\t sad: %b\n\t   happy: %b",
                getTextRepresentationOfValue(String.valueOf(this.value)), this.even,
                this.odd, this.buzz, this.duck, this.palindromic, this.gapful,
                this.spy, this.sunny, this.square, this.jumping, this.sad, this.happy);
    }

    String getTextInPlaneFormat() {
        List<String> allProperties = getAllProperties();
        StringBuilder builder = new StringBuilder(getTextRepresentationOfValue(String.valueOf(this.value)));
        builder.append(" is ");

        for (int i = 0; i < allProperties.size(); i++) {
            if (i == allProperties.size() - 1) {
                builder.append(allProperties.get(i));
            } else {
                builder.append(allProperties.get(i));
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    private List<String> getAllProperties() {
        ArrayList<String> properties = new ArrayList<>();

        if (this.buzz) {
            properties.add("buzz");
        }

        if (this.duck) {
            properties.add("duck");
        }

        if (this.palindromic) {
            properties.add("palindromic");
        }

        if (this.gapful) {
            properties.add("gapful");
        }

        if (this.even) {
            properties.add("even");
        }

        if (this.odd) {
            properties.add("odd");
        }

        if (this.spy) {
            properties.add("spy");
        }

        if (this.square) {
            properties.add("square");
        }

        if (this.sunny) {
            properties.add("sunny");
        }

        if (this.jumping) {
            properties.add("jumping");
        }

        if (this.sad) {
            properties.add("sad");
        }

        if (this.happy) {
            properties.add("happy");
        }

        return properties;
    }

}
