package mss.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.RealExpr;

import mss.marco.Marco;

public class TestMarco {

	public static void testMarcoPaper(){
		System.out.println("\n\nThis example comes from the MARCO paper");
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		BoolExpr a = ctx.mkBoolConst("a");
		BoolExpr b = ctx.mkBoolConst("b");
		BoolExpr[] C = {a, ctx.mkOr(ctx.mkNot(a),b), ctx.mkNot(a), ctx.mkNot(b) }; 
		Marco marco = new Marco(ctx, null);
		ArrayList<Set<Integer>> allMSS = marco.run(C);
		Marco.printAllMSSs(C,allMSS);
	}
	
	public static void testNikolaj(){
		System.out.println("\n\nThis example is from Nikolaj");
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		// constraints = [x > 2, x < 1, x < 0, Or(x + y > 0, y < 0), Or(y >= 0, x >= 0), 
		// Or(y < 0, x < 0), Or(y > 0, x < 0)]
		RealExpr x = ctx.mkRealConst("x");
		RealExpr y = ctx.mkRealConst("y");
		RealExpr ZERO = ctx.mkReal(0);
		ArrayList<BoolExpr> constraints = new ArrayList<BoolExpr>();
		constraints.add(ctx.mkGt(x, ctx.mkReal(2))); // x > 2
		constraints.add(ctx.mkLt(x, ctx.mkReal(1))); // x < 1
		constraints.add(ctx.mkLt(x, ZERO)); // x < 0
		constraints.add(ctx.mkOr(ctx.mkGt(ctx.mkAdd(x,y),ZERO), // x + y > 0
				ctx.mkLt(y, ZERO) // y < 0
				));
		constraints.add(ctx.mkOr(ctx.mkGe(y, ZERO), ctx.mkGe(x, ZERO))); // Or(y >= 0, x >= 0) 
		constraints.add(ctx.mkOr(ctx.mkLt(y, ZERO), ctx.mkLt(x, ZERO))); // Or(y < 0, x < 0) 
		constraints.add(ctx.mkOr(ctx.mkGt(y, ZERO), ctx.mkLt(x, ZERO))); // Or(y > 0, x < 0)
		BoolExpr C[] = constraints.toArray(new BoolExpr[1]);
		Marco marco = new Marco(ctx, null);
		ArrayList<Set<Integer>> allMSS = marco.run(C);
		Marco.printAllMSSs(C,allMSS);
	}
	
	public static void main (String[] args){
		testMarcoPaper();
		testNikolaj();
	}
	
	
}
