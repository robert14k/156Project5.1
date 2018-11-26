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
    
    public void add(Invoice t) {
    	InvoiceListNode aNode = new InvoiceListNode(t);
    	double aCost, iCost;
    	aCost = t.getProductsSubtotal()-t.getProuductsDiscount(t.getProductsSubtotal());
    	if(size == 0) {
    		addToStart(t);
    		return;
    	}
    	
    	InvoiceListNode currentNode = start;
    	for(int i=1; i<size; i++) {
    		iCost = currentNode.getInvoice().getProductsSubtotal() - currentNode.getInvoice().getProuductsDiscount(currentNode.getInvoice().getProductsSubtotal());
    		if(iCost <= aCost) {
    			//add the invoice here 
    			size++;
    			if(currentNode == start) {
    				addToStart(t);
    				return;
    			}else if(currentNode == end) {
    				addToEnd(t);
    				return;
    			}
    			InvoiceListNode previousNode = getInvoiceListNode(i-1);
    			previousNode.setNext(aNode);
    			aNode.setNext(currentNode);
    			return;
    		}else {
    			//move currnet node to next 
    			currentNode = currentNode.getNext();
    		}
    	}
    	addToEnd(t);
    	return;
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

}
