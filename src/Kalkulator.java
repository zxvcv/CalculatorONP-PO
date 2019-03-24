
public class Kalkulator {
	public static void main(String[] args) {
		System.out.println("hello!\n");
		
		String dzialanie = "-(-3)-(-1)+1=";
		
		System.out.print(dzialanie + " ");
		String wynik = Calculator.ONP_translate(dzialanie);
		//System.out.println(wynik); //wyswietlanie dzialania w postaci ONP
		wynik = Calculator.calculate(wynik);
		System.out.println(wynik);
		
		System.out.println("\nbye!");
	}

}
