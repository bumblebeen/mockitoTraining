public class MathApplication {

    MathApplication(){
    }

    MathApplication (CalculatorService calcService){
        this.calcService = calcService;
    }

    private CalculatorService calcService;

    public void setCalculatorService(CalculatorService calcService){
        this.calcService = calcService;
    }

    public double add(double input1, double input2){
        return calcService.add(input1, input2);
    }

    public double subtract(double input1, double input2){
        return calcService.subtract(input1, input2);
    }

    public double multiply(double input1, double input2){
        return calcService.multiply(input1, input2);
    }

    public double divide(double input1, double input2){
        return calcService.divide(input1, input2);
    }

    public int square(int input1){
        return calcService.square(input1);
    }

    public int plusOneThenSquare(int input1){
        return calcService.square(input1 + 1);
    }
}