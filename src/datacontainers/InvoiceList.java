package datacontainers;

public class InvoiceList {
	
	public InvoiceListNode start;
	public InvoiceListNode end;
	public int size=0;
	

    public void clear() {
    	 start = null;
    	 size = 0;
    }

    public void addToStart(Invoice t) {
    	InvoiceListNode aNode = new InvoiceListNode(t);
    	size++ ;
    	if(start == null){
    		start = aNode;
    		end = start;
    	}
    	else {
    		aNode.setNext(start);
    		start = aNode;
    	}
    }

    public void addToEnd(Invoice t) {
    	InvoiceListNode aNode = new InvoiceListNode(t);
    	size++ ;
    	if(start == null){
    		start = aNode;
    		end = start;
    	}
    	else {
    		end.setNext(aNode);
    		end = aNode;
    	}

    }

    public void remove(int position) {
    	if(position == size && size == 1){
    		this.clear();
    		return;
    	}
    	else if(position<1 || position>size){
    		throw new IndexOutOfBoundsException("Index out of bounds");
    	}
    	InvoiceListNode currentNode = start;
    	for(int i = 1; i<position-1; i++){
    		if(currentNode.getNext() == null)
    			return;
    		currentNode = currentNode.getNext();
    	}
    	InvoiceListNode nodeToBeRemoved = currentNode.getNext();
    	InvoiceListNode nextNode = nodeToBeRemoved.getNext();
    	currentNode.setNext(nextNode);
    	end = nextNode;
    	size--;
    }
    
    private InvoiceListNode getInvoiceListNode(int position) {
    	InvoiceListNode headNode = start;
    	
    	if(position<1 || position>size){
    		throw new IndexOutOfBoundsException("Index out of bounds");
    	}
    	for(int i = 1; i<position; i++) {
    		if(headNode.getNext() == null){
    			return null;
    		}
    		headNode = headNode.getNext();
    	}
    	return headNode;
    }
    
    public Invoice getInvoice(int position) {
    	InvoiceListNode headNode = start;
    	
    	if(position<1 || position>size){
    		throw new IndexOutOfBoundsException("Index out of bounds");
    	}
    	for(int i = 1; i<position; i++) {
    		if(headNode.getNext() == null){
    			return null;
    		}
    		headNode = headNode.getNext();
    	}
    	return headNode.getInvoice();
  	
    }

    public void print() {
    	InvoiceListNode currentNode = start; 
    	//System.out.println(currentNode.getInvoice().toString());
    	while(currentNode != null){
    		System.out.println(currentNode.getInvoice().toString());
    		currentNode= currentNode.getNext();
    	}
    }

}
