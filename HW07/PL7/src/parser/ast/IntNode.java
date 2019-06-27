package parser.ast;

public class IntNode implements Node {
	private  Integer value;

	public  IntNode(String text){
		this.value = new Integer(text);
	}
	@Override
	public String toString(){
		return "INT: " + Integer.toString(value);
	}
}
