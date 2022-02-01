package servlet;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.soap.ws.client.Calculator;
import com.soap.ws.client.CalculatorSoap;
import com.soap.ws.client.NumberConversion;
import com.soap.ws.client.NumberConversionSoapType;

public class NumberConvertor {
public NumberConvertor() {}

	public static void main(String[] args) {
		//int luku = 5;
		//Calculator Calc_service = new Calculator();
		//CalculatorSoap Calc_serviveSoap = Calc_service.getCalculatorSoap();
		//int luku2 = Calc_serviveSoap.multiply(luku, luku);
        //System.out.println(luku2);
	}
	
    
	public String wealth(String inputStr, String inputStr2) {
		
		int keskitalous = 56516;
		int i = Integer.parseInt(inputStr);
		int j = Integer.parseInt(inputStr2);
		
		int luku2 = j + i;
		
		String a = "";
		int b;
		if (keskitalous < luku2) {
			b = luku2 - keskitalous;
			a = " is over median household income (by +$"+b+")";
		}
		else {
			b = keskitalous - luku2;
			a =" is under median household income (by -$"+b+")";
		}
		
		// NOTE: For some reason I just couldn't get the calculator working
		//Calculator Calc_service = new Calculator();
		
		// NOTE: This following line threw very odd errors
		//CalculatorSoap Calc_serviveSoap = Calc_service.getCalculatorSoap();
		//int luku2 = Calc_serviveSoap.multiply(luku, luku);
		
		BigDecimal input_D = new BigDecimal(luku2);
		NumberConversion NC_service = new NumberConversion();
        NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap();
        String result = NC_serviceSOAP.numberToDollars(input_D);
        String f_result = result + a;
        return f_result;
	}

	public String pension(String value1, String value2) {
		
		int i = Integer.parseInt(value1);
		int j = Integer.parseInt(value2);
		
		int sum = i + j;
		int lifetime = sum * 30;
		double pension = lifetime * 0.015 / 12;
		
		BigDecimal input_D = new BigDecimal(pension);
		NumberConversion NC_service = new NumberConversion(); //created service object
        NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
        String result = NC_serviceSOAP.numberToDollars(input_D);
        String f_result = result + " per month";
		
		return f_result;
	}

}
