package gp.objects;

import java.util.Comparator;

public class ValueComparatorAsc implements Comparator<GenericObject> {

	 public int compare(GenericObject o1, GenericObject o2) {
	        return new Double(o1.getValue1()).compareTo(new Double(o2.getValue1()));
	    }
	
	
}
