package visidia.examples.algo;

import java.util.ArrayList;

import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.process.messages.IntegerMessage;

public class LookUp extends Algorithm {
	private static final long serialVersionUID = -53962930988676811L;

	@SuppressWarnings("deprecation")
	public String getDescription() {
		return "Lookup - Last Build : " + new java.util.Date().getHours()+ ":" + new java.util.Date().getMinutes();
	}

	public Object clone() {
		return new LookUp();

	}

	boolean[] incommingDoor = new boolean[getNetSize() + 1];
	int[] tab = new int[getNetSize() + 1];
	int[] leftLower = new int[getNetSize() + 1];
	ArrayList<Integer> bottom = new ArrayList<Integer>();
	ArrayList<Integer> typeA = new ArrayList<Integer>();
	ArrayList<Integer> typeB = new ArrayList<Integer>();
	ArrayList<Integer> typeC = new ArrayList<Integer>();
	ArrayList<Integer> father = new ArrayList<Integer>();
	
	public void init() {

		int getVoisin = getArity();

		if (getVoisin == 1) //Seul dans la branche, donc forcement au plus bas de l'arbre
			bottom.add(getId());
		if(getArity() > 1) // Pere d'un ou plusieurs fils
			father.add(getId());

		for (int i = 0; i < getVoisin; i++) {
			
			sendTo(i, new IntegerMessage(i));
			setDoorState(new MarkedState(true), i);
			String receive = receiveFrom(i).toString();

			if (receive.equals("1")) {
				putProperty("label", "A");
				typeA.add(getId());
			}
			if (receive.equals("2")) {
				putProperty("label", "B");
				typeB.add(getId());
			}
			if (receive.equals("0")) {
				putProperty("label", "C");
				typeC.add(getId());
			}
			setDoorState(new MarkedState(false), i);
		}
		System.out.println("****VAR****");
		System.out.println("CountA =" + typeA.size() + " CountB =" + typeB.size()+ " CountC=" + typeC.size()+", Integer = "+bottom.size());
		for (Integer integer : bottom) {
			System.out.println("Integer = "+integer);
		}

	}
	public static void main(String[] args) {
		System.out.println("COMPILED");
		return;
	}
}
