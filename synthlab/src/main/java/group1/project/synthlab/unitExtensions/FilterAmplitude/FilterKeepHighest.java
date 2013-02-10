package group1.project.synthlab.unitExtensions.FilterAmplitude;

import group1.project.synthlab.signal.Signal;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitBinaryOperator;
import com.jsyn.unitgen.UnitFilter;

/**
 * @author Groupe 1
 * Compare deux signaux A et B, garde A si B < A ou renvoie 0
 */
public class FilterKeepHighest extends UnitBinaryOperator{


	
	public FilterKeepHighest (){
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputsA = inputA.getValues();
		double[] inputsB = inputB.getValues();
		double[] outputs = output.getValues();

		
		for (int i = start; i < limit; i++) {
			double a = inputsA[i];
			double b = inputsB[i];
			if (a > 0) {
				if (a > b)
					outputs[i] = a;
				else
					outputs[i] = 0;
				System.err.println(a + " - " +b);
			}			
			else {
				if (a < b)
					outputs[i] = a;
				else
					outputs[i] = 0;
			}
				
				
		}
		
	}
	



	
}
