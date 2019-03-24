
public class Stack {
	private String data;
	
	public Stack()
	{
		data = new String();
	}
	
	public boolean isEmpty()
	{
		return data.isEmpty();
	}
	
	public void push(String str)
	{
		data = data + "{" + str + "}";
	}
	
	public String front()
	{
		if(!data.isEmpty())
		{
			String data2 = new String();
			data2 = data.substring(0);
			String ch = new String();
			do
			{
				ch = data2.substring(data2.length()-1, data2.length()) + ch;
				data2 = data2.substring(0, data2.length()-1);
			}while(!ch.substring(0, 1).equals("{"));
			
			return data.substring(data.length() - ch.length() + 1, data.length() - 1);
		}
		else
			return null;
	}
	
	public void pop()
	{
		if(!data.isEmpty())
		{
			String data2 = new String();
			data2 = data.substring(0);
			String ch = new String();
			do
			{
				ch = data2.substring(data2.length()-1, data2.length()) + ch;
				data2 = data2.substring(0, data2.length()-1);
			}while(!ch.substring(0, 1).equals("{"));
			
			data = data.substring(0, data.length() - ch.length());
		}
		
	}
}
