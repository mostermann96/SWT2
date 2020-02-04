public class CalculatorImpl implements Calculator {
    public int add(int x, int y) {
        return x+y;
    }
    public int subtract(int x, int y) {
        return x-y;
    }
    public int divide(int x, int y) {
        if (!zero(y)) {
            return x / y;
        }
        return 0;
    }
    public boolean zero(int y){
        return y != 0;
    }
    public String Hello(){
        return "Привет";
    }
}
