package java_files;

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy {
	
	//Inner class for nodes
	public class TariffNode{
		private Tariff tariff;
		private TariffNode next;
		public TariffNode() {
			this.tariff=null;
			this.next=null;
		}
		
		//Constructors
		public TariffNode(Tariff tariff, TariffNode next) {
			this.tariff = tariff;
			this.next = next;
		}
		public TariffNode(TariffNode obj) {
		    if (obj != null) {
		        this.tariff = new Tariff(obj.tariff); 
		        this.next = (obj.next != null) ? new TariffNode(obj.next) : null;
		    }
		}
		
		//Clone method
		public TariffNode clone() {
			return new TariffNode(this);
		}
		
		//equals
		public boolean equals(TariffNode node) {
		    if (node == null) 
		    	return false;

		    boolean tariffsEqual = (this.tariff == null && node.tariff == null) ||
		                           (this.tariff != null && this.tariff.equals(node.tariff));

		    boolean nextEqual = (this.next == null && node.next == null) ||
		                        (this.next != null && this.next.equals(node.next));

		    return tariffsEqual && nextEqual;
		}
		
		//Getters and Setters
		public Tariff getTariff() {
			return tariff;
		}
		public void setTariff(Tariff tariff) {
			this.tariff = tariff;
		}
		public TariffNode getNext() {
			return next;
		}
		public void setNext(TariffNode next) {
			this.next = next;
		}
		
	}
	
	//Attributes for the Linkedlist
	private TariffNode head;
	private int size;
	
	//Constructors
	public TariffList() {
		head = null;
		size = 0;
	}
	
	public TariffList(TariffList obj) {
		this.head = obj.head;
		this.size = obj.size;
	}
	
	//Add new product to the beginning of the list
	public void addToStart(Tariff t) {
		TariffNode tariff = new TariffNode(t,null);
		tariff.next = head;
		head = tariff;
		size++;
	}
	
	//Add a new product at a certain index
	public void insertAtIndex(Tariff newTariff, int index) throws NoSuchElementException{
		//check if index is valid
		if(index < 0 || index >= this.size) {
			throw new NoSuchElementException("Invalid index: " + index);
		}
		//if inserting as first element use addToStart()
		if(index == 0) {
			this.addToStart(newTariff);
		}
		//Other cases
		else {
			TariffNode newNode = new TariffNode(newTariff,null);
		TariffNode position = head;
		for(int j = 0; j < index-1; j++) {
			position = position.next;
		}
		newNode.next = position.next;
		position.next = newNode;
		}
		size++;
	}
	
	//Remove an element from a certain index
	public void removeFromIndex(int index) throws NoSuchElementException{
		//check if index is valid
		if(index < 0 || index >= size) {
			throw new NoSuchElementException("Invalid index: " + index);
		}
		//If only one element
		TariffNode position = head;
		if(position.next == null) {
			head = null;
		}
		//Other cases
		else {
			for(int j = 0; j < index-1; j++) {
			position = position.next;
		}
		position.next = position.next.next;
		position.next.next = null;
		}
		size--;
	}
	
	//Remove the first product
	public void deleteFromStart() {
		if (head == null) {
			return;
		}
		TariffNode temporary = head;
		head = head.next;
		temporary.next = null;
		size--;
	}
	
	//Replace a product at a certain index
	public void replaceAtIndex(Tariff newTariff, int index) {
		//check if index is valid
		if(index < 0 || index >= size) {
			return;
		}
		TariffNode position = head;
		for(int j = 0; j < index; ) {
			position = position.next;
		}
		position.setTariff(newTariff);
	}
	
	//Find a certain product in the linked list using its information
	public TariffNode find(String origin, String destination, String category) {
		if (head == null) {
			return null;
		}
		TariffNode position = head;
		for(int i = 0; i < size; i++) {
			if(position.getTariff().getOriginCountry().equals(origin) && 
			   position.getTariff().getDestinationCountry().equals(destination) && 
			   position.getTariff().getProductCategory().equals(category) ) {
				System.out.println(i+" iterations were made.");
				return position;
				
			}
			position = position.next;
			
			
		}
		return null;
	}
	
	//Check if the list contains a certain product
	public boolean contains(String origin, String destination, String category) {
		if (head == null) {
			return false;
		}
		TariffNode position = head;
		for(int i = 0; i < size; i++) {
			if(position.getTariff().getOriginCountry().equals(origin) && 
			   position.getTariff().getDestinationCountry().equals(destination) && 
			   position.getTariff().getProductCategory().equals(category) ) {
				return true;
				
			}
			position = position.next;
			
			
		}
		return false;
	}
	
	//equals
	public boolean equals(TariffList list) {
		return this.head.equals(list.head) && this.size==list.size;
		
	}
	
	
	//Getters and Setters
	public TariffNode getHead() {
		return head;
	}

	public void setHead(TariffNode head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	//Implemented class from the interface, status of a trade request
	@Override
	public String evaluateTrade(double proposedTariff, double minimumTariff) {
		// Trade Request Accepted
	    if (proposedTariff >= minimumTariff) {
	        return "Trade request accepted.";
	    }
	    
	    double tariffDifference = minimumTariff - proposedTariff;
	    if (tariffDifference <= minimumTariff * 0.20) {
	        return "Trade request conditionally accepted.";
	    }
	    
	    return "Trade request rejected.";
	}
	

}
